package ufc.quixada.npi.gp.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ufc.quixada.npi.gp.model.Folga;
import ufc.quixada.npi.gp.service.FolgaService;


@Component
@Controller
@RequestMapping("admin")
public class AdminController {

	@Inject
	private FolgaService folgaService;

	@RequestMapping(value = {"","/"}, method = RequestMethod.GET)
	public String paginaInicialAdmin(Model Model, HttpSession session)  {
		return "redirect:/supervisor/turmas";
	}

	@RequestMapping(value = "/folga", method = RequestMethod.GET)
	public String novaFolgaPeriodo(Model model) {
		model.addAttribute("action", "cadastrar");

		model.addAttribute("folga", new Folga());

		return "supervisor/form-folga";
	}

	@RequestMapping(value = "/folga", method = RequestMethod.POST)
	public String adicionarFolgaPeriodo(@Valid @ModelAttribute("folga") Folga folga, BindingResult result, Model model) {
		model.addAttribute("action", "cadastrar");
		
		if (result.hasErrors()) {
			return "supervisor/form-folga";
		}

		folgaService.save(folga);

		return "redirect:/supervisor/periodos";
	}
	
	@RequestMapping(value = "/folga/{idFolga}/editar", method = RequestMethod.GET)
	public String paginaEditarFolga(@PathVariable("idFolga") Long idFolga, Model model) {
		model.addAttribute("action", "editar");

		Folga folga = folgaService.find(Folga.class, idFolga);
		
		model.addAttribute("folga", folga);

		return "supervisor/form-folga";
	}

	@RequestMapping(value = "/folga/{idFolga}/editar", method = RequestMethod.POST)
	public String editarFolga(@Valid @ModelAttribute("folga") Folga folga, BindingResult result, Model model) {
		model.addAttribute("action", "editar");

		if (result.hasErrors()) {
			return "supervisor/form-folga";
		}

		folgaService.update(folga);

		return "redirect:/supervisor/periodos";
	}
		
}
