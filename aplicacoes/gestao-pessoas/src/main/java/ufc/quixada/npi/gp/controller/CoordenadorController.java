package ufc.quixada.npi.gp.controller;

import java.util.ArrayList;
import java.util.Calendar;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.http.MediaType;

import ufc.quixada.npi.gp.model.Estagiario;
import ufc.quixada.npi.gp.model.Folga;
import ufc.quixada.npi.gp.model.Periodo;
import ufc.quixada.npi.gp.model.PeriodoJson;
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
	private GenericService<Periodo> servicePeriodo;

	@Inject
	private GenericService<Turma> serviceTurma;

	@Inject
	private GenericService<Folga> serviceFolga;

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
		model.addAttribute("estagiarios", serviceEstagiario.find(Estagiario.class));
		
		return "coordenador/vincularMembros";
	}
	
	@RequestMapping(value = "/vincularMembros", method = RequestMethod.POST)
	public String vincularMembrosProjeto(ModelMap model, @ModelAttribute("projeto") Projeto projeto) {

		projeto = atualizarProjeto(projeto);
		serviceProjeto.update(projeto);

		return "redirect:/coordenador/projetos";
	}
	
	/*
	 * TURMA
	 */
	@RequestMapping(value = "/turmas", method = RequestMethod.GET)
	public String listarTurmas(ModelMap model) {

		model.addAttribute("turmas", serviceTurma.find(Turma.class));
		
		return "coordenador/turmas";
	}

	@RequestMapping(value = "/{idPeriodo}/turma", method = RequestMethod.GET)
	public String adicionarTurmaPeriodo(ModelMap model, @PathVariable("idPeriodo") Long idPeriodo) {

		model.addAttribute("periodo", servicePeriodo.find(Periodo.class, idPeriodo));
		model.addAttribute("turma", new Turma());
		
		return "coordenador/formTurma";
	}

	@RequestMapping(value = "/{idPeriodo}/turma", method = RequestMethod.POST)
	public String adicionarTurmaPeriodo(ModelMap model, @Valid @ModelAttribute("turma") Turma turma,  @PathVariable("idPeriodo") Long idPeriodo, BindingResult result, HttpSession session) {

		if (result.hasErrors()) {
			return "coordenador/formTurma";
		}
		
		turma.setSupervisor(getUsuarioLogado(session));
		turma.setPeriodo(servicePeriodo.find(Periodo.class, idPeriodo));
		turma = atualizarTurma(turma);
		serviceTurma.save(turma);
		
		turma.setCodigo(geraCodigoTurma(turma.getId()));
		serviceTurma.update(turma);

		return "redirect:/coordenador/periodos";
	}

	@RequestMapping(value = "/turma/{idTurma}/editar", method = RequestMethod.GET)
	public String editarTurma(@PathVariable("idTurma") Long idTurma, ModelMap model) {

		model.addAttribute("turma", serviceTurma.find(Turma.class, idTurma));
		
		return "coordenador/formTurma";
	}

	@RequestMapping(value = "/turma/{idTurma}/exluir", method = RequestMethod.GET)
	public String excluirTurma(@PathVariable("idTurma") Long idTurma, ModelMap model) {

		Turma turma = serviceTurma.find(Turma.class, idTurma);
		
		if(turma != null){
			serviceTurma.delete(turma);
		}
		
		return "coordenador/turmas";
	}

	@RequestMapping(value = "/turma/{idTurma}/detalhes", method = RequestMethod.GET)
	public String detalhesTurma(@PathVariable("idTurma") Long idTurma, ModelMap model) {

		model.addAttribute("turma", serviceTurma.find(Turma.class, idTurma));
		
		return "coordenador/detalheTurma";
	}
	
	@RequestMapping(value = "/{idTurma}/vincularEstagiarios", method = RequestMethod.GET)
	public String vincularEstagiariosTurma(ModelMap model, @PathVariable("idTurma") Long idTurma) {
		
		model.addAttribute("turma", serviceTurma.find(Turma.class, idTurma));
		model.addAttribute("membros", serviceEstagiario.find(Estagiario.class));
		
		return "coordenador/vincularEstagiarios";
	}
	
	@RequestMapping(value = "/vincularEstagiarios", method = RequestMethod.POST)
	public String vincularEstagiariosTurma(ModelMap model, @ModelAttribute("turma") Turma turma) {
		
		turma = atualizarTurma(turma);
		serviceTurma.update(turma);
		
		return "redirect:/coordenador/turmas";
	}

	/*
	 * PERIODO
	 */
	@RequestMapping(value = "/periodos", method = RequestMethod.GET)
	public String listarPeriodos(ModelMap model) {
		model.addAttribute("periodos", servicePeriodo.find(Periodo.class));
		return "coordenador/periodos";
	}
	
	@RequestMapping(value = "/periodos.json", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<Periodo> getAtividades(@RequestBody PeriodoJson json) {
		List<Periodo> periodos = null;
		return periodos;
	}

	@RequestMapping(value = "/periodo", method = RequestMethod.GET)
	public String adicionarPeriodo(ModelMap model) {
		
		model.addAttribute("periodo", new Periodo());
		
		return "coordenador/formPeriodo";
	}

	@RequestMapping(value = "/periodo/{idPeriodo}/editar", method = RequestMethod.GET)
	public String editarPeriodo(@PathVariable("idPeriodo") Long idPeriodo, ModelMap model) {
		
		model.addAttribute("projeto", serviceProjeto.find(Projeto.class, idPeriodo));
		
		return "coordenador/formPeriodo";
	}

	@RequestMapping(value = "/periodo", method = RequestMethod.POST)
	public String adicionarPeriodo(ModelMap model, @Valid @ModelAttribute("periodo") Periodo periodo, BindingResult result) {

		if (result.hasErrors()) {
			return "coordenador/formTurma";
		}		
		
		if(periodo.getId() == null)
			servicePeriodo.save(periodo);
		else
			servicePeriodo.update(periodo);
		
		return "redirect:/coordenador/periodos";
	}

	@RequestMapping(value = "/turma/{idPeriodo}/exluir", method = RequestMethod.GET)
	public String excluirPeriodo(@PathVariable("idPeriodo") Long idPeriodo, ModelMap model) {

		Periodo periodo = servicePeriodo.find(Periodo.class, idPeriodo);
		
		if(periodo != null){
			servicePeriodo.delete(periodo);
		}
		
		return "coordenador/periodos";
	}

	@RequestMapping(value = "/periodo/{idPeriodo}/detalhes", method = RequestMethod.GET)
	public String detalhesPeriodo(@PathVariable("idPeriodo") Long idPeriodo, ModelMap model) {

		model.addAttribute("periodo", servicePeriodo.find(Periodo.class, idPeriodo));
		
		return "coordenador/detalhePeriodo";
	}

	@RequestMapping(value = "/periodo/{idPeriodo}/folga", method = RequestMethod.GET)
	public String adicionarFolgaPeriodo(@PathVariable("idPeriodo") Long idPeriodo, ModelMap model) {

		model.addAttribute("periodo", servicePeriodo.find(Periodo.class, idPeriodo));
		model.addAttribute("folga", new Folga());
		
		return "coordenador/formFolga";
	}

	@RequestMapping(value = "/periodo/{idPeriodo}/folga", method = RequestMethod.POST)
	public String adicionarFolgaPeriodo(@PathVariable("idPeriodo") Long idPeriodo,@ModelAttribute("folga") Folga folga, ModelMap model) {

		folga.setPeriodo(servicePeriodo.find(Periodo.class, idPeriodo));
		
		serviceFolga.save(folga);		
		return "redirect:/coordenador/periodos";
	}
	
	
	
	/*
	 * METODOS
	 */
	private Pessoa getUsuarioLogado(HttpSession session) {
		return (Pessoa) session.getAttribute(Constants.USUARIO_LOGADO);
	}

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
		
	private Turma atualizarTurma(Turma turma) {
		List<Estagiario> estagiarios = new ArrayList<Estagiario>();

		if (turma.getEstagiarios() != null) {
			for (Estagiario estagiario : turma.getEstagiarios()) {
				if(estagiario.getId() != null){
					estagiario = serviceEstagiario.find(Estagiario.class, estagiario.getId());
//					estagiario.setTurma(turma);
					estagiarios.add(estagiario);
				}
			}
		}
		
		turma.setEstagiarios(estagiarios);
		return turma;
	}
	
	private String geraCodigoPeriodo() {
		if(Calendar.MONTH <= 6)
			return ""+Calendar.YEAR+"_1";
		else
			return ""+Calendar.YEAR+"_2";
		
	}
	
	private String geraCodigoTurma(Long id) {
		if (id < 10) {
			return "TURMA_" + id;
		} else {
			return "TURMA_" + id;
		}
	}
}

