package ufc.quixada.npi.gp.controller;

import javax.inject.Inject;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ufc.quixada.npi.gp.model.AvaliacaoRendimento;
import ufc.quixada.npi.gp.model.Estagiario;
import ufc.quixada.npi.gp.model.Pessoa;
import ufc.quixada.npi.gp.model.Submissao;
import ufc.quixada.npi.gp.model.Turma;
import ufc.quixada.npi.gp.service.AvaliacaoService;
import ufc.quixada.npi.gp.service.EstagiarioService;
import ufc.quixada.npi.gp.service.PessoaService;
import ufc.quixada.npi.gp.service.SubmissaoService;
import ufc.quixada.npi.gp.service.TurmaService;
import ufc.quixada.npi.gp.utils.Constants;

@Component
@Controller
@RequestMapping("supervisor/turma/")
public class AvaliacaoController {

	@Inject
	private AvaliacaoService avaliacaoService;
	
	@Inject
	private SubmissaoService submissaoService;

	@Inject
	private PessoaService pessoaService;

	@Inject
	private EstagiarioService estagiarioService;

	@Inject
	private TurmaService turmaService;

	@RequestMapping(value = "{idTurma}/acompanhamento-avaliacao/estagiario/{idEstagiario}/adicionar/", method = RequestMethod.GET)
	public String novaAvaliacaoEstagio(Model model, @PathVariable("idEstagiario") Long idEstagiario, @PathVariable("idTurma") Long idTurma) {
		model.addAttribute("action", "cadastrar");
		model.addAttribute("avaliacaoEstagio", new AvaliacaoRendimento());
		model.addAttribute("turma",turmaService.getTurmaByIdAndEstagiarioId(idTurma, idEstagiario));
		model.addAttribute("estagiario",estagiarioService.find(Estagiario.class, idEstagiario));
		return "supervisor/form-avaliacao-estagio";
	}

	@RequestMapping(value = "{idTurma}/acompanhamento-avaliacao/estagiario/{idEstagiario}/adicionar/", method = RequestMethod.POST)
	public String adicionarAvaliacaoEstagio(Model model,
			@Valid @ModelAttribute("avaliacaoEstagio") AvaliacaoRendimento avaliacaoRendimento, HttpSession session,
			RedirectAttributes redirect, @PathVariable("idEstagiario") Long idEstagiario,
			@PathVariable("idTurma") Long idTurma) {
		model.addAttribute("action", "cadastrar");
		Pessoa pessoa = getUsuarioLogado(session);
		Estagiario estagiario = estagiarioService.find(Estagiario.class, idEstagiario);
		Turma turma = turmaService.getTurmaByIdAndEstagiarioId(idTurma, idEstagiario);
		
		avaliacaoRendimento.setSupervisor(pessoa);
		avaliacaoRendimento.setTurma(turma);
		avaliacaoRendimento.setEstagiario(estagiario);
		avaliacaoService.save(avaliacaoRendimento);
		redirect.addFlashAttribute("success", "Avaliação cadastrada com sucesso.");

		return "redirect:/supervisor/turma/{idTurma}/acompanhamento-avaliacao/estagiario/{idEstagiario}";
	}
	
	@RequestMapping(value = "{idTurma}/avaliacao/{idAvaliacaoEstagio}/estagiario/{idEstagiario}/editar", method = RequestMethod.GET)
	public String paginaEditarAvaliacaoEstagio(@PathVariable("idEstagiario") Long idEstagiario, @PathVariable("idTurma") Long idTurma, @PathVariable("idAvaliacaoEstagio") Long idAvaliacaoEstagio, Model model, HttpSession session) {
		model.addAttribute("action", "editar");
		model.addAttribute("avaliacaoEstagio", avaliacaoService.find(AvaliacaoRendimento.class, idAvaliacaoEstagio));
		model.addAttribute("turma",turmaService.getTurmaByIdAndEstagiarioId(idTurma, idEstagiario));
		model.addAttribute("estagiario",estagiarioService.find(Estagiario.class, idEstagiario));
		return "supervisor/form-avaliacao-estagio";
	}

	@RequestMapping(value = "{idTurma}/avaliacao/{idAvaliacaoEstagio}/estagiario/{idEstagiario}/editar", method = RequestMethod.POST)
	public String editarAvaliacaoEstagio(Model model,
			@Valid @ModelAttribute("avaliacaoEstagio") AvaliacaoRendimento avaliacaoRendimento, HttpSession session,
			RedirectAttributes redirect, @PathVariable("idEstagiario") Long idEstagiario,
			@PathVariable("idTurma") Long idTurma) {

		model.addAttribute("action", "editar");
		AvaliacaoRendimento avaliacaoDoBanco = avaliacaoService.find(AvaliacaoRendimento.class, avaliacaoRendimento.getId());
		Pessoa pessoa = getUsuarioLogado(session);
		Estagiario estagiario = estagiarioService.find(Estagiario.class, idEstagiario);
		Turma turma = turmaService.getTurmaByIdAndEstagiarioId(idTurma, idEstagiario);
		
		avaliacaoDoBanco.setSupervisor(pessoa);
		avaliacaoDoBanco.setEstagiario(estagiario);
		avaliacaoDoBanco.setTurma(turma);
		avaliacaoDoBanco.setNota(avaliacaoRendimento.getNota());
		avaliacaoDoBanco.setFatorAssiduidadeDisciplina(avaliacaoRendimento.getFatorAssiduidadeDisciplina());
		avaliacaoDoBanco.setFatorIniciativaProdutividade(avaliacaoRendimento.getFatorIniciativaProdutividade());
		avaliacaoDoBanco.setFatorRelacionamento(avaliacaoRendimento.getFatorRelacionamento());
		avaliacaoDoBanco.setFatorResponsabilidade(avaliacaoRendimento.getFatorResponsabilidade());
		
		avaliacaoService.update(avaliacaoDoBanco);

		return "redirect:/supervisor/turma/{idTurma}/acompanhamento-avaliacao/estagiario/{idEstagiario}";
	}
	
	@RequestMapping(value = "{idTurma}/acompanhamento-avaliacao/estagiario/{idEstagiario}/avaliar-submissao", method = RequestMethod.POST)
	public String avaliarSubmissao(Model model,
			@Valid @ModelAttribute("submissoes") Submissao submissao, HttpSession session,
			RedirectAttributes redirect, @PathVariable("idEstagiario") Long idEstagiario,
			@PathVariable("idTurma") Long idTurma) {

		model.addAttribute("action", "editar");
		Submissao submissaoDoBanco = submissaoService.find(Submissao.class, submissao.getId());
		Pessoa pessoa = getUsuarioLogado(session);
		Estagiario estagiario = estagiarioService.find(Estagiario.class, idEstagiario);
		Turma turma = turmaService.getTurmaByIdAndEstagiarioId(idTurma, idEstagiario);
		
		submissaoDoBanco.setEstagiario(estagiario);
		submissaoDoBanco.setTurma(turma);
		submissaoDoBanco.setNota(submissao.getNota());
		submissaoDoBanco.setStatusEntrega(submissao.getStatusEntrega());
		submissaoDoBanco.setComentario(submissao.getComentario());
		
		submissaoService.update(submissaoDoBanco);

		return "redirect:/supervisor/turma/{idTurma}/acompanhamento-avaliacao/estagiario/{idEstagiario}";
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
