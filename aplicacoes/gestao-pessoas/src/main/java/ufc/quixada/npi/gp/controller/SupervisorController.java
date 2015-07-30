package ufc.quixada.npi.gp.controller;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.Entity;
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

import ufc.quixada.npi.gp.model.Estagiario;
import ufc.quixada.npi.gp.model.Horario;
import ufc.quixada.npi.gp.model.Papel;
import ufc.quixada.npi.gp.model.Periodo;
import ufc.quixada.npi.gp.model.Pessoa;
import ufc.quixada.npi.gp.model.Servidor;
import ufc.quixada.npi.gp.model.Turma;
import ufc.quixada.npi.gp.model.enums.Dia;
import ufc.quixada.npi.gp.service.EstagiarioService;
import ufc.quixada.npi.gp.service.PapelService;
import ufc.quixada.npi.gp.service.PeriodoService;
import ufc.quixada.npi.gp.service.PessoaService;
import ufc.quixada.npi.gp.service.ServidorService;
import ufc.quixada.npi.gp.service.TurmaService;
import ufc.quixada.npi.gp.utils.Constants;
import br.ufc.quixada.npi.ldap.service.UsuarioService;


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
	private EstagiarioService estagiarioService;
	
	@Inject
	private TurmaService turmaService;

	@Inject
	private PeriodoService periodoService;
	
	@RequestMapping(value = "/inicio", method = RequestMethod.GET)	
	public String paginaInicial(ModelMap modelMap, HttpSession session)  {
		
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

		return "supervisor/inicial";
	}

	@RequestMapping(value = "/vincular-estagiarios-turma", method = RequestMethod.GET)
	public String paginaVincularEstagiarioTurma(Model model, HttpSession session)  {
		model.addAttribute("estagiarios", estagiarioService.find(Estagiario.class));
		return "supervisor/vincular-estagiarios-turma";
	}

	@RequestMapping(value = "/vincular-estagiarios-turma/{id}", method = RequestMethod.GET)
	public String paginaVincularEstagiarioTurma(Model model, HttpSession session, @PathVariable("id") Long idTurma)  {

		model.addAttribute("turma", turmaService.find(Turma.class, idTurma));
		model.addAttribute("estagiarios", estagiarioService.find(Estagiario.class));

		return "supervisor/vincular-estagiarios-turma";
	}

	@RequestMapping(value = "/vincular-estagiarios-turma/{id}", method = RequestMethod.POST)
	public String atualizarVinculoEstagiarioTurma(Model model, HttpSession session, @ModelAttribute("turma") Turma turma)  {
		
		Turma turmaDoBanco = turmaService.find(Turma.class, turma.getId());
		
		turmaDoBanco.setEstagiarios(atualizarListaEstagiarios(turma));
		
		turmaService.update(turmaDoBanco);

		model.addAttribute("turma", turmaDoBanco);
		model.addAttribute("estagiarios", estagiarioService.find(Estagiario.class));

		return "redirect:/supervisor/vincular-estagiarios-turma/" + turmaDoBanco.getId();
	}
	
	@RequestMapping(value = "periodo/{idPeriodo}/adicionar-turma", method = RequestMethod.GET)
	public String novaTurmaPeriodo(ModelMap model, @PathVariable("idPeriodo") Long idPeriodo) {

		model.addAttribute("periodo", periodoService.find(Periodo.class, idPeriodo));

		Turma turma = new Turma();

		model.addAttribute("turma", turma);
		model.addAttribute("dias", Dia.values());
		
		return "supervisor/form-turma";
	}

	@RequestMapping(value = "periodo/{idPeriodo}/adicionar-turma", method = RequestMethod.POST)
	public String adicionarTurmaPeriodo(ModelMap model, @Valid @ModelAttribute("turma") Turma turma,  @PathVariable("idPeriodo") Long idPeriodo, BindingResult result, HttpSession session) {

		Periodo periodo = periodoService.find(Periodo.class, idPeriodo);
		
		if (result.hasErrors()) {
			model.addAttribute("periodo", periodo);
			model.addAttribute("dias", Dia.values());
			return "supervisor/form-turma";
		}
		
		Pessoa pessoa = getUsuarioLogado(session);
		
		turma.setSupervisor(pessoa);
		turma.setPeriodo(periodo);
		turmaService.save(turma);
		
		return "redirect:/coordenador/periodos";
	}
	

	private Pessoa getUsuarioLogado(HttpSession session) {
		if (session.getAttribute(Constants.USUARIO_LOGADO) == null) {
			Pessoa pessoa = pessoaService.getPessoaByCpf(SecurityContextHolder.getContext().getAuthentication().getName());
			session.setAttribute(Constants.USUARIO_LOGADO, pessoa);
		}
		return (Pessoa) session.getAttribute(Constants.USUARIO_LOGADO);
	}
	
	private List<Estagiario> atualizarListaEstagiarios(Turma turma) {
		List<Estagiario> estagiarios = new ArrayList<Estagiario>();
		if (turma.getEstagiarios() != null) {
			for (Estagiario estagiario : turma.getEstagiarios()) {
				if(estagiario.getId() != null){
					estagiario = estagiarioService.find(Estagiario.class, estagiario.getId());
					estagiarios.add(estagiario);
				}
			}
		}
		return estagiarios;
	}
}


