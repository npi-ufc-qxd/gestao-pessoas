package br.ufc.quixada.npi.ge.controller;

import static br.ufc.quixada.npi.ge.utils.Constants.DETALHES_SELECAO;
import static br.ufc.quixada.npi.ge.utils.Constants.FORMULARIO_ADICIONAR_SELECAO;
import static br.ufc.quixada.npi.ge.utils.Constants.FORMULARIO_EDITAR_SELECAO;
import static br.ufc.quixada.npi.ge.utils.Constants.REDIRECT_PAGINA_INICIAL_SUPERVISOR;
import static br.ufc.quixada.npi.ge.utils.Constants.REDIRECT_DETALHES_SELECAO;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;

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
		Turma turma = turmaService.buscarTurmaPorId(idTurma);
		if(turma == null){
			redirect.addFlashAttribute("error", "Turma inexistente.");
			return REDIRECT_PAGINA_INICIAL_SUPERVISOR;
		}
		if(turma.getSelecao() != null){
			redirect.addFlashAttribute("error", "Esta turma já possui seleção");
			return REDIRECT_PAGINA_INICIAL_SUPERVISOR;
		}
		if(turma.getTipoTurma() == Turma.TipoTurma.EMPRESA){
			redirect.addFlashAttribute("error", "Esta turma é do tipo Empresa e não pode ter seleção");
			return REDIRECT_PAGINA_INICIAL_SUPERVISOR;
		}
		
		Selecao selecao = new Selecao();
		
		model.addAttribute("turma", turma);
		model.addAttribute("selecao", selecao);
		return FORMULARIO_ADICIONAR_SELECAO;
	}
	
	@RequestMapping(value = "{idTurma}/Adicionar", method = RequestMethod.POST)
	public String adicionarSelecao(Model model, @PathVariable("idTurma") Long idTurma, @Valid @ModelAttribute("selecao") Selecao selecao, BindingResult result, RedirectAttributes redirect){
		Turma turma = turmaService.buscarTurmaPorId(idTurma);
		if(turma == null){
			redirect.addFlashAttribute("error", "Turma inexistente.");
			return REDIRECT_PAGINA_INICIAL_SUPERVISOR;
		}
		if(turma.getSelecao() != null){
			redirect.addFlashAttribute("error", "Esta turma já possui seleção");
			return REDIRECT_PAGINA_INICIAL_SUPERVISOR;
		}
		if(turma.getTipoTurma() == Turma.TipoTurma.EMPRESA){
			redirect.addFlashAttribute("error", "Esta turma é do tipo Empresa e não pode ter seleção");
			return REDIRECT_PAGINA_INICIAL_SUPERVISOR;
		}
		turma.setSelecao(selecao);
		selecao.setTurma(turma);
		if (result.hasErrors()) {
			model.addAttribute("turma", turma);
			model.addAttribute("selecao", selecao);
			return FORMULARIO_ADICIONAR_SELECAO;
		}
		selecaoService.adicionarSelecao(selecao);
		redirect.addFlashAttribute("sucesso", "Seleção cadastrada com sucesso.");
		return REDIRECT_DETALHES_SELECAO + selecao.getId();
	}
	
	@RequestMapping(value = "/{idSelecao}/Editar", method = RequestMethod.GET)
	public String formularioEditarSelecao(Model model, @PathVariable("idSelecao") Long idSelecao, RedirectAttributes redirect){
		Selecao selecao = selecaoService.buscarSelecaoPorId(idSelecao);
		
		if(selecao == null){
			redirect.addFlashAttribute("error", "Seleção inexistente.");
			return REDIRECT_PAGINA_INICIAL_SUPERVISOR;
		}
		
		model.addAttribute("selecao", selecao);
		return FORMULARIO_EDITAR_SELECAO;
	}
	
	@RequestMapping(value = "{idSelecao}/Editar", method = RequestMethod.POST)
	public String editarSelecao(Model model, @PathVariable("idSelecao") Long idSelecao, @Valid @ModelAttribute("selecao") Selecao selecao, BindingResult result, RedirectAttributes redirect){
		if (result.hasErrors()) {
			model.addAttribute("selecao", selecao);
			return FORMULARIO_ADICIONAR_SELECAO;
		}
		selecaoService.editarSelecao(selecao);
		redirect.addFlashAttribute("sucesso", "Alterações salvas com sucesso.");
		return REDIRECT_DETALHES_SELECAO + selecao.getId();
	}
	
	@RequestMapping(value = "{idSelecao}", method = RequestMethod.GET)
	public String detalhesSelecao(Model model, @PathVariable("idSelecao") Long idSelecao, RedirectAttributes redirect){
		Selecao selecao = selecaoService.buscarSelecaoPorId(idSelecao);
		if(selecao == null){
			redirect.addFlashAttribute("error", "Seleção inexistente.");
			return REDIRECT_PAGINA_INICIAL_SUPERVISOR;
		}
		model.addAttribute("turma", selecao.getTurma());
		model.addAttribute("selecao", selecao);
		return DETALHES_SELECAO;
	}
	
}
