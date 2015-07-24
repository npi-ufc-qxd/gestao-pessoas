package ufc.quixada.npi.gp.controller;

import static ufc.quixada.npi.gp.utils.Constants.PAGINA_FORM_ESTAGIARIO;
import static ufc.quixada.npi.gp.utils.Constants.REDIRECT_PAGINA_INICIAL_ESTAGIARIO;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
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

@Component
@Controller
@RequestMapping("home")
public class HomeController {

	@Inject
	private EstagiarioService estagiarioService;

	@Inject
	private PessoaService pessoaService;

	@RequestMapping(value = "/meu-cadastro", method = RequestMethod.GET)
	public String paginaCadastroEstagiario(ModelMap modelMap, HttpSession session) {
		modelMap.addAttribute("action", "cadastrar");
		modelMap.addAttribute("estagiario", new Estagiario());

		return PAGINA_FORM_ESTAGIARIO;
	}

	@RequestMapping(value = "/meu-cadastro", method = RequestMethod.POST)
	public String adicionarEstagiario( @Valid @ModelAttribute("estagiario") Estagiario estagiario, BindingResult result, HttpSession session, RedirectAttributes redirect, Model model) {

		if (result.hasErrors()) {
			return PAGINA_FORM_ESTAGIARIO;
		}

		String cpf = SecurityContextHolder.getContext().getAuthentication().getName();
		Pessoa pessoa = new Pessoa(cpf);
		pessoaService.save(pessoa);

		estagiario.setPessoa(pessoa);
		estagiarioService.save(estagiario);
		
		getUsuarioLogado(session);

		redirect.addFlashAttribute("success", "Seu cadastro foi realizado com sucesso! Agora, voçê faz parte do NPI!");
		redirect.addFlashAttribute("warning", "Aguarde, você sera vinculada a uma turma, desde já sinta-se parte deste grupo, NPI.");

		return REDIRECT_PAGINA_INICIAL_ESTAGIARIO;
	}

	@RequestMapping(value = "/solicitar-acesso", method = RequestMethod.GET)
	public String paginaConfimarcaoSupervisor(ModelMap modelMap, HttpSession session) {
		modelMap.addAttribute("action", "cadastrar");
		
		
		
		modelMap.addAttribute("estagiario", new Estagiario());

		return PAGINA_FORM_ESTAGIARIO;
	}

	@RequestMapping(value = "/solicitar-acesso", method = RequestMethod.POST)
	public String confirmarServidor( @Valid @ModelAttribute("estagiario") Estagiario estagiario, BindingResult result, HttpSession session, RedirectAttributes redirect, Model model) {

		if (result.hasErrors()) {
			return PAGINA_FORM_ESTAGIARIO;
		}

		String cpf = SecurityContextHolder.getContext().getAuthentication().getName();
		Pessoa pessoa = new Pessoa(cpf);
		pessoaService.save(pessoa);

		estagiario.setPessoa(pessoa);
		estagiarioService.save(estagiario);
		
		getUsuarioLogado(session);

		redirect.addFlashAttribute("success", "Seu cadastro foi realizado com sucesso! Agora, voçê faz parte do NPI!");
		redirect.addFlashAttribute("warning", "Aguarde, você sera vinculada a uma turma, desde já sinta-se parte deste grupo, NPI.");

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


