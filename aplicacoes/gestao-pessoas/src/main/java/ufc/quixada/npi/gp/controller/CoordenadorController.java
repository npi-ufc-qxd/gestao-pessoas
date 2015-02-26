package ufc.quixada.npi.gp.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ufc.quixada.npi.gp.model.Estagiario;
import ufc.quixada.npi.gp.model.Filtro;
import ufc.quixada.npi.gp.model.Folga;
import ufc.quixada.npi.gp.model.Frequencia;
import ufc.quixada.npi.gp.model.Periodo;
import ufc.quixada.npi.gp.model.Pessoa;
import ufc.quixada.npi.gp.model.Projeto;
import ufc.quixada.npi.gp.model.Turma;
import ufc.quixada.npi.gp.model.enums.StatusFrequencia;
import ufc.quixada.npi.gp.model.enums.TipoFrequencia;
import ufc.quixada.npi.gp.service.EstagiarioService;
import ufc.quixada.npi.gp.service.FrequenciaService;
import ufc.quixada.npi.gp.service.PeriodoService;
import ufc.quixada.npi.gp.service.PessoaService;
import ufc.quixada.npi.gp.service.TurmaService;
import ufc.quixada.npi.gp.utils.Constants;
import br.ufc.quixada.npi.service.GenericService;

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
	private TurmaService serviceTurma;	

	@Inject
	private PeriodoService servicePeriodo;

	@Inject
	private FrequenciaService frequenciaService;

	@Inject
	private GenericService<Folga> serviceFolga;
	
	@Inject
	private PessoaService pessoaService;

	@RequestMapping(value = {"/inicial", "/index"}, method = RequestMethod.GET)
	public String inicial(ModelMap modelMap, HttpSession session) throws JRException {
		modelMap.addAttribute("usuario", SecurityContextHolder.getContext().getAuthentication().getName());
		return "coordenador/inicial";
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

/* INICIO PROJETO */

	@RequestMapping(value = "/projetos", method = RequestMethod.GET)
	public String listarProjetos( ModelMap model) {
		model.addAttribute("projetos", serviceProjeto.find(Projeto.class));
		return "/coordenador/list-projetos";
	}

	@RequestMapping(value = "/projeto", method = RequestMethod.GET)
	public String novoProjeto( ModelMap model) {
		model.addAttribute("projeto", new Projeto());
		return "coordenador/form-projeto";
	}
	@RequestMapping(value = "/projeto", method = RequestMethod.POST)
	public String adicionarProjeto(ModelMap model, @Valid @ModelAttribute("projeto") Projeto projeto, BindingResult result) {
		
		if (result.hasErrors()) {
			return "coordenador/form-projeto";
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
		return "coordenador/form-projeto";
	}

	@RequestMapping(value = "/projeto/{idProjeto}/detalhes", method = RequestMethod.GET)
	public String detalhesProjeto(@PathVariable("idProjeto") Long idProjeto, ModelMap model) {
		Projeto projeto = serviceProjeto.find(Projeto.class, idProjeto);
		model.addAttribute("projeto", projeto);
		return "coordenador/info-projeto";
	}

	@RequestMapping(value = "/projeto/{idProjeto}/excluir", method = RequestMethod.GET)
	public String excluirProjeto(@PathVariable("idProjeto") Long idProjeto, ModelMap model) {
		Projeto projeto = serviceProjeto.find(Projeto.class, idProjeto);
		
		if(projeto != null){
			serviceProjeto.delete(projeto);
		}
		
		return "redirect:/coordenador/projetos";
	}

	@RequestMapping(value = "/{idProjeto}/add-membros-projeto", method = RequestMethod.GET)
	public String vincularMembrosProjeto(ModelMap model, @PathVariable("idProjeto") Long idProjeto) {
		model.addAttribute("projeto", serviceProjeto.find(Projeto.class, idProjeto));
		
		return "coordenador/add-membros-projeto";
	}
	
	@RequestMapping(value = "/{idProjeto}/add-membros-projeto", method = RequestMethod.POST)
	public String vincularEstagiariosProjeto(ModelMap modelMap, HttpSession session, @RequestParam("turma") Long turma, @PathVariable("idProjeto") Long idProjeto) {
		modelMap.addAttribute("projeto", serviceProjeto.find(Projeto.class, idProjeto));
		modelMap.addAttribute("estagiarios", serviceTurma.find(Turma.class, turma).getEstagiarios());

		return "coordenador/add-membros-projeto";
	}

	@RequestMapping(value = "/vincular-membros-projeto", method = RequestMethod.POST)
	public String vincularMembrosProjeto(ModelMap model, @ModelAttribute("projeto") Projeto projeto) {
		projeto = atualizarProjeto(projeto);
		serviceProjeto.update(projeto);
		return "redirect:/coordenador/projetos";
	}
	
	

/* FINAL PROJETO */

/* 	INICIO ESTAGIARIO */	
	@RequestMapping(value = "/estagiarios", method = RequestMethod.GET)
	public String listaEstagiarios(ModelMap modelMap, HttpSession session) {
		modelMap.addAttribute("filtro", new Filtro());
		return "coordenador/list-estagiarios-periodo";
	}

	@RequestMapping(value = "/estagiarios-turma", method = RequestMethod.POST)
	public String estagiarios(ModelMap modelMap, HttpSession session, @RequestParam("turma") Long turma) {
		List<Estagiario> estagiarios = serviceTurma.find(Turma.class, turma).getEstagiarios();
		modelMap.addAttribute("estagiarios", estagiarios);

		return "coordenador/list-estagiarios-periodo";
	}
/* FINAL ESTAGIARIO */

/* 	INICIO PERIODO */

	@RequestMapping(value = "/periodos", method = RequestMethod.GET)
	public String listarPeriodos(ModelMap model) {
		model.addAttribute("periodos", servicePeriodo.find(Periodo.class));
		return "coordenador/list-periodos";
	}

	@RequestMapping(value = "/periodo", method = RequestMethod.GET)
	public String novoPeriodo(ModelMap model) {
		model.addAttribute("periodo", new Periodo());

		return "coordenador/form-periodo";
	}

	@RequestMapping(value = "/{idPeriodo}/editar", method = RequestMethod.GET)
	public String editarPeriodo(@PathVariable("idPeriodo") Long idPeriodo, ModelMap model) {
		model.addAttribute("projeto", serviceProjeto.find(Projeto.class, idPeriodo));

		return "coordenador/form-periodo";
	}

	@RequestMapping(value = "/periodo", method = RequestMethod.POST)
	public String adicionarPeriodo(ModelMap model, @Valid @ModelAttribute("periodo") Periodo periodo, BindingResult result, RedirectAttributes redirectAttributes) {

		if (result.hasErrors()) {
			return "coordenador/form-periodo";
		}

		if (periodo.getId() == null) {
			
			try {
				servicePeriodo.save(periodo);
			} catch (Exception e) {
				model.addAttribute("erro", "O periodo "  + periodo.getAno() + "." + periodo.getSemestre() + " já esta cadastrado.");
				return "coordenador/form-periodo";
			}
			
		} else {
			servicePeriodo.update(periodo);
		}

		redirectAttributes.addAttribute("erro", "Periodo " + periodo.getAno() + "." + periodo.getSemestre() + " cadastrado com sucesso.");
		return "redirect:/coordenador/periodos";
	}

	@RequestMapping(value = "/{idPeriodo}/detalhes", method = RequestMethod.GET)
	public String detalhesPeriodo(@PathVariable("idPeriodo") Long idPeriodo, ModelMap model) {
		model.addAttribute("periodo", servicePeriodo.find(Periodo.class, idPeriodo));

		return "coordenador/info-periodo";
	}

	@RequestMapping(value = "/{idPeriodo}/folga", method = RequestMethod.GET)
	public String novaFolgaPeriodo(@PathVariable("idPeriodo") Long idPeriodo, ModelMap model) {
		model.addAttribute("periodo", servicePeriodo.find(Periodo.class, idPeriodo));
		model.addAttribute("folga", new Folga());

		return "coordenador/form-folga";
	}

	@RequestMapping(value = "/{idPeriodo}/folga", method = RequestMethod.POST)
	public String adicionarFolgaPeriodo(@PathVariable("idPeriodo") Long idPeriodo, @Valid @ModelAttribute("folga") Folga folga, BindingResult result, ModelMap model) {
		
		Periodo periodo = servicePeriodo.find(Periodo.class, idPeriodo);
		
		if (result.hasErrors()) {
			model.addAttribute("periodo", periodo);
			return "coordenador/form-folga";
		}

		folga.setPeriodo(periodo);

		serviceFolga.save(folga);
		return "redirect:/coordenador/periodos";
	}
/* 	FINAL PERIODO */

/* 	INICIO REPOSICAO */
	@RequestMapping(value = "/reposicao", method = RequestMethod.GET)
	public String reposicao(ModelMap model) {
		
//		model.addAttribute("objeto", frequenciaService.getFrequenciaRepor());
		return "coordenador/reposicao";
	}

	@RequestMapping(value = "/reposicao-atraso", method = RequestMethod.POST)
	public String reposicaoAtraso(ModelMap model, @RequestParam("turma") Long idTurma) {
		if (idTurma != null) {
			model.addAttribute("reposicao", "ATRASADO");
			model.addAttribute("objeto", frequenciaService.getReposicaoAtraso(idTurma));
		}
		return "coordenador/reposicao";
	}

	@RequestMapping(value = "/reposicao-falta", method = RequestMethod.POST)
	public String reposicaoFalta(ModelMap model, @RequestParam("turma") Long idTurma) {
		if (idTurma != null) {
			model.addAttribute("reposicao", "FALTA");
			model.addAttribute("objeto", frequenciaService.getReposicaoFalta(idTurma));
		}
		return "coordenador/reposicao";
	}

	@RequestMapping(value = "/agendar-reposicao.json", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Model agendarReposicao(Model model, @RequestParam("turma") Long idTurma, @RequestParam("estagiario") Long idEstagiario, @RequestParam("status") StatusFrequencia statusFrequencia, @RequestParam("data") String dataReposicao) {
		Date reposicao = null;
		try {
			DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("dd/MM/yyyy");
			DateTime date = dateTimeFormatter.parseDateTime(dataReposicao);
			reposicao = date.toDate();
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			Date today;
				today = format.parse(format.format(new Date()));
				if(reposicao.before(today)) {
					model.addAttribute("erro", true);
					model.addAttribute("msg", true);
					return model;
				}
		} catch (ParseException e) {
			e.printStackTrace();
		}		

		Frequencia frequenciaAgendada = new Frequencia();

		if (statusFrequencia.equals(StatusFrequencia.ATRASADO)) {
			List<Frequencia> frequencias = frequenciaService.getFrequenciaStatus(idTurma, idEstagiario, statusFrequencia, 2);

			if (frequencias.size() == 2) {

				frequenciaAgendada.setData(reposicao);
				frequenciaAgendada.setEstagiario(frequencias.get(0).getEstagiario());
				frequenciaAgendada.setTurma(frequencias.get(0).getTurma());
				frequenciaAgendada.setTipoFrequencia(TipoFrequencia.REPOSICAO);
				frequenciaAgendada.setStatusFrequencia(StatusFrequencia.AGUARDO);

				for (Frequencia frequenciaComAtraso : frequencias) {
					frequenciaComAtraso.setStatusFrequencia(StatusFrequencia.REPOSICAO_ATRASO);
//					serviceFrequencia.update(frequenciaComAtraso);
				}
//				serviceFrequencia.save(frequenciaAgendada);
				model.addAttribute("sucesso", true);
			} else {
				
			}
			
			
		} 
		
		else if (statusFrequencia.equals(StatusFrequencia.FALTA)) {
			List<Frequencia> frequencias = frequenciaService.getFrequenciaStatus(idTurma, idEstagiario, statusFrequencia, 1);

			if (frequencias.size() == 1) {
				frequenciaAgendada.setData(reposicao);
				frequenciaAgendada.setEstagiario(frequencias.get(0).getEstagiario());
				frequenciaAgendada.setTurma(frequencias.get(0).getTurma());
				frequenciaAgendada.setTipoFrequencia(TipoFrequencia.REPOSICAO);
				frequenciaAgendada.setStatusFrequencia(StatusFrequencia.AGUARDO);

				for (Frequencia frequenciaComFalta : frequencias) {
					frequenciaComFalta.setStatusFrequencia(StatusFrequencia.REPOSICAO_FALTA);
//					serviceFrequencia.update(frequenciaComAtraso);
				}
//				serviceFrequencia.save(frequenciaAgendada);
				model.addAttribute("sucesso", true);
			}

		}
		return model;
	}

/* 	FINAL REPOSICAO */	

/* UTILS */
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
	
	private Pessoa getUsuarioLogado(HttpSession session) {
		if (session.getAttribute(Constants.USUARIO_LOGADO) == null) {
			Pessoa pessoa = pessoaService.getPessoaByCPF(SecurityContextHolder.getContext().getAuthentication().getName());
			session.setAttribute(Constants.USUARIO_LOGADO, pessoa);
		}
		return (Pessoa) session.getAttribute(Constants.USUARIO_LOGADO);
	}
	
}


