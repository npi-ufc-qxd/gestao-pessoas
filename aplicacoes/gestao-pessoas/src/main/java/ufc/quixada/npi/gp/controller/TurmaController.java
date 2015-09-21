package ufc.quixada.npi.gp.controller;

import static ufc.quixada.npi.gp.utils.Constants.PAGINA_DECLARACAO_ESTAGIO;
import static ufc.quixada.npi.gp.utils.Constants.PAGINA_TCE;

import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ufc.quixada.npi.gp.model.Estagiario;
import ufc.quixada.npi.gp.model.Frequencia;
import ufc.quixada.npi.gp.model.Horario;
import ufc.quixada.npi.gp.model.Pessoa;
import ufc.quixada.npi.gp.model.Turma;
import ufc.quixada.npi.gp.model.enums.Dia;
import ufc.quixada.npi.gp.model.enums.StatusTurma;
import ufc.quixada.npi.gp.service.DadoConsolidado;
import ufc.quixada.npi.gp.service.EstagiarioService;
import ufc.quixada.npi.gp.service.FrequenciaService;
import ufc.quixada.npi.gp.service.HorarioService;
import ufc.quixada.npi.gp.service.PessoaService;
import ufc.quixada.npi.gp.service.TurmaService;
import ufc.quixada.npi.gp.utils.Constants;


@Component
@Controller
@RequestMapping("supervisor/turma")
public class TurmaController {
	
	@Inject
	private PessoaService pessoaService;
	
	@Inject
	private EstagiarioService estagiarioService;
	
	@Inject
	private TurmaService turmaService;

	@Inject
	private FrequenciaService frequenciaService; 
	
	@Inject
	private HorarioService horarioService; 
	
	private JRDataSource jrDatasource;

	@RequestMapping(value = "/{idTurma}/tce", method = RequestMethod.GET)
	public String gerarTermoDeCompromisso(@PathVariable("idTurma") Long idTurma, Model model) throws JRException {
		jrDatasource = new JRBeanCollectionDataSource(estagiarioService.getEstagiarioByTurmaId(idTurma));
		
		model.addAttribute("datasource", jrDatasource);
		model.addAttribute("format", "pdf");
		return PAGINA_TCE;
	}

	@RequestMapping(value = "/{idTurma}/declaracoes", method = RequestMethod.GET)
	public String gerarDeclaracaoEstagio( Model model, @PathVariable("idTurma") Long idTurma) throws JRException {
		jrDatasource = new JRBeanCollectionDataSource(estagiarioService.getEstagiarioByTurmaId(idTurma));
		
		model.addAttribute("datasource", jrDatasource);
		model.addAttribute("format", "pdf");
		return PAGINA_DECLARACAO_ESTAGIO;
	}

	@RequestMapping(value = "/adicionar", method = RequestMethod.GET)
	public String novaTurma(Model model) {
		model.addAttribute("action", "cadastrar");

		Turma turma = new Turma();

		model.addAttribute("turma", turma);
		model.addAttribute("dias", Dia.values());
		
		return "supervisor/form-turma";
	}

	@RequestMapping(value = "/adicionar", method = RequestMethod.POST)
	public String adicionarTurma(Model model, @Valid @ModelAttribute("turma") Turma turma,  BindingResult result, HttpSession session, RedirectAttributes redirect) {
		model.addAttribute("action", "cadastrar");
		
		turma.setHorarios(atualizarHorarios(turma));

		if (result.hasErrors()) {
			model.addAttribute("dias", Dia.values());
			return "supervisor/form-turma";
		}

		Pessoa pessoa = getUsuarioLogado(session);
		
		turma.setSupervisor(pessoa);
		turmaService.save(turma);
		
		redirect.addFlashAttribute("success", "Turma cadastrada com sucesso.");

		return "redirect:/supervisor/turmas";
	}
	
	@RequestMapping(value = "/{idTurma}/editar", method = RequestMethod.GET)
	public String paginaEditarTurma(@PathVariable("idTurma") Long idTurma, Model model, HttpSession session) {
		model.addAttribute("action", "editar");

		Pessoa pessoa = getUsuarioLogado(session);

		model.addAttribute("turma", turmaService.getTurmaByIdAndSupervisorById(idTurma, pessoa.getId()));
		model.addAttribute("dias", Dia.values());

		return "supervisor/form-turma";
	}

	@RequestMapping(value = "/{idTurma}/editar", method = RequestMethod.POST)
	public String editarTurma(Model model, @Valid @ModelAttribute("turma") Turma turma,  BindingResult result, HttpSession session) {

		model.addAttribute("action", "editar");

		if (result.hasErrors()) {
			model.addAttribute("dias", Dia.values());
			return "supervisor/form-turma";
		}
		
		Pessoa pessoa = getUsuarioLogado(session);
		Turma turmaDoBanco = turmaService.getTurmaByIdAndSupervisorById(turma.getId(), pessoa.getId());
		
		turmaDoBanco.setNome(turma.getNome());
		turmaDoBanco.setStatusTurma(turma.getStatusTurma());
		turmaDoBanco.setAno(turma.getAno());
		turmaDoBanco.setSemestre(turma.getSemestre());
		turmaDoBanco.setInicio(turma.getInicio());
		turmaDoBanco.setTermino(turma.getTermino());

		turmaService.update(turmaDoBanco);

		return "redirect:/supervisor/turmas";
	}

	@RequestMapping(value = "/{idTurma}", method = RequestMethod.GET)
	public String detalhesTurma(@PathVariable("idTurma") Long idTurma, Model model, HttpSession session) {
		Pessoa pessoa = getUsuarioLogado(session);

		model.addAttribute("turma", turmaService.getTurmaByIdAndSupervisorById(idTurma, pessoa.getId()));
		turmaService.getTurmaByIdAndSupervisorById(idTurma, pessoa.getId());

		return "supervisor/info-turma";
	}
	
	@RequestMapping(value = "/{idTurma}/horarios", method = RequestMethod.GET)
	public String paginaExpedienteTurma(Model model, @ModelAttribute("idTurma") Long idTurma) {

		model.addAttribute("dias", Dia.values());
		model.addAttribute("horario", new Horario());
		model.addAttribute("turma", turmaService.find(Turma.class, idTurma));
		
		return "supervisor/form-horario";
	}
	
	@RequestMapping(value = "/{idTurma}/horario", method = RequestMethod.POST)
	public String paginaExpediente(Model model, @Valid @ModelAttribute("horario") Horario horario, @ModelAttribute("idTurma") Long idTurma, BindingResult result, HttpSession session, RedirectAttributes redirect) {

		if (result.hasErrors()) {
			model.addAttribute("dias", Dia.values());
			return "supervisor/form-horario";
		}
		
		Pessoa supervisor = getUsuarioLogado(session);
		Turma turma = turmaService.getTurmaByIdAndSupervisorById(idTurma, supervisor.getId());
		horario.setTurma(turma);

		horarioService.save(horario);
		
		redirect.addFlashAttribute("success", "Horário cadastrado com sucesso.");

		return "redirect:/supervisor/turma/"+ idTurma +"/horarios";
	}

	@RequestMapping(value = "/{idTurma}/horario/{idHorario}/excluir", method = RequestMethod.GET)
	public String excluirExpediente(Model model, @ModelAttribute("idHorario") Long idHorario, @ModelAttribute("idTurma") Long idTurma, BindingResult result, HttpSession session, RedirectAttributes redirect) {

		horarioService.delete(horarioService.find(Horario.class, idHorario));
		
		redirect.addFlashAttribute("success", "Horário excluído com sucesso.");

		return "redirect:/supervisor/turma/"+ idTurma +"/horarios";
	}
	
	@RequestMapping(value = "/{id}/vincular", method = RequestMethod.GET)
	public String paginaVincularEstagiarioTurma(Model model, HttpSession session, @PathVariable("id") Long idTurma)  {
		Pessoa pessoa = getUsuarioLogado(session);

		model.addAttribute("turma", turmaService.getTurmaByIdAndSupervisorById(idTurma, pessoa.getId()));
		model.addAttribute("estagiariosDaTurma", estagiarioService.getEstagiarioByTurmaId(idTurma));
		model.addAttribute("outrosEstagiarios", estagiarioService.getEstagiarioByNotTurmaIdOrSemTurma(idTurma));

		return "supervisor/form-vincular-estagiarios-turma";
	}

	@RequestMapping(value = "/{id}/vincular", method = RequestMethod.POST)
	public String atualizarVinculoEstagiarioTurma(Model model, HttpSession session, @ModelAttribute("turma") Turma turma)  {
		Pessoa pessoa = getUsuarioLogado(session);
		
		Turma turmaDoBanco = turmaService.getTurmaByIdAndSupervisorById(turma.getId(), pessoa.getId());
		
		List<Estagiario> estagiariosSelecionados = new ArrayList<Estagiario>();
		
		if(turma.getEstagiarios() != null) {
			estagiariosSelecionados = getEstagiariosSelecionados(turma.getEstagiarios());
			estagiariosSelecionados = atualizarTurmaEstagiarios(estagiariosSelecionados, turmaDoBanco);
		}

		turmaDoBanco.setEstagiarios(estagiariosSelecionados);

		turmaService.update(turmaDoBanco);

		model.addAttribute("turma", turmaDoBanco);
		model.addAttribute("estagiarios", estagiarioService.find(Estagiario.class));

		return "redirect:/supervisor/turma/" + turmaDoBanco.getId();
	}

	@RequestMapping(value = "/{idTurma}/mapa-frequencia", method = RequestMethod.GET)
	public String listarFrequenciaTurma(@PathVariable("idTurma") Long idTurma, Model model, HttpSession session) {
		Pessoa pessoa = getUsuarioLogado(session);
		Date dataAtual = new Date();
		List<Frequencia> frequencias = frequenciaService.getFrequenciasByTurmaIdAndData(dataAtual, idTurma);

		model.addAttribute("turma", turmaService.getTurmaByIdAndSupervisorById(idTurma, pessoa.getId()));
		model.addAttribute("frequencias", frequencias);
		model.addAttribute("dataSelecionada", dataAtual);

		return "supervisor/list-frequencias";
	}

	@RequestMapping(value = "/{idTurma}/frequencias", method = RequestMethod.POST)
	public String listarFrequenciaTurmaData(@PathVariable("idTurma") Long idTurma, @RequestParam("data") Date data, Model model, HttpSession session) {
		Pessoa pessoa = getUsuarioLogado(session);

		List<Frequencia> frequencias = frequenciaService.getFrequenciasByTurmaIdAndData(data, idTurma);

		model.addAttribute("turma", turmaService.getTurmaByIdAndSupervisorById(idTurma, pessoa.getId()));
		model.addAttribute("turmas", turmaService.getTurmasBySupervisorIdAndStatus(StatusTurma.ABERTA, pessoa.getId()));
		model.addAttribute("frequencias", frequencias);
		model.addAttribute("dataSelecionada", data);

		return "supervisor/list-frequencias";
	}
	
	@RequestMapping(value = "/{idTurma}/estagiario/{idEstagiario}/frequencia", method = RequestMethod.GET)
	public String minhaPresenca(HttpSession session, Model model, @PathVariable("idTurma") Long idTurma, @PathVariable("idEstagiario") Long idEstagiario) {

		Estagiario estagiario = estagiarioService.find(Estagiario.class, idEstagiario);
		
		Turma turma = turmaService.find(Turma.class, idTurma);

		List<Frequencia> frequencias = frequenciaService.getFrequenciasByEstagiarioId(estagiario.getId());
		
		frequencias.addAll(frequenciaService.gerarFrequencia(turma, idEstagiario));

		DadoConsolidado dadosConsolidados = frequenciaService.calcularDadosConsolidados(frequencias);

		model.addAttribute("estagiario", estagiario);
		model.addAttribute("frequencias", frequencias);
		model.addAttribute("dadosConsolidados", dadosConsolidados);		
		
		return "supervisor/list-frequencia-estagiario";
	}

	private Pessoa getUsuarioLogado(HttpSession session) {
		if (session.getAttribute(Constants.USUARIO_LOGADO) == null) {
			Pessoa pessoa = pessoaService.getPessoaByCpf(SecurityContextHolder.getContext().getAuthentication().getName());
			session.setAttribute(Constants.USUARIO_LOGADO, pessoa);
		}
		return (Pessoa) session.getAttribute(Constants.USUARIO_LOGADO);
	}

	private List<Estagiario> getEstagiariosSelecionados(List<Estagiario> estagiarios) {
		List<Estagiario> estagiariosSelecionados = new ArrayList<Estagiario>();

		for (Estagiario estagiario : estagiarios) {
			if(estagiario.getId() != null){
				estagiario = estagiarioService.find(Estagiario.class, estagiario.getId());

				estagiariosSelecionados.add(estagiario);
			}
		}

		return estagiariosSelecionados;
	}

	private List<Estagiario> atualizarTurmaEstagiarios(List<Estagiario> estagiarios, Turma turma) {
		for (Estagiario estagiario : estagiarios) {
			if(estagiario.getTurmas() != null) {
				if(!estagiario.getTurmas().contains(turma)){
					estagiario.getTurmas().add(turma);
				}
			} else {
				estagiario.setTurmas(new ArrayList<Turma>());
				estagiario.getTurmas().add(turma);
			}
		}

		return estagiarios;
	}

	private List<Horario> atualizarHorarios(Turma turma) {
		List<Horario> horariosAtualizados = new ArrayList<Horario>();
		
		if(turma.getHorarios() == null){
			return null;
		}

		for (Horario horario : turma.getHorarios()) {
			if(horario.getDia() != null) {
				horariosAtualizados.add(horario);
			}
		}
		return horariosAtualizados;
	}
}
