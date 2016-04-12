package ufc.quixada.npi.gp.controller;

import static ufc.quixada.npi.gp.utils.Constants.PAGINA_AVALIACAO;
import static ufc.quixada.npi.gp.utils.Constants.PAGINA_FORM_ESTAGIARIO;
import static ufc.quixada.npi.gp.utils.Constants.PAGINA_INICIAL_ESTAGIARIO;
import static ufc.quixada.npi.gp.utils.Constants.PAGINA_MINHA_PRESENCA;
import static ufc.quixada.npi.gp.utils.Constants.REDIRECT_PAGINA_INICIAL_ESTAGIARIO;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.security.core.context.SecurityContextHolder;
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

import br.ufc.quixada.npi.ldap.service.UsuarioService;
import ufc.quixada.npi.gp.model.Documento;
import ufc.quixada.npi.gp.model.Estagiario;
import ufc.quixada.npi.gp.model.Frequencia;
import ufc.quixada.npi.gp.model.Pessoa;
import ufc.quixada.npi.gp.model.Submissao;
import ufc.quixada.npi.gp.model.Turma;
import ufc.quixada.npi.gp.model.enums.StatusEntrega;
import ufc.quixada.npi.gp.model.enums.StatusFrequencia;
import ufc.quixada.npi.gp.model.enums.StatusTurma;
import ufc.quixada.npi.gp.model.enums.Tipo;
import ufc.quixada.npi.gp.service.DadoConsolidado;
import ufc.quixada.npi.gp.service.EstagiarioService;
import ufc.quixada.npi.gp.service.FrequenciaService;
import ufc.quixada.npi.gp.service.PessoaService;
import ufc.quixada.npi.gp.service.SubmissaoService;
import ufc.quixada.npi.gp.service.TurmaService;
import ufc.quixada.npi.gp.utils.Constants;

@Controller
@RequestMapping("estagiario")
public class EstagiarioController {

	@Inject
	private PessoaService pessoaService;

	@Inject
	private EstagiarioService estagiarioService;

	@Inject
	private TurmaService turmaService;

	@Inject
	private FrequenciaService frequenciaService;

	@Inject
	private UsuarioService usuarioService;

	@Inject
	private SubmissaoService submissaoService;

	@RequestMapping(value = {"/",""}, method = RequestMethod.GET)
	public String paginaInicial(Model model, HttpSession session) {
		Pessoa pessoa = getUsuarioLogado(session);

		Estagiario estagiario = estagiarioService.getEstagiarioByPessoaId(pessoa.getId());

		List<Turma> turmasInicial = turmaService.getTurmasByEstagiarioId(estagiario.getId());

		model.addAttribute("turmasInicial", turmasInicial);


		if(!estagiarioService.possuiTurmaAtiva(pessoa.getCpf())){
			model.addAttribute("possuiTurma", false);
		}

		return PAGINA_INICIAL_ESTAGIARIO;
	}

	@RequestMapping(value = "/meus-dados", method = RequestMethod.GET)
	public String paginaPerfilEstagiario(Model model) {
		String cpf = SecurityContextHolder.getContext().getAuthentication().getName();

		Estagiario estagiario = estagiarioService.getEstagiarioByPessoaCpf(cpf);

		if (estagiario == null) {
			return "redirect:/home/meu-cadastro";
		} else {
			model.addAttribute("action", "editar");
			model.addAttribute("estagiario", estagiario);
		}

		return PAGINA_FORM_ESTAGIARIO;
	}

	@RequestMapping(value = "/editar-perfil", method = RequestMethod.GET)
	public String paginaEditarPerfil(Model model) {
		String cpf = SecurityContextHolder.getContext().getAuthentication().getName();
		model.addAttribute("estagiario", estagiarioService.getEstagiarioByPessoaCpf(cpf));
		model.addAttribute("action", "editar");

		return PAGINA_FORM_ESTAGIARIO;
	}

	@RequestMapping(value = "/editar-perfil", method = RequestMethod.POST)
	public String adicionarEstagiario(@Valid @ModelAttribute("estagiario") Estagiario estagiario, BindingResult result, HttpSession session, RedirectAttributes redirect, Model model) {
		model.addAttribute("action", "editar");

		if (result.hasErrors()) {
			return PAGINA_FORM_ESTAGIARIO;
		}

		Pessoa pessoa = getUsuarioLogado(session);

		estagiario.setPessoa(pessoa);
		estagiarioService.update(estagiario);

		getUsuarioLogado(session);

		redirect.addFlashAttribute("info", "Parabéns, " + pessoa.getNome() + ", seu cadastro foi realizado com sucesso!");

		return REDIRECT_PAGINA_INICIAL_ESTAGIARIO;
	}

	@RequestMapping(value = "/minha-frequencia", method = RequestMethod.GET)
	public String minhaFrequecia(HttpSession session, Model model) {
		Pessoa pessoa = getUsuarioLogado(session);

		Estagiario estagiario = estagiarioService.getEstagiarioByPessoaId(pessoa.getId());

		List<Turma> turmas = turmaService.getTurmasByEstagiarioId(estagiario.getId());

		model.addAttribute("turmas", turmas);

		return PAGINA_MINHA_PRESENCA;
	}

	@RequestMapping(value = "/turmas", method = RequestMethod.GET)
	public String listarTurmas(Model model, HttpSession session) {
		Pessoa pessoa = getUsuarioLogado(session);

		Estagiario estagiario = estagiarioService.getEstagiarioByPessoaId(pessoa.getId());

		List<Turma> turmasInicial = turmaService.getTurmasByEstagiarioId(estagiario.getId());

		model.addAttribute("turmas", turmasInicial);

		return "estagiario/list-turmas";
	}


	@RequestMapping(value = "/turma/{idTurma}", method = RequestMethod.GET)
	public String detalhesTurma(@PathVariable("idTurma") Long idTurma, Model model, HttpSession session) {
		Pessoa pessoa = getUsuarioLogado(session);

		Estagiario estagiario = estagiarioService.getEstagiarioByPessoaId(pessoa.getId());

		List<Submissao> submissoes = submissaoService.getSubmissoesByEstagiarioIdAndIdTurma(estagiario.getId(), idTurma);

		model.addAttribute("submissoes", submissoes);
		model.addAttribute("estagiarioNome", estagiario.getNomeCompleto());
		model.addAttribute("turma", turmaService.getTurmaByIdAndEstagiarioId(idTurma, estagiario.getId()));

		return "estagiario/info-turma";
	}

	@RequestMapping(value = "/minha-documentacao/turma/{idTurma}", method = RequestMethod.POST)
	public String minhaDocumentacao(@Valid @RequestParam("anexo") MultipartFile anexo, HttpSession session, Model model, @RequestParam("tipo") Tipo tipo, @ModelAttribute("idTurma") Long idTurma, RedirectAttributes redirectAttributes ){
		Pessoa pessoa = getUsuarioLogado(session);
		Estagiario estagiario = estagiarioService.getEstagiarioByPessoaId(pessoa.getId());
		Turma turma = turmaService.getTurmaByIdAndEstagiarioId(idTurma, estagiario.getId());

		Submissao submissao = submissaoService.getSubmissaoByEstagiarioIdAndIdTurmaAndTipo(estagiario.getId(), idTurma, tipo);

		try {
			if(!anexo.getContentType().equals("application/pdf")){
				redirectAttributes.addFlashAttribute("error", "Escolha um arquivo pdf.");
				return "redirect:/estagiario/turma/" + idTurma;
			}
			if(submissao == null && anexo.getBytes() != null && anexo.getBytes().length != 0 && anexo.getContentType().equals("application/pdf")){
				Documento documento = new Documento();
				documento.setNome(tipo+"_"+estagiario.getNomeCompleto().toUpperCase());
				documento.setExtensao(anexo.getContentType());
				documento.setArquivo(anexo.getBytes());

				submissao = new Submissao();
				submissao.setData(new Date());
				submissao.setHorario(new Date());
				submissao.setStatusEntrega(StatusEntrega.ENVIADO);
				submissao.setTipo(tipo);
				submissao.setEstagiario(estagiario);
				submissao.setTurma(turma);
				submissao.setDocumento(documento);
				submissaoService.salvar(submissao);
			} else if(submissao.getStatusEntrega().equals(StatusEntrega.ENVIADO) && anexo.getBytes() != null && anexo.getBytes().length != 0 && anexo.getContentType().equals("application/pdf")){
				submissao.getDocumento().setNome(tipo+"_"+estagiario.getNomeCompleto().toUpperCase());
				submissao.getDocumento().setArquivo(anexo.getBytes());
				submissao.getDocumento().setExtensao(anexo.getContentType());
				submissao.setData(new Date());
				submissao.setHorario(new Date());
				submissao.setStatusEntrega(StatusEntrega.ENVIADO);
				submissao.setTipo(tipo);
				submissao.setEstagiario(estagiario);
				submissao.setTurma(turma);
				submissaoService.update(submissao);
			}
		} catch (IOException e) {
			return "redirect:/500";
		}

		return "redirect:/estagiario/turma/" + idTurma;
	}

	@RequestMapping(value = "/minha-frequencia/turma/{idTurma}", method = RequestMethod.GET)
	public String getFrequeciaByTurma(HttpSession session, Model model, @ModelAttribute("idTurma") Long idTurma) {
		Pessoa pessoa = getUsuarioLogado(session);

		Estagiario estagiario = estagiarioService.getEstagiarioByPessoaId(pessoa.getId());

		List<Turma> turmas = turmaService.getTurmasByEstagiarioIdAndStatus(StatusTurma.ABERTA, estagiario.getId());

		Turma turma = turmaService.getTurmaByIdAndEstagiarioId(idTurma, estagiario.getId());

		boolean liberarPresenca = false;

		boolean possuiTurma  = turma != null ? true : false;

		if(possuiTurma) {

			boolean frequenciaNaoRealizada = frequenciaService.getFrequenciaDeHojeByEstagiarioId(estagiario.getId()) == null ? true : false;

			if(frequenciaService.liberarPreseca(turma) && frequenciaNaoRealizada) {
				liberarPresenca = true;
			}


			List<Frequencia> frequencias = frequenciaService.getFrequenciasByEstagiarioId(estagiario.getId(), turma.getId());

			DadoConsolidado dadosConsolidados = frequenciaService.calcularDadosConsolidados(frequencias);

			model.addAttribute("frequencias", frequencias);
			model.addAttribute("dadosConsolidados", dadosConsolidados);		
			model.addAttribute("dadosConsolidados", dadosConsolidados);		
			model.addAttribute("estagiario", estagiario);
			model.addAttribute("liberarPresenca", liberarPresenca);
			model.addAttribute("frequenciaNaoRealizada", frequenciaNaoRealizada);
			model.addAttribute("turma", turma);
			model.addAttribute("turmas", turmas);
		}

		model.addAttribute("possuiTurma", possuiTurma);

		return PAGINA_MINHA_PRESENCA;
	}

	@RequestMapping(value = "/minha-frequencia/turma/{idTurma}", method = RequestMethod.POST)
	public String cadastrarFrequencia(HttpSession session, @RequestParam("senha") String senha, @ModelAttribute("idTurma") Long idTurma, RedirectAttributes redirectAttributes) {
		Pessoa pessoa = getUsuarioLogado(session);
		Estagiario estagiario = estagiarioService.getEstagiarioByPessoaId(pessoa.getId());

		boolean estagiarioValido = usuarioService.autentica(pessoa.getCpf(), senha);

		Turma turma = turmaService.getTurmaByIdAndEstagiarioId(idTurma, estagiario.getId());

		boolean presencaLiberada = false;
		if(turma != null) {
			presencaLiberada = frequenciaService.liberarPreseca(turma);
		}

		boolean frequenciaNaoRealizada = frequenciaService.getFrequenciaDeHojeByEstagiarioId(estagiario.getId()) == null ? true : false;

		if(estagiarioValido && presencaLiberada && frequenciaNaoRealizada){
			Frequencia frequencia = new Frequencia();

			frequencia.setEstagiario(estagiario);
			frequencia.setTurma(turma);

			frequencia.setData(new Date());
			frequencia.setStatusFrequencia(StatusFrequencia.PRESENTE);
			frequencia.setHorario(new Date());

			frequenciaService.save(frequencia);
		}

		return "redirect:/estagiario/minha-frequencia/turma/" + idTurma;
	}

	@RequestMapping(value = "/minha-avaliacao", method = RequestMethod.GET)
	public String avaliacao(HttpSession session, Model model) {
		return PAGINA_AVALIACAO;
	}

	private Pessoa getUsuarioLogado(HttpSession session) {
		Pessoa pessoa = (Pessoa) session.getAttribute(Constants.USUARIO_LOGADO);

		if (pessoa == null || pessoa.getNome() == null) {
			pessoa = pessoaService.getPessoaByCpf(SecurityContextHolder.getContext().getAuthentication().getName());
			session.setAttribute(Constants.USUARIO_LOGADO, pessoa);
		}

		return pessoa;
	}
}