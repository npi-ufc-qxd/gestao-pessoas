package ufc.quixada.npi.gp.controller;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ufc.quixada.npi.gp.model.Estagiario;
import ufc.quixada.npi.gp.model.Frequencia;
import ufc.quixada.npi.gp.model.Papel;
import ufc.quixada.npi.gp.model.Pessoa;
import ufc.quixada.npi.gp.model.Projeto;
import ufc.quixada.npi.gp.model.Servidor;
import ufc.quixada.npi.gp.model.Turma;
import ufc.quixada.npi.gp.model.enums.StatusFrequencia;
import ufc.quixada.npi.gp.model.enums.StatusTurma;
import ufc.quixada.npi.gp.service.EstagiarioService;
import ufc.quixada.npi.gp.service.FrequenciaService;
import ufc.quixada.npi.gp.service.PapelService;
import ufc.quixada.npi.gp.service.PessoaService;
import ufc.quixada.npi.gp.service.ProjetoService;
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
	private ProjetoService projetoService; 
	
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

	@RequestMapping(value = "/projetos", method = RequestMethod.GET)
	public String listarProjetos( Model model) {
		model.addAttribute("projetos", projetoService.find(Projeto.class));

		return "supervisor/list-projetos";
	}

	@RequestMapping(value = "/projeto", method = RequestMethod.GET)
	public String novoProjeto( Model model) {
		model.addAttribute("action", "cadastrar");
		model.addAttribute("projeto", new Projeto());

		return "supervisor/form-projeto";
	}

	@RequestMapping(value = "/projeto", method = RequestMethod.POST)
	public String adicionarProjeto(Model model, @Valid @ModelAttribute("projeto") Projeto projeto, BindingResult result) {
		model.addAttribute("action", "cadastrar");
		
		if (result.hasErrors()) {
			return "supervisor/form-projeto";
		}

		projetoService.save(projeto);

		return "redirect:/supervisor/projetos";
	}

	@RequestMapping(value = "/projeto/{idProjeto}/editar", method = RequestMethod.GET)
	public String editarProjeto(@PathVariable("idProjeto") Long idProjeto, Model model) {
		model.addAttribute("action", "editar");
		model.addAttribute("projeto", projetoService.find(Projeto.class, idProjeto));

		return "supervisor/form-projeto";
	}

	@RequestMapping(value = "/projeto/{idProjeto}/editar", method = RequestMethod.POST)
	public String editarProjeto(Model model, @Valid @ModelAttribute("projeto") Projeto projeto, BindingResult result) {
		model.addAttribute("action", "editar");

		if (result.hasErrors()) {
			return "supervisor/form-projeto";
		}
	
		projeto = atualizarProjeto(projeto);

		projetoService.update(projeto);

		return "redirect:/supervisor/projetos";
	}

	@RequestMapping(value = "/projeto/{idProjeto}/informacoes", method = RequestMethod.GET)
	public String detalhesProjeto(@PathVariable("idProjeto") Long idProjeto, Model model) {
		Projeto projeto = projetoService.find(Projeto.class, idProjeto);

		model.addAttribute("projeto", projeto);

		return "supervisor/info-projeto";
	}

	@RequestMapping(value = "/projeto/{idProjeto}/excluir", method = RequestMethod.GET)
	public String excluirProjeto(@PathVariable("idProjeto") Long idProjeto, Model model) {
		Projeto projeto = projetoService.find(Projeto.class, idProjeto);
		
		if(projeto != null){
			projetoService.delete(projeto);
		}

		return "redirect:/supervisor/projetos";
	}
	
	@RequestMapping(value = "/projeto/{idProjeto}/vincular", method = RequestMethod.GET)
	public String paginaVincularMembrosProjeto(Model model, @PathVariable("idProjeto") Long idProjeto, HttpSession session) {
		model.addAttribute("projeto", projetoService.find(Projeto.class, idProjeto));
		model.addAttribute("estagiarios", estagiarioService.find(Estagiario.class));
		
		return "supervisor/form-vincular-membros-projeto";
	}

	@RequestMapping(value = "/projeto/{idProjeto}/vincular", method = RequestMethod.POST)
	public String vincularMembrosProjeto(Model model, @ModelAttribute("projeto") Projeto projeto) {
		
		projeto = atualizarProjeto(projeto);
		
		projetoService.update(projeto);

		return "redirect:/supervisor/projeto/" + projeto.getId() + "/informacoes";
	}

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

	private List<Estagiario> getEstagiariosSelecionados(List<Estagiario> estagiarios) {
		List<Estagiario> estagiariosSelecionados = new ArrayList<Estagiario>();

		for (Estagiario estagiario : estagiarios) {
			if(estagiario.getId() != null){
				estagiario = estagiarioService.find(Estagiario.class, estagiario.getId());

				estagiariosSelecionados.add(estagiario);
			}
		}

		return estagiariosSelecionados;
	}

	private List<Estagiario> atualizarTurmaEstagiarios(List<Estagiario> estagiarios, Turma turma) {
		for (Estagiario estagiario : estagiarios) {
			if(estagiario.getTurmas() != null) {
				if(!estagiario.getTurmas().contains(turma)){
					estagiario.getTurmas().add(turma);
				}
			} else {
				estagiario.setTurmas(new ArrayList<Turma>());
				estagiario.getTurmas().add(turma);
			}
		}

		return estagiarios;
	}

	private Projeto atualizarProjeto(Projeto projeto) {
		List<Estagiario> membros = new ArrayList<Estagiario>();
		if (projeto.getMembros() != null) {
			for (Estagiario membro : projeto.getMembros()) {
				if(membro.getId() != null){
					membro = estagiarioService.find(Estagiario.class, membro.getId());
					membros.add(membro);
				}
			}
		}
		projeto.setMembros(membros);
		return projeto;
	}
}
