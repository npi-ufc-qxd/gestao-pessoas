package ufc.quixada.npi.gp.controller;

import static ufc.quixada.npi.gp.utils.Constants.PAGINA_INICIAL_COORDENADOR;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Component
@Controller
@RequestMapping("supervisor")
public class SupervisorController {
	
	

	@RequestMapping(value = "/inicio", method = RequestMethod.GET)	
	public String paginaInicial(ModelMap modelMap, HttpSession session)  {
		
		
		return PAGINA_INICIAL_COORDENADOR;
	}

}


