package ufc.quixada.npi.gp.controller;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ufc.quixada.npi.gp.model.AvaliacaoEstagio;
import ufc.quixada.npi.gp.model.Estagiario;
import ufc.quixada.npi.gp.model.Pessoa;
import ufc.quixada.npi.gp.model.Turma;
import ufc.quixada.npi.gp.model.enums.Dia;
import ufc.quixada.npi.gp.service.AvaliacaoService;
import ufc.quixada.npi.gp.service.EstagiarioService;
import ufc.quixada.npi.gp.service.PessoaService;
import ufc.quixada.npi.gp.service.TurmaService;
import ufc.quixada.npi.gp.utils.Constants;

@Component
@Controller
@RequestMapping("supervisor/turma/")
public class AvaliacaoController {

	@Inject
	private AvaliacaoService avaliacaoService;

	@Inject
	private PessoaService pessoaService;

	@Inject
	private EstagiarioService estagiarioService;

	@Inject
	private TurmaService turmaService;

	@RequestMapping(value = "{idTurma}/acompanhamento-avaliacao/estagiario/{idEstagiario}/adicionar/", method = RequestMethod.GET)
	public String novaAvaliacaoEstagio(Model model, @PathVariable("idEstagiario") Long idEstagiario, @PathVariable("idTurma") Long idTurma) {
		model.addAttribute("action", "cadastrar");
		model.addAttribute("avaliacaoEstagio", new AvaliacaoEstagio());
		model.addAttribute("turma",turmaService.getTurmaByIdAndEstagiarioId(idTurma, idEstagiario));
		model.addAttribute("estagiario",estagiarioService.find(Estagiario.class, idEstagiario));
		return "supervisor/form-avaliacao-estagio";
	}

	@RequestMapping(value = "{idTurma}/acompanhamento-avaliacao/estagiario/{idEstagiario}/adicionar/", method = RequestMethod.POST)
	public String adicionarAvaliacaoEstagio(Model model,
			@Valid @ModelAttribute("avaliacaoEstagio") AvaliacaoEstagio avaliacaoEstagio, HttpSession session,
			RedirectAttributes redirect, @PathVariable("idEstagiario") Long idEstagiario,
			@PathVariable("idTurma") Long idTurma) {
		model.addAttribute("action", "cadastrar");
		Pessoa pessoa = getUsuarioLogado(session);
		Estagiario estagiario = estagiarioService.find(Estagiario.class, idEstagiario);
		Turma turma = turmaService.getTurmaByIdAndEstagiarioId(idTurma, idEstagiario);
		
		avaliacaoEstagio.setSupervisor(pessoa);
		avaliacaoEstagio.setTurma(turma);
		avaliacaoEstagio.setEstagiario(estagiario);
		avaliacaoService.save(avaliacaoEstagio);
		redirect.addFlashAttribute("success", "Avaliação cadastrada com sucesso.");

		return "redirect:/supervisor/turma/{idTurma}/acompanhamento-avaliacao/estagiario/{idEstagiario}";
	}
	
	@RequestMapping(value = "/{idAvalicaoEstagio}/editar", method = RequestMethod.GET)
	public String paginaEditarTurma(@PathVariable("idAvaliacaoEstagio") Long idAvaliacaoEstagio, Model model, HttpSession session) {
		model.addAttribute("action", "editar");
		model.addAttribute("avaliacaoEstagio", avaliacaoService.find(AvaliacaoEstagio.class, idAvaliacaoEstagio));

		return "supervisor/form-avaliacao-estagio";
	}

	@RequestMapping(value = "/{idAvalia}/editar", method = RequestMethod.POST)
	public String editarTurma(Model model, @Valid @ModelAttribute("turma") Turma turma,  BindingResult result, HttpSession session) {

		model.addAttribute("action", "editar");

		if (result.hasErrors()) {
			model.addAttribute("dias", Dia.values());
			return "supervisor/form-turma";
		}
		
		Pessoa pessoa = getUsuarioLogado(session);
		Turma turmaDoBanco = turmaService.getTurmaByIdAndSupervisorById(turma.getId(), pessoa.getId());
		
		turmaDoBanco.setNome(turma.getNome());
		turmaDoBanco.setStatusTurma(turma.getStatusTurma());
		turmaDoBanco.setAno(turma.getAno());
		turmaDoBanco.setSemestre(turma.getSemestre());
		turmaDoBanco.setInicio(turma.getInicio());
		turmaDoBanco.setTermino(turma.getTermino());

		turmaService.update(turmaDoBanco);

		return "redirect:/supervisor/turmas";
	}

	private Pessoa getUsuarioLogado(HttpSession session) {
		if (session.getAttribute(Constants.USUARIO_LOGADO) == null) {
			Pessoa pessoa = pessoaService.getPessoaByCpf(SecurityContextHolder.getContext().getAuthentication()
					.getName());
			session.setAttribute(Constants.USUARIO_LOGADO, pessoa);
		}
		return (Pessoa) session.getAttribute(Constants.USUARIO_LOGADO);
	}

}
