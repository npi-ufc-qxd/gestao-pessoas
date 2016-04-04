package ufc.quixada.npi.gp.controller;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.ufc.quixada.npi.ldap.model.Usuario;
import br.ufc.quixada.npi.ldap.service.UsuarioService;
import ufc.quixada.npi.gp.model.Estagiario;
import ufc.quixada.npi.gp.model.Papel;
import ufc.quixada.npi.gp.model.Pessoa;
import ufc.quixada.npi.gp.service.EstagiarioService;
import ufc.quixada.npi.gp.service.PapelService;
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

	@Inject
	private PapelService papelService;

	@Inject
	private UsuarioService usuarioService;
	
	@RequestMapping(value = "/meu-cadastro", method = RequestMethod.GET)
	public String paginaCadastroEstagiario(Model model, HttpSession session) {
		model.addAttribute("action", "cadastrar");

		Usuario usuario = usuarioService.getByCpf(SecurityContextHolder.getContext().getAuthentication().getName());
		
		if (estagiarioService.getEstagiarioByPessoaCpf(usuario.getCpf()) != null) {
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

		Papel papel = papelService.getPapel("ROLE_ESTAGIARIO_NPI");

		Pessoa pessoa = new Pessoa(cpf);
		pessoa.setPapeis(new ArrayList<Papel>());
		pessoa.getPapeis().add(papel);

		pessoaService.save(pessoa);


		estagiario.setPessoa(pessoa);
		estagiarioService.save(estagiario);
		
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
	
}


