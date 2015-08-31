package ufc.quixada.npi.gp.controller;

import static ufc.quixada.npi.gp.utils.Constants.PAGINA_DECLARACAO_ESTAGIO;
import static ufc.quixada.npi.gp.utils.Constants.PAGINA_TCE;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import net.objectlab.kit.datecalc.common.DefaultHolidayCalendar;
import net.objectlab.kit.datecalc.common.HolidayCalendar;
import net.objectlab.kit.datecalc.joda.LocalDateKitCalculatorsFactory;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.joda.time.LocalDate;
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
import ufc.quixada.npi.gp.model.Folga;
import ufc.quixada.npi.gp.model.Frequencia;
import ufc.quixada.npi.gp.model.Papel;
import ufc.quixada.npi.gp.model.Pessoa;
import ufc.quixada.npi.gp.model.Projeto;
import ufc.quixada.npi.gp.model.Servidor;
import ufc.quixada.npi.gp.model.Turma;
import ufc.quixada.npi.gp.model.enums.Dia;
import ufc.quixada.npi.gp.model.enums.StatusFrequencia;
import ufc.quixada.npi.gp.model.enums.StatusTurma;
import ufc.quixada.npi.gp.model.enums.TipoFrequencia;
import ufc.quixada.npi.gp.service.DadoConsolidado;
import ufc.quixada.npi.gp.service.EstagiarioService;
import ufc.quixada.npi.gp.service.FolgaService;
import ufc.quixada.npi.gp.service.FrequenciaService;
import ufc.quixada.npi.gp.service.PapelService;
import ufc.quixada.npi.gp.service.PessoaService;
import ufc.quixada.npi.gp.service.ProjetoService;
import ufc.quixada.npi.gp.service.ServidorService;
import ufc.quixada.npi.gp.service.TurmaService;
import ufc.quixada.npi.gp.utils.Constants;
import ufc.quixada.npi.gp.utils.UtilGestao;
import br.ufc.quixada.npi.ldap.service.UsuarioService;


@Component
@Controller
@RequestMapping("supervisor")
public class SupervisorController {
	
	@Inject
	private PessoaService pessoaService;
	
	@Inject
	private ServidorService servidorService;
	
	@Inject
	private UsuarioService usuarioService;
	
	@Inject
	private PapelService papelService;
	
	@Inject
	private FolgaService folgaService;
	
	@Inject
	private EstagiarioService estagiarioService;
	
	@Inject
	private TurmaService turmaService;

	@Inject
	private ProjetoService projetoService; 
	
	@Inject
	private FrequenciaService frequenciaService; 
	
	private JRDataSource jrDatasource;

	@RequestMapping(value = {"","/"}, method = RequestMethod.GET)
	public String paginaInicial(Model Model, HttpSession session)  {

		String cpf = SecurityContextHolder.getContext().getAuthentication().getName();

		if(!pessoaService.isPessoa(cpf)){

			Papel papel = papelService.getPapel("ROLE_SUPERVISOR");

			Pessoa pessoa = new Pessoa(cpf);
			pessoa.setPapeis(new ArrayList<Papel>());
			pessoa.getPapeis().add(papel);

			pessoaService.save(pessoa);

			Servidor servidor = new Servidor(pessoa, usuarioService.getByCpf(cpf).getSiape());
			servidorService.save(servidor);
		}

		return "redirect:/supervisor/turmas";
	}
	
	@RequestMapping(value = "/turma/{idTurma}/tce", method = RequestMethod.GET)
	public String gerarTermoDeCompromisso(@PathVariable("idTurma") Long idTurma, Model model) throws JRException {
		jrDatasource = new JRBeanCollectionDataSource(estagiarioService.getEstagiarioByTurmaId(idTurma));
		
		model.addAttribute("datasource", jrDatasource);
		model.addAttribute("format", "pdf");
		return PAGINA_TCE;
	}

	@RequestMapping(value = "/turma/{idTurma}/declaracoes", method = RequestMethod.GET)
	public String gerarDeclaracaoEstagio( Model model, @PathVariable("idTurma") Long idTurma) throws JRException {
		jrDatasource = new JRBeanCollectionDataSource(estagiarioService.getEstagiarioByTurmaId(idTurma));
		
		model.addAttribute("datasource", jrDatasource);
		model.addAttribute("format", "pdf");
		return PAGINA_DECLARACAO_ESTAGIO;
	}

	@RequestMapping(value = "/turmas", method = RequestMethod.GET)
	public String listarTurmas(Model model, HttpSession session) {
		Pessoa pessoa = getUsuarioLogado(session);
		model.addAttribute("turmas", turmaService.getTurmasBySupervisorId(pessoa.getId()));

		return "supervisor/list-turmas";
	}

	@RequestMapping(value = "/turma", method = RequestMethod.GET)
	public String novaTurma(Model model) {
		model.addAttribute("action", "cadastrar");

		Turma turma = new Turma();

		model.addAttribute("turma", turma);
		model.addAttribute("dias", Dia.values());
		
		return "supervisor/form-turma";
	}

	@RequestMapping(value = "/turma", method = RequestMethod.POST)
	public String adicionarTurma(Model model, @Valid @ModelAttribute("turma") Turma turma,  BindingResult result, HttpSession session) {
		model.addAttribute("action", "cadastrar");

		if (result.hasErrors()) {
			model.addAttribute("dias", Dia.values());
			return "supervisor/form-turma";
		}

		Pessoa pessoa = getUsuarioLogado(session);
		
		turma.setSupervisor(pessoa);
		turmaService.save(turma);

		return "redirect:/supervisor/turmas";
	}
	
	@RequestMapping(value = "/turma/{idTurma}", method = RequestMethod.GET)
	public String detalhesTurma(@PathVariable("idTurma") Long idTurma, Model model, HttpSession session) {
		Pessoa pessoa = getUsuarioLogado(session);

		model.addAttribute("turma", turmaService.getTurmaByIdAndSupervisorById(idTurma, pessoa.getId()));
		turmaService.getTurmaByIdAndSupervisorById(idTurma, pessoa.getId());

		return "supervisor/info-turma";
	}
	
	@RequestMapping(value = "/turma/{id}/vincular", method = RequestMethod.GET)
	public String paginaVincularEstagiarioTurma(Model model, HttpSession session, @PathVariable("id") Long idTurma)  {
		Pessoa pessoa = getUsuarioLogado(session);

		model.addAttribute("turma", turmaService.getTurmaByIdAndSupervisorById(idTurma, pessoa.getId()));
		model.addAttribute("estagiarios", estagiarioService.find(Estagiario.class));

		return "supervisor/form-vincular-estagiarios-turma";
	}

	@RequestMapping(value = "/turma/{id}/vincular", method = RequestMethod.POST)
	public String atualizarVinculoEstagiarioTurma(Model model, HttpSession session, @ModelAttribute("turma") Turma turma)  {
		Pessoa pessoa = getUsuarioLogado(session);
		
		Turma turmaDoBanco = turmaService.getTurmaByIdAndSupervisorById(turma.getId(), pessoa.getId());
		
		turmaDoBanco.setEstagiarios(atualizarListaEstagiarios(turma));
		
		turmaService.update(turmaDoBanco);

		model.addAttribute("turma", turmaDoBanco);
		model.addAttribute("estagiarios", estagiarioService.find(Estagiario.class));

		return "redirect:/supervisor/turma/" + turmaDoBanco.getId();
	}

	@RequestMapping(value = "/projetos", method = RequestMethod.GET)
	public String listarProjetos( Model model) {
		model.addAttribute("projetos", projetoService.find(Projeto.class));

		return "supervisor/list-projetos";
	}

	@RequestMapping(value = "/projeto", method = RequestMethod.GET)
	public String novoProjeto( Model model) {
		model.addAttribute("action", "cadastrar");
		model.addAttribute("projeto", new Projeto());

		return "supervisor/form-projeto";
	}

	@RequestMapping(value = "/projeto", method = RequestMethod.POST)
	public String adicionarProjeto(Model model, @Valid @ModelAttribute("projeto") Projeto projeto, BindingResult result) {
		model.addAttribute("action", "cadastrar");
		
		if (result.hasErrors()) {
			return "supervisor/form-projeto";
		}

		projetoService.save(projeto);

		return "redirect:/supervisor/projetos";
	}

	@RequestMapping(value = "/projeto/{idProjeto}/editar", method = RequestMethod.GET)
	public String editarProjeto(@PathVariable("idProjeto") Long idProjeto, Model model) {
		model.addAttribute("action", "editar");
		model.addAttribute("projeto", projetoService.find(Projeto.class, idProjeto));

		return "supervisor/form-projeto";
	}

	@RequestMapping(value = "/projeto/{idProjeto}/editar", method = RequestMethod.POST)
	public String editarProjeto(Model model, @Valid @ModelAttribute("projeto") Projeto projeto, BindingResult result) {
		model.addAttribute("action", "editar");

		if (result.hasErrors()) {
			return "supervisor/form-projeto";
		}
	
		projeto = atualizarProjeto(projeto);

		projetoService.update(projeto);

		return "redirect:/supervisor/projetos";
	}

	@RequestMapping(value = "/projeto/{idProjeto}/informacoes", method = RequestMethod.GET)
	public String detalhesProjeto(@PathVariable("idProjeto") Long idProjeto, Model model) {
		Projeto projeto = projetoService.find(Projeto.class, idProjeto);

		model.addAttribute("projeto", projeto);

		return "supervisor/info-projeto";
	}

	@RequestMapping(value = "/projeto/{idProjeto}/excluir", method = RequestMethod.GET)
	public String excluirProjeto(@PathVariable("idProjeto") Long idProjeto, Model model) {
		Projeto projeto = projetoService.find(Projeto.class, idProjeto);
		
		if(projeto != null){
			projetoService.delete(projeto);
		}

		return "redirect:/supervisor/projetos";
	}
	
	@RequestMapping(value = "/projeto/{idProjeto}/vincular", method = RequestMethod.GET)
	public String paginaVincularMembrosProjeto(Model model, @PathVariable("idProjeto") Long idProjeto, HttpSession session) {
		model.addAttribute("projeto", projetoService.find(Projeto.class, idProjeto));
		model.addAttribute("estagiarios", estagiarioService.find(Estagiario.class));
		
		return "supervisor/form-vincular-membros-projeto";
	}

	@RequestMapping(value = "/projeto/{idProjeto}/vincular", method = RequestMethod.POST)
	public String vincularMembrosProjeto(Model model, @ModelAttribute("projeto") Projeto projeto) {
		
		projeto = atualizarProjeto(projeto);
		
		projetoService.update(projeto);

		return "redirect:/supervisor/" + projeto.getId() + "/informacoes";
	}

	@RequestMapping(value = "/frequencias", method = RequestMethod.GET)
	public String paginaListarFrequenciaTurma(Model model, HttpSession session) {
		Pessoa pessoa = getUsuarioLogado(session);

		model.addAttribute("turmas", turmaService.getTurmasBySupervisorIdAndStatus(StatusTurma.ABERTA, pessoa.getId()));

		return "supervisor/list-frequencias";
	}

	@RequestMapping(value = "/turma/{idTurma}/frequencias", method = RequestMethod.GET)
	public String listarFrequenciaTurma(@PathVariable("idTurma") Long idTurma, Model model, HttpSession session) {
		Pessoa pessoa = getUsuarioLogado(session);
		Date dataAtual = new Date();
		List<Frequencia> frequencias = frequenciaService.getFrequenciasByTurmaIdAndData(dataAtual, idTurma);

		model.addAttribute("turma", turmaService.getTurmaByIdAndSupervisorById(idTurma, pessoa.getId()));
		model.addAttribute("turmas", turmaService.getTurmasBySupervisorIdAndStatus(StatusTurma.ABERTA, pessoa.getId()));
		model.addAttribute("frequencias", frequencias);
		model.addAttribute("dataSelecionada", dataAtual);

		return "supervisor/list-frequencias";
	}

	@RequestMapping(value = "/turma/{idTurma}/frequencias", method = RequestMethod.POST)
	public String listarFrequenciaTurmaData(@PathVariable("idTurma") Long idTurma, @RequestParam("data") Date data, Model model, HttpSession session) {
		Pessoa pessoa = getUsuarioLogado(session);

		List<Frequencia> frequencias = frequenciaService.getFrequenciasByTurmaIdAndData(data, idTurma);

		model.addAttribute("turma", turmaService.getTurmaByIdAndSupervisorById(idTurma, pessoa.getId()));
		model.addAttribute("turmas", turmaService.getTurmasBySupervisorIdAndStatus(StatusTurma.ABERTA, pessoa.getId()));
		model.addAttribute("frequencias", frequencias);
		model.addAttribute("dataSelecionada", data);

		return "supervisor/list-frequencias";
	}
	
	@RequestMapping(value = "/estagiario/{idEstagiario}/frequencia", method = RequestMethod.GET)
	public String minhaPresenca(HttpSession session, Model model, @PathVariable("idEstagiario") Long idEstagiario) {

		Estagiario estagiario = estagiarioService.find(Estagiario.class, idEstagiario);
		
		boolean frequenciaNaoRealizada = frequenciaService.getFrequenciaDeHojeByEstagiarioId(estagiario.getId()) == null ? true : false;
		
		List<Frequencia> frequencias = frequenciaService.getFrequenciasByEstagiarioId(estagiario.getId());

		LocalDate inicioPeriodoTemporario;
		if(!frequenciaNaoRealizada){
			inicioPeriodoTemporario = new LocalDate(new Date()).plusDays(1);
		}else{
			inicioPeriodoTemporario = new LocalDate(new Date());
		}
		
		LocalDate fimPeriodo = new LocalDate(estagiario.getTurma().getTermino());
		frequencias.addAll(gerarFrequencia(estagiario, inicioPeriodoTemporario, fimPeriodo));

		DadoConsolidado dadosConsolidados = frequenciaService.calcularDadosConsolidados(frequencias);

		model.addAttribute("estagiario", estagiario);
		model.addAttribute("frequencias", frequencias);
		model.addAttribute("dadosConsolidados", dadosConsolidados);		
		
		return "supervisor/list-frequencia-estagiario";
	}
	@RequestMapping(value = "/frequencia/realizar-observacao", method = RequestMethod.POST)
	public String frequenciaObservar(@RequestParam("pk") Long idFrequencia, @RequestParam("value") String observacao, Model model) {
		Frequencia frequencia = frequenciaService.find(Frequencia.class, idFrequencia);
		
		if(frequencia != null){
			frequencia.setObservacao(observacao);
			frequenciaService.update(frequencia);
			return "supervisor/list-frequencia-estagiario";
		}
		
		return "";
	}
	
	@RequestMapping(value = "/frequencia/atualizar-status", method = RequestMethod.POST)
	public String atualizarStatus(@RequestParam("pk") Long idFrequencia, @RequestParam("value") StatusFrequencia status, Model model, RedirectAttributes redirectAttributes) {
		Frequencia frequencia = frequenciaService.find(Frequencia.class, idFrequencia);
		
		if(frequencia != null){
			frequencia.setStatusFrequencia(status);
			frequenciaService.update(frequencia);
			return "supervisor/list-frequencia-estagiario";
		}

		return "";
	}

	private Pessoa getUsuarioLogado(HttpSession session) {
		if (session.getAttribute(Constants.USUARIO_LOGADO) == null) {
			Pessoa pessoa = pessoaService.getPessoaByCpf(SecurityContextHolder.getContext().getAuthentication().getName());
			session.setAttribute(Constants.USUARIO_LOGADO, pessoa);
		}
		return (Pessoa) session.getAttribute(Constants.USUARIO_LOGADO);
	}
	
	private List<Estagiario> atualizarListaEstagiarios(Turma turma) {
		List<Estagiario> estagiarios = new ArrayList<Estagiario>();
		if (turma.getEstagiarios() != null) {
			for (Estagiario estagiario : turma.getEstagiarios()) {
				if(estagiario.getId() != null){
					estagiario = estagiarioService.find(Estagiario.class, estagiario.getId());
					estagiarios.add(estagiario);
				}
			}
		}
		return estagiarios;
	}

	private Projeto atualizarProjeto(Projeto projeto) {
		List<Estagiario> membros = new ArrayList<Estagiario>();
		if (projeto.getMembros() != null) {
			for (Estagiario membro : projeto.getMembros()) {
				if(membro.getId() != null){
					membro = estagiarioService.find(Estagiario.class, membro.getId());
					membros.add(membro);
				}
			}
		}
		projeto.setMembros(membros);
		return projeto;
	}
	
	private List<Frequencia> gerarFrequencia(Estagiario estagiario, LocalDate inicioPeriodoTemporario, LocalDate fimPeriodo) {

		Set<LocalDate> dataDosFeriados = new HashSet<LocalDate>();
		
		List<Folga> folgas = folgaService.find(Folga.class);

		if (folgas != null) {
			for (Folga folga : folgas) {
				dataDosFeriados.add(new LocalDate(folga.getData()));
			}
		}

		HolidayCalendar<LocalDate> calendarioDeFeriados = new DefaultHolidayCalendar<LocalDate>(dataDosFeriados);
		LocalDateKitCalculatorsFactory.getDefaultInstance().registerHolidays("NPI", calendarioDeFeriados);

		List<Frequencia> frequencias = new ArrayList<Frequencia>();
		
		while (!inicioPeriodoTemporario.isAfter(fimPeriodo)) {

			if (UtilGestao.isDiaDeTrabahoDaTurma(estagiario.getTurma().getHorarios(), inicioPeriodoTemporario)) {
				Frequencia frequencia = new Frequencia();
				frequencia.setTipoFrequencia(TipoFrequencia.NORMAL);
				frequencia.setData(inicioPeriodoTemporario.toDate());
				frequencia.setStatusFrequencia(StatusFrequencia.AGUARDO);

				frequencias.add(frequencia);
			}
			inicioPeriodoTemporario = inicioPeriodoTemporario.plusDays(1);
		}

		return frequencias;
	}
	
		
}
