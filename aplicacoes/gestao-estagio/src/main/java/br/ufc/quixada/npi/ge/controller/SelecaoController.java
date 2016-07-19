package br.ufc.quixada.npi.ge.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.ufc.quixada.npi.ge.model.Curso;
import br.ufc.quixada.npi.ge.model.Selecao;
import br.ufc.quixada.npi.ge.model.Turma;
import br.ufc.quixada.npi.ge.service.CursoService;
import br.ufc.quixada.npi.ge.service.SelecaoService;
import br.ufc.quixada.npi.ge.service.TurmaService;

import static br.ufc.quixada.npi.ge.utils.Constants.FORMULARIO_ADICIONAR_SELECAO;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;;

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
	
	@RequestMapping(value = "/Adicionar", method = RequestMethod.GET)
	public String formularioAdicionarSelecao(Model model){
		Turma turma = turmaService.buscarTurmaPorId(1l);
		Selecao selecao = new Selecao();
		
		turma.setSelecao(selecao);
		selecao.setTurma(turma);
		
		model.addAttribute("selecao", selecao);
		return FORMULARIO_ADICIONAR_SELECAO;
	}
	
	@RequestMapping(value = "/Adicionar", method = RequestMethod.POST)
	public String adicionarSelecao(@Valid Selecao selecao){
		selecaoService.adicionarSelecao(selecao);
		return FORMULARIO_ADICIONAR_SELECAO;
	}
}
