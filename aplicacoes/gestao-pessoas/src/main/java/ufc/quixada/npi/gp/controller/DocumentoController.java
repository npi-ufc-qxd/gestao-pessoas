package ufc.quixada.npi.gp.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.springframework.security.core.context.SecurityContextHolder;
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
import ufc.quixada.npi.gp.utils.Constants;

@Controller
@RequestMapping("documento")
public class DocumentoController {
	@Inject
	private DocumentoService serviceDocumento;
	@Inject
	private PessoaService servicePessoa;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public void getFile(@PathVariable("id") Long id,
			HttpServletResponse response, HttpSession session) {
		try {
			Documento documento = serviceDocumento.find(Documento.class, id);
			if (documento != null
					&& (getUsuarioLogado(session).equals(
							documento.getProjeto().getAutor()) || servicePessoa
							.isCoordenador(getUsuarioLogado(session)))) {
				InputStream is = new ByteArrayInputStream(
						documento.getArquivo());
				response.setContentType(documento.getTipo());
				response.setHeader("Content-Disposition",
						"attachment; filename="
								+ documento.getNomeOriginal().replace(" ", "_"));
				IOUtils.copy(is, response.getOutputStream());
				response.flushBuffer();
			}
		} catch (IOException ex) {
			throw new RuntimeException("IOError writing file to output stream");
		}
	}

	@RequestMapping(value = "/ajax/remover/{id}", method = RequestMethod.POST)
	@ResponseBody
	public ModelMap excluirDocumento(@PathVariable("id") Long id,
			HttpSession session) {
		ModelMap map = new ModelMap();
		Documento documento = serviceDocumento.find(Documento.class, id);
		if (documento == null) {
			map.addAttribute("result", "erro");
			map.addAttribute("mensagem", "Documento não existe");
			return map;
		}
		if (!getUsuarioLogado(session)
				.equals(documento.getProjeto().getAutor())) {
			map.addAttribute("result", "erro");
			map.addAttribute("mensagem", "Permissão negada");
			return map;
		}
		serviceDocumento.delete(documento);
		map.addAttribute("result", "ok");
		return map;
	}

	private Pessoa getUsuarioLogado(HttpSession session) {
		if (session.getAttribute(Constants.USUARIO_LOGADO) == null) {
			Pessoa usuario = servicePessoa
					.getUsuarioByLogin(SecurityContextHolder.getContext()
							.getAuthentication().getName());
			session.setAttribute(Constants.USUARIO_LOGADO, usuario);
		}
		return (Pessoa) session.getAttribute(Constants.USUARIO_LOGADO);
	}
}