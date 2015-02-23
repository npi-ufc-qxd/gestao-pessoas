package ufc.quixada.npi.gp.controller;

import java.util.List;

import javax.inject.Inject;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ufc.quixada.npi.gp.model.Estagiario;
import ufc.quixada.npi.gp.model.FiltroJson;
import ufc.quixada.npi.gp.model.Frequencia;
import ufc.quixada.npi.gp.model.Turma;
import ufc.quixada.npi.gp.model.enums.StatusFrequencia;
import ufc.quixada.npi.gp.service.EstagiarioService;
import ufc.quixada.npi.gp.service.FrequenciaService;
import ufc.quixada.npi.gp.service.TurmaService;
import ufc.quixada.npi.gp.utils.UtilGestao;

@Controller
@RequestMapping("frequencia")
public class FrequenciaController {

	@Inject
	private EstagiarioService serviceEstagiario;
	
	private JRDataSource jrDatasource;

	@Inject
	private TurmaService serviceTurma;

	@Inject
	private FrequenciaService serviceFrequencia;

	@RequestMapping(value = "/frequencias", method = RequestMethod.GET)
	public String frequencia(ModelMap modelMap) {
		modelMap.addAttribute("frequencias", serviceFrequencia.find(Frequencia.class));
		return "coordenador/list-frequencias";
	}	

	@RequestMapping(value = "/frequencias.json", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<Frequencia> getFrequencias(@RequestBody FiltroJson frequenciaJson, Model model) {
		List<Frequencia> frequencias = serviceFrequencia.getFrequencias(frequenciaJson.getData(), serviceTurma.find(Turma.class, frequenciaJson.getTurma()));
		return frequencias;
	}

	@RequestMapping(value = "{idEstagiario}/frequenciaIndividual", method = RequestMethod.GET)
	public String frequenciaIndividual( ModelMap model, @PathVariable("idEstagiario") Long idEstagiario) throws JRException {
		Estagiario estagiario = serviceEstagiario.find(Estagiario.class, idEstagiario);
		jrDatasource = new JRBeanCollectionDataSource(estagiario.getFrequencias());
		
		model.addAttribute("datasource", jrDatasource);
		model.addAttribute("format", "pdf");
		return "estagio";
	}

	@RequestMapping(value = "/observacao", method = RequestMethod.POST)
	public String frequenciaObservar(@RequestParam("pk") Long idFrequencia, @RequestParam("value") String observacao, Model model) {
		Frequencia frequencia = serviceFrequencia.find(Frequencia.class, idFrequencia);
		frequencia.setObservacao(observacao);

		serviceFrequencia.update(frequencia);
		
		return "coordenador/list-frequencias";
	}
	
	@RequestMapping(value = "/atualizarStatus", method = RequestMethod.POST)
	public String atualizarStatus(@RequestParam("pk") Long idFrequencia, @RequestParam("value") StatusFrequencia status, Model model, RedirectAttributes redirectAttributes) {
		Frequencia frequencia = serviceFrequencia.find(Frequencia.class, idFrequencia);
//		boolean horarioDeTrabalho = UtilGestao.isHoraPermitida(frequencia.getTurma().getHoraInicio(), frequencia.getTurma().getHoraFinal());
		boolean horarioDeTrabalho = UtilGestao.isHoraPermitida(frequencia.getTurma().getHorarios());
		
		if(!frequencia.getStatusFrequencia().equals(StatusFrequencia.FALTA) && horarioDeTrabalho){
			frequencia.setStatusFrequencia(StatusFrequencia.ATRASADO);
			serviceFrequencia.update(frequencia);
			redirectAttributes.addAttribute("info", "Alteração efetuada com sucesso.");
		}else{
			redirectAttributes.addAttribute("info", "Alteração não permitida.");
		}
		return "coordenador/list-frequencias";
	}

}

