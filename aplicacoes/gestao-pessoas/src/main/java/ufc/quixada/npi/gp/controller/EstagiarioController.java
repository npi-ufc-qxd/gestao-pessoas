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
import org.springframework.web.bind.annotation.PathVariable;
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
		modelMap.addAttribute("estagiario", serviceEstagiario
				.estagiarioCadastrado(getUsuarioLogado(session).getId()));
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
		serviceEstagiario.save(estagiario);

		
		redirect.addFlashAttribute("info", "Estagiário cadastrado com sucesso.");
		return "redirect:/estagiario/inicial";
	}

	@RequestMapping(value = "/{id}/contaspessoais", method = RequestMethod.GET)
	public String contasPessoais(@PathVariable("id") long id, Model model,
			HttpSession session, RedirectAttributes redirectAttributes) {

		Estagiario estagiario = serviceEstagiario.find(Estagiario.class, id);
		Pessoa pessoa = getUsuarioLogado(session);

		if (estagiario == null) {
			redirectAttributes.addFlashAttribute("erro",
					"Estagiario inexistente.");
			return "redirect:/estagiario/inicial";
		}
		if (pessoa.getId() == estagiario.getPessoa().getId()) {
			model.addAttribute("estagiario", estagiario);
			model.addAttribute("action", "contaspessoais");
			return "estagiario/contaspessoais";
		}
		return "redirect:/estagiario/inicial";
	}

	@RequestMapping(value = "/{id}/contaspessoais", method = RequestMethod.POST)
	public String atualizarEstagiario(
			@PathVariable("id") Long id,
			@Valid @ModelAttribute(value = "estagiario") Estagiario estagiarioAtualizado,
			BindingResult result, Model model, HttpSession session,
			RedirectAttributes redirect) {

		Estagiario estagiario = serviceEstagiario.find(Estagiario.class, id);
		estagiario.setContaRedmine(estagiarioAtualizado.getContaRedmine());
		estagiario.setContaGithub(estagiarioAtualizado.getContaGithub());
		estagiario.setContaHangout(estagiarioAtualizado.getContaHangout());
		this.serviceEstagiario.update(estagiario);

		return "redirect:/estagiario/inicial";
	}

	private Pessoa getUsuarioLogado(HttpSession session) {
		if (session.getAttribute(Constants.USUARIO_LOGADO) == null) {
			Pessoa pessoa = servicePessoa
					.getPessoaByLogin(SecurityContextHolder.getContext()
							.getAuthentication().getName());
			session.setAttribute(Constants.USUARIO_LOGADO, pessoa);
		}
		return (Pessoa) session.getAttribute(Constants.USUARIO_LOGADO);
	}

}