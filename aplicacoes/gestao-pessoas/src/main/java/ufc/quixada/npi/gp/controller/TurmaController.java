package ufc.quixada.npi.gp.controller;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ufc.quixada.npi.gp.model.Estagiario;
import ufc.quixada.npi.gp.model.FiltroJson;
import ufc.quixada.npi.gp.model.Horario;
import ufc.quixada.npi.gp.model.Periodo;
import ufc.quixada.npi.gp.model.Pessoa;
import ufc.quixada.npi.gp.model.Turma;
import ufc.quixada.npi.gp.model.enums.Dia;
import ufc.quixada.npi.gp.service.EstagiarioService;
import ufc.quixada.npi.gp.service.PeriodoService;
import ufc.quixada.npi.gp.service.TurmaService;
import ufc.quixada.npi.gp.utils.Constants;

@Controller
@RequestMapping("turma")
public class TurmaController {

	@Inject
	private EstagiarioService serviceEstagiario;

	@Inject
	private PeriodoService servicePeriodo;

	@Inject
	private TurmaService serviceTurma;

	@RequestMapping(value = "/minhas-turmas", method = RequestMethod.GET)
	public String listarTurmas(ModelMap model, HttpSession session) {
		model.addAttribute("turmas", serviceTurma.getMinhasTurma(((Pessoa) session.getAttribute(Constants.USUARIO_LOGADO)).getId()));
		return "coordenador/list-minhas-turmas";
	}

	@RequestMapping(value = "/turmas.json", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<Turma> getTurmas(@RequestBody FiltroJson frequenciaJson, Model model) {
		List<Turma> turmas = serviceTurma.getTurmaPeriodo(frequenciaJson.getAno(), frequenciaJson.getSemestre());
		return turmas;
	}

	@RequestMapping(value = "/{idPeriodo}/turma", method = RequestMethod.GET)
	public String novaTurmaPeriodo(ModelMap model, @PathVariable("idPeriodo") Long idPeriodo) {

		model.addAttribute("periodo", servicePeriodo.find(Periodo.class, idPeriodo));

		Turma turma = new Turma();
		List<Horario> horarios = new ArrayList<Horario>();
		for (int i = 0; i < 5; i++) {
			horarios.add(new Horario());
		}
		turma.setHorarios(horarios);
		
		model.addAttribute("turma", turma);
		model.addAttribute("dias", Dia.values());
		
		return "coordenador/form-turma";
	}

	@RequestMapping(value = "/{idPeriodo}/turma", method = RequestMethod.POST)
	public String adicionarTurmaPeriodo(ModelMap model, @Valid @ModelAttribute("turma") Turma turma,  @PathVariable("idPeriodo") Long idPeriodo, BindingResult result, HttpSession session) {

		Periodo periodo = servicePeriodo.find(Periodo.class, idPeriodo);
		
		if (result.hasErrors()) {
			model.addAttribute("periodo", periodo);
			return "coordenador/form-turma";
		}
		
		turma.setSupervisor((Pessoa) session.getAttribute(Constants.USUARIO_LOGADO));
		turma.setPeriodo(periodo);
		turma = atualizarTurma(turma);
		serviceTurma.save(turma);
		
		return "redirect:/coordenador/periodos";
	}

	@RequestMapping(value = "/{idTurma}/editar", method = RequestMethod.GET)
	public String editarTurma(@PathVariable("idTurma") Long idTurma, ModelMap model) {
		model.addAttribute("turma", serviceTurma.find(Turma.class, idTurma));
		
		return "coordenador/form-turma";
	}

	@RequestMapping(value = "/{idTurma}/exluir", method = RequestMethod.GET)
	public String excluirTurma(@PathVariable("idTurma") Long idTurma, ModelMap model) {
		Turma turma = serviceTurma.find(Turma.class, idTurma);
		
		if(turma != null) {
			serviceTurma.delete(turma);
		}
		
		return "coordenador/list-turmas";
	}

	@RequestMapping(value = "/{idTurma}/detalhes", method = RequestMethod.GET)
	public String detalhesTurma(@PathVariable("idTurma") Long idTurma, ModelMap model) {
		model.addAttribute("turma", serviceTurma.find(Turma.class, idTurma));

		return "coordenador/info-turma";
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

}

