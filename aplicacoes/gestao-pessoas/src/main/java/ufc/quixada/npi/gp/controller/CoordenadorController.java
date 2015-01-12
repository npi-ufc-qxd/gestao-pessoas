package ufc.quixada.npi.gp.controller;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ufc.quixada.npi.gp.model.Estagiario;
import ufc.quixada.npi.gp.model.Filtro;
import ufc.quixada.npi.gp.model.Frequencia;
import ufc.quixada.npi.gp.model.FrequenciaJson;
import ufc.quixada.npi.gp.model.JsonTurma;
import ufc.quixada.npi.gp.model.Periodo;
import ufc.quixada.npi.gp.model.Projeto;
import ufc.quixada.npi.gp.model.Turma;
import ufc.quixada.npi.gp.service.EstagiarioService;
import ufc.quixada.npi.gp.service.GenericService;
import ufc.quixada.npi.gp.service.PeriodoService;
import ufc.quixada.npi.gp.service.TurmaService;

@Component
@Controller
@RequestMapping("coordenador")
public class CoordenadorController {

	@Inject
	private EstagiarioService serviceEstagiario;
	
	private JRDataSource jrDatasource;

	@Inject
	private GenericService<Projeto> serviceProjeto;

	@Inject
	private PeriodoService servicePeriodo;
	
	@Inject
	private TurmaService serviceTurma;	

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(ModelMap modelMap, HttpSession session) {
		return "coordenador/inicial";
	}

	@RequestMapping(value = "/inicial")
	public String inicial(ModelMap modelMap, HttpSession session) throws JRException {

		modelMap.addAttribute("usuario", SecurityContextHolder.getContext().getAuthentication().getName());

		modelMap.addAttribute("estagiarios", serviceEstagiario.find(Estagiario.class));

		return "redirect:/coordenador/inicial";
	}

	@RequestMapping(value = "/estagiarios", method = RequestMethod.GET)
	public String listaEstagiarios(ModelMap modelMap, HttpSession session) {
		modelMap.addAttribute("usuario", SecurityContextHolder.getContext().getAuthentication().getName());
		modelMap.addAttribute("filtro", new Filtro());
		return "coordenador/estagiarios";
	}

	@RequestMapping(value = "/turmasPeriodo", method = RequestMethod.POST)
	public String listaTurmas(ModelMap modelMap, HttpSession session, Filtro filtro) {
		
		List<Turma> turmas = serviceTurma.getTurmaPeriodo(filtro.getAno(), filtro.getSemestre());
		modelMap.addAttribute("turmas", turmas);

		return "coordenador/estagiarios";
	}
	
	
	@RequestMapping(value = "/turmasEstagiarios", method = RequestMethod.POST)
	public String listaEstagiarios(ModelMap modelMap, HttpSession session, @ModelAttribute("filtro") Filtro filtro) {
		modelMap.addAttribute("usuario", SecurityContextHolder.getContext().getAuthentication().getName());

		Periodo periodo = servicePeriodo.getPeriodo(filtro.getAno(), filtro.getSemestre());
		List<Estagiario> estagiarios = new ArrayList<Estagiario>();
		if(periodo != null){
			if(periodo.getTurmas() != null){
				for (Turma turma : periodo.getTurmas()) {
					estagiarios.addAll(turma.getEstagiarios());
				}
				modelMap.addAttribute("estagiarios", estagiarios);
			}
		}
		modelMap.addAttribute("filtro", filtro);
		return "coordenador/estagiarios";
	}

	@RequestMapping(value = "/es", method = RequestMethod.POST)
//	@RequestMapping(value = "/es", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)	
	public String estagiarios(ModelMap modelMap, Filtro filtro) {
		modelMap.addAttribute("usuario", SecurityContextHolder.getContext().getAuthentication().getName());

		Periodo periodo = servicePeriodo.getPeriodo(filtro.getAno(), filtro.getSemestre());
		List<Estagiario> estagiarios = new ArrayList<Estagiario>();
		if(periodo != null){
			if(periodo.getTurmas() != null){
				for (Turma turma : periodo.getTurmas()) {
					estagiarios.addAll(turma.getEstagiarios());
				}
				modelMap.addAttribute("estagiarios", estagiarios);
			}
		}
		modelMap.addAttribute("filtro", filtro);
		return "coordenador/estagiarios";
/*
 * 	
	@ResponseBody
	public List<Frequencia> getFrequencias(@RequestBody FrequenciaJson frequenciaJson, Model model) {
		List<Frequencia> frequencias = serviceFrequencia.getFrequencias(frequenciaJson.getData(), serviceTurma.find(Turma.class, frequenciaJson.getTurma()));

		return frequencias;//serviceFrequencia.getFrequencias(frequenciaJson.getData(), serviceTurma.find(Turma.class, frequenciaJson.getTurma()));
	}

 * 
 */
	
	
	}
	
	
	@RequestMapping(value = "/jrreport", method = RequestMethod.GET)
	public String printWelcome(ModelMap model) throws JRException {
		//filtrar os estagiarios
		jrDatasource = new JRBeanCollectionDataSource(serviceEstagiario.find(Estagiario.class));
		
		model.addAttribute("datasource", jrDatasource);
		model.addAttribute("format", "pdf");
		return "multiViewReport";
	}
	
	@RequestMapping(value = "/declaracaoEstagio", method = RequestMethod.GET)
	public String declaracaoEstagio( ModelMap model) throws JRException {
		
		//filtrar os estagiarios
		jrDatasource = new JRBeanCollectionDataSource(serviceEstagiario.find(Estagiario.class));
		
		model.addAttribute("datasource", jrDatasource);
		model.addAttribute("format", "pdf");
		return "declaracaoEstagioIndividual";
	}

	/*
	 * PROJETO
	 */
	@RequestMapping(value = "/projetos", method = RequestMethod.GET)
	public String listarProjetos( ModelMap model) {
		model.addAttribute("projetos", serviceProjeto.find(Projeto.class));
		return "/coordenador/projetos";
	}

	@RequestMapping(value = "/projeto", method = RequestMethod.GET)
	public String adicionarProjeto( ModelMap model) {
		model.addAttribute("projeto", new Projeto());
		return "coordenador/formProjeto";
	}

	@RequestMapping(value = "/projeto", method = RequestMethod.POST)
	public String adicionarProjeto(ModelMap model, @Valid @ModelAttribute("projeto") Projeto projeto, BindingResult result) {
		
		if (result.hasErrors()) {
			return "coordenador/formProjeto";
		}
		
		if(projeto.getId() == null)
			serviceProjeto.save(projeto);
		else{
			projeto = atualizarProjeto(projeto);
			serviceProjeto.update(projeto);
		}
		return "redirect:/coordenador/projetos";
	}

	@RequestMapping(value = "/projeto/{idProjeto}/editar", method = RequestMethod.GET)
	public String editarProjeto(@PathVariable("idProjeto") Long idProjeto, ModelMap model) {
		model.addAttribute("projeto", serviceProjeto.find(Projeto.class, idProjeto));
		return "coordenador/formProjeto";
	}

	@RequestMapping(value = "/projeto/{idProjeto}/detalhes", method = RequestMethod.GET)
	public String detalhesProjeto(@PathVariable("idProjeto") Long idProjeto, ModelMap model) {
		Projeto projeto = serviceProjeto.find(Projeto.class, idProjeto);
		model.addAttribute("projeto", projeto);
		return "coordenador/detalheProjeto";
	}

	@RequestMapping(value = "/projeto/{idProjeto}/excluir", method = RequestMethod.GET)
	public String excluirProjeto(@PathVariable("idProjeto") Long idProjeto, ModelMap model) {
		Projeto projeto = serviceProjeto.find(Projeto.class, idProjeto);
		
		if(projeto != null){
			serviceProjeto.delete(projeto);
		}
		
		return "redirect:/coordenador/projetos";
	}

	@RequestMapping(value = "/{idProjeto}/vincularMembros", method = RequestMethod.GET)
	public String vincularMembrosProjeto(ModelMap model, @PathVariable("idProjeto") Long idProjeto) {
		
		model.addAttribute("projeto", serviceProjeto.find(Projeto.class, idProjeto));
		model.addAttribute("filtro", new Filtro());
		
		return "coordenador/vincularMembros";
	}

	@RequestMapping(value = "/vincula", method = RequestMethod.GET)
	public String vincularMembros(ModelMap model) {
		
		model.addAttribute("projeto", serviceProjeto.find(Projeto.class, 4L));
		Periodo periodo = servicePeriodo.getPeriodo(2015, "2");
		model.addAttribute("periodo", periodo);

		return "coordenador/vincula";
	}

	@RequestMapping(value = "/teste", method = RequestMethod.GET)
	public String teste(ModelMap model) {
		
		return "coordenador/vincula";
	}

	@RequestMapping(value = "/{idProjeto}/buscaEstagiarios", method = RequestMethod.POST)
	public String vincularMembros(ModelMap model, @ModelAttribute("filtro") Filtro filtro, @PathVariable("idProjeto") Long idProjeto) {		

			Periodo periodo = servicePeriodo.getPeriodo(filtro.getAno(), filtro.getSemestre());
			List<Estagiario> estagiarios = new ArrayList<Estagiario>();
			if(periodo != null){
				if(periodo.getTurmas() != null){
					for (Turma turma : periodo.getTurmas()) {
						estagiarios.addAll(turma.getEstagiarios());
					}
					model.addAttribute("turmas", periodo.getTurmas());
					model.addAttribute("estagiarios", estagiarios);
				}
			}
		
		
		model.addAttribute("projeto", serviceProjeto.find(Projeto.class, idProjeto));
		return "/coordenador/vincularMembros";
	}
	
	@RequestMapping(value = "/vincularMembros", method = RequestMethod.POST)
	public String vincularMembrosProjeto(ModelMap model, @ModelAttribute("projeto") Projeto projeto) {
		projeto = atualizarProjeto(projeto);
		serviceProjeto.update(projeto);
		return "redirect:/coordenador/projetos";
	}
	
	
	/*
	 * METODOS
	 */
	private Projeto atualizarProjeto(Projeto projeto) {		
		List<Estagiario> membros = new ArrayList<Estagiario>();
		
		if (projeto.getMembros() != null) {
			for (Estagiario membro : projeto.getMembros()) {
				if(membro.getId() != null){
					membro = serviceEstagiario.find(Estagiario.class, membro.getId());
					membros.add(membro);
				}
			}
		}
		
		projeto.setMembros(membros);
		return projeto;
	}
		

}

