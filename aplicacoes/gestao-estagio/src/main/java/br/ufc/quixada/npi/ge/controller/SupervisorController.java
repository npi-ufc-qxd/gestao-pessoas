package br.ufc.quixada.npi.ge.controller;

import static br.ufc.quixada.npi.ge.utils.Constants.ACOMPANHAMENTO_ESTAGIARIO;
import static br.ufc.quixada.npi.ge.utils.Constants.AVALIAR_RELATORIO;
import static br.ufc.quixada.npi.ge.utils.Constants.DECLARACAO_ESTAGIO;
import static br.ufc.quixada.npi.ge.utils.Constants.DETALHES_FREQUENCIA_ESTAGIARIO;
import static br.ufc.quixada.npi.ge.utils.Constants.DETALHES_TURMA;
import static br.ufc.quixada.npi.ge.utils.Constants.FORMULARIO_ADICIONAR_AVALIACAO_RENDIMENTO;
import static br.ufc.quixada.npi.ge.utils.Constants.FORMULARIO_ADICIONAR_TURMA;
import static br.ufc.quixada.npi.ge.utils.Constants.FORMULARIO_AVALIAR_PLANO;
import static br.ufc.quixada.npi.ge.utils.Constants.FORMULARIO_EDITAR_AVALIACAO_RENDIMENTO;
import static br.ufc.quixada.npi.ge.utils.Constants.FORMULARIO_EDITAR_TURMA;
import static br.ufc.quixada.npi.ge.utils.Constants.FORMULARIO_EVENTO;
import static br.ufc.quixada.npi.ge.utils.Constants.FORMULARIO_EXPEDIENTE;
import static br.ufc.quixada.npi.ge.utils.Constants.GERENCIAR_FREQUENCIAS;
import static br.ufc.quixada.npi.ge.utils.Constants.MAPA_FREQUENCIAS;
import static br.ufc.quixada.npi.ge.utils.Constants.NOME_USUARIO;
import static br.ufc.quixada.npi.ge.utils.Constants.PAGINA_INICIAL_SUPERVISOR;
import static br.ufc.quixada.npi.ge.utils.Constants.REDIRECT_ACOMPANHAMENTO_ESTAGIARIO;
import static br.ufc.quixada.npi.ge.utils.Constants.REDIRECT_DETALHES_TURMA;
import static br.ufc.quixada.npi.ge.utils.Constants.REDIRECT_PAGINA_INICIAL_SUPERVISOR;
import static br.ufc.quixada.npi.ge.utils.Constants.TERMO_COMPROMISSO_ESTAGIO;
import static br.ufc.quixada.npi.ge.utils.Constants.VINCULOS_TURMA;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.ufc.quixada.npi.ge.model.AvaliacaoRendimento;
import br.ufc.quixada.npi.ge.model.Estagiario;
import br.ufc.quixada.npi.ge.model.Estagio;
import br.ufc.quixada.npi.ge.model.Evento;
import br.ufc.quixada.npi.ge.model.Expediente;
import br.ufc.quixada.npi.ge.model.Frequencia;
import br.ufc.quixada.npi.ge.model.Papel;
import br.ufc.quixada.npi.ge.model.Pessoa;
import br.ufc.quixada.npi.ge.model.Servidor;
import br.ufc.quixada.npi.ge.model.Submissao;
import br.ufc.quixada.npi.ge.model.Submissao.StatusEntrega;
import br.ufc.quixada.npi.ge.model.Submissao.TipoSubmissao;
import br.ufc.quixada.npi.ge.model.Turma;
import br.ufc.quixada.npi.ge.service.EstagioService;
import br.ufc.quixada.npi.ge.service.PessoaService;
import br.ufc.quixada.npi.ge.service.TurmaService;
import br.ufc.quixada.npi.ge.utils.UtilGestao;
import br.ufc.quixada.npi.ge.validation.AvaliacaoRendimentoValidator;
import br.ufc.quixada.npi.ldap.model.Usuario;
import br.ufc.quixada.npi.ldap.service.UsuarioService;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Component
@Controller
@RequestMapping("Supervisao")
public class SupervisorController {

	@Autowired
	private PessoaService pessoaService;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private EstagioService estagioService;

	@Autowired
	private TurmaService turmaService;
	
	@Autowired
	private AvaliacaoRendimentoValidator avaliacaoRendimentoValidator;

	private JRDataSource jrDatasource;

	@RequestMapping(value = { "", "/", "/Turmas" }, method = RequestMethod.GET)
	public String listarTurmas(Model model, HttpSession session) {
		inserirNomeUsuarioNaSessao(session);

		Servidor servidor = servidorEstaCadastrado(pessoaService.buscarPessoaPorCpf(getCpfUsuarioLogado()));

		if (servidor == null) {
			servidor = pessoaService.buscarServidorPorCpf(getCpfUsuarioLogado());
		}

		List<Turma> turmas = turmaService.buscarTurmasSupervisorOuOrientador(servidor.getId());
		List<Turma> turmasEncerradas = turmaService.buscarTurmasEncerradasEAbertasSupervisouOuOrientador(servidor.getId());
		
		if(turmasEncerradas != null){
			model.addAttribute("turmasEncerradas", turmasEncerradas);
		}
		
		model.addAttribute("turmas", turmas);

		return PAGINA_INICIAL_SUPERVISOR;
	}

	@RequestMapping(value = "/Turma/Adicionar", method = RequestMethod.GET)
	public String formularioAdicionarTurma(Model model, @ModelAttribute("turma") Turma turma) {

		model.addAttribute("turma", new Turma());
		model.addAttribute("servidores", pessoaService.buscarServidores());

		return FORMULARIO_ADICIONAR_TURMA;
	}

	@RequestMapping(value = "/Turma/Adicionar", method = RequestMethod.POST)
	public String adicionarTurma(Model model, @Valid @ModelAttribute("turma") Turma turma,
			@RequestParam(value = "orientadorId", required = false) Long orientadorId, @RequestParam(value = "supervisoresId", required = false) List<Long> supervisoresId,
			RedirectAttributes redirect) {

		Servidor orientador = null;
		if(orientadorId != null){
			orientador = pessoaService.buscarServidorPorId(orientadorId);
		}

		List<Servidor> supervisores = null;
		if(supervisoresId != null){
			supervisores = new ArrayList<Servidor>();
			for (long supervisor : supervisoresId) {
				supervisores.add(pessoaService.buscarServidorPorId(supervisor));
			}
		}
		
		turma.setOrientador(orientador);
		turma.setSupervisores(supervisores);

		turmaService.adicionarTurma(turma);
		redirect.addFlashAttribute("sucesso", "Turma cadastrada com sucesso.");

		return REDIRECT_DETALHES_TURMA + turma.getId();
	}

	@ModelAttribute("statusTurma")
	public List<Turma.StatusTurma> statusTurma() {
		return Arrays.asList(Turma.StatusTurma.values());
	}

	@ModelAttribute("tipoTurma")
	public List<Turma.TipoTurma> tipoTurma() {
		return Arrays.asList(Turma.TipoTurma.values());
	}

	@RequestMapping(value = "/Turma/{idTurma}/Editar", method = RequestMethod.GET)
	public String formularioEditarTurma(@PathVariable("idTurma") Long idTurma, Model model, HttpSession session) {

		Servidor servidor = pessoaService.buscarServidorPorCpf(getCpfUsuarioLogado());

		Turma turma = turmaService.buscarTurmaPorServidorId(idTurma, servidor.getId());
		model.addAttribute("turma", turma);
		model.addAttribute("servidores", pessoaService.buscarServidores());

		return FORMULARIO_EDITAR_TURMA;
	}

	@RequestMapping(value = "/Turma/{idTurma}/Editar", method = RequestMethod.POST)
	public String editarTurma(Model model, @PathVariable("idTurma") Long idTurma, @Valid @ModelAttribute("turma") Turma turmaNew, BindingResult result, RedirectAttributes redirect) {

		if (result.hasErrors()) {
			return FORMULARIO_EDITAR_TURMA;
		}
		
		Servidor servidor = pessoaService.buscarServidorPorCpf(getCpfUsuarioLogado());

		Turma turma = turmaService.buscarTurmaPorServidorId(idTurma, servidor.getId());

		List<Servidor> supervisores = new ArrayList<Servidor>();
		if(turmaNew.getSupervisores() != null) {
			supervisores = validarSupervisores(turmaNew.getSupervisores());
		}
		
		turma.setNome(turmaNew.getNome());
		turma.setTipoTurma(turmaNew.getTipoTurma());
		turma.setStatus(turmaNew.getStatus());
		turma.setSemestre(turmaNew.getSemestre());
		turma.setOrientador(turmaNew.getOrientador());
		turma.setSupervisores(supervisores);
		turma.setInicio(turmaNew.getInicio());
		turma.setTermino(turmaNew.getTermino());

		turmaService.editarTurma(turma);

		redirect.addFlashAttribute("sucesso", "Alterações salvas com sucesso.");
		return REDIRECT_DETALHES_TURMA + idTurma;
	}

	@RequestMapping(value = "/Turma/{idTurma}", method = RequestMethod.GET)
	public String visualizarDetalhesTurma(@PathVariable("idTurma") Long idTurma, RedirectAttributes redirect, Model model, HttpSession session) {
		Turma turma = turmaService.buscarTurmaPorServidorId(idTurma, pessoaService.buscarServidorPorCpf(getCpfUsuarioLogado()).getId());
		Date data = new Date();
		
		if(turma == null){
			redirect.addFlashAttribute("error", "Você não tem acesso");
			return REDIRECT_PAGINA_INICIAL_SUPERVISOR;
		}
		
		if(data.after(turma.getTermino())){
			model.addAttribute("turmaEncerrada", true);
		}

		model.addAttribute("turma", turma);

		return DETALHES_TURMA;
	}

	@RequestMapping(value = "/Turma/{idTurma}/AtualizarVinculos", method = RequestMethod.GET)
	public String formularioVinculosTurma(@PathVariable("idTurma") Long idTurma, Model model) {
		model.addAttribute("turma", turmaService.buscarTurmaPorId(idTurma));
		model.addAttribute("estagiariosSemVinculo", estagioService.buscarEstagiariosSemVinculoComTurma(idTurma));

		return VINCULOS_TURMA;
	}

	@RequestMapping(value = "/Turma/{idTurma}/AtualizarVinculos/buscarPorNome/{nomeEstagiario}", method = RequestMethod.GET)
	public List<Estagiario> buscarEstagiarioSemVinculoPorNome(@PathVariable("idTurma") Long idTurma,
			@PathVariable("nomeEstagiario") String nomeEstagiario) {
		return estagioService.buscarEstagiariosSemVinculoComTurma(idTurma);
	}

	@RequestMapping(value = "/Acompanhamento/buscarEstagiarioSemVinculo/{nomeEstagiario}/{idTurma}", method = RequestMethod.GET)
	public String buscarEstagiariosSemVinculoTurmaPorNome(Model model,
			@PathVariable("nomeEstagiario") String nomeEstagiario, @PathVariable("idTurma") Long idTurma) {
		if (nomeEstagiario == null || idTurma == null) {
			model.addAttribute("estagiarios", null);

		} else {
			model.addAttribute("turma", turmaService.buscarTurmaPorId(idTurma));
			model.addAttribute("estagiarios",
					estagioService.buscarEstagiariosSemVinculoComTurmaPorNomeEstagiario(idTurma, nomeEstagiario));
		}

		return "supervisao/vinculos-turma :: resultList";
	}

	@RequestMapping(value = "/Acompanhamento/buscarEstagiarioSemVinculo", method = RequestMethod.GET)
	public String buscarEstagiariosSemVinculoTurma(Model model) {
		model.addAttribute("estagiarios", null);
		return "supervisao/vinculos-turma :: resultList";
	}

	@RequestMapping(value = "/Turma/{idTurma}/TermosCompromisso", method = RequestMethod.GET)
	public String gerarTermoDeCompromisso(@PathVariable("idTurma") Long idTurma, Model model) throws JRException {

		Turma turma = turmaService.buscarTurmaPorId(idTurma);

		Usuario usuario = usuarioService.getByCpf(getCpfUsuarioLogado());

		jrDatasource = new JRBeanCollectionDataSource(turma.getEstagios());

		SimpleDateFormat dataFormatada = new SimpleDateFormat("dd/MM/yyyy");

		model.addAttribute("NOME", usuario.getNome());
		model.addAttribute("SIAPE", usuario.getSiape());
		model.addAttribute("TELEFONE", usuario.getTelefone());
		model.addAttribute("TURNO", UtilGestao.getTurnoExpediente(turma.getExpedientes().get(0)));
		model.addAttribute("INICIO_ESTAGIO", dataFormatada.format(turma.getInicio()));
		model.addAttribute("FINAL_ESTAGIO", dataFormatada.format(turma.getTermino()));
		model.addAttribute("datasource", jrDatasource);
		model.addAttribute("format", "pdf");

		if (turma.getExpedientes() != null) {
			model = configurarExpediente(turma.getExpedientes(), model);
		}

		return TERMO_COMPROMISSO_ESTAGIO;

	}

	@RequestMapping(value = "/Turma/{idTurma}/Declaracoes", method = RequestMethod.GET)
	public String gerarDeclaracaoEstagio(Model model, @PathVariable("idTurma") Long idTurma) throws JRException {
		jrDatasource = new JRBeanCollectionDataSource(turmaService.buscarTurmaPorId(idTurma).getEstagios());

		model.addAttribute("datasource", jrDatasource);
		model.addAttribute("format", "pdf");

		return DECLARACAO_ESTAGIO;
	}

	@RequestMapping(value = "/Turma/{idTurma}/Expediente", method = RequestMethod.GET)
	public String formularioExpediente(Model model, @PathVariable("idTurma") Long idTurma) {
		model.addAttribute("expediente", new Expediente());
		model.addAttribute("turma", turmaService.buscarTurmaPorId(idTurma));

		return FORMULARIO_EXPEDIENTE;
	}

	@RequestMapping(value = "/Turma/{idTurma}/Expediente", method = RequestMethod.POST)
	public String adicionarExpediente(Model model, @PathVariable("idTurma") Long idTurma,
			@Valid @ModelAttribute("expediente") Expediente expediente, BindingResult result,
			RedirectAttributes redirect) {

		model.addAttribute("turma", turmaService.buscarTurmaPorId(idTurma));

		if (result.hasErrors()) {
			return FORMULARIO_EXPEDIENTE;
		}

		Turma turma = turmaService.buscarTurmaPorId(idTurma);
		turmaService.adicionarExpediente(expediente);

		turma.getExpedientes().add(expediente);

		turmaService.adicionarTurma(turma);
		

		redirect.addFlashAttribute("sucesso", "O expediente foi adicionado com sucesso.");
		return REDIRECT_DETALHES_TURMA + idTurma + "/Expediente";
	}

	@ModelAttribute("diaDaSemana")
	public List<Expediente.DiaDaSemana> diaDaSemana() {
		return Arrays.asList(Expediente.DiaDaSemana.values());
	}

	@RequestMapping(value = "/Turma/{idTurma}/Expediente/{idExpediente}/Excluir", method = RequestMethod.GET)
	public @ResponseBody boolean excluirExpediente(Model model, @PathVariable("idTurma") Long idTurma,
			@PathVariable("idExpediente") Long idExpediente) {
		turmaService.excluirExpediente(idExpediente);
		return true;
	}

	@RequestMapping(value = "/Turma/{idTurma}/Evento", method = RequestMethod.GET)
	public String formularioEvento(Model model, @PathVariable("idTurma") Long idTurma) {
		model.addAttribute("evento", new Evento());
		model.addAttribute("turma", turmaService.buscarTurmaPorId(idTurma));

		return FORMULARIO_EVENTO;
	}

	@RequestMapping(value = "/Turma/{idTurma}/Evento", method = RequestMethod.POST)
	public String adicionarEvento(Model model, @PathVariable("idTurma") Long idTurma,
			@Valid @ModelAttribute("evento") Evento evento, BindingResult result, RedirectAttributes redirect) {

		if (result.hasErrors()) {
			model.addAttribute("turma", turmaService.buscarTurmaPorId(idTurma));
			return FORMULARIO_EVENTO;
		}

		Turma turma = turmaService.buscarTurmaPorId(idTurma);
		evento.setTurma(turma);
		turmaService.adicionarEvento(evento);

		redirect.addFlashAttribute("sucesso", "O evento foi adicionado com sucesso");

		return REDIRECT_DETALHES_TURMA + idTurma + "/Evento";
	}

	@RequestMapping(value = "/Turma/{idTurma}/Evento/{idEvento}/Excluir", method = RequestMethod.GET)
	public @ResponseBody boolean excluirEvento(Model model, @PathVariable("idTurma") Long idTurma,
			@PathVariable("idEvento") Long idEvento) {
		turmaService.excluirEvento(idEvento);
		return true;
	}

	@RequestMapping(value = "/Turma/{idTurma}/MapaFrequencia", method = RequestMethod.GET)
	public String listarFrequenciaTurma(@PathVariable("idTurma") Long idTurma, Model model, HttpSession session) {

		List<Frequencia> frequencias = estagioService.buscarFrequenciasPorDataETurmaId(new Date(), idTurma);
		model.addAttribute("frequencias", frequencias);
		model.addAttribute("turma", turmaService.buscarTurmaPorId(idTurma));

		return MAPA_FREQUENCIAS;
	}

	@RequestMapping(value = "/Turma/{idTurma}/MapaFrequencia", method = RequestMethod.GET, params = "data")
	public String listarFrequenciaTurma(@PathVariable("idTurma") Long idTurma, @RequestParam("data") Date data,
			Model model, HttpSession session, RedirectAttributes redirect) {

		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		List<Frequencia> frequencias = estagioService.buscarFrequenciasPorDataETurmaId(data, idTurma);

		if (frequencias == null || frequencias.isEmpty()) {
			model.addAttribute("error", "Não existem frequências para esta data, " + formato.format(data));
		} else {
			model.addAttribute("frequencias", frequencias);
		}

		return MAPA_FREQUENCIAS + " :: frequencias";
	}

	@RequestMapping(value = "/Acompanhamento/{idEstagio}", method = RequestMethod.GET)
	public String detalhesAcompanhamentoEstagiario(Model model, @PathVariable("idEstagio") Long idEstagio,
			RedirectAttributes redirect) {

		Estagio estagio = estagioService.buscarEstagioPorIdEServidorId(idEstagio,
				pessoaService.buscarServidorPorCpf(getCpfUsuarioLogado()).getId());

		if (estagio == null) {
			redirect.addFlashAttribute("error", "Estágio não existe");
			return REDIRECT_PAGINA_INICIAL_SUPERVISOR;
		}

		model.addAttribute("estagio", estagio);
		model.addAttribute("submissaoPlano", estagioService.buscarSubmissaoPorTipoSubmissaoEEstagioId(TipoSubmissao.PLANO_ESTAGIO, idEstagio));
		model.addAttribute("submissaoRelatorio", estagioService.buscarSubmissaoPorTipoSubmissaoEEstagioId(TipoSubmissao.RELATORIO_FINAL_ESTAGIO, idEstagio));
		model.addAttribute("consolidadoFrequencia", estagioService.consolidarFrequencias(estagio));
		return ACOMPANHAMENTO_ESTAGIARIO;

	}

	@RequestMapping(value = "/Acompanhamento/{idEstagio}/ConfigurarExpediente", method = RequestMethod.GET)
	public String formularioConfiguracaoExpediente(Model model, @PathVariable("idEstagio") Long idEstagio, RedirectAttributes redirect) {

		Estagio estagio = estagioService.buscarEstagioPorIdEServidorId(idEstagio,
				pessoaService.buscarServidorPorCpf(getCpfUsuarioLogado()).getId());

		if (estagio == null) {
			redirect.addFlashAttribute("error", "Estágio não existe");
			return REDIRECT_PAGINA_INICIAL_SUPERVISOR;
		}

		model.addAttribute("estagio", estagio);
		model.addAttribute("submissaoPlano", estagioService.buscarSubmissaoPorTipoSubmissaoEEstagioId(TipoSubmissao.PLANO_ESTAGIO, idEstagio));
		model.addAttribute("submissaoRelatorio", estagioService.buscarSubmissaoPorTipoSubmissaoEEstagioId(TipoSubmissao.RELATORIO_FINAL_ESTAGIO, idEstagio));
		model.addAttribute("consolidadoFrequencia", estagioService.consolidarFrequencias(estagio));
		return ACOMPANHAMENTO_ESTAGIARIO;

	}

	@RequestMapping( value = "/Acompanhamento/{idEstagio}/Desvincular", method = RequestMethod.GET)
	public @ResponseBody boolean desvincularEstagiario(Model model, @PathVariable("idEstagio") Long idEstagio, RedirectAttributes redirect) {
		
		try {
			Estagio estagio = estagioService.buscarEstagioPorId(idEstagio);
			if(estagio.getTurma().getStatus().name().equals("ABERTA")){
				estagioService.desvincularEstagiario(idEstagio);
				return true;
			}else{
				return false;	
			}
			
		}catch(Exception e){
			return false;
		}

	}

	@RequestMapping(value = "/Acompanhamento/{idEstagiario}/Vincular/{idTurma}", method = RequestMethod.GET)
	public String vincularEstagiario(@PathVariable("idEstagiario") Long idEstagiario,
			@PathVariable("idTurma") Long idTurma, Model model, RedirectAttributes redirect) {

		if (idEstagiario == null && idTurma == null) {
			redirect.addFlashAttribute("error", "Não foi possivel realizar o vinculo!");
			return "redirect:/Supervisao/Turma/" + turmaService.buscarTurmaPorId(idTurma).getId() + "/AtualizarVinculos";
		}

		estagioService.vincularEstagiario(idTurma, idEstagiario);
		Estagiario estagiario = pessoaService.buscarEstagiarioPorId(idEstagiario);

		redirect.addFlashAttribute("sucesso", "O estagiário, " + estagiario.getNomeCompleto() + ", vinculado com sucesso!");
		return "redirect:/Supervisao/Turma/" + turmaService.buscarTurmaPorId(idTurma).getId() + "/AtualizarVinculos";
	}

	@RequestMapping(value = "/Acompanhamento/{idEstagio}/Frequencias", method = RequestMethod.GET)
	public String formularioFrequencias(Model model, @PathVariable("idEstagio") Long idEstagio) {
		Estagio estagio = estagioService.buscarEstagioPorId(idEstagio);
		model.addAttribute("estagio", estagio);
		model.addAttribute("consolidadoFrequencia", estagioService.consolidarFrequencias(estagio));

		return GERENCIAR_FREQUENCIAS;
	}

	@RequestMapping(value = "/Acompanhamento/{idEstagio}/AgendarReposicao", method = RequestMethod.POST)
	public String agendarReposicao(Model model, @PathVariable("idEstagio") Long idEstagio, @RequestParam("dataReposicao") Date dataReposicao, RedirectAttributes attributes) {

		Servidor servidor = pessoaService.buscarServidorPorCpf(getCpfUsuarioLogado());

		Estagio estagio = estagioService.buscarEstagioPorIdEOrientadorOuSupervisor(idEstagio, servidor.getId());

		if(estagio == null) {
			attributes.addFlashAttribute("error", "Você não permisão para agendar está reposição");
			return REDIRECT_PAGINA_INICIAL_SUPERVISOR; 
		}
		
		estagioService.agendarReposicao(estagio, dataReposicao);

		attributes.addFlashAttribute("sucesso", "Reposição agendada com sucesso!");
		return "redirect:/Supervisao/Acompanhamento/" + idEstagio + "/Frequencias";
	}

	@RequestMapping(value = "/Acompanhamento/{idEstagio}/AvaliarPlano", method = RequestMethod.GET)
	public String formularioAvaliarPlanoEstagio(@PathVariable("idEstagio") Long idEstagio, Model model, RedirectAttributes attributes) {
		Servidor servidor = pessoaService.buscarServidorPorCpf(getCpfUsuarioLogado());
		
		if(!estagioService.isEstagioAcessoSupervisorOuOrientador(idEstagio, servidor.getId())) {
			attributes.addFlashAttribute("error", "Você não permisão para avaliar esta submissão.");
			return REDIRECT_PAGINA_INICIAL_SUPERVISOR; 
		}
		
		Submissao submissaoPlano = estagioService.buscarSubmissaoPorTipoSubmissaoEEstagioId(TipoSubmissao.PLANO_ESTAGIO, idEstagio);
		
		if(submissaoPlano == null){
			attributes.addFlashAttribute("error", "Submissão inexistente;");
			return REDIRECT_PAGINA_INICIAL_SUPERVISOR; 
		}
		

		model.addAttribute("submissaoPlano", submissaoPlano);
		model.addAttribute("estagio", submissaoPlano.getEstagio());

		return FORMULARIO_AVALIAR_PLANO;
	}

	@RequestMapping(value = "/Acompanhamento/{idEstagio}/AvaliarPlano", method = RequestMethod.POST)
	public String avaliarPlanoEstagio(RedirectAttributes redirect, @PathVariable("idEstagio") Long idEstagio,
			@RequestParam("nota") Double nota, @RequestParam("status") Submissao.StatusEntrega status,
			@RequestParam("comentario") String comentario, RedirectAttributes attributes) {

		Servidor servidor = pessoaService.buscarServidorPorCpf(getCpfUsuarioLogado());
		if(!estagioService.isEstagioAcessoSupervisorOuOrientador(idEstagio, servidor.getId())) {
			attributes.addFlashAttribute("error", "Você não permisão para avaliar esta submissão.");
			return REDIRECT_PAGINA_INICIAL_SUPERVISOR; 
		}
		
		Long idSubmissao = estagioService.buscarIdSubmissaoPorTipoSubmissaoEEstagioId(TipoSubmissao.PLANO_ESTAGIO, idEstagio);
		if(idSubmissao == null){
			attributes.addFlashAttribute("error", "Submissão inexistente;");
			return REDIRECT_PAGINA_INICIAL_SUPERVISOR; 
		}

		Submissao submissao = new Submissao();
		submissao.setId(idSubmissao);
		submissao.setStatusEntrega(status);
		submissao.setNota(nota);
		submissao.setComentario(comentario);

		estagioService.avaliarSubmissao(submissao);

		redirect.addFlashAttribute("sucesso", "Avaliação do Plano de estágio realizada!");
		return REDIRECT_ACOMPANHAMENTO_ESTAGIARIO + idEstagio;
	}

	@RequestMapping(value = "/Acompanhamento/{idEstagio}/AvaliarRelatorio", method = RequestMethod.GET)
	public String avaliarRelatorio(RedirectAttributes redirect, @PathVariable("idEstagio") Long idEstagio,
			Model model, RedirectAttributes attributes) {

		Servidor servidor = pessoaService.buscarServidorPorCpf(getCpfUsuarioLogado());
		
		if(!estagioService.isEstagioAcessoSupervisorOuOrientador(idEstagio, servidor.getId())) {
			attributes.addFlashAttribute("error", "Você não permisão para avaliar esta submissão.");
			return REDIRECT_PAGINA_INICIAL_SUPERVISOR; 
		}
		
		Submissao submissaoRelatorio = estagioService.buscarSubmissaoPorTipoSubmissaoEEstagioId(TipoSubmissao.RELATORIO_FINAL_ESTAGIO, idEstagio);
		
		if(submissaoRelatorio == null){
			attributes.addFlashAttribute("error", "Submissão inexistente;");
			return REDIRECT_PAGINA_INICIAL_SUPERVISOR; 
		}
		

		model.addAttribute("submissaoRelatorio", submissaoRelatorio);
		model.addAttribute("estagio", submissaoRelatorio.getEstagio());

		return AVALIAR_RELATORIO;
	}

	@RequestMapping(value = "/Acompanhamento/{idEstagio}/AvaliarRelatorio", method = RequestMethod.POST)
	public String avaliarRelatorioFinalEstagio(RedirectAttributes redirect, @PathVariable("idEstagio") Long idEstagio,
			@RequestParam("nota") Double nota, @RequestParam("status") StatusEntrega status,
			@RequestParam("comentario") String comentario, RedirectAttributes attributes) {

		Servidor servidor = pessoaService.buscarServidorPorCpf(getCpfUsuarioLogado());
		if(!estagioService.isEstagioAcessoSupervisorOuOrientador(idEstagio, servidor.getId())) {
			attributes.addFlashAttribute("error", "Você não permisão para avaliar esta submissão.");
			return REDIRECT_PAGINA_INICIAL_SUPERVISOR; 
		}
		
		Long idSubmissao = estagioService.buscarIdSubmissaoPorTipoSubmissaoEEstagioId(TipoSubmissao.RELATORIO_FINAL_ESTAGIO, idEstagio);
		if(idSubmissao == null){
			attributes.addFlashAttribute("error", "Submissão inexistente;");
			return REDIRECT_PAGINA_INICIAL_SUPERVISOR; 
		}
		Submissao submissao = new Submissao();
		submissao.setId(idSubmissao);
		submissao.setStatusEntrega(status);
		submissao.setNota(nota);
		submissao.setComentario(comentario);

		estagioService.avaliarSubmissao(submissao);
		redirect.addFlashAttribute("sucesso", "Avaliação do Relatório final de estágio realizada!");
		return REDIRECT_ACOMPANHAMENTO_ESTAGIARIO + idEstagio;
	}

	@ModelAttribute("statusEntrega")
	public List<Submissao.StatusEntrega> statusEntrega() {
		return Arrays.asList(Submissao.StatusEntrega.values());
	}

	@RequestMapping(value = "/Acompanhamento/{idEstagio}/DownloadRelatorio", method = RequestMethod.GET)
	@ResponseBody
	public HttpEntity<byte[]> downloadRelatorio(@PathVariable("idEstagio") Long idEstagio,
			RedirectAttributes redirectAttributes) {

		Submissao submissaoRelatorio = estagioService
				.buscarSubmissaoPorTipoSubmissaoEEstagioId(Submissao.TipoSubmissao.RELATORIO_FINAL_ESTAGIO, idEstagio);

		// if(submissaoPlano == null){
		// redirectAttributes.addFlashAttribute("error", "Acesso negado.");
		// return REDIRECT_PAGINA_INICIAL_ESTAGIARIO;
		// }

		byte[] relatorio = submissaoRelatorio.getDocumento().getArquivo();
		String[] tipo = submissaoRelatorio.getDocumento().getExtensao().split("/");

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType(tipo[0], tipo[1]));
		headers.set("Content-Disposition", "attachment; filename=" + submissaoRelatorio.getDocumento().getNome());
		headers.setContentLength(relatorio.length);

		return new HttpEntity<byte[]>(relatorio, headers);
	}

	@RequestMapping(value = "/Acompanhamento/{idEstagio}/DownloadPlano", method = RequestMethod.GET)
	@ResponseBody
	public HttpEntity<byte[]> downloadPlano(@PathVariable("idEstagio") Long idEstagio,
			RedirectAttributes redirectAttributes) {

		Submissao submissaoPlano = estagioService
				.buscarSubmissaoPorTipoSubmissaoEEstagioId(Submissao.TipoSubmissao.PLANO_ESTAGIO, idEstagio);

		// if(submissaoPlano == null){
		// redirectAttributes.addFlashAttribute("error", "Acesso negado.");
		// return REDIRECT_PAGINA_INICIAL_ESTAGIARIO;
		// }

		byte[] plano = submissaoPlano.getDocumento().getArquivo();
		String[] tipo = submissaoPlano.getDocumento().getExtensao().split("/");

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType(tipo[0], tipo[1]));
		headers.set("Content-Disposition", "attachment; filename=" + submissaoPlano.getDocumento().getNome());
		headers.setContentLength(plano.length);

		return new HttpEntity<byte[]>(plano, headers);
	}

	// AVALIAÇÃO DE RENDIMENTO

	@RequestMapping(value = "/Acompanhamento/{idEstagio}/AvaliacaoRendimento", method = RequestMethod.GET)
	public String formularioAdicionarAvaliacaoRendimento(Model model, @PathVariable("idEstagio") Long idEstagio, RedirectAttributes attributes) {
		
		Servidor servidor = pessoaService.buscarServidorPorCpf(getCpfUsuarioLogado());
		Estagio estagio = estagioService.buscarEstagioPorIdEOrientadorOuSupervisor(idEstagio, servidor.getId());
		
		if(estagio == null) {
			attributes.addFlashAttribute("error", "Você não possui permissão para avaliar ou estágio inexistente");
			return REDIRECT_PAGINA_INICIAL_SUPERVISOR; 
		}
//		Foi comentada para a realização de teste. Após será descomentada
//		if(estagio.getAvaliacaoRendimento() != null){
//			attributes.addFlashAttribute("error", "Avaliação de rendimento já avaliada.");
//			return REDIRECT_PAGINA_INICIAL_SUPERVISOR;
//		}
		
		model.addAttribute("avaliacaoRendimento", new AvaliacaoRendimento());
		model.addAttribute("estagio", estagio);

		return FORMULARIO_ADICIONAR_AVALIACAO_RENDIMENTO;
	}

	@RequestMapping(value = "/Acompanhamento/{idEstagio}/AvaliacaoRendimento", method = RequestMethod.POST)
	public String adicionarAvaliacaoRendimento(Model model, @PathVariable("idEstagio") Long idEstagio, @ModelAttribute("avaliacaoRendimento") AvaliacaoRendimento avaliacaoRendimento, BindingResult result, RedirectAttributes attributes) {
		
		Servidor servidor = pessoaService.buscarServidorPorCpf(getCpfUsuarioLogado());
		Estagio estagio = estagioService.buscarEstagioPorIdEOrientadorOuSupervisor(idEstagio, servidor.getId());
		
		if(estagio == null) {
			attributes.addFlashAttribute("error", "Você não possui permissão para avaliar ou estágio inexistente");
			return REDIRECT_PAGINA_INICIAL_SUPERVISOR; 
		}
		
//		Foi comentada para a realização de teste. Após será descomentada
//		if(estagio.getAvaliacaoRendimento() != null){
//			attributes.addFlashAttribute("error", "Avaliação de rendimento já avaliada.");
//			return REDIRECT_PAGINA_INICIAL_SUPERVISOR;
//		}
		
		avaliacaoRendimentoValidator.validate(avaliacaoRendimento, result);
		
		if (result.hasErrors()) {
			model.addAttribute("avaliacaoRendimento", avaliacaoRendimento);
			model.addAttribute("estagio", estagio);
			return FORMULARIO_ADICIONAR_AVALIACAO_RENDIMENTO;
		}
		
		estagio.setAvaliacaoRendimento(avaliacaoRendimento);
		avaliacaoRendimento.setEstagio(estagio);
		
		avaliacaoRendimento.setModo(AvaliacaoRendimento.Modo.FORMULARIO);
		avaliacaoRendimento.setCriadaPor(servidor);
		avaliacaoRendimento.setDataAvaliacao(new Date());
		estagioService.adicionarAvaliacaoRendimento(avaliacaoRendimento);

		attributes.addFlashAttribute("sucesso", "Avaliação de Rendimento realizada!");
		return REDIRECT_ACOMPANHAMENTO_ESTAGIARIO + idEstagio;
	}

	@RequestMapping(value = "/Acompanhamento/{idEstagio}/AvaliacaoRendimento/{idAvaliacaoRendimento}/Editar", method = RequestMethod.GET)
	public String formularioEditarAvaliacaoRendimento(Model model, @PathVariable("idEstagio") Long idEstagio) {
		return FORMULARIO_EDITAR_AVALIACAO_RENDIMENTO;
	}

	@RequestMapping(value = "/Acompanhamento/{idEstagio}/AvaliacaoRendimento/{idAvaliacaoRendimento}/Editar", method = RequestMethod.POST)
	public String editarAvaliacaoRendimento(Model model,
			@Valid @ModelAttribute("avaliacaoRendimento") AvaliacaoRendimento avaliacaoRendimento,
			RedirectAttributes redirect, @PathVariable("idEstagio") Long idEstagio) {
		return REDIRECT_ACOMPANHAMENTO_ESTAGIARIO + idEstagio;
	}

	@RequestMapping(value = "/Acompanhamento/{idEstagio}/Frequencia", method = RequestMethod.GET)
	public String detalhesFrequenciaEstagiario(Model model, @PathVariable("idEstagio") Long idEstagio) {
		return DETALHES_FREQUENCIA_ESTAGIARIO;
	}

	@ModelAttribute("frequencias")
	public List<AvaliacaoRendimento.Frequencia> todasFrequencias() {
		return Arrays.asList(AvaliacaoRendimento.Frequencia.values());
	}

	@ModelAttribute("permanencias")
	public List<AvaliacaoRendimento.Permanencia> todasPermanencias() {
		return Arrays.asList(AvaliacaoRendimento.Permanencia.values());
	}

	@ModelAttribute("disciplinas")
	public List<AvaliacaoRendimento.DisciplinaQuantoAoCumprimentoDasNormas> todasDisciplinas() {
		return Arrays.asList(AvaliacaoRendimento.DisciplinaQuantoAoCumprimentoDasNormas.values());
	}

	@ModelAttribute("iniciativas")
	public List<AvaliacaoRendimento.Iniciativa> todasIniciativas() {
		return Arrays.asList(AvaliacaoRendimento.Iniciativa.values());
	}

	@ModelAttribute("qualidades")
	public List<AvaliacaoRendimento.QualidadeDoTrabalho> todasQualidades() {
		return Arrays.asList(AvaliacaoRendimento.QualidadeDoTrabalho.values());
	}

	@ModelAttribute("quantidades")
	public List<AvaliacaoRendimento.QuantidadeDeTrabalho> todasQuantidades() {
		return Arrays.asList(AvaliacaoRendimento.QuantidadeDeTrabalho.values());
	}

	@ModelAttribute("cumprimentos")
	public List<AvaliacaoRendimento.CumprimentoPrazos> todosCumprimentos() {
		return Arrays.asList(AvaliacaoRendimento.CumprimentoPrazos.values());
	}

	@ModelAttribute("relacionamentos")
	public List<AvaliacaoRendimento.RelacionamentoGerenciaEFuncionarios> todosRelacionamentos() {
		return Arrays.asList(AvaliacaoRendimento.RelacionamentoGerenciaEFuncionarios.values());
	}

	@ModelAttribute("trabalhos")
	public List<AvaliacaoRendimento.TrabalhoEmEquipe> todosTrabalhos() {
		return Arrays.asList(AvaliacaoRendimento.TrabalhoEmEquipe.values());
	}

	@ModelAttribute("comprometimentos")
	public List<AvaliacaoRendimento.ComprometimentoComTrabalho> todosComprometimentos() {
		return Arrays.asList(AvaliacaoRendimento.ComprometimentoComTrabalho.values());
	}

	@ModelAttribute("cuidados")
	public List<AvaliacaoRendimento.CuidadoMateriaisEEquipamentos> todosCuidados() {
		return Arrays.asList(AvaliacaoRendimento.CuidadoMateriaisEEquipamentos.values());
	}

	private void inserirNomeUsuarioNaSessao(HttpSession session) {
		if (session.getAttribute(NOME_USUARIO) == null) {
			session.setAttribute(NOME_USUARIO, usuarioService.getByCpf(getCpfUsuarioLogado()).getNome());
		}
	}

	private String getCpfUsuarioLogado() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}

	private Model configurarExpediente(List<Expediente> expedientes, Model model) {
		SimpleDateFormat horaFormatada = new SimpleDateFormat("HH:mm");

		for (Expediente expediente : expedientes) {
			String descricaoExpediente = horaFormatada.format(expediente.getHoraInicio()) + " as "
					+ horaFormatada.format(expediente.getHoraTermino());

			if (expediente.getDiaSemana().equals(Expediente.DiaDaSemana.SEGUNDA)) {
				model.addAttribute("EXPEDIENTE_SEGUNDA", descricaoExpediente);
			}
			if (expediente.getDiaSemana().equals(Expediente.DiaDaSemana.TERCA)) {
				model.addAttribute("EXPEDIENTE_TERCA", descricaoExpediente);
			}
			if (expediente.getDiaSemana().equals(Expediente.DiaDaSemana.QUARTA)) {
				model.addAttribute("EXPEDIENTE_QUARTA", descricaoExpediente);
			}
			if (expediente.getDiaSemana().equals(Expediente.DiaDaSemana.QUINTA)) {
				model.addAttribute("EXPEDIENTE_UINTA", descricaoExpediente);
			}
			if (expediente.getDiaSemana().equals(Expediente.DiaDaSemana.SEXTA)) {
				model.addAttribute("EXPEDIENTE_SEXTA", descricaoExpediente);
			}
		}
		return model;

	}

	private Servidor servidorEstaCadastrado(Pessoa pessoa) {
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
			return servidor;
		}
		return null;
	}

	private  List<Servidor> validarSupervisores(List<Servidor> supervisores) {
		List<Servidor> supervisoresValidos = new ArrayList<Servidor>(); 
		for(Servidor supervisor : supervisores) {
			if(supervisor != null && supervisor.getId() != null) {
				supervisoresValidos.add(pessoaService.buscarServidorPorId(supervisor.getId()));
			}
		}
		return supervisoresValidos;
	}
	
}
