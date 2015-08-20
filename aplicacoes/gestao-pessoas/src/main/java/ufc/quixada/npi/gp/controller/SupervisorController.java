package ufc.quixada.npi.gp.controller;

import static ufc.quixada.npi.gp.utils.Constants.PAGINA_DECLARACAO_ESTAGIO;
import static ufc.quixada.npi.gp.utils.Constants.PAGINA_TCE;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import net.objectlab.kit.datecalc.common.DateCalculator;
import net.objectlab.kit.datecalc.common.DefaultHolidayCalendar;
import net.objectlab.kit.datecalc.common.HolidayCalendar;
import net.objectlab.kit.datecalc.common.HolidayHandlerType;
import net.objectlab.kit.datecalc.joda.LocalDateKitCalculatorsFactory;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.joda.time.LocalDate;
import org.springframework.http.HttpStatus;
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
import ufc.quixada.npi.gp.model.Periodo;
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
import ufc.quixada.npi.gp.service.PeriodoService;
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
	private EstagiarioService estagiarioService;
	
	@Inject
	private TurmaService turmaService;

	@Inject
	private PeriodoService periodoService;
	
	@Inject
	private FolgaService folgaService;
	
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

		return "redirect:/supervisor/minhas-turmas";
	}
	
	@RequestMapping(value = "/tce-turma/{idTurma}", method = RequestMethod.GET)
	public String gerarTermoDeCompromisso(@PathVariable("idTurma") Long idTurma, Model model) throws JRException {
		jrDatasource = new JRBeanCollectionDataSource(estagiarioService.getEstagiarioTurma(idTurma));
		
		model.addAttribute("datasource", jrDatasource);
		model.addAttribute("format", "pdf");
		return PAGINA_TCE;
	}

	@RequestMapping(value = "/declaracoes-turma/{idTurma}", method = RequestMethod.GET)
	public String gerarDeclaracaoEstagio( Model model, @PathVariable("idTurma") Long idTurma) throws JRException {
		jrDatasource = new JRBeanCollectionDataSource(estagiarioService.getEstagiarioTurma(idTurma));
		
		model.addAttribute("datasource", jrDatasource);
		model.addAttribute("format", "pdf");
		return PAGINA_DECLARACAO_ESTAGIO;
	}

	@RequestMapping(value = "/periodos", method = RequestMethod.GET)
	public String listarPeriodos(Model model) {
		model.addAttribute("periodos", periodoService.find(Periodo.class));
		return "supervisor/list-periodos";
	}

	@RequestMapping(value = "/adicionar-periodo", method = RequestMethod.GET)
	public String novoPeriodo(Model model) {
		model.addAttribute("periodo", new Periodo());
		model.addAttribute("action", "cadastrar");

		return "supervisor/form-periodo";
	}

	@RequestMapping(value = "/adicionar-periodo", method = RequestMethod.POST)
	public String adicionarPeriodo(Model model, @Valid @ModelAttribute("periodo") Periodo periodo, BindingResult result, RedirectAttributes redirectAttributes) {
		model.addAttribute("action", "cadastrar");

		if (result.hasErrors()) {
			return "supervisor/form-periodo";
		}
			
		try {
			periodoService.save(periodo);
		} catch (Exception e) {
			model.addAttribute("erro", "O periodo "  + periodo.getAno() + "." + periodo.getSemestre() + " já esta cadastrado.");
			return "supervisor/form-periodo";
		}
		
		redirectAttributes.addAttribute("info", "Periodo " + periodo.getAno() + "." + periodo.getSemestre() + " cadastrado com sucesso.");
		return "redirect:/supervisor/periodos";

	}

	@RequestMapping(value = "/editar-periodo/{idPeriodo}", method = RequestMethod.GET)
	public String paginaEditarPeriodo(@PathVariable("idPeriodo") Long idPeriodo, Model model) {
		model.addAttribute("periodo", periodoService.find(Periodo.class, idPeriodo));
		model.addAttribute("action", "editar");

		return "supervisor/form-periodo";
	}

	@RequestMapping(value = "/editar-periodo/{idPeriodo}", method = RequestMethod.POST)
	public String editarPeriodo(Model model, @Valid @ModelAttribute("periodo") Periodo periodo, BindingResult result, RedirectAttributes redirectAttributes) {
		model.addAttribute("action", "editar");

		if (result.hasErrors()) {
			return "supervisor/form-periodo";
		}

		try {
			Periodo periodoBanco = periodoService.find(Periodo.class, periodo.getId());

			periodo.setFolgas(periodoBanco.getFolgas());
			periodo.setTurmas(periodoBanco.getTurmas());
			periodoService.update(periodo);
		} catch (Exception e) {
			model.addAttribute("erro", "O periodo "  + periodo.getAno() + "." + periodo.getSemestre() + " já esta cadastrado.");
			return "supervisor/form-periodo";
		}

		redirectAttributes.addAttribute("info", "Periodo " + periodo.getAno() + "." + periodo.getSemestre() + " atualizado com sucesso.");
		return "redirect:/supervisor/periodos";
	}
	
	@RequestMapping(value = "/informacoes-periodo/{idPeriodo}", method = RequestMethod.GET)
	public String detalhesPeriodo(@PathVariable("idPeriodo") Long idPeriodo, Model model) {
		model.addAttribute("periodo", periodoService.find(Periodo.class, idPeriodo));

		return "supervisor/info-periodo";
	}

	@RequestMapping(value = "periodo/{idPeriodo}/adicionar-turma", method = RequestMethod.GET)
	public String novaTurmaPeriodo(Model model, @PathVariable("idPeriodo") Long idPeriodo) {
		model.addAttribute("action", "cadastrar");

		model.addAttribute("periodo", periodoService.find(Periodo.class, idPeriodo));

		Turma turma = new Turma();

		model.addAttribute("turma", turma);
		model.addAttribute("dias", Dia.values());
		
		return "supervisor/form-turma";
	}

	@RequestMapping(value = "periodo/{idPeriodo}/adicionar-turma", method = RequestMethod.POST)
	public String adicionarTurmaPeriodo(Model model, @Valid @ModelAttribute("turma") Turma turma,  @PathVariable("idPeriodo") Long idPeriodo, BindingResult result, HttpSession session) {
		model.addAttribute("action", "cadastrar");

		Periodo periodo = periodoService.find(Periodo.class, idPeriodo);
		
		if (turma.getHorarios() == null) {
			model.addAttribute("periodo", periodo);
			model.addAttribute("dias", Dia.values());
			model.addAttribute("horarioError", "Informe pelo menos um horario.");
			return "supervisor/form-turma";
		}

		if (result.hasErrors()) {
			model.addAttribute("periodo", periodo);
			model.addAttribute("dias", Dia.values());
			return "supervisor/form-turma";
		}
		
		
		Pessoa pessoa = getUsuarioLogado(session);
		
		turma.setSupervisor(pessoa);
		turma.setPeriodo(periodo);
		turmaService.save(turma);
		
		return "redirect:/supervisor/periodos";
	}
	
	@RequestMapping(value = "/periodo/{idPeriodo}/adicionar-folga", method = RequestMethod.GET)
	public String novaFolgaPeriodo(@PathVariable("idPeriodo") Long idPeriodo, Model model) {
		model.addAttribute("action", "cadastrar");
		model.addAttribute("periodo", periodoService.find(Periodo.class, idPeriodo));
		model.addAttribute("folga", new Folga());

		return "supervisor/form-folga";
	}

	@RequestMapping(value = "/periodo/{idPeriodo}/adicionar-folga", method = RequestMethod.POST)
	public String adicionarFolgaPeriodo(@PathVariable("idPeriodo") Long idPeriodo, @Valid @ModelAttribute("folga") Folga folga, BindingResult result, Model model) {
		model.addAttribute("action", "cadastrar");
		
		Periodo periodo = periodoService.find(Periodo.class, idPeriodo);
		
		if (result.hasErrors()) {
			model.addAttribute("periodo", periodo);
			return "supervisor/form-folga";
		}

		folga.setPeriodo(periodo);

		folgaService.save(folga);

		return "redirect:/supervisor/periodos";
	}
	
	@RequestMapping(value = "/periodo/{idPeriodo}/editar-folga/{idFolga}", method = RequestMethod.GET)
	public String paginaEditarFolga(@PathVariable("idFolga") Long idFolga, Model model) {
		model.addAttribute("action", "editar");
		Folga folga = folgaService.find(Folga.class, idFolga);
		model.addAttribute("folga", folga);
		model.addAttribute("periodo", folga.getPeriodo());

		return "supervisor/form-folga";
	}

	@RequestMapping(value = "/periodo/{idPeriodo}/editar-folga/{idFolga}", method = RequestMethod.POST)
	public String editarFolga(@PathVariable("idPeriodo") Long idPeriodo, @Valid @ModelAttribute("folga") Folga folga, BindingResult result, Model model) {
		model.addAttribute("action", "editar");
		
		Periodo periodo = periodoService.find(Periodo.class, idPeriodo);
		
		if (result.hasErrors()) {
			model.addAttribute("periodo", periodo);
			return "supervisor/form-folga";
		}

		folga.setPeriodo(periodo);

		folgaService.update(folga);

		return "redirect:/supervisor/periodos";
	}
	
	@RequestMapping(value = "/minhas-turmas", method = RequestMethod.GET)
	public String listarTurmas(Model model, HttpSession session) {
		Pessoa pessoa = getUsuarioLogado(session);
		model.addAttribute("turmas", turmaService.getMinhasTurma(pessoa.getId()));

		return "supervisor/list-minhas-turmas";
	}

	@RequestMapping(value = "informacoes-turma/{idTurma}", method = RequestMethod.GET)
	public String detalhesTurma(@PathVariable("idTurma") Long idTurma, Model model) {
		model.addAttribute("turma", turmaService.find(Turma.class, idTurma));
		return "supervisor/info-turma";
	}
	
	@RequestMapping(value = "/vincular-estagiarios-turma/{id}", method = RequestMethod.GET)
	public String paginaVincularEstagiarioTurma(Model model, HttpSession session, @PathVariable("id") Long idTurma)  {

		model.addAttribute("turma", turmaService.find(Turma.class, idTurma));
		model.addAttribute("estagiarios", estagiarioService.find(Estagiario.class));

		return "supervisor/vincular-estagiarios-turma";
	}

	@RequestMapping(value = "/vincular-estagiarios-turma/{id}", method = RequestMethod.POST)
	public String atualizarVinculoEstagiarioTurma(Model model, HttpSession session, @ModelAttribute("turma") Turma turma)  {
		
		Turma turmaDoBanco = turmaService.find(Turma.class, turma.getId());
		
		turmaDoBanco.setEstagiarios(atualizarListaEstagiarios(turma));
		
		turmaService.update(turmaDoBanco);

		model.addAttribute("turma", turmaDoBanco);
		model.addAttribute("estagiarios", estagiarioService.find(Estagiario.class));

		return "redirect:/supervisor/informacoes-turma/" + turmaDoBanco.getId();
	}

	@RequestMapping(value = "/projetos", method = RequestMethod.GET)
	public String listarProjetos( Model model) {
		model.addAttribute("projetos", projetoService.find(Projeto.class));

		return "supervisor/list-projetos";
	}

	@RequestMapping(value = "/adicionar-projeto", method = RequestMethod.GET)
	public String novoProjeto( Model model) {
		model.addAttribute("action", "cadastrar");
		model.addAttribute("projeto", new Projeto());

		return "supervisor/form-projeto";
	}
	
	@RequestMapping(value = "/adicionar-projeto", method = RequestMethod.POST)
	public String adicionarProjeto(Model model, @Valid @ModelAttribute("projeto") Projeto projeto, BindingResult result) {
		model.addAttribute("action", "cadastrar");
		
		if (result.hasErrors()) {
			return "supervisor/form-projeto";
		}

		projetoService.save(projeto);

		return "redirect:/supervisor/projetos";
	}

	@RequestMapping(value = "/editar-projeto/{idProjeto}", method = RequestMethod.GET)
	public String editarProjeto(@PathVariable("idProjeto") Long idProjeto, Model model) {
		model.addAttribute("action", "editar");
		model.addAttribute("projeto", projetoService.find(Projeto.class, idProjeto));

		return "supervisor/form-projeto";
	}

	@RequestMapping(value = "/editar-projeto/{idProjeto}", method = RequestMethod.POST)
	public String editarProjeto(Model model, @Valid @ModelAttribute("projeto") Projeto projeto, BindingResult result) {
		model.addAttribute("action", "editar");

		if (result.hasErrors()) {
			return "supervisor/form-projeto";
		}
	
		projeto = atualizarProjeto(projeto);

		projetoService.update(projeto);

		return "redirect:/supervisor/projetos";
	}

	@RequestMapping(value = "/informacoes-projeto/{idProjeto}", method = RequestMethod.GET)
	public String detalhesProjeto(@PathVariable("idProjeto") Long idProjeto, Model model) {
		Projeto projeto = projetoService.find(Projeto.class, idProjeto);

		model.addAttribute("projeto", projeto);

		return "supervisor/info-projeto";
	}

	@RequestMapping(value = "/excluir-projeto/{idProjeto}", method = RequestMethod.GET)
	public String excluirProjeto(@PathVariable("idProjeto") Long idProjeto, Model model) {
		Projeto projeto = projetoService.find(Projeto.class, idProjeto);
		
		if(projeto != null){
			projetoService.delete(projeto);
		}

		return "redirect:/supervisor/projetos";
	}
	
	@RequestMapping(value = "/vincular-membros-projeto/{idProjeto}", method = RequestMethod.GET)
	public String paginaVincularMembrosProjeto(Model model, @PathVariable("idProjeto") Long idProjeto, HttpSession session) {
		Pessoa pessoa = getUsuarioLogado(session);

		model.addAttribute("turmas", turmaService.getTurmasSupervisorByStatus(StatusTurma.ABERTA, pessoa.getId()));
		model.addAttribute("projeto", projetoService.find(Projeto.class, idProjeto));
		
		return "supervisor/vincular-membros-projeto";
	}
	
	@RequestMapping(value = "/vincular-membros-projeto/{idProjeto}/turma/{idTurma}", method = RequestMethod.GET)
	public String vincularEstagiariosProjeto(Model model, HttpSession session, @PathVariable("idTurma") Long idTurma, @PathVariable("idProjeto") Long idProjeto) {
		Pessoa pessoa = getUsuarioLogado(session);

		model.addAttribute("turmas", turmaService.getTurmasSupervisorByStatus(StatusTurma.ABERTA, pessoa.getId()));
		model.addAttribute("projeto", projetoService.find(Projeto.class, idProjeto));
		model.addAttribute("estagiarios", turmaService.find(Turma.class, idTurma).getEstagiarios());

		return "supervisor/vincular-membros-projeto";
	}

	@RequestMapping(value = "/vincular-membros-projeto/{idProjeto}/turma/{idTurma}", method = RequestMethod.POST)
	public String vincularMembrosProjeto(Model model, @ModelAttribute("projeto") Projeto projeto) {
		
		projeto = atualizarProjeto(projeto);
		
		projetoService.update(projeto);

		return "redirect:/supervisor/projetos";
	}

	@RequestMapping(value = "/estagiarios", method = RequestMethod.GET)
	public String listaEstagiarios(Model model, HttpSession session) {
		Pessoa pessoa = getUsuarioLogado(session);

		model.addAttribute("turmas", turmaService.getTurmasSupervisorByStatus(StatusTurma.ABERTA, pessoa.getId()));

		return "supervisor/list-estagiarios";
	}

	@RequestMapping(value = "/estagiarios-turma/{idTurma}", method = RequestMethod.GET)
	public String estagiarios(Model model, HttpSession session, @PathVariable("idTurma") Long idTurma) {
		Pessoa pessoa = getUsuarioLogado(session);

		model.addAttribute("turmas", turmaService.getTurmasSupervisorByStatus(StatusTurma.ABERTA, pessoa.getId()));

		List<Estagiario> estagiarios = turmaService.find(Turma.class, idTurma).getEstagiarios();
		
		if(estagiarios.isEmpty()){
			model.addAttribute("warning", "Não há estagiarios vinculados a esta turma.");
		}else{
			model.addAttribute("estagiarios", estagiarios);
		}

		return "supervisor/list-estagiarios";
	}
	
	@RequestMapping(value = "/frequencias", method = RequestMethod.GET)
	public String paginaListarFrequenciaTurma(Model model, HttpSession session) {
		Pessoa pessoa = getUsuarioLogado(session);

		model.addAttribute("turmas", turmaService.getTurmasSupervisorByStatus(StatusTurma.ABERTA, pessoa.getId()));

		return "supervisor/list-frequencias";
	}

	@RequestMapping(value = "/frequencias-turma/{idTurma}", method = RequestMethod.GET)
	public String listarFrequenciaTurma(@PathVariable("idTurma") Long idTurma, Model model, HttpSession session) {
		Pessoa pessoa = getUsuarioLogado(session);
		Date dataAtual = new Date();
		List<Frequencia> frequencias = frequenciaService.getFrequencias(dataAtual, idTurma);

		model.addAttribute("turma", turmaService.getTurmaSupervisorById(idTurma, pessoa.getId()));
		model.addAttribute("turmas", turmaService.getTurmasSupervisorByStatus(StatusTurma.ABERTA, pessoa.getId()));
		model.addAttribute("frequencias", frequencias);
		model.addAttribute("dataSelecionada", dataAtual);

		return "supervisor/list-frequencias";
	}

	@RequestMapping(value = "/frequencias-turma/{idTurma}", method = RequestMethod.POST)
	public String listarFrequenciaTurmaData(@PathVariable("idTurma") Long idTurma, @RequestParam("data") Date data, Model model, HttpSession session) {
		Pessoa pessoa = getUsuarioLogado(session);

		List<Frequencia> frequencias = frequenciaService.getFrequencias(data, idTurma);

		model.addAttribute("turma", turmaService.getTurmaSupervisorById(idTurma, pessoa.getId()));
		model.addAttribute("turmas", turmaService.getTurmasSupervisorByStatus(StatusTurma.ABERTA, pessoa.getId()));
		model.addAttribute("frequencias", frequencias);
		model.addAttribute("dataSelecionada", data);

		return "supervisor/list-frequencias";
	}
	
	@RequestMapping(value = "/frequencia-estagiario/{idEstagiario}", method = RequestMethod.GET)
	public String minhaPresenca(HttpSession session, Model model, @PathVariable("idEstagiario") Long idEstagiario) {

		Estagiario estagiario = estagiarioService.find(Estagiario.class, idEstagiario);
		
		boolean frequenciaNaoRealizada = frequenciaService.getFrequenciaDeHojeByEstagiario(estagiario.getId()) == null ? true : false;
		
		List<Frequencia> frequencias = frequenciaService.getFrequenciaByEstagiario(estagiario.getId());

		LocalDate inicioPeriodoTemporario;
		if(!frequenciaNaoRealizada){
			inicioPeriodoTemporario = new LocalDate(new Date()).plusDays(1);
		}else{
			inicioPeriodoTemporario = new LocalDate(new Date());
		}
		
		LocalDate fimPeriodo = new LocalDate(estagiario.getTurma().getPeriodo().getTermino());
		frequencias.addAll(gerarFrequencia(estagiario, inicioPeriodoTemporario, fimPeriodo));

		DadoConsolidado dadosConsolidados = frequenciaService.calcularDadosConsolidados(frequencias);

		model.addAttribute("estagiario", estagiario);
		model.addAttribute("frequencias", frequencias);
		model.addAttribute("dadosConsolidados", dadosConsolidados);		
		
		return "supervisor/frequencia-estagiario";
	}
	@RequestMapping(value = "/frequencia-realizar-observacao", method = RequestMethod.POST)
	public String frequenciaObservar(@RequestParam("pk") Long idFrequencia, @RequestParam("value") String observacao, Model model) {
		Frequencia frequencia = frequenciaService.find(Frequencia.class, idFrequencia);
		
		if(frequencia != null){
			frequencia.setObservacao(observacao);
			frequenciaService.update(frequencia);
			return "supervisor/frequencia-estagiario";
		}
		
		return "";
	}
	
	@RequestMapping(value = "/frequencia-atualizar-status", method = RequestMethod.POST)
	public String atualizarStatus(@RequestParam("pk") Long idFrequencia, @RequestParam("value") StatusFrequencia status, Model model, RedirectAttributes redirectAttributes) {
		Frequencia frequencia = frequenciaService.find(Frequencia.class, idFrequencia);
		
		if(frequencia != null){
			frequencia.setStatusFrequencia(status);
			frequenciaService.update(frequencia);
			return "supervisor/frequencia-estagiario";
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

		if (estagiario.getTurma().getPeriodo().getFolgas() != null) {
			for (Folga folga : estagiario.getTurma().getPeriodo().getFolgas()) {
				dataDosFeriados.add(new LocalDate(folga.getData()));
			}
		}

		HolidayCalendar<LocalDate> calendarioDeFeriados = new DefaultHolidayCalendar<LocalDate>(dataDosFeriados);
		LocalDateKitCalculatorsFactory.getDefaultInstance().registerHolidays("NPI", calendarioDeFeriados);
		DateCalculator<LocalDate> calendario = LocalDateKitCalculatorsFactory.getDefaultInstance().getDateCalculator("NPI", HolidayHandlerType.FORWARD);

		List<Frequencia> frequencias = new ArrayList<Frequencia>();
		
		int cont = 0;
		
		while (!inicioPeriodoTemporario.isAfter(fimPeriodo)) {
			cont++;

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
