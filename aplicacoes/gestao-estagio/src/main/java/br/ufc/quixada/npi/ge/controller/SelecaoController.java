package br.ufc.quixada.npi.ge.controller;

import static br.ufc.quixada.npi.ge.utils.Constants.FORMULARIO_ADICIONAR_SELECAO;
import static br.ufc.quixada.npi.ge.utils.Constants.REDIRECT_PAGINA_INICIAL_SUPERVISOR;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.ufc.quixada.npi.ge.model.Curso;
import br.ufc.quixada.npi.ge.model.Selecao;
import br.ufc.quixada.npi.ge.model.Turma;
import br.ufc.quixada.npi.ge.service.CursoService;
import br.ufc.quixada.npi.ge.service.PessoaService;
import br.ufc.quixada.npi.ge.service.SelecaoService;
import br.ufc.quixada.npi.ge.service.TurmaService;;

@Controller
@RequestMapping("Selecao")
public class SelecaoController {
	
	@Inject
	private TurmaService turmaService;
	
	@Inject 
	private CursoService cursoService;

	@Inject
	private SelecaoService selecaoService;
	
	@Inject
	private PessoaService pessoaService;
	
	@ModelAttribute("status")
	public List<Selecao.Status> status(){
		return Arrays.asList(Selecao.Status.values());
	}
	
	@ModelAttribute("cursos")
	public List<Curso> cursos(){
		return cursoService.buscarCursos();
	}
	
	@RequestMapping(value = "/{idTurma}/Adicionar", method = RequestMethod.GET)
	public String formularioAdicionarSelecao(Model model, @PathVariable("idTurma") Long idTurma, RedirectAttributes redirect){
		Turma turma = turmaService.buscarTurmaPorServidorId(idTurma, pessoaService.buscarServidorPorCpf(getCpfUsuarioLogado()).getId());
		if(turma == null){
			redirect.addFlashAttribute("error", "Você não tem acesso");
			return REDIRECT_PAGINA_INICIAL_SUPERVISOR;
		}
		if(turma.getSelecao() != null){
			redirect.addFlashAttribute("error", "Esta turma já possui seleção");
			return REDIRECT_PAGINA_INICIAL_SUPERVISOR;
		}
		
		Selecao selecao = new Selecao();
		
		model.addAttribute("turma", turma);
		model.addAttribute("selecao", selecao);
		return FORMULARIO_ADICIONAR_SELECAO;
	}
	
	@RequestMapping(value = "{idTurma}/Adicionar", method = RequestMethod.POST)
	public String adicionarSelecao(Model model, @PathVariable("idTurma") Long idTurma, @Valid @ModelAttribute("selecao") Selecao selecao, BindingResult result, RedirectAttributes redirect){
		Turma turma = turmaService.buscarTurmaPorServidorId(idTurma, pessoaService.buscarServidorPorCpf(getCpfUsuarioLogado()).getId());
		if(turma == null){
			redirect.addFlashAttribute("error", "Você não tem acesso");
			return REDIRECT_PAGINA_INICIAL_SUPERVISOR;
		}
		if(turma.getSelecao() != null){
			redirect.addFlashAttribute("error", "Esta turma já possui seleção");
			return REDIRECT_PAGINA_INICIAL_SUPERVISOR;
		}
		turma.setSelecao(selecao);
		selecao.setTurma(turma);
		if (result.hasErrors()) {
			model.addAttribute("turma", turma);
			model.addAttribute("selecao", selecao);
			return FORMULARIO_ADICIONAR_SELECAO;
		}
//		selecaoService.adicionarSelecao(selecao);
		return "redirect:/Selecao/"+1l+"/Adicionar";
	}
	
	private String getCpfUsuarioLogado() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}
}
