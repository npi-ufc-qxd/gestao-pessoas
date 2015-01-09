package ufc.quixada.npi.gp.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.joda.time.LocalTime;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ufc.quixada.npi.gp.model.Estagiario;
import ufc.quixada.npi.gp.model.Filtro;
import ufc.quixada.npi.gp.model.Frequencia;
import ufc.quixada.npi.gp.model.FrequenciaJson;
import ufc.quixada.npi.gp.model.Periodo;
import ufc.quixada.npi.gp.model.Turma;
import ufc.quixada.npi.gp.model.enums.StatusFrequencia;
import ufc.quixada.npi.gp.service.EstagiarioService;
import ufc.quixada.npi.gp.service.FrequenciaService;
import ufc.quixada.npi.gp.service.PeriodoService;
import ufc.quixada.npi.gp.service.TurmaService;

@Controller
@RequestMapping("frequencia")
public class FrequenciaController {
	@Inject
	private EstagiarioService serviceEstagiario;
	
	private JRDataSource jrDatasource;

	@Inject
	private PeriodoService servicePeriodo;

	@Inject
	private TurmaService serviceTurma;

	@Inject
	private FrequenciaService serviceFrequencia;


	@RequestMapping(value = {"/", ""}, method = RequestMethod.GET)
	public String frequencia(ModelMap modelMap) {
		modelMap.addAttribute("filtro", new Filtro());
		return "frequencia/frequencia";
	}	

	@RequestMapping(value = "/buscarEstagiarios", method = RequestMethod.POST)
	public String frequenciaEstagiarios(ModelMap modelMap, @ModelAttribute("filtro") Filtro filtro) {
		Periodo periodo = servicePeriodo.getPeriodo(filtro.getAno(), filtro.getSemestre());
		List<Estagiario> estagiarios = new ArrayList<Estagiario>();
		if(periodo != null){
			if(periodo.getTurmas() != null){
				for (Turma turma : periodo.getTurmas()) {
					estagiarios.addAll(turma.getEstagiarios());
				}
				modelMap.addAttribute("frequencias", estagiarios.get(0).getFrequencias());
				modelMap.addAttribute("turmas", periodo.getTurmas());
			}
		}

		modelMap.addAttribute("filtro", filtro);
		return "frequencia/frequencia";
	}
	@RequestMapping(value = "/observacao", method = RequestMethod.POST)
	public String frequenciaObservar(@RequestParam("pk") Long idFrequencia, @RequestParam("value") String observacao, Model model) {
		Frequencia frequencia = serviceFrequencia.find(Frequencia.class, idFrequencia);
		frequencia.setObservacao(observacao);

		serviceFrequencia.update(frequencia);
		model.addAttribute("filtro", new Filtro());
		
		return "frequencia/frequencia";
	}
	
	@RequestMapping(value = "/atualizarStatus", method = RequestMethod.POST)
	public String atualizarStatus(@RequestParam("pk") Long idFrequencia, @RequestParam("value") StatusFrequencia status, Model model, RedirectAttributes redirectAttributes) {
		Frequencia frequencia = serviceFrequencia.find(Frequencia.class, idFrequencia);
		boolean horarioDeTrabalho = isHoraPermitida(frequencia.getTurma().getHoraInicio(), frequencia.getTurma().getHoraFinal());
		
		if(!frequencia.getStatusFrequencia().equals(StatusFrequencia.FALTA) && horarioDeTrabalho){
			frequencia.setStatusFrequencia(StatusFrequencia.ATRASADO);
			serviceFrequencia.update(frequencia);
			redirectAttributes.addAttribute("info", "Alteração efetuada com sucesso.");
		}else{
			redirectAttributes.addAttribute("info", "Alteração não permitida.");
		}
		model.addAttribute("filtro", new Filtro());
		return "frequencia/frequencia";
	}	
	

	@RequestMapping(value = "{idEstagiario}/frequenciaIndividual", method = RequestMethod.GET)
	public String frequenciaIndividual( ModelMap model, @PathVariable("idEstagiario") Long idEstagiario) throws JRException {

		Estagiario estagiario = serviceEstagiario.find(Estagiario.class, idEstagiario);

		//filtrar os estagiarios
		//jrDatasource = new JRBeanCollectionDataSource(frequencias);
		jrDatasource = new JRBeanCollectionDataSource(estagiario.getFrequencias());
		
		model.addAttribute("datasource", jrDatasource);
		model.addAttribute("format", "pdf");
		return "estagio";
	}

	/*
	 * Parte Critica
	 */

	@RequestMapping(value = "/frequencias")
	public String listar(Model model) {
		List<Turma> turmas = new ArrayList<Turma>();
		model.addAttribute("turmas", turmas);
		return "frequencia/listar";
	}
	
	@RequestMapping(value = "/frequencias.json", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<Frequencia> getFrequencias(@RequestBody FrequenciaJson frequenciaJson, Model model) {
		List<Frequencia> frequencias = serviceFrequencia.getFrequencias(frequenciaJson.getData(), serviceTurma.find(Turma.class, frequenciaJson.getTurma()));
		
		for (Frequencia frequencia : frequencias) {
			frequencia.setTurma(null);
			frequencia.setEstagiario(null);
		}
		

		return frequencias;//serviceFrequencia.getFrequencias(frequenciaJson.getData(), serviceTurma.find(Turma.class, frequenciaJson.getTurma()));
	}

	@RequestMapping(value = "/turmas.json", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<Turma> getTurmas(@RequestBody FrequenciaJson frequenciaJson, Model model) {
		List<Turma> turmas = serviceTurma.getTurmaPeriodo(frequenciaJson.getAno(), frequenciaJson.getSemestre());
		
//		if(frequenciaJson.getAno() != null && frequenciaJson.getSemestre()!= null){
//			Periodo periodo = servicePeriodo.getPeriodo(frequenciaJson.getAno(), frequenciaJson.getSemestre());
//
//			
//			
//			for (Turma turma : periodo.getTurmas()) {
////				turma.setEstagiarios(null);
////				turma.setFrequencias(null);
////				turma.setPeriodo(null);
////				turma.setSupervisor(null);
//			}
//		}
//		
		return turmas;
	}
	@RequestMapping(value = "/turmasjson", method = RequestMethod.POST)//, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public String getTurmasF5(@RequestBody FrequenciaJson frequenciaJson, Model model) {
		Periodo periodo = servicePeriodo.getPeriodo(frequenciaJson.getAno(), frequenciaJson.getSemestre());
		
		
		List<Turma> turmas = new ArrayList<Turma>();
		if(periodo != null){
	
			for (Turma turma : periodo.getTurmas()) {
				turma.setEstagiarios(null);
				turma.setFrequencias(null);
				turma.setPeriodo(null);
				turma.setSupervisor(null);
				turmas.add(turma);
			}
		}
		
		model.addAttribute("turmas", turmas);
		return "frequencia/listar";
	}
	
	//METODOS
	private boolean isHoraPermitida(Date horaInicio, Date horaFinal) {
		LocalTime inicio = new LocalTime(horaInicio);
		LocalTime fim = new LocalTime(horaFinal);
		LocalTime horaAtual = new LocalTime();
		return (horaAtual.equals(inicio) || horaAtual.isAfter(inicio)) && (horaAtual.equals(fim) || horaAtual.isBefore(fim));
	}

	
}

