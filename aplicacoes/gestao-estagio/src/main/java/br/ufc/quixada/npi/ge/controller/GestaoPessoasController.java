package br.ufc.quixada.npi.ge.controller;
import static br.ufc.quixada.npi.ge.utils.Constants.FORMULARIO_CADASTRO_ESTAGIARIO;
import static br.ufc.quixada.npi.ge.utils.Constants.REDIRECT_PAGINA_LOGIN;
import static br.ufc.quixada.npi.ge.utils.Constants.FORMULARIO_CADASTRO_SUPERVISOR;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.ufc.quixada.npi.ge.model.Estagiario;
import br.ufc.quixada.npi.ge.model.Papel;
import br.ufc.quixada.npi.ge.model.Pessoa;
import br.ufc.quixada.npi.ge.model.Servidor;
import br.ufc.quixada.npi.ge.service.PessoaService;
import br.ufc.quixada.npi.ldap.model.Usuario;
import br.ufc.quixada.npi.ldap.service.UsuarioService;

@Controller
public class GestaoPessoasController {
	
	@Inject
	private UsuarioService usuarioService;

	@Inject
	private PessoaService pessoaService;
	
	@RequestMapping(value = {"/", "/login", ""}, method = RequestMethod.GET)
	public ModelAndView login(
			@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout, HttpSession http) {
		ModelAndView model = new ModelAndView();
		if (error != null) {
			model.addObject("error", "Usuário e/ou senha inválidos!");
		}
		if (logout != null) {
			model.addObject("msg", "You've been logged out successfully.");
		}
		model.setViewName("login");
		return model;
	}
	
	@RequestMapping(value = "/loginfailed", method = RequestMethod.GET)
	public String loginerror(ModelMap model) {
		model.addAttribute("error", "Usuário e/ou senha inválidos!");
		return "login";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/login";
	}

	@RequestMapping(value = "/CacelarCadastro", method = RequestMethod.GET)
	public String cacelarCadastro(HttpSession session) {
		return logout(session);
	}
	
	@RequestMapping(value = "/CadastroSupervisor", method = RequestMethod.GET)
	public String formularioCadastroServidor(ModelMap model, HttpSession session) {
		model.addAttribute("servidor", new Servidor());
		return FORMULARIO_CADASTRO_SUPERVISOR;
	}
	
	@RequestMapping(value = "/CadastroSupervisor", method = RequestMethod.POST)
	public String adicionarSupervisor( @Valid @ModelAttribute("servidor") Servidor servidor, BindingResult result, RedirectAttributes redirect, Model model, HttpSession session) {

		if (result.hasErrors()) {
			return FORMULARIO_CADASTRO_SUPERVISOR;
		}

		Usuario usuario = usuarioService.getByCpf(SecurityContextHolder.getContext().getAuthentication().getName());

		Papel papel = pessoaService.buscarPapelPorNome("SUPERVISOR");

		Pessoa pessoa = new Pessoa();
		pessoa.setCpf(usuario.getCpf());
		pessoa.adicionarPapel(papel);

		pessoaService.adicionarPessoa(pessoa);

		servidor.setPessoa(pessoa);
		pessoaService.adicionarServidor(servidor);

		session.invalidate();
		redirect.addFlashAttribute("success", "Seu cadastro foi realizado com sucesso! Agora, você pode efetuar o login!");
		return REDIRECT_PAGINA_LOGIN;
	}

	@RequestMapping(value = "/CadastroEstagiario", method = RequestMethod.GET)
	public String formularioCadastroEstagio(ModelMap model, HttpSession session) {
		model.addAttribute("estagiario", new Estagiario());
		return FORMULARIO_CADASTRO_ESTAGIARIO;
	}
	
	@RequestMapping(value = "/CadastroEstagiario", method = RequestMethod.POST)
	public String adicionarEstagiario( @Valid @ModelAttribute("estagiario") Estagiario estagiario, BindingResult result, RedirectAttributes redirect, Model model, HttpSession session) {

		if (result.hasErrors()) {
			return FORMULARIO_CADASTRO_ESTAGIARIO;
		}

		Usuario usuario = usuarioService.getByCpf(SecurityContextHolder.getContext().getAuthentication().getName());

		Papel papel = pessoaService.buscarPapelPorNome("ESTAGIARIO");

		Pessoa pessoa = new Pessoa();
		pessoa.setCpf(usuario.getCpf());
		pessoa.setPapeis(new ArrayList<Papel>());
		pessoa.getPapeis().add(papel);

		pessoaService.adicionarPessoa(pessoa);

		estagiario.setPessoa(pessoa);
		pessoaService.adicionarEstagiario(estagiario);

		session.invalidate();
		redirect.addFlashAttribute("success", "Seu cadastro foi realizado com sucesso! Agora, você pode efetuar o login!");
		return REDIRECT_PAGINA_LOGIN;
	}

	@ModelAttribute("cursos")
	public List<Estagiario.Curso> cursos() {
		return Arrays.asList(Estagiario.Curso.values());
	}

	@ModelAttribute("estados")
	public List<Estagiario.Estado> estados() {
		return Arrays.asList(Estagiario.Estado.values());
	}

	
}