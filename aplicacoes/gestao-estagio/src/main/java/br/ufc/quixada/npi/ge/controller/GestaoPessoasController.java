package br.ufc.quixada.npi.ge.controller;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class GestaoPessoasController {
	
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
	public String logout(ModelMap model, HttpSession session) {
		session.invalidate();
		return "redirect:/login";
	}
	
/*
	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public String acessoNegado(ModelMap model, Principal user) {
		if (user != null) {
			model.addAttribute("message", "Olá, " + user.getName()
					+ ", você não tem permissão para acessar essa página!");
		} else {
			model.addAttribute("message",
					"Você não tem permissão para acessar essa página!");
		}
		return PAGINA_ERRO_403;
	}	

	@RequestMapping(value = "/404", method = RequestMethod.GET)
	public String paginaInexistente(ModelMap model, Principal user) {
		model.addAttribute("message", "Oops, página não encontrada.");
		return PAGINA_ERRO_404;
	}
	
	@RequestMapping(value = "/500", method = RequestMethod.GET)
	public String erroServidor(ModelMap model, Principal user) {
		model.addAttribute("message", "Ops, o site teve um erro técnico.");
		return PAGINA_ERRO_500;
	}
	
	@RequestMapping(value = "/error", method = RequestMethod.GET)
	public String erroServidorrr(ModelMap model, Principal user) {
		model.addAttribute("message", "Ops, o site teve um erro técnico.");
		return PAGINA_ERRO_500;
	}
	
	/**
	@Inject
	private PessoaService pessoaService;

	@Inject
	private UsuarioService usuarioService;
	
	@RequestMapping(value = "/meu-cadastro", method = RequestMethod.GET)
	public String paginaCadastroEstagiario(Model model, HttpSession session) {
		model.addAttribute("action", "cadastrar");

		Usuario usuario = usuarioService.getByCpf(SecurityContextHolder.getContext().getAuthentication().getName());
		
		if (pessoaService.getEstagiarioByPessoaCpf(usuario.getCpf()) != null) {
			return "redirect:/estagiario/meus-dados";
		}
		
		model.addAttribute("estagiario", new Estagiario());
		model.addAttribute("usuario", usuario);

		return Constants.PAGINA_FORM_ESTAGIARIO;
	}

	@RequestMapping(value = "/meu-cadastro", method = RequestMethod.POST)
	public String adicionarEstagiario( @Valid @ModelAttribute("estagiario") Estagiario estagiario, BindingResult result, HttpSession session, RedirectAttributes redirect, Model model) {

		if (result.hasErrors()) {
			return Constants.PAGINA_FORM_ESTAGIARIO;
		}

		String cpf = SecurityContextHolder.getContext().getAuthentication().getName();

		Papel papel = pessoaService.getPapel("ROLE_ESTAGIARIO_NPI");

		Pessoa pessoa = new Pessoa(cpf);
		pessoa.setPapeis(new ArrayList<Papel>());
		pessoa.getPapeis().add(papel);

		pessoaService.save(pessoa);
		//pessoaService.adicionarPessoa(pessoa);


		estagiario.setPessoa(pessoa);
		pessoaService.save(estagiario);
		//pessoaService.adicionarEstagiario(estagiario);
		
		getUsuarioLogado(session);

		redirect.addFlashAttribute("success", "Seu cadastro foi realizado com sucesso! Agora, você faz parte do NPI!");
		redirect.addFlashAttribute("warning", "Aguarde, você sera vinculada a uma turma, desde já sinta-se parte deste grupo, NPI.");

		return Constants.REDIRECT_PAGINA_INICIAL_ESTAGIARIO;
	}

	private Pessoa getUsuarioLogado(HttpSession session) {
		if (session.getAttribute(Constants.USUARIO_LOGADO) == null) {
			Pessoa pessoa = pessoaService.getPessoaByCpf(SecurityContextHolder.getContext().getAuthentication().getName());
			session.setAttribute(Constants.USUARIO_LOGADO, pessoa);
		}
		return (Pessoa) session.getAttribute(Constants.USUARIO_LOGADO);
	}
 */
	
	
	

}