package ufc.quixada.npi.gp.controller;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.ufc.quixada.npi.ldap.service.UsuarioService;
import ufc.quixada.npi.gp.model.Frequencia;
import ufc.quixada.npi.gp.model.Papel;
import ufc.quixada.npi.gp.model.Pessoa;
import ufc.quixada.npi.gp.model.Servidor;
import ufc.quixada.npi.gp.model.enums.StatusFrequencia;
import ufc.quixada.npi.gp.service.FrequenciaService;
import ufc.quixada.npi.gp.service.PapelService;
import ufc.quixada.npi.gp.service.PessoaService;
import ufc.quixada.npi.gp.service.ServidorService;
import ufc.quixada.npi.gp.service.TurmaService;
import ufc.quixada.npi.gp.utils.Constants;

@Component
@Controller
@RequestMapping("supervisor")
public class SupervisorController {
	
	@Inject
	private PessoaService pessoaService;
	
	@Inject
	private ServidorService servidorService;
	
	@Inject
	private UsuarioService usuarioService;
	
	@Inject
	private PapelService papelService;
	
	@Inject
	private TurmaService turmaService;

	@Inject
	private FrequenciaService frequenciaService; 
	
	@RequestMapping(value = {"","/"}, method = RequestMethod.GET)
	public String paginaInicial(Model Model, HttpSession session)  {

		String cpf = SecurityContextHolder.getContext().getAuthentication().getName();

		if(!pessoaService.isPessoa(cpf)){

			Papel papel = papelService.getPapel("ROLE_SUPERVISOR");

			Pessoa pessoa = new Pessoa(cpf);
			pessoa.setPapeis(new ArrayList<Papel>());
			pessoa.getPapeis().add(papel);

			pessoaService.save(pessoa);

			Servidor servidor = new Servidor(pessoa, usuarioService.getByCpf(cpf).getSiape());
			servidorService.save(servidor);
		}

		return "redirect:/supervisor/turmas";
	}
	
	@RequestMapping(value = "/turmas", method = RequestMethod.GET)
	public String listarTurmas(Model model, HttpSession session) {
		Pessoa pessoa = getUsuarioLogado(session);
		model.addAttribute("turmas", turmaService.getTurmasBySupervisorId(pessoa.getId()));

		return "supervisor/list-turmas";
	}
	
	//@RequestMapping(value = "/")

	@RequestMapping(value = "/frequencia/realizar-observacao", method = RequestMethod.POST)
	public String frequenciaObservar(@RequestParam("pk") Long idFrequencia, @RequestParam("value") String observacao, Model model) {
		Frequencia frequencia = frequenciaService.find(Frequencia.class, idFrequencia);
		
		if(frequencia != null){
			frequencia.setObservacao(observacao);
			frequenciaService.update(frequencia);
			return "supervisor/list-frequencia-estagiario";
		}
		
		return "";
	}
	
	@RequestMapping(value = "/frequencia/atualizar-status", method = RequestMethod.POST)
	public String atualizarStatus(@RequestParam("pk") Long idFrequencia, @RequestParam("value") StatusFrequencia status, Model model, RedirectAttributes redirectAttributes) {
		Frequencia frequencia = frequenciaService.find(Frequencia.class, idFrequencia);
		
		if(frequencia != null){
			frequencia.setStatusFrequencia(status);
			frequenciaService.update(frequencia);
			return "supervisor/list-frequencia-estagiario";
		}

		return "";
	}
	
	

	private Pessoa getUsuarioLogado(HttpSession session) {
		if (session.getAttribute(Constants.USUARIO_LOGADO) == null) {
			Pessoa pessoa = pessoaService.getPessoaByCpf(SecurityContextHolder.getContext().getAuthentication().getName());
			session.setAttribute(Constants.USUARIO_LOGADO, pessoa);
		}
		return (Pessoa) session.getAttribute(Constants.USUARIO_LOGADO);
	}

	
	
}
