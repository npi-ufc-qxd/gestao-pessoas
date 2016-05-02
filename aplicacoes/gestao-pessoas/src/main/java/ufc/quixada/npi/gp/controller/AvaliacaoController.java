package ufc.quixada.npi.gp.controller;

import java.io.IOException;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ufc.quixada.npi.gp.model.AvaliacaoRendimento;
import ufc.quixada.npi.gp.model.Documento;
import ufc.quixada.npi.gp.model.Estagiario;
import ufc.quixada.npi.gp.model.Pessoa;
import ufc.quixada.npi.gp.model.Submissao;
import ufc.quixada.npi.gp.model.Turma;
import ufc.quixada.npi.gp.model.enums.Comprometimento;
import ufc.quixada.npi.gp.model.enums.CuidadoMateriais;
import ufc.quixada.npi.gp.model.enums.CumprimentoPrazos;
import ufc.quixada.npi.gp.model.enums.Disciplina;
import ufc.quixada.npi.gp.model.enums.Frequencia;
import ufc.quixada.npi.gp.model.enums.Iniciativa;
import ufc.quixada.npi.gp.model.enums.Permanencia;
import ufc.quixada.npi.gp.model.enums.QualidadeDeTrabalho;
import ufc.quixada.npi.gp.model.enums.QuantidadeDeTrabalho;
import ufc.quixada.npi.gp.model.enums.Relacionamento;
import ufc.quixada.npi.gp.model.enums.TrabalhoEmEquipe;
import ufc.quixada.npi.gp.model.enums.Tipo;
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
		Turma turma = turmaService.getTurmaByIdAndEstagiarioId(idTurma, idEstagiario);
		boolean showTurmaNPI = true;
		if(turma.getTipoTurma().getLabel() == "Empresa"){
			showTurmaNPI = false;
		}

		model.addAttribute("action", "cadastrar");
		model.addAttribute("avaliacaoRendimento", new AvaliacaoRendimento());

		model.addAttribute("turma",turma);
		model.addAttribute("estagiario",estagiarioService.find(Estagiario.class, idEstagiario));

		
		model.addAttribute("frequencias",Frequencia.values());
		model.addAttribute("permanencias",Permanencia.values());
		model.addAttribute("disciplinas",Disciplina.values());
		model.addAttribute("quantidades",QuantidadeDeTrabalho.values());
		model.addAttribute("qualidades",QualidadeDeTrabalho.values());
		model.addAttribute("cumprimentos",CumprimentoPrazos.values());
		model.addAttribute("frequencias",Frequencia.values());
		model.addAttribute("iniciativas",Iniciativa.values());
		model.addAttribute("comprometimentos",Comprometimento.values());
		model.addAttribute("cuidados",CuidadoMateriais.values());
		model.addAttribute("relacionamentos",Relacionamento.values());
		model.addAttribute("trabalhos",TrabalhoEmEquipe.values());
		model.addAttribute("showTurmaNPI", showTurmaNPI);
		return "supervisor/form-avaliacao-estagio";
	}

	@RequestMapping(value = "{idTurma}/acompanhamento-avaliacao/estagiario/{idEstagiario}/adicionar/", method = RequestMethod.POST)
	public String adicionarAvaliacaoEstagio(Model model,
			@Valid @ModelAttribute("avaliacaoRendimento") AvaliacaoRendimento avaliacaoRendimento, HttpSession session,
			RedirectAttributes redirect, @PathVariable("idEstagiario") Long idEstagiario,
			@PathVariable("idTurma") Long idTurma, @Valid @RequestParam("nota") Double nota, @Valid @RequestParam("rendimento") MultipartFile rendimento){

		if(!rendimento.getContentType().equals("application/pdf")){
			redirect.addFlashAttribute("error", "Escolha um arquivo pdf.");
			return "redirect:/supervisor/turma/{idTurma}/acompanhamento-avaliacao/estagiario/{idEstagiario}";
		}
		
		model.addAttribute("action", "cadastrar");
		Pessoa pessoa = getUsuarioLogado(session);
		Estagiario estagiario = estagiarioService.find(Estagiario.class, idEstagiario);
		Turma turma = turmaService.getTurmaByIdAndEstagiarioId(idTurma, idEstagiario);

		Tipo tipo = Tipo.AVALIACAO_RENDIMENTO;
		
		try {
			turmaService.submeterDocumento(estagiario, turma, tipo, rendimento);
		} catch (IOException e) {
			return "redirect:/500";
		}
		
		Submissao submissao = turmaService.getSubmissaoByEstagiarioIdAndIdTurmaAndTipo(idEstagiario, idTurma, tipo);
		Documento documento = submissao.getDocumento();
		avaliacaoRendimento.setSupervisor(pessoa);
		avaliacaoRendimento.setTurma(turma);
		avaliacaoRendimento.setEstagiario(estagiario);
		avaliacaoRendimento.setNota(nota);
		avaliacaoRendimento.setDocumento(documento);
		avaliacaoService.save(avaliacaoRendimento);
		redirect.addFlashAttribute("success", "Avaliação cadastrada com sucesso.");

		return "redirect:/supervisor/turma/{idTurma}/acompanhamento-avaliacao/estagiario/{idEstagiario}";
	}

	@RequestMapping(value = "{idTurma}/avaliacao/{idAvaliacaoRendimento}/estagiario/{idEstagiario}/editar", method = RequestMethod.GET)
	public String paginaEditarAvaliacaoEstagio(@PathVariable("idEstagiario") Long idEstagiario,
			@PathVariable("idTurma") Long idTurma, @PathVariable("idAvaliacaoRendimento") Long idAvaliacaoRendimento,
			Model model, HttpSession session) {
		model.addAttribute("action", "editar");
		model.addAttribute("avaliacaoRendimento",
				avaliacaoService.find(AvaliacaoRendimento.class, idAvaliacaoRendimento));
		model.addAttribute("turma", turmaService.getTurmaByIdAndEstagiarioId(idTurma, idEstagiario));
		model.addAttribute("estagiario", estagiarioService.find(Estagiario.class, idEstagiario));
		return "supervisor/form-avaliacao-estagio";
	}

	@RequestMapping(value = "{idTurma}/avaliacao/{idAvaliacaoRendimento}/estagiario/{idEstagiario}/editar", method = RequestMethod.POST)
	public String editarAvaliacaoEstagio(Model model,
			@Valid @ModelAttribute("avaliacaoRendimento") AvaliacaoRendimento avaliacaoRendimento, HttpSession session,
			RedirectAttributes redirect, @PathVariable("idEstagiario") Long idEstagiario,
			@PathVariable("idTurma") Long idTurma) {

		model.addAttribute("action", "editar");
		AvaliacaoRendimento avaliacaoDoBanco = avaliacaoService.find(AvaliacaoRendimento.class,
				avaliacaoRendimento.getId());
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
		avaliacaoDoBanco.setNotaSeminario(avaliacaoRendimento.getNotaSeminario());
		avaliacaoDoBanco.setFatorComentarioSeminario(avaliacaoRendimento.getFatorComentarioSeminario());
		
		avaliacaoService.update(avaliacaoDoBanco);

		return "redirect:/supervisor/turma/{idTurma}/acompanhamento-avaliacao/estagiario/{idEstagiario}";
	}

	@RequestMapping(value = "{idTurma}/submissao/{idSubmissao}/estagiario/{idEstagiario}/avaliar-submissao-estagiario", method = RequestMethod.GET)
	public String avaliarSubmissaoEstagiario(Model model, @Valid @ModelAttribute("submissoes") Submissao submissao,
			HttpSession session, RedirectAttributes redirect, @PathVariable("idEstagiario") Long idEstagiario,
			@PathVariable("idTurma") Long idTurma, @PathVariable("idSubmissao") Long idSubmissao) {
		
		model.addAttribute("turma", turmaService.getTurmaByIdAndEstagiarioId(idTurma, idEstagiario));
		model.addAttribute("submissao", turmaService.getSubmissaoById(idSubmissao));
		model.addAttribute("estagiario", estagiarioService.find(Estagiario.class, idEstagiario));
		model.addAttribute("Submissao", new Submissao());
		
		return "supervisor/avaliar-submissao";
	}
	@RequestMapping( value = "{idTurma}/submissao/{idSubmissao}/estagiario/{idEstagiario}/salvar-submissao-estagiario", method = RequestMethod.POST)
	public String salvarSubmisssaoEstagiario(Model model, @Valid @ModelAttribute("submissao") Submissao submissao,
			HttpSession session, RedirectAttributes redirect, @PathVariable("idEstagiario") Long idEstagiario,
			@PathVariable("idTurma") Long idTurma, @PathVariable("idSubmissao") Long idSubmissao){
		
		Submissao submissaoDoBanco = turmaService.getSubmissaoById(submissao.getId());
		Estagiario estagiario = estagiarioService.find(Estagiario.class, idEstagiario);
		Turma turma = turmaService.getTurmaByIdAndEstagiarioId(idTurma, idEstagiario);

		submissaoDoBanco.setEstagiario(estagiario);
		submissaoDoBanco.setNota(submissao.getNota());
		submissaoDoBanco.setStatusEntrega(submissao.getStatusEntrega());
		submissaoDoBanco.setComentario(submissao.getComentario());

		turma.getSubmissoes().add(submissaoDoBanco);
		turmaService.update(turma);
		
		return "redirect:/supervisor/turma/{idTurma}/submissao/{idSubmissao}/estagiario/{idEstagiario}/avaliar-submissao-estagiario";
	}

	private Pessoa getUsuarioLogado(HttpSession session) {
		if (session.getAttribute(Constants.USUARIO_LOGADO) == null) {
			Pessoa pessoa = pessoaService
					.getPessoaByCpf(SecurityContextHolder.getContext().getAuthentication().getName());
			session.setAttribute(Constants.USUARIO_LOGADO, pessoa);
		}
		return (Pessoa) session.getAttribute(Constants.USUARIO_LOGADO);
	}

}
