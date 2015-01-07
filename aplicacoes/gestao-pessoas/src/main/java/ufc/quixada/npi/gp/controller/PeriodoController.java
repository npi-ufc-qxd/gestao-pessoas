package ufc.quixada.npi.gp.controller;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ufc.quixada.npi.gp.model.Folga;
import ufc.quixada.npi.gp.model.Periodo;
import ufc.quixada.npi.gp.model.Projeto;
import ufc.quixada.npi.gp.service.GenericService;
import ufc.quixada.npi.gp.service.PeriodoService;

@Controller
@RequestMapping("periodo")
public class PeriodoController {

	@Inject
	private GenericService<Projeto> serviceProjeto;

	@Inject
	private PeriodoService servicePeriodo;

	@Inject
	private GenericService<Folga> serviceFolga;

	@RequestMapping(value = "/periodos", method = RequestMethod.GET)
	public String listarPeriodos(ModelMap model) {
		model.addAttribute("periodos", servicePeriodo.find(Periodo.class));
		return "periodo/periodos";
	}

	@RequestMapping(value = "/periodo", method = RequestMethod.GET)
	public String adicionarPeriodo(ModelMap model) {

		model.addAttribute("periodo", new Periodo());

		return "periodo/formPeriodo";
	}

	@RequestMapping(value = "/{idPeriodo}/editar", method = RequestMethod.GET)
	public String editarPeriodo(@PathVariable("idPeriodo") Long idPeriodo,
			ModelMap model) {

		model.addAttribute("projeto", serviceProjeto.find(Projeto.class, idPeriodo));

		return "periodo/formPeriodo";
	}

	@RequestMapping(value = "/periodo", method = RequestMethod.POST)
	public String adicionarPeriodo(ModelMap model, @Valid @ModelAttribute("periodo") Periodo periodo, BindingResult result) {

		if (result.hasErrors()) {
			return "periodo/formTurma";
		}

		if (periodo.getId() == null)
			servicePeriodo.save(periodo);
		else
			servicePeriodo.update(periodo);

		return "redirect:/periodo/periodos";
	}

	@RequestMapping(value = "/{idPeriodo}/detalhes", method = RequestMethod.GET)
	public String detalhesPeriodo(@PathVariable("idPeriodo") Long idPeriodo,
			ModelMap model) {

		model.addAttribute("periodo",
				servicePeriodo.find(Periodo.class, idPeriodo));

		return "periodo/detalhePeriodo";
	}

	@RequestMapping(value = "/{idPeriodo}/folga", method = RequestMethod.GET)
	public String adicionarFolgaPeriodo(@PathVariable("idPeriodo") Long idPeriodo, ModelMap model) {

		model.addAttribute("periodo",
				servicePeriodo.find(Periodo.class, idPeriodo));
		model.addAttribute("folga", new Folga());

		return "periodo/formFolga";
	}

	@RequestMapping(value = "/{idPeriodo}/folga", method = RequestMethod.POST)
	public String adicionarFolgaPeriodo(@PathVariable("idPeriodo") Long idPeriodo, @ModelAttribute("folga") Folga folga, ModelMap model) {

		folga.setPeriodo(servicePeriodo.find(Periodo.class, idPeriodo));

		serviceFolga.save(folga);
		return "redirect:/periodo/periodos";
	}

}
