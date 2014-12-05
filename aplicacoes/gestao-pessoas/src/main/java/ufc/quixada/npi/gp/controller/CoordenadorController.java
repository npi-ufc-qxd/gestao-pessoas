package ufc.quixada.npi.gp.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ufc.quixada.npi.gp.model.Estagiario;
import ufc.quixada.npi.gp.model.Pessoa;
import ufc.quixada.npi.gp.model.Projeto;
import ufc.quixada.npi.gp.model.Turma;
import ufc.quixada.npi.gp.service.EstagiarioService;
import ufc.quixada.npi.gp.service.GenericService;
import ufc.quixada.npi.gp.service.PessoaService;
import ufc.quixada.npi.gp.utils.Constants;

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
	private PessoaService servicePessoa;

	@Inject
	private GenericService<Turma> serviceTurma;

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

	@RequestMapping(value = "/estagiarios")
	public String listaEstagiarios(ModelMap modelMap, HttpSession session) {
		modelMap.addAttribute("usuario", SecurityContextHolder.getContext()
				.getAuthentication().getName());

		modelMap.addAttribute("estagiarios",
				serviceEstagiario.find(Estagiario.class));

		return "coordenador/estagiarios";
	}

	@RequestMapping(value = "/jrreport", method = RequestMethod.GET)
	public String printWelcome(ModelMap model) throws JRException {
		
		jrDatasource = new JRBeanCollectionDataSource(serviceEstagiario.find(Estagiario.class));
		
		model.addAttribute("datasource", jrDatasource);
		model.addAttribute("format", "pdf");
		return "multiViewReport";
	}
	
	@RequestMapping(value = "/declaracaoEstagio", method = RequestMethod.GET)
	public String declaracaoEstagio( ModelMap model) throws JRException {
		
		jrDatasource = new JRBeanCollectionDataSource(serviceEstagiario.find(Estagiario.class));
		
		model.addAttribute("datasource", jrDatasource);
		model.addAttribute("format", "pdf");
		return "declaracaoEstagioIndividual";
	}
	
	@RequestMapping(value = "/projetos", method = RequestMethod.GET)
	public String listarProjetos( ModelMap model) {
		model.addAttribute("projetos", serviceProjeto.find(Projeto.class));
		return "/coordenador/projetos";
	}

	@RequestMapping(value = "/projeto", method = RequestMethod.GET)
	public String adicionarProjetos( ModelMap model) {
		model.addAttribute("projeto", new Projeto());
		return "coordenador/formProjeto";
	}

	@RequestMapping(value = "/projeto", method = RequestMethod.POST)
	public String adicionarProjetos(ModelMap model, @Valid @ModelAttribute("projeto") Projeto projeto, BindingResult result) {
		if (result.hasErrors()) {
			return "coordenador/formProjeto";
		}
		
		if(projeto.getId() == null)
			serviceProjeto.save(projeto);
		else
			serviceProjeto.update(projeto);
		
		return "redirect:/coordenador/projetos";
	}

	@RequestMapping(value = "/{id}/projeto", method = RequestMethod.GET)
	public String editarProjetos(@PathVariable("id") Long id, ModelMap model) {
		model.addAttribute("projeto", serviceProjeto.find(Projeto.class, id));
		return "coordenador/formProjeto";
	}

	@RequestMapping(value = "/{id}/vincularParticipantes", method = RequestMethod.GET)
	public String vincularParticipantes(ModelMap model, @PathVariable("id") Long id) {
		model.addAttribute("projeto", serviceProjeto.find(Projeto.class, id));
		model.addAttribute("estagiarios", serviceEstagiario.find(Estagiario.class));
		return "coordenador/vincularParticipantes";
	}
	@RequestMapping(value = "/vincularParticipantes", method = RequestMethod.POST)
	public String vincularParticipantes(ModelMap model, @ModelAttribute("projeto") Projeto projeto) {
		projeto = atualizarProjeto(projeto);
		serviceProjeto.update(projeto);
		return "redirect:/coordenador/projetos";
	}

	@RequestMapping(value = "/turmas", method = RequestMethod.GET)
	public String listarTurmas(ModelMap model) {
		model.addAttribute("turmas", serviceTurma.find(Turma.class));
		return "coordenador/turmas";
	}

	@RequestMapping(value = "/turma", method = RequestMethod.GET)
	public String adicionarTurma(ModelMap model) {
		model.addAttribute("turma", new Turma());
		return "coordenador/formTurma";
	}

	@RequestMapping(value = "/turma", method = RequestMethod.POST)
	public String adicionarTurma(ModelMap model, @Valid @ModelAttribute("turma") Turma turma, BindingResult result, HttpSession session) {

		if (result.hasErrors()) {
			return "coordenador/formTurma";
		}

		turma.setSupervisor(getUsuarioLogado(session));
		
		serviceTurma.save(turma);

		return "coordenador/turmas";
	}
	
	private Pessoa getUsuarioLogado(HttpSession session) {
		if (session.getAttribute(Constants.USUARIO_LOGADO) == null) {
			Pessoa usuario = servicePessoa.getPessoaByLogin(SecurityContextHolder.getContext().getAuthentication().getName());
			session.setAttribute(Constants.USUARIO_LOGADO, usuario);
		}
		return (Pessoa) session.getAttribute(Constants.USUARIO_LOGADO);
	}
	
	private String geraCodigoTurma(Long id) {
		if (id < 10) {
			return "TURMA" + id;
		} else {
			return "TURMA" + id;
		}
	}
	
	private Projeto atualizarProjeto(Projeto projeto) {
		if (projeto.getParticipantes() != null) {
			List<Estagiario> estagiarios = new ArrayList<Estagiario>();
			for (Estagiario estagiario : projeto.getParticipantes()) {
				if(estagiario.getId() != null){
					estagiario = serviceEstagiario.find(Estagiario.class, estagiario.getId());
					estagiario.setProjeto(projeto);
					estagiarios.add(estagiario);
				}
				projeto.setParticipantes(estagiarios);
			}
		}
		return projeto;
	}	
}
