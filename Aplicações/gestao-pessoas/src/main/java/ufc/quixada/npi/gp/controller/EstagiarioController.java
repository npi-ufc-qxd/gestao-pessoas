package ufc.quixada.npi.gp.controller;

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
@RequestMapping("estagiario")
public class EstagiarioController {
	@Inject
	private PessoaService servicePessoa;
	
	@Inject
	private EstagiarioService serviceEstagiario;

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(ModelMap modelMap, HttpSession session) {

		return "redirect:/estagiario/inicial";
	}

	@RequestMapping(value = "/inicial")
	public String inicial(ModelMap modelMap, HttpSession session) {

		modelMap.addAttribute("usuario", SecurityContextHolder.getContext()
				.getAuthentication().getName());
		getUsuarioLogado(session);

		
		modelMap.addAttribute("cadastrado", serviceEstagiario.estagiarioCadastrado(getUsuarioLogado(session).getId()));
		
		return "estagiario/inicial";
	}

	@RequestMapping(value = "/cadastrar", method = RequestMethod.GET)
	public String cadastro(Model model) {
		model.addAttribute("estagiario", new Estagiario());
		return "estagiario/cadastrar";
	}

	@RequestMapping(value = "/cadastrar", method = RequestMethod.POST)
	public String adicionarEstagiario(
			@Valid @ModelAttribute("estagiario") Estagiario estagiario,
			BindingResult result, HttpSession session,
			RedirectAttributes redirect) {

		estagiario.setPessoa(getUsuarioLogado(session));
		
		System.out.println(estagiario.toString());
		
		serviceEstagiario.save(estagiario);
		redirect.addFlashAttribute("info", "Estagiário cadastrado com sucesso.");
		
		return "redirect:/estagiario/inicial";
	}

	private Pessoa getUsuarioLogado(HttpSession session) {
		if (session.getAttribute(Constants.USUARIO_LOGADO) == null) {
			Pessoa pessoa = servicePessoa
					.getUsuarioByLogin(SecurityContextHolder.getContext()
							.getAuthentication().getName());
			session.setAttribute(Constants.USUARIO_LOGADO, pessoa);
		}
		return (Pessoa) session.getAttribute(Constants.USUARIO_LOGADO);
	}

}