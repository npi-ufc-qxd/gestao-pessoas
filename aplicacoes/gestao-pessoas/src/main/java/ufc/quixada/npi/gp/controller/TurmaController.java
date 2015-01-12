package ufc.quixada.npi.gp.controller;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ufc.quixada.npi.gp.model.Estagiario;
import ufc.quixada.npi.gp.model.Periodo;
import ufc.quixada.npi.gp.model.Pessoa;
import ufc.quixada.npi.gp.model.Turma;
import ufc.quixada.npi.gp.service.EstagiarioService;
import ufc.quixada.npi.gp.service.GenericService;
import ufc.quixada.npi.gp.service.PeriodoService;
import ufc.quixada.npi.gp.utils.Constants;

@Controller
@RequestMapping("turma")
public class TurmaController {

	@Inject
	private EstagiarioService serviceEstagiario;

	@Inject
	private PeriodoService servicePeriodo;

	@Inject
	private GenericService<Turma> serviceTurma;

	@RequestMapping(value = "/turmas", method = RequestMethod.GET)
	public String listarTurmas(ModelMap model) {

		model.addAttribute("turmas", serviceTurma.find(Turma.class));
		
		return "turma/turmas";
	}

	@RequestMapping(value = "/{idPeriodo}/turma", method = RequestMethod.GET)
	public String adicionarTurmaPeriodo(ModelMap model, @PathVariable("idPeriodo") Long idPeriodo) {

		model.addAttribute("periodo", servicePeriodo.find(Periodo.class, idPeriodo));
		model.addAttribute("turma", new Turma());
		
		return "turma/formTurma";
	}

	@RequestMapping(value = "/{idPeriodo}/turma", method = RequestMethod.POST)
	public String adicionarTurmaPeriodo(ModelMap model, @Valid @ModelAttribute("turma") Turma turma,  @PathVariable("idPeriodo") Long idPeriodo, BindingResult result, HttpSession session) {

		if (result.hasErrors()) {
			return "turma/formTurma";
		}
		
		turma.setSupervisor(getUsuarioLogado(session));
		turma.setPeriodo(servicePeriodo.find(Periodo.class, idPeriodo));
		turma = atualizarTurma(turma);
		serviceTurma.save(turma);
		
		turma.setCodigo(geraCodigoTurma(turma.getId()));
		serviceTurma.update(turma);

		return "redirect:/periodo/periodos";
	}

	@RequestMapping(value = "/{idTurma}/editar", method = RequestMethod.GET)
	public String editarTurma(@PathVariable("idTurma") Long idTurma, ModelMap model) {

		model.addAttribute("turma", serviceTurma.find(Turma.class, idTurma));
		
		return "turma/formTurma";
	}

	@RequestMapping(value = "/{idTurma}/exluir", method = RequestMethod.GET)
	public String excluirTurma(@PathVariable("idTurma") Long idTurma, ModelMap model) {

		Turma turma = serviceTurma.find(Turma.class, idTurma);
		
		if(turma != null){
			serviceTurma.delete(turma);
		}
		
		return "turma/turmas";
	}

	@RequestMapping(value = "/{idTurma}/detalhes", method = RequestMethod.GET)
	public String detalhesTurma(@PathVariable("idTurma") Long idTurma, ModelMap model) {

		model.addAttribute("turma", serviceTurma.find(Turma.class, idTurma));
		
		return "turma/detalheTurma";
	}
	
	@RequestMapping(value = "/{idTurma}/vincularEstagiarios", method = RequestMethod.GET)
	public String vincularEstagiariosTurma(ModelMap model, @PathVariable("idTurma") Long idTurma) {
		
		model.addAttribute("turma", serviceTurma.find(Turma.class, idTurma));
		model.addAttribute("membros", serviceEstagiario.find(Estagiario.class));
		
		return "turma/vincularEstagiarios";
	}
	
	@RequestMapping(value = "/vincularEstagiarios", method = RequestMethod.POST)
	public String vincularEstagiariosTurma(ModelMap model, @ModelAttribute("turma") Turma turma) {
		
		turma = atualizarTurma(turma);
		serviceTurma.update(turma);
		
		return "redirect:/turma/turmas";
	}

	
	// METODOS
	private Pessoa getUsuarioLogado(HttpSession session) {
		return (Pessoa) session.getAttribute(Constants.USUARIO_LOGADO);
	}

		
	private Turma atualizarTurma(Turma turma) {
		List<Estagiario> estagiarios = new ArrayList<Estagiario>();

		if (turma.getEstagiarios() != null) {
			for (Estagiario estagiario : turma.getEstagiarios()) {
				if(estagiario.getId() != null){
					estagiario = serviceEstagiario.find(Estagiario.class, estagiario.getId());
					estagiarios.add(estagiario);
				}
			}
		}
		
		turma.setEstagiarios(estagiarios);
		return turma;
	}
	
	private String geraCodigoTurma(Long id) {
		if (id < 10) {
			return "TURMA_" + id;
		} else {
			return "TURMA_" + id;
		}
	}

}

