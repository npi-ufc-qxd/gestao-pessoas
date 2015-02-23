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
import ufc.quixada.npi.gp.model.Pessoa;
import ufc.quixada.npi.gp.service.DocumentoService;
import ufc.quixada.npi.gp.service.PessoaService;


@Controller
@RequestMapping("documento")
public class DocumentoController {
	
	@Inject
	private DocumentoService documentoService;
	
	@Inject
	private PessoaService pessoaService;
	
	@RequestMapping(value = "/{idPessoa}/{idArquivo}", method = RequestMethod.GET)
	public void getArquivo(@PathVariable("idPessoa") Long idPessoa, @PathVariable("idArquivo") Long idArquivo, HttpServletResponse response, HttpSession session) {
		try {
			Pessoa pessoa = pessoaService.find(Pessoa.class, idPessoa);
			Documento documento = documentoService.getDocumentoById(idArquivo);
			if(documento != null) {
				InputStream is = new ByteArrayInputStream(documento.getArquivo());
				response.setContentType(documento.toString());
				response.setHeader("Content-Disposition", "attachment; filename=" + documento.getNome());
				IOUtils.copy(is, response.getOutputStream());
				response.flushBuffer();
			}
		} catch (IOException ex) {
		}
	}
	
	@RequestMapping(value = "/remover/{id}", method = RequestMethod.POST)
	@ResponseBody public  ModelMap excluirDocumento(@PathVariable("id") Long id, HttpSession session) {
		ModelMap model = new ModelMap();
		Documento documento = documentoService.getDocumentoById(id);

		if(documento == null) {
			model.addAttribute("mensagem", "Documento inexistente.");
			return model;
		}

		documentoService.remover(documento);
		model.addAttribute("result", "ok");
		return model;
	}
}
