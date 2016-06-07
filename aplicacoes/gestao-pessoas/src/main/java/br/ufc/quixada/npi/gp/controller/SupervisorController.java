package br.ufc.quixada.npi.gp.controller;

import static br.ufc.quixada.npi.gp.utils.Constants.ACOMPANHAMENTO_ESTAGIARIO;
import static br.ufc.quixada.npi.gp.utils.Constants.AVALIAR_RELATORIO;
import static br.ufc.quixada.npi.gp.utils.Constants.DECLARACAO_ESTAGIO;
import static br.ufc.quixada.npi.gp.utils.Constants.DETALHES_FREQUENCIA_ESTAGIARIO;
import static br.ufc.quixada.npi.gp.utils.Constants.DETALHES_TURMA;
import static br.ufc.quixada.npi.gp.utils.Constants.FORMULARIO_ADICIONAR_AVALIACAO_RENDIMENTO;
import static br.ufc.quixada.npi.gp.utils.Constants.FORMULARIO_ADICIONAR_TURMA;
import static br.ufc.quixada.npi.gp.utils.Constants.FORMULARIO_AVALIAR_PLANO;
import static br.ufc.quixada.npi.gp.utils.Constants.FORMULARIO_EDITAR_AVALIACAO_RENDIMENTO;
import static br.ufc.quixada.npi.gp.utils.Constants.FORMULARIO_EDITAR_TURMA;
import static br.ufc.quixada.npi.gp.utils.Constants.MAPA_FREQUENCIAS;
import static br.ufc.quixada.npi.gp.utils.Constants.NOME_USUARIO;
import static br.ufc.quixada.npi.gp.utils.Constants.PAGINA_INICIAL_SUPERVISOR;
import static br.ufc.quixada.npi.gp.utils.Constants.REDIRECT_ACOMPANHAMENTO_ESTAGIARIO;
import static br.ufc.quixada.npi.gp.utils.Constants.REDIRECT_DETALHES_TURMA;
import static br.ufc.quixada.npi.gp.utils.Constants.REDIRECT_PAGINA_INICIAL_SUPERVISOR;
import static br.ufc.quixada.npi.gp.utils.Constants.TERMO_COMPROMISSO_ESTAGIO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.ufc.quixada.npi.gp.model.AvaliacaoRendimento;
import br.ufc.quixada.npi.gp.model.Papel;
import br.ufc.quixada.npi.gp.model.Pessoa;
import br.ufc.quixada.npi.gp.model.Servidor;
import br.ufc.quixada.npi.gp.model.Submissao;
import br.ufc.quixada.npi.gp.model.Submissao.StatusEntrega;
import br.ufc.quixada.npi.gp.model.Submissao.TipoSubmissao;
import br.ufc.quixada.npi.gp.model.Turma;
import br.ufc.quixada.npi.gp.service.EstagioService;
import br.ufc.quixada.npi.gp.service.PessoaService;
import br.ufc.quixada.npi.gp.service.TurmaService;
import br.ufc.quixada.npi.ldap.service.UsuarioService;
import net.sf.jasperreports.engine.JRException;

@Component
@Controller
@RequestMapping("Supervisor")
public class SupervisorController {

	@Autowired
	private PessoaService pessoaService;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private EstagioService estagioService;

	// @Inject
	// private EstagioService estagioService;
	//
	@Autowired
	private TurmaService turmaService;
	//
	// private JRDataSource jrDatasource;

	@RequestMapping(value = { "", "/", "/Turmas" }, method = RequestMethod.GET)
	public String listarTurmas(Model model, HttpSession session) {
		inserirNomeUsuarioNaSessao(session);

		Pessoa pessoa = pessoaService.buscarPessoaPorCpf(getCpfUsuarioLogado());

		if (pessoa == null) {
			pessoa = new Pessoa();
			pessoa.setCpf(getCpfUsuarioLogado());
			pessoa.setPapeis(new ArrayList<Papel>());
			pessoa.getPapeis().add(pessoaService.buscarPapelPorNome("ROLE_SUPERVISOR"));

			pessoaService.adicionarPessoa(pessoa);

			Servidor servidor = new Servidor();
			servidor.setPessoa(pessoa);
			servidor.setSiape(usuarioService.getByCpf(getCpfUsuarioLogado()).getSiape());

			pessoaService.adicionarServidor(servidor);
		}

		return PAGINA_INICIAL_SUPERVISOR;
	}

	@RequestMapping(value = "/Turma/Adicionar", method = RequestMethod.GET)
	public String formularioAdicionarTurma(Model model) {
		// model.addAttribute("turma", new Turma());
		return FORMULARIO_ADICIONAR_TURMA;
	}

	@RequestMapping(value = "/Turma/Adicionar", method = RequestMethod.POST)
	public String adicionarTurma(Model model, @Valid @ModelAttribute("turma") Turma turma, BindingResult result,
			HttpSession session, RedirectAttributes redirect) {

		// model.addAttribute("action", "cadastrar");
		// turma.setHorarios(atualizarHorarios(turma));
		//
		// if (result.hasErrors()) {
		// model.addAttribute("dias", Dia.values());
		// return PAGINA_FORM_TURMA;
		// }
		//
		// String cpf =
		// SecurityContextHolder.getContext().getAuthentication().getName();
		// Pessoa pessoa = pessoaService.buscarPessoaPorCpf(cpf);
		//
		// turma.setSupervisor(pessoa);
		// turmaService.save(turma);

		redirect.addFlashAttribute("success", "Turma cadastrada com sucesso.");

		return REDIRECT_DETALHES_TURMA;
	}

	@RequestMapping(value = "/Turma/{idTurma}/Editar", method = RequestMethod.GET)
	public String formularioEditarTurma(@PathVariable("idTurma") Long idTurma, Model model, HttpSession session) {

		// String cpf =
		// SecurityContextHolder.getContext().getAuthentication().getName();
		// Pessoa pessoa = pessoaService.buscarPessoaPorCpf(cpf);
		//
		// model.addAttribute("turma",
		// turmaService.getTurmaByIdAndSupervisorById(idTurma, pessoa.getId()));

		return FORMULARIO_EDITAR_TURMA;
	}

	@RequestMapping(value = "/Turma/{idTurma}/Editar", method = RequestMethod.POST)
	public String editarTurma(Model model, @Valid @ModelAttribute("turma") Turma turma, BindingResult result,
			HttpSession session) {

		// if (result.hasErrors()) {
		// model.addAttribute("dias", Dia.values());
		// return FORMULARIO_EDITAR_ESTAGIARIO;
		// }
		//
		// Pessoa pessoa = getUsuarioLogado(session);
		// Turma turmaDoBanco =
		// turmaService.getTurmaByIdAndSupervisorById(turma.getId(),
		// pessoa.getId());
		//
		// turmaDoBanco.setNome(turma.getNome());
		// turmaDoBanco.setStatusTurma(turma.getStatusTurma());
		// turmaDoBanco.setTipoTurma(turma.getTipoTurma());
		// turmaDoBanco.setSemestre(turma.getSemestre());
		// turmaDoBanco.setInicio(turma.getInicio());
		// turmaDoBanco.setTermino(turma.getTermino());
		//
		// turmaService.update(turmaDoBanco);

		return REDIRECT_DETALHES_TURMA;
	}

	@RequestMapping(value = "/Turma/{idTurma}", method = RequestMethod.GET)
	public String visualizarDetalhesTurma(@PathVariable("idTurma") Long idTurma, RedirectAttributes redirect,
			Model model, HttpSession session) {
		//
		// Servidor servidor = pessoaService.buscarServidorPorCpf(getCpf());
		//
		// model.addAttribute("turma",
		// turmaService.getTurmaByIdAndSupervisorById(idTurma, pessoa.getId()));
		// turmaService.buscarTurmaPorIdEServidorId(idTurma, pessoa.getId());
		//
		//
		// List<Estagiario> aniversariantes =
		// estagiarioService.getAniversariantesMesByTurmaId(idTurma);
		// model.addAttribute("aniversariantes", aniversariantes);
		Pessoa pessoa = pessoaService.buscarPessoaPorCpf(getCpfUsuarioLogado());

		Turma turma = turmaService.buscarTurmaPorId(idTurma);

		if (turma == null) {
			redirect.addFlashAttribute("error", "Turma não existe");
			return REDIRECT_PAGINA_INICIAL_SUPERVISOR;
		}

		List<Servidor> supervisores = turma.getSupervisores();
		for (Servidor servidor : supervisores) {
			if (servidor.getPessoa().getCpf().equals(pessoa.getCpf())) {
				model.addAttribute("turma", turma);
				return DETALHES_TURMA;
			}
		}

		redirect.addFlashAttribute("error", "Permissão negada");
		return REDIRECT_PAGINA_INICIAL_SUPERVISOR;

	}

	@RequestMapping(value = "/Turma/{idTurma}/TermosCompromisso", method = RequestMethod.GET)
	public String gerarTermoDeCompromisso(@PathVariable("idTurma") Long idTurma, Model model) throws JRException {

		// Turma turma = turmaService.find(Turma.class, idTurma);
		//
		// Usuario usuario =
		// usuarioService.getByCpf(turma.getSupervisor().getCpf());
		//
		// jrDatasource = new
		// JRBeanCollectionDataSource(turma.getEstagiarios());
		//
		// SimpleDateFormat dataFormatada = new SimpleDateFormat("dd/MM/yyyy");
		//
		// model.addAttribute("NOME", usuario.getNome());
		// model.addAttribute("SIAPE", usuario.getSiape());
		// model.addAttribute("TELEFONE", usuario.getTelefone());
		// model.addAttribute("TURNO",
		// UtilGestao.getTurnoExpediente(turma.getHorarios().get(0)));
		// model.addAttribute("INICIO_ESTAGIO",
		// dataFormatada.format(turma.getInicio()));
		// model.addAttribute("FINAL_ESTAGIO",
		// dataFormatada.format(turma.getTermino()));
		// model.addAttribute("datasource", jrDatasource);
		// model.addAttribute("format", "pdf");
		//
		// if (turma.getHorarios() != null) {
		// model = configurarExpediente(turma.getHorarios(), model);
		// }

		return TERMO_COMPROMISSO_ESTAGIO;
	}

	@RequestMapping(value = "/Turma/{idTurma}/DeclaracaoEstagio", method = RequestMethod.GET)
	public String gerarDeclaracaoEstagio(Model model, @PathVariable("idTurma") Long idTurma) throws JRException {
		// jrDatasource = new
		// JRBeanCollectionDataSource(estagiarioService.getEstagiarioByTurmaId(idTurma));
		//
		// model.addAttribute("datasource", jrDatasource);
		// model.addAttribute("format", "pdf");

		return DECLARACAO_ESTAGIO;
	}

	@RequestMapping(value = "/Turma/{idTurma}/MapaFrequencia/{data}", method = RequestMethod.GET)
	public String listarFrequenciaTurma(@PathVariable("idTurma") Long idTurma, @PathVariable("data") String data,
			Model model, HttpSession session) {
		// Pessoa pessoa = getUsuarioLogado(session);
		// Date dataAtual = new Date();
		// List<Frequencia> frequencias =
		// frequenciaService.getFrequenciasByTurmaIdAndData(dataAtual, idTurma);
		// List<Estagiario> estagiarios =
		// frequenciaService.getEstagiariosSemFrequencia(dataAtual, idTurma);
		//
		// model.addAttribute("turma",
		// turmaService.getTurmaByIdAndSupervisorById(idTurma, pessoa.getId()));
		// model.addAttribute("frequencias", frequencias);
		// model.addAttribute("estagiarios", estagiarios);
		// model.addAttribute("dataAtual", dataAtual);

		// String cpf =
		// SecurityContextHolder.getContext().getAuthentication().getName();
		// Pessoa pessoa = pessoaService.buscarPessoaPorCpf(cpf);
		//
		// List<Frequencia> frequencias =
		// frequenciaService.getFrequenciasByTurmaIdAndData(data, idTurma);
		//
		// List<Estagiario> estagiarios =
		// frequenciaService.getEstagiariosSemFrequencia(data, idTurma);
		//
		// model.addAttribute("turma",
		// turmaService.getTurmaByIdAndSupervisorById(idTurma, pessoa.getId()));
		// model.addAttribute("turmas",
		// turmaService.getTurmasBySupervisorIdAndStatus(StatusTurma.ABERTA,
		// pessoa.getId()));
		// model.addAttribute("frequencias", frequencias);
		// model.addAttribute("estagiarios", estagiarios);
		// model.addAttribute("dataAtual", new Date());
		// return "supervisor/list-frequencias";

		return MAPA_FREQUENCIAS;
	}

	@RequestMapping(value = "/Turma/Acompanhamento/{idEstagio}", method = RequestMethod.GET)
	public String detalhesAcompanhamentoEstagiario(Model model, @PathVariable("idEstagio") Long idEstagio) {
		return ACOMPANHAMENTO_ESTAGIARIO;
	}

	@RequestMapping(value = "/Turma/Acompanhamento/{idEstagio}/AvaliarPlano", method = RequestMethod.GET)
	public String formularioAvaliarPlanoEstagio(@PathVariable("idEstagio") Long idEstagio, Model model) {

		Submissao submissaoPlano = estagioService.buscarSubmissaoPorTipoSubmissaoEEstagioId(TipoSubmissao.PLANO_ESTAGIO,
				idEstagio);
		model.addAttribute("submissaoPlano", submissaoPlano);

		return FORMULARIO_AVALIAR_PLANO;
	}

	@RequestMapping(value = "/Turma/Acompanhamento/{idEstagio}/AvaliarPlano", method = RequestMethod.POST)
	public String avaliarPlanoEstagio(RedirectAttributes redirect, @PathVariable("idEstagio") Long idEstagio,
			@RequestParam("nota") Double nota, @RequestParam("status") Submissao.StatusEntrega status,
			@RequestParam("comentario") String comentario) {

		Submissao submissao = estagioService.buscarSubmissaoPorTipoSubmissaoEEstagioId(TipoSubmissao.PLANO_ESTAGIO,
				idEstagio);

		submissao.setStatusEntrega(status);
		submissao.setNota(nota);
		submissao.setComentario(comentario);

		estagioService.avaliarSubmissao(submissao);

		return REDIRECT_ACOMPANHAMENTO_ESTAGIARIO + idEstagio;
	}

	@RequestMapping(value = "/Turma/Acompanhamento/{idEstagio}/AvaliarRelatorio", method = RequestMethod.GET)
	public String avaliarRelatorio(RedirectAttributes redirect, @PathVariable("idEstagio") Long idEstagio,
			Model model) {

		Submissao submissaoRelatorio = estagioService
				.buscarSubmissaoPorTipoSubmissaoEEstagioId(TipoSubmissao.RELATORIO_FINAL_ESTAGIO, idEstagio);
		model.addAttribute("submissaoRelatorio", submissaoRelatorio);

		return AVALIAR_RELATORIO;
	}

	@RequestMapping(value = "/Turma/Acompanhamento/{idEstagio}/AvaliarRelatorio", method = RequestMethod.POST)
	public String avaliarRelatorioFinalEstagio(RedirectAttributes redirect, @PathVariable("idEstagio") Long idEstagio,
			@RequestParam("nota") Double nota, @RequestParam("status") StatusEntrega status,
			@RequestParam("comentario") String comentario) {

		Submissao submissao = estagioService.buscarSubmissaoPorTipoSubmissaoEEstagioId(TipoSubmissao.RELATORIO_FINAL_ESTAGIO, idEstagio);
		
		submissao.setStatusEntrega(status);
		submissao.setNota(nota);
		submissao.setComentario(comentario);

		estagioService.avaliarSubmissao(submissao);

		return REDIRECT_ACOMPANHAMENTO_ESTAGIARIO + idEstagio;
	}

	@ModelAttribute("statusEntrega")
	public List<Submissao.StatusEntrega> statusEntrega() {
		return Arrays.asList(Submissao.StatusEntrega.values());
	}

	// AVALIAÇÃO DE RENDIMENTO

	@RequestMapping(value = "/Turma/AcompanhamentoEstagiario/{idEstagio}/AvaliacaoRendimento", method = RequestMethod.GET)
	public String formularioAdicionarAvaliacaoRendimento(Model model, @PathVariable("idEstagio") Long idEstagio) {
		// model.addAttribute("avaliacaoEstagio",
		// avaliacaoService.getAvaliacoesEstagioByEstagiarioIdAndTurmaById(idEstagiario,
		// idTurma));
		// model.addAttribute("turma", turmaService.find(Turma.class, idTurma));
		// model.addAttribute("estagiario",
		// estagiarioService.find(Estagiario.class, idEstagiario));
		// model.addAttribute("submissoes",
		// turmaService.getSubmissoesByEstagiarioIdAndIdTurma(idEstagiario,
		// idTurma));

		return FORMULARIO_ADICIONAR_AVALIACAO_RENDIMENTO;
	}

	@RequestMapping(value = "/Turma/AcompanhamentoEstagiario/{idEstagio}/AvaliacaoRendimento", method = RequestMethod.POST)
	public String adicionarAvaliacaoRendimento(Model model,
			@Valid @ModelAttribute("avaliacaoRendimento") AvaliacaoRendimento avaliacaoRendimento,
			RedirectAttributes redirect, @PathVariable("idEstagio") Long idEstagio,
			@RequestParam("arquivoAvaliacaoRendimento") MultipartFile arquivoAvaliacaoRendimento) {

		// if(!rendimento.getContentType().equals("application/pdf")){
		// redirect.addFlashAttribute("error", "Escolha um arquivo pdf.");
		// return
		// "redirect:/supervisor/turma/{idTurma}/acompanhamento-avaliacao/estagiario/{idEstagiario}";
		// }
		//
		// model.addAttribute("action", "cadastrar");
		// Pessoa pessoa = getUsuarioLogado(session);
		// Estagiario estagiario = estagiarioService.find(Estagiario.class,
		// idEstagiario);
		// Turma turma = turmaService.getTurmaByIdAndEstagiarioId(idTurma,
		// idEstagiario);
		//
		// Tipo tipo = Tipo.AVALIACAO_RENDIMENTO;
		//
		// try {
		// turmaService.submeterDocumento(estagiario, turma, tipo, rendimento);
		// } catch (IOException e) {
		// return "redirect:/500";
		// }
		//
		// Submissao submissao =
		// turmaService.getSubmissaoByEstagiarioIdAndIdTurmaAndTipo(idEstagiario,
		// idTurma, tipo);
		// Documento documento = submissao.getDocumento();
		// avaliacaoRendimento.setSupervisor(pessoa);
		// avaliacaoRendimento.setTurma(turma);
		// avaliacaoRendimento.setEstagiario(estagiario);
		// avaliacaoRendimento.setNota(nota);
		// avaliacaoRendimento.setDocumento(documento);
		// avaliacaoService.save(avaliacaoRendimento);
		// redirect.addFlashAttribute("success", "Avaliação cadastrada com
		// sucesso.");

		return REDIRECT_ACOMPANHAMENTO_ESTAGIARIO + idEstagio;
	}

	@RequestMapping(value = "/Turma/AcompanhamentoEstagiario/{idEstagio}/AvaliacaoRendimento/{idAvaliacaoRendimento}/Editar", method = RequestMethod.GET)
	public String formularioEditarAvaliacaoRendimento(Model model, @PathVariable("idEstagio") Long idEstagio) {
		// model.addAttribute("action", "editar");
		// model.addAttribute("avaliacaoRendimento",
		// avaliacaoService.find(AvaliacaoRendimento.class,
		// idAvaliacaoRendimento));
		// model.addAttribute("turma",
		// turmaService.getTurmaByIdAndEstagiarioId(idTurma, idEstagiario));
		// model.addAttribute("estagiario",
		// estagiarioService.find(Estagiario.class, idEstagiario));

		return FORMULARIO_EDITAR_AVALIACAO_RENDIMENTO;
	}

	@RequestMapping(value = "/Turma/AcompanhamentoEstagiario/{idEstagio}/AvaliacaoRendimento/{idAvaliacaoRendimento}/Editar", method = RequestMethod.POST)
	public String editarAvaliacaoRendimento(Model model,
			@Valid @ModelAttribute("avaliacaoRendimento") AvaliacaoRendimento avaliacaoRendimento,
			RedirectAttributes redirect, @PathVariable("idEstagio") Long idEstagio) {

		// model.addAttribute("action", "editar");
		// AvaliacaoRendimento avaliacaoDoBanco =
		// avaliacaoService.find(AvaliacaoRendimento.class,
		// avaliacaoRendimento.getId());
		// String cpf =
		// SecurityContextHolder.getContext().getAuthentication().getName();
		// Pessoa pessoa = pessoaService.buscarPessoaPorCpf(cpf);
		//
		// Estagiario estagiario = estagiarioService.find(Estagiario.class,
		// idEstagiario);
		// Turma turma = turmaService.getTurmaByIdAndEstagiarioId(idTurma,
		// idEstagiario);
		//
		// avaliacaoDoBanco.setSupervisor(pessoa);
		// avaliacaoDoBanco.setEstagiario(estagiario);
		// avaliacaoDoBanco.setTurma(turma);
		// avaliacaoDoBanco.setNota(avaliacaoRendimento.getNota());
		// avaliacaoDoBanco.setFatorAssiduidadeDisciplina(avaliacaoRendimento.getFatorAssiduidadeDisciplina());
		// avaliacaoDoBanco.setFatorIniciativaProdutividade(avaliacaoRendimento.getFatorIniciativaProdutividade());
		// avaliacaoDoBanco.setFatorRelacionamento(avaliacaoRendimento.getFatorRelacionamento());
		// avaliacaoDoBanco.setFatorResponsabilidade(avaliacaoRendimento.getFatorResponsabilidade());
		// avaliacaoDoBanco.setNotaSeminario(avaliacaoRendimento.getNotaSeminario());
		// avaliacaoDoBanco.setFatorComentarioSeminario(avaliacaoRendimento.getFatorComentarioSeminario());
		//
		// avaliacaoService.update(avaliacaoDoBanco);
		//
		return REDIRECT_ACOMPANHAMENTO_ESTAGIARIO + idEstagio;
	}

	@RequestMapping(value = "/Turma/AcompanhamentoEstagiario/{idEstagio}/Frequencia", method = RequestMethod.GET)
	public String detalhesFrequenciaEstagiario(Model model, @PathVariable("idEstagio") Long idEstagio) {
		return DETALHES_FREQUENCIA_ESTAGIARIO;
	}

	private void inserirNomeUsuarioNaSessao(HttpSession session) {
		if (session.getAttribute(NOME_USUARIO) == null) {
			session.setAttribute(NOME_USUARIO, usuarioService.getByCpf(getCpfUsuarioLogado()).getNome());
		}
	}

	private String getCpfUsuarioLogado() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}

	/**
	 * EM ESTUDO (SELECAO E INSCRICAO)
	 * 
	 * @RequestMapping(value = "/Turma/{idTurma}/Vincular", method =
	 *                       RequestMethod.GET) public String
	 *                       paginaVincularEstagiarioTurma(Model model,
	 *                       HttpSession session, @PathVariable("idTurma") Long
	 *                       idTurma) { String cpf =
	 *                       SecurityContextHolder.getContext().
	 *                       getAuthentication().getName(); Pessoa pessoa =
	 *                       pessoaService.buscarPessoaPorCpf(cpf);
	 * 
	 *                       model.addAttribute("turma",
	 *                       turmaService.getTurmaByIdAndSupervisorById(idTurma,
	 *                       pessoa.getId()));
	 *                       model.addAttribute("estagiariosDaTurma",
	 *                       estagiarioService.getEstagiarioByTurmaId(idTurma));
	 *                       model.addAttribute("outrosEstagiarios",
	 *                       estagiarioService.
	 *                       getEstagiarioByNotTurmaIdOrSemTurma(idTurma));
	 * 
	 *                       return PAGINA_FORM_VINCULOS; }
	 * 
	 * 
	 * @RequestMapping(value = "/Turma/{idTurma}/Vincular", method =
	 *                       RequestMethod.POST) public String
	 *                       atualizarVinculoEstagiarioTurma(Model model,
	 *                       HttpSession session, @ModelAttribute("turma") Turma
	 *                       turma) { String cpf =
	 *                       SecurityContextHolder.getContext().
	 *                       getAuthentication().getName(); Pessoa pessoa =
	 *                       pessoaService.buscarPessoaPorCpf(cpf);
	 * 
	 *                       Turma turmaDoBanco =
	 *                       turmaService.getTurmaByIdAndSupervisorById(turma.
	 *                       getId(), pessoa.getId());
	 * 
	 *                       List<Estagiario> estagiariosSelecionados = new
	 *                       ArrayList<Estagiario>();
	 * 
	 *                       if (turma.getEstagiarios() != null) {
	 *                       estagiariosSelecionados =
	 *                       getEstagiariosSelecionados(turma.getEstagiarios());
	 *                       estagiariosSelecionados =
	 *                       atualizarTurmaEstagiarios(estagiariosSelecionados,
	 *                       turmaDoBanco); }
	 * 
	 *                       turmaDoBanco.setEstagiarios(estagiariosSelecionados
	 *                       );
	 * 
	 *                       turmaService.update(turmaDoBanco);
	 * 
	 *                       model.addAttribute("turma", turmaDoBanco);
	 *                       model.addAttribute("estagiarios",
	 *                       estagiarioService.find(Estagiario.class));
	 * 
	 *                       return "redirect:/supervisor/turma/" +
	 *                       turmaDoBanco.getId(); }
	 * 
	 * 
	 *                       OPERAÇÕES DE CRUD DO EVENTO DA TURMA
	 * 
	 * @RequestMapping(value = "/Turma/{idTurma}/Evento/Adicionar", method =
	 *                       RequestMethod.POST) public String
	 *                       postAdicionarEventos(@ModelAttribute("evento")
	 *                       Evento evento, @PathVariable("idTurma") Long
	 *                       idTurma, HttpSession session, RedirectAttributes
	 *                       redirect, Model model) {
	 *                       model.addAttribute("action", "cadastrar"); Pessoa
	 *                       supervisor = getUsuarioLogado(session); Turma turma
	 *                       =
	 *                       turmaService.getTurmaByIdAndSupervisorById(idTurma,
	 *                       supervisor.getId());
	 * 
	 *                       evento.setTurma(turma); turmaService.save(evento);
	 * 
	 *                       redirect.addFlashAttribute("success",
	 *                       "Evento cadastrado com sucesso.");
	 * 
	 *                       return "redirect:/supervisor/turma/" + idTurma +
	 *                       "/evento"; }
	 * 
	 * @RequestMapping(value="/Turma/{idTurma}/Evento/{idEvento}/Editar", method
	 *                                                                    =
	 *                                                                    RequestMethod
	 *                                                                    .POST)
	 *                                                                    public
	 *                                                                    String
	 *                                                                    postEditarEvento
	 *                                                                    (
	 *                                                                    @ModelAttribute
	 *                                                                    (
	 *                                                                    "evento")
	 *                                                                    Evento
	 *                                                                    evento,
	 *                                                                    RedirectAttributes
	 *                                                                    redirect
	 *                                                                    ){
	 *                                                                    turmaService
	 *                                                                    .
	 *                                                                    update
	 *                                                                    (
	 *                                                                    evento
	 *                                                                    );
	 *                                                                    redirect
	 *                                                                    .
	 *                                                                    addFlashAttribute
	 *                                                                    (
	 *                                                                    "success",
	 *                                                                    "Alterações realizadas com sucesso!"
	 *                                                                    );
	 *                                                                    return
	 *                                                                    "redirect:/supervisor/turma/"
	 *                                                                    +
	 *                                                                    evento
	 *                                                                    .
	 *                                                                    getTurma
	 *                                                                    ().
	 *                                                                    getId(
	 *                                                                    ) +
	 *                                                                    "/evento";
	 *                                                                    }
	 * 
	 * @RequestMapping(value = "/Turma/{idTurma}/Evento/{idEvento}/Excluir",
	 *                       method = RequestMethod.GET) public String
	 *                       getExcluirEvento(@PathVariable("idEvento") Long
	 *                       idEvento, @PathVariable("idTurma") Long idTurma,
	 *                       RedirectAttributes redirect) {
	 *                       turmaService.excluirEvento(turmaService.
	 *                       buscarEventoPorIdETurmaId(idEvento, idTurma));
	 *                       redirect.addFlashAttribute("success",
	 *                       "Evento excluído com sucesso!"); return
	 *                       "redirect:/supervisor/turma/" + idTurma +
	 *                       "/evento"; }
	 */

	/**
	 * @RequestMapping(
	 * value = "/Turma/{idTurma}/Acompanhamento/{idEstagio}/Frequencias", method =
	 *                       RequestMethod.GET) public String
	 *                       minhaPresenca(HttpSession session, Model
	 *                       model, @PathVariable("idTurma") Long
	 *                       idTurma, @PathVariable("idEstagiario") Long
	 *                       idEstagiario) {
	 * 
	 *                       Estagiario estagiario =
	 *                       estagiarioService.find(Estagiario.class,
	 *                       idEstagiario); Turma turma =
	 *                       turmaService.find(Turma.class, idTurma);
	 * 
	 *                       List<Frequencia> frequenciaCompleta = new ArrayList
	 *                       <Frequencia>(); List
	 *                       <Frequencia> frequenciaPendentes =
	 *                       frequenciaService.frequenciaPendente(turma,
	 *                       estagiario);
	 * 
	 *                       frequenciaCompleta =
	 *                       frequenciaService.gerarFrequencia(turma,
	 *                       estagiario); DadoConsolidado dadosConsolidados =
	 *                       frequenciaService.calcularDadosConsolidados(
	 *                       frequenciaCompleta);
	 * 
	 *                       System.out.println("Pendentes: " +
	 *                       frequenciaPendentes.size());
	 * 
	 *                       model.addAttribute("estagiario", estagiario);
	 *                       model.addAttribute("turma", turma);
	 *                       model.addAttribute("frequencias",
	 *                       frequenciaCompleta);
	 * 
	 *                       model.addAttribute("pendentes",
	 *                       frequenciaPendentes.size());
	 * 
	 *                       model.addAttribute("dadosConsolidados",
	 *                       dadosConsolidados);
	 *                       model.addAttribute("statusFrequencias",
	 *                       StatusFrequencia.values());
	 *                       model.addAttribute("dataAtual", new Date());
	 * 
	 *                       return "supervisor/list-frequencia-estagiario"; }
	 * 
	 * @RequestMapping(
	 * value = "/Turma/{idTurma}/Acompanhamento/{idEstagio}/Frequencias/Pendente",
	 *                       method = RequestMethod.POST) public String
	 *                       lancarFrequencia(@PathVariable("idEstagiario") Long
	 *                       idEstagiario, @PathVariable("idTurma") Long
	 *                       idTurma, @RequestParam("data") Date
	 *                       data, @RequestParam("statusFrequencia")
	 *                       StatusFrequencia
	 *                       statusFrequencia, @RequestParam("observacao")
	 *                       String observacao, Model model, RedirectAttributes
	 *                       redirectAttributes) {
	 * 
	 *                       Turma turma = turmaService.find(Turma.class,
	 *                       idTurma); Estagiario estagiario =
	 *                       estagiarioService.find(Estagiario.class,
	 *                       idEstagiario);
	 * 
	 *                       Frequencia frequencia = frequenciaService.
	 *                       getFrequenciaByDataByTurmaByEstagiario(data,
	 *                       idTurma, idEstagiario);
	 * 
	 *                       if (frequencia == null) {
	 * 
	 *                       if (statusFrequencia == null) {
	 *                       redirectAttributes.addFlashAttribute("error",
	 *                       "Escolha um Status Válido"); } else { frequencia =
	 *                       new Frequencia(); frequencia.setTurma(turma);
	 *                       frequencia.setEstagiario(estagiario);
	 *                       frequencia.setData(data); frequencia.setHorario(new
	 *                       Date());
	 *                       frequencia.setTipoFrequencia(TipoFrequencia.NORMAL)
	 *                       ; frequencia.setStatusFrequencia(statusFrequencia);
	 *                       frequencia.setObservacao(observacao);
	 *                       frequenciaService.save(frequencia);
	 *                       redirectAttributes.addFlashAttribute("sucesso",
	 *                       "Frequência lançada com sucesso"); }
	 * 
	 *                       } else {
	 *                       redirectAttributes.addFlashAttribute("error",
	 *                       "Não é possivel lançar a Frequência para esta data"
	 *                       ); }
	 * 
	 *                       return "redirect:/supervisor/turma/" + idTurma +
	 *                       "/estagiario/" + idEstagiario + "/frequencia"; }
	 * 
	 * @RequestMapping(
	 * value = "/Turma/{idTurma}/Acompanhamento/{idEstagio}/Frequencias/EditarStatus",
	 *                       method = RequestMethod.POST) public String
	 *                       atualizarStatus(@RequestParam("pk") Long
	 *                       idFrequencia, @RequestParam("value")
	 *                       StatusFrequencia status, Model model,
	 *                       RedirectAttributes redirectAttributes) { Frequencia
	 *                       frequencia =
	 *                       frequenciaService.find(Frequencia.class,
	 *                       idFrequencia);
	 * 
	 *                       if (frequencia != null) {
	 *                       frequencia.setStatusFrequencia(status);
	 *                       frequenciaService.update(frequencia); return
	 *                       "supervisor/list-frequencia-estagiario"; }
	 * 
	 *                       return ""; }
	 * 
	 * 
	 *                       private List
	 *                       <Estagiario> atualizarTurmaEstagiarios(List
	 *                       <Estagiario> estagiarios, Turma turma) { for
	 *                       (Estagiario estagiario : estagiarios) { if
	 *                       (estagiario.getTurmas() != null) { if
	 *                       (!estagiario.getTurmas().contains(turma)) {
	 *                       estagiario.getTurmas().add(turma); } } else {
	 *                       estagiario.setTurmas(new ArrayList<Turma>());
	 *                       estagiario.getTurmas().add(turma); } }
	 * 
	 *                       return estagiarios; }
	 * 
	 *                       private List<Horario> atualizarHorarios(Turma
	 *                       turma) { List<Horario> horariosAtualizados = new
	 *                       ArrayList<Horario>();
	 * 
	 *                       if (turma.getHorarios() == null) { return null; }
	 * 
	 *                       for (Horario horario : turma.getHorarios()) { if
	 *                       (horario.getDia() != null) {
	 *                       horariosAtualizados.add(horario); } } return
	 *                       horariosAtualizados; }
	 * 
	 *                       private Model configurarExpediente(List
	 *                       <Horario> horarios, Model model) { SimpleDateFormat
	 *                       horaFormatada = new SimpleDateFormat("HH:mm");
	 * 
	 *                       for (Horario horario : horarios) {
	 * 
	 *                       String expediente =
	 *                       horaFormatada.format(horario.getInicioExpediente())
	 *                       + " as " +
	 *                       horaFormatada.format(horario.getFinalExpediente());
	 * 
	 *                       if (horario.getDia().equals(Dia.SEGUNDA)) {
	 *                       model.addAttribute("EXPEDIENTE_SEGUNDA",
	 *                       expediente); } if
	 *                       (horario.getDia().equals(Dia.TERCA)) {
	 *                       model.addAttribute("EXPEDIENTE_TERCA", expediente);
	 *                       } if (horario.getDia().equals(Dia.QUARTA)) {
	 *                       model.addAttribute("EXPEDIENTE_QUARTA",
	 *                       expediente); } } return model;
	 * 
	 *                       }
	 * 
	 * 
	 *                       private List
	 *                       <Estagiario> getEstagiariosSelecionados(List
	 *                       <Estagiario> estagiarios) { List
	 *                       <Estagiario> estagiariosSelecionados = new
	 *                       ArrayList<Estagiario>();
	 * 
	 *                       for (Estagiario estagiario : estagiarios) { if
	 *                       (estagiario.getId() != null) { estagiario =
	 *                       estagiarioService.find(Estagiario.class,
	 *                       estagiario.getId());
	 * 
	 *                       estagiariosSelecionados.add(estagiario); } }
	 * 
	 *                       return estagiariosSelecionados; }
	 * 
	 */

}
