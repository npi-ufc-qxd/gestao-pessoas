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

import ufc.quixada.npi.gp.model.Submissao;
import ufc.quixada.npi.gp.model.Pessoa;
import ufc.quixada.npi.gp.service.SubmissaoService;
import ufc.quixada.npi.gp.service.PessoaService;


@Controller
@RequestMapping("submissao")
public class SubmissaoController {
	
	@Inject
	private SubmissaoService submissaoService;
	
	@Inject
	private PessoaService pessoaService;
	
	@RequestMapping(value = "/{idPessoa}/{idArquivo}", method = RequestMethod.GET)
	public void getArquivo(@PathVariable("idPessoa") Long idPessoa, @PathVariable("idArquivo") Long idArquivo, HttpServletResponse response, HttpSession session) {
		try {
			Pessoa pessoa = pessoaService.find(Pessoa.class, idPessoa);
			Submissao submissao = submissaoService.getSubmissaoById(idArquivo);
			if(submissao != null) {
				InputStream is = new ByteArrayInputStream(submissao.getArquivo());
				response.setContentType(submissao.toString());
			//	response.setHeader("Content-Disposition", "attachment; filename=" + submissao.getNome());
				IOUtils.copy(is, response.getOutputStream());
				response.flushBuffer();
			}
		} catch (IOException ex) {
		}
	}
	
	@RequestMapping(value = "/remover/{id}", method = RequestMethod.POST)
	@ResponseBody public  ModelMap excluirSubmissao(@PathVariable("id") Long id, HttpSession session) {
		ModelMap model = new ModelMap();
		Submissao submissao = submissaoService.getSubmissaoById(id);

		if(submissao == null) {
			model.addAttribute("mensagem", "Submissão inexistente.");
			return model;
		}

		submissaoService.remover(submissao);
		model.addAttribute("result", "ok");
		return model;
	}
}
