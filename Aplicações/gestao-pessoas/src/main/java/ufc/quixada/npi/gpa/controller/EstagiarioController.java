package ufc.quixada.npi.gpa.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ufc.quixada.npi.gpa.model.Pessoa;
import ufc.quixada.npi.gpa.service.PessoaService;
import ufc.quixada.npi.gpa.utils.Constants;

@Component
@Controller
@RequestMapping("estagiario")
public class EstagiarioController {
	@Inject
	private PessoaService servicePessoa;

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index() {
		return "redirect:/estagiario/inicial";
	}

	@RequestMapping(value = "/inicial")
	public String listar(ModelMap modelMap, HttpSession session) {
		
		return "estagiario/inicial";
	}

	private Pessoa getUsuarioLogado(final HttpSession session) {
		if (session.getAttribute(Constants.USUARIO_LOGADO) == null) {
			Pessoa pessoa = servicePessoa
			.getUsuarioByLogin(SecurityContextHolder.getContext()
			.getAuthentication().getName());
			session.setAttribute(Constants.USUARIO_LOGADO, pessoa);
		}
		return (Pessoa) session.getAttribute(Constants.USUARIO_LOGADO);
	}

}