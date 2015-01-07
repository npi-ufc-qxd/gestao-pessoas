package ufc.quixada.npi.gp.controller;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ufc.quixada.npi.gp.utils.EstagiarioDataSource;

@Controller
@RequestMapping("jasper")
public class JasperController {

	private JRDataSource jrDatasource;

	public JasperController() throws JRException {
		EstagiarioDataSource dsStudent = new EstagiarioDataSource();
		jrDatasource = dsStudent.create(null);
	}

	@RequestMapping(value = "/jrreport", method = RequestMethod.GET)
	public String printWelcome(ModelMap model) {
		model.addAttribute("datasource", jrDatasource);
		model.addAttribute("format", "pdf");
		return "multiViewReport";
	}
}
