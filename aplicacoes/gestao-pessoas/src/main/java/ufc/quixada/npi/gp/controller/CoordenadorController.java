package ufc.quixada.npi.gp.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ufc.quixada.npi.gp.model.Estagiario;
import ufc.quixada.npi.gp.service.EstagiarioService;

@Component
@Controller
@RequestMapping("coordenador")
public class CoordenadorController {

	@Inject
	private EstagiarioService serviceEstagiario;
	
	private JRDataSource jrDatasource;

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(ModelMap modelMap, HttpSession session) {
		return "coordenador/inicial";
	}

	@RequestMapping(value = "/inicial")
	public String inicial(ModelMap modelMap, HttpSession session)
			throws JRException {

		modelMap.addAttribute("usuario", SecurityContextHolder.getContext()
				.getAuthentication().getName());

		modelMap.addAttribute("estagiarios",
				serviceEstagiario.find(Estagiario.class));

		return "redirect:/coordenador/inicial";
	}

	@RequestMapping(value = "/listaEstagiarios")
	public String listaEstagiarios(ModelMap modelMap, HttpSession session) {
		modelMap.addAttribute("usuario", SecurityContextHolder.getContext()
				.getAuthentication().getName());

		modelMap.addAttribute("estagiarios",
				serviceEstagiario.find(Estagiario.class));

		return "coordenador/listaEstagiarios";
	}

	@RequestMapping(value = "/jrreport", method = RequestMethod.GET)
	public String printWelcome(ModelMap model) throws JRException {
		
		jrDatasource = new JRBeanCollectionDataSource(serviceEstagiario.find(Estagiario.class));
		
		model.addAttribute("datasource", jrDatasource);
		model.addAttribute("format", "pdf");
		return "multiViewReport";
	}
	
	@RequestMapping(value = "/declaracaoEstagio", method = RequestMethod.GET)
	public String declaracaoEstagio( ModelMap model) throws JRException {
		
		jrDatasource = new JRBeanCollectionDataSource(serviceEstagiario.find(Estagiario.class));
		
		model.addAttribute("datasource", jrDatasource);
		model.addAttribute("format", "pdf");
		return "declaracaoEstagioIndividual";
	}

}
