package ufc.quixada.npi.gp.controller;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ufc.quixada.npi.gp.model.Documento;
import ufc.quixada.npi.gp.model.Submissao;
import ufc.quixada.npi.gp.service.TurmaService;


@Controller
@RequestMapping("documento")
public class DocumentoController {
	
	@Inject
	private TurmaService turmaService;
	
	@RequestMapping(value = "/{idSubmissao}", method = RequestMethod.GET)
	public void getArquivo(@PathVariable("idSubmissao") Long idSubmissao, HttpServletResponse response, HttpSession session) {
		try {
			Submissao submissao = turmaService.getSubmissaoById(idSubmissao);
			if(submissao != null) {
				InputStream is = new ByteArrayInputStream(submissao.getDocumento().getArquivo());
				response.setContentType(submissao.toString());
				response.setHeader("Content-Disposition", "attachment; filename=" + submissao.getDocumento().getNome());
				IOUtils.copy(is, response.getOutputStream());
				response.flushBuffer();
			}
		} catch (IOException ex) {
		}
	}
	
	@RequestMapping(value = "/remover/{id}", method = RequestMethod.POST)
	@ResponseBody public  ModelMap excluirDocumento(@PathVariable("id") Long id, HttpSession session) {
		ModelMap model = new ModelMap();
		Submissao documento = turmaService.getSubmissaoById(id);

		if(documento == null) {
			model.addAttribute("mensagem", "Documento inexistente.");
			return model;
		}

		turmaService.remover(documento);
		model.addAttribute("result", "ok");
		return model;
	}
}
