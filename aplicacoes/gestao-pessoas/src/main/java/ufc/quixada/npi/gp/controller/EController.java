package ufc.quixada.npi.gp.controller;

import static ufc.quixada.npi.gp.utils.Constants.PAGINA_INICIAL_ESTAGIARIO;
import static ufc.quixada.npi.gp.utils.Constants.PAGINA_FORM_ESTAGIARIO;
import static ufc.quixada.npi.gp.utils.Constants.PAGINA_INFO_ESTAGIARIO;
import static ufc.quixada.npi.gp.utils.Constants.REDIRECT_PAGINA_INICIAL_ESTAGIARIO;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ufc.quixada.npi.gp.model.Estagiario;
import ufc.quixada.npi.gp.model.Pessoa;
import ufc.quixada.npi.gp.service.EstagiarioService;
import ufc.quixada.npi.gp.service.PessoaService;
import ufc.quixada.npi.gp.utils.Constants;

@Controller
@RequestMapping("estagiari")
public class EController {

	@Inject
	private PessoaService pessoaService;

	@Inject
	private EstagiarioService estagiarioService;


	@RequestMapping(value = "/inicio", method = RequestMethod.GET)
	public String paginaInicial(Model model, HttpSession session) {
		Pessoa pessoa = getUsuarioLogado(session);
		
		if(!estagiarioService.possuiTurma(pessoa.getCpf())){
			model.addAttribute("warning", "Aguarde, você sera vinculada a uma turma, desde já sinta-se parte deste grupo, NPI.");
		}

		return PAGINA_INICIAL_ESTAGIARIO;
	}

	@RequestMapping(value = "/meus-dados", method = RequestMethod.GET)
	public String paginaPerfilEstagiario(Model model) {
		String cpf = SecurityContextHolder.getContext().getAuthentication().getName();
		model.addAttribute("action", "editar");
		model.addAttribute("estagiario", estagiarioService.getEstagiarioByCpf(cpf));

		return PAGINA_FORM_ESTAGIARIO;
	}

	@RequestMapping(value = "/editar-perfil", method = RequestMethod.GET)
	public String paginaEditarPerfil(Model model) {
		String cpf = SecurityContextHolder.getContext().getAuthentication().getName();
		model.addAttribute("estagiario", estagiarioService.getEstagiarioByCpf(cpf));
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

	
	
	private Pessoa getUsuarioLogado(HttpSession session) {
		if (session.getAttribute(Constants.USUARIO_LOGADO) == null) {
			Pessoa pessoa = pessoaService.getPessoaByCpf(SecurityContextHolder.getContext().getAuthentication().getName());
			session.setAttribute(Constants.USUARIO_LOGADO, pessoa);
		}
		return (Pessoa) session.getAttribute(Constants.USUARIO_LOGADO);
	}
}