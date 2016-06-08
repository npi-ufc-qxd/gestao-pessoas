package br.ufc.quixada.npi.gp.controller;

import static br.ufc.quixada.npi.gp.utils.Constants.ACOMPANHAMENTO_ESTAGIO;
import static br.ufc.quixada.npi.gp.utils.Constants.FORMULARIO_EDITAR_ESTAGIARIO;
import static br.ufc.quixada.npi.gp.utils.Constants.NOME_USUARIO;
import static br.ufc.quixada.npi.gp.utils.Constants.PAGINA_INICIAL_ESTAGIARIO;
import static br.ufc.quixada.npi.gp.utils.Constants.REDIRECT_ACOMPANHAMENTO_ESTAGIO;
import static br.ufc.quixada.npi.gp.utils.Constants.REDIRECT_PAGINA_INICIAL_ESTAGIARIO;

import java.io.IOException;
import java.util.Date;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.ufc.quixada.npi.gp.model.Documento;
import br.ufc.quixada.npi.gp.model.Estagiario;
import br.ufc.quixada.npi.gp.model.Estagio;
import br.ufc.quixada.npi.gp.model.Frequencia;
import br.ufc.quixada.npi.gp.model.Submissao;
import br.ufc.quixada.npi.gp.model.Submissao.StatusEntrega;
import br.ufc.quixada.npi.gp.model.Submissao.TipoSubmissao;
import br.ufc.quixada.npi.gp.service.EstagioService;
import br.ufc.quixada.npi.ldap.service.UsuarioService;

@Controller
@RequestMapping("Estagiario")
public class EstagiarioController {
	

//	@Inject
//	private PessoaService pessoaService;
//
	@Inject
	private EstagioService estagioService;
	
	@Autowired
	private UsuarioService usuarioService;
	

	@RequestMapping(value = {"", "/", "/MinhasTurmas"}, method = RequestMethod.GET)
	public String listarTurmas(Model model, HttpSession session) {
		inserirNomeUsuarioNaSessao(session);

		//Estagiario estagiario = pessoaService.getEstagiarioByPessoaCpf(getCpf());
		//model.addAttribute("estagios", estagiario);
		
		return PAGINA_INICIAL_ESTAGIARIO;
	}

	
	@RequestMapping(value = "/MeusDados", method = RequestMethod.GET)
	public String visualizarMeusDados(Model model) {
//		Estagiario estagiario = pessoaService.getEstagiarioByCpf(getCpf());
//		model.addAttribute("estagiario", estagiario);

		return FORMULARIO_EDITAR_ESTAGIARIO;
	}

	@RequestMapping(value = "/MeusDados", method = RequestMethod.POST)
	public String editarMeusDados(@Valid @ModelAttribute("estagiario") Estagiario estagiario, BindingResult result, RedirectAttributes redirect) {

		if (result.hasErrors()) {
			return FORMULARIO_EDITAR_ESTAGIARIO;
		}

//		estagiario.setPessoa(pessoaService.getPessoaByCpf(getCpf()));
//		pessoaService.editarEstagiario(estagiario);
		redirect.addFlashAttribute("msg", "Parabéns, seu dados foram atualizados com sucesso!");

		return REDIRECT_PAGINA_INICIAL_ESTAGIARIO;
	}
	

	@RequestMapping(value = "/Acompanhamento/{idEstagio}", method = RequestMethod.GET)
	public String visualizarAcompanhamento(Model model, @PathVariable("idEstagio") Long idEstagio, RedirectAttributes redirectAttributes) {

		Estagio estagio =  estagioService.buscarEstagioPorIdEEstagiarioCpf(idEstagio, getCpfUsuarioLogado());
		
		if(estagio == null) {
			redirectAttributes.addAttribute("error", "Estagio inexistente");
			return REDIRECT_PAGINA_INICIAL_ESTAGIARIO;
		}
		
		Submissao submissaoPlano = estagioService.buscarSubmissaoPorTipoSubmissaoEEstagioIdECpf(Submissao.TipoSubmissao.PLANO_ESTAGIO, idEstagio, getCpfUsuarioLogado());
		Submissao submissaoRelatorio = estagioService.buscarSubmissaoPorTipoSubmissaoEEstagioIdECpf(Submissao.TipoSubmissao.RELATORIO_FINAL_ESTAGIO, idEstagio, getCpfUsuarioLogado());

		model.addAttribute("estagio", estagio);
		model.addAttribute("submissaoPlano", submissaoPlano);
		model.addAttribute("submissaoRelatorio", submissaoRelatorio);

		return ACOMPANHAMENTO_ESTAGIO;
	}
	
	@RequestMapping(value="/Acompanhamento/{idEstagio}/DownloadPlano", method=RequestMethod.GET)
	@ResponseBody
	public HttpEntity<byte[]> downloadPlano(@PathVariable("idEstagio") Long idEstagio, RedirectAttributes redirectAttributes) {
		
		Submissao submissaoPlano = estagioService.buscarSubmissaoPorTipoSubmissaoEEstagioIdECpf(Submissao.TipoSubmissao.PLANO_ESTAGIO, idEstagio, getCpfUsuarioLogado());
	    
//		if(submissaoPlano == null){
//			redirectAttributes.addFlashAttribute("error", "Acesso negado.");
//			return REDIRECT_PAGINA_INICIAL_ESTAGIARIO;
//	    }
	    
		byte[] plano = submissaoPlano.getDocumento().getArquivo();
		String[] tipo = submissaoPlano.getDocumento().getExtensao().split("/");
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType(tipo[0], tipo[1]));
		headers.set("Content-Disposition", "attachment; filename=" + submissaoPlano.getDocumento().getNome());
		headers.setContentLength(plano.length);
		
		
	    return new HttpEntity<byte[]>(plano, headers);
	}
	
	@RequestMapping(value="/Acompanhamento/{idEstagio}/DownloadRelatorio", method=RequestMethod.GET)
	@ResponseBody
	public HttpEntity<byte[]> downloadRelatorio(@PathVariable("idEstagio") Long idEstagio, RedirectAttributes redirectAttributes) {
		
		Submissao submissaoRelatorio = estagioService.buscarSubmissaoPorTipoSubmissaoEEstagioIdECpf(Submissao.TipoSubmissao.RELATORIO_FINAL_ESTAGIO, idEstagio, getCpfUsuarioLogado());
	    
//		if(submissaoPlano == null){
//			redirectAttributes.addFlashAttribute("error", "Acesso negado.");
//			return REDIRECT_PAGINA_INICIAL_ESTAGIARIO;
//	    }
	    
		byte[] relatorio = submissaoRelatorio.getDocumento().getArquivo();
		String[] tipo = submissaoRelatorio.getDocumento().getExtensao().split("/");
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType(tipo[0], tipo[1]));
		headers.set("Content-Disposition", "attachment; filename=" + submissaoRelatorio.getDocumento().getNome());
		headers.setContentLength(relatorio.length);
		
		
	    return new HttpEntity<byte[]>(relatorio, headers);
	}

	
	@RequestMapping(value = "/Acompanhamento/{idEstagio}/Presenca", method = RequestMethod.POST)
	public @ResponseBody Frequencia.StatusFrequencia realizarPresenca(HttpSession session, @PathVariable("idEstagio") Long idEstagio) {

//		Estagio estagio = estagioService.getEstagioByIdAndEstagiarioCpf(idEstagio, getCpf());
//		
//		if(estagio != null) {
//			return estagioService.realizarPresenca(Estagio estagio);
//		}
		
		return null;
	}
		
	@RequestMapping(value = "/Acompanhamento/{idEstagio}/SubmeterPlano", method = RequestMethod.POST)
	public String submeterPlano(@Valid @RequestParam("planoEstagio") MultipartFile planoEstagio, @PathVariable("idEstagio") Long idEstagio, RedirectAttributes redirectAttributes ) throws Exception{
		
		try {
			
			if(!validarArquivo(planoEstagio)){
				redirectAttributes.addFlashAttribute("error", "Escolha um arquivo pdf.");
				return ACOMPANHAMENTO_ESTAGIO;
			}		
			
			Estagio estagio = estagioService.buscarEstagioPorIdEEstagiarioCpf(idEstagio, getCpfUsuarioLogado());
			
			if(estagio == null){
				redirectAttributes.addFlashAttribute("error", "Acesso negado.");
				return REDIRECT_PAGINA_INICIAL_ESTAGIARIO;
			}
			
			Submissao submissao = estagioService.buscarSubmissaoPorTipoSubmissaoEEstagioIdECpf(Submissao.TipoSubmissao.PLANO_ESTAGIO, idEstagio, getCpfUsuarioLogado());
			
			if (submissao == null) {
				redirectAttributes.addFlashAttribute("error", "Acesso negado.");
				return REDIRECT_PAGINA_INICIAL_ESTAGIARIO;
			}
			
			submissao = new Submissao();
			Documento documento = new Documento();
			documento.setNome(TipoSubmissao.PLANO_ESTAGIO + "_" + estagio.getEstagiario().getNomeCompleto().toUpperCase());
			documento.setExtensao(planoEstagio.getContentType());
			documento.setArquivo(planoEstagio.getBytes());
			submissao.setTipoSubmissao(TipoSubmissao.PLANO_ESTAGIO);
			submissao.setDocumento(documento);
			submissao.setSubmetidoEm(new Date());
			submissao.setStatusEntrega(StatusEntrega.SUBMETIDO);
			estagioService.submeter(submissao);
			
		} catch (IOException e) {
			return "redirect:/500";
		}

		return REDIRECT_ACOMPANHAMENTO_ESTAGIO + idEstagio;
	}
	
	@RequestMapping(value = "/Acompanhamento/{idEstagio}/EditarPlano", method = RequestMethod.POST)
	public String editarPlano(Long idEstagio, MultipartFile planoEstagio, RedirectAttributes redirectAttributes) throws Exception{
		
		if(!validarArquivo(planoEstagio)){
			redirectAttributes.addFlashAttribute("error", "Escolha um arquivo pdf.");
			return ACOMPANHAMENTO_ESTAGIO;
		}
		
		Submissao submissao = estagioService.buscarSubmissaoPorTipoSubmissaoEEstagioIdECpf(Submissao.TipoSubmissao.PLANO_ESTAGIO, idEstagio, getCpfUsuarioLogado());
		
		if(submissao == null){
			redirectAttributes.addFlashAttribute("error", "Acesso negado.");
			return REDIRECT_PAGINA_INICIAL_ESTAGIARIO;
		}
		
		submissao.getDocumento().setArquivo(planoEstagio.getBytes());
		estagioService.editarSubmissao(submissao);
		redirectAttributes.addFlashAttribute("msg", "Plano editado com sucesso.");
		return REDIRECT_ACOMPANHAMENTO_ESTAGIO + idEstagio;
	}
	
	@RequestMapping(value = "/Acompanhamento/Estagio/{idEstagio}/SubmeterRelatorio", method = RequestMethod.POST)
	public String postSubmeterRelatorio(@Valid @RequestParam("relatorio") MultipartFile relatorio, @PathVariable("idEstagio") Long idEstagio, RedirectAttributes redirectAttributes ) throws Exception{

		try {
			
			if(!validarArquivo(relatorio)){
				redirectAttributes.addFlashAttribute("error", "Escolha um arquivo pdf.");
				return ACOMPANHAMENTO_ESTAGIO;
			}
			
			Estagio estagio = estagioService.buscarEstagioPorIdEEstagiarioCpf(idEstagio, getCpfUsuarioLogado());
			
			if (estagio == null) {
				redirectAttributes.addFlashAttribute("error", "Acesso negado.");
				return REDIRECT_PAGINA_INICIAL_ESTAGIARIO;
			}
			
			Submissao submissao = estagioService.buscarSubmissaoPorTipoSubmissaoEEstagioIdECpf(Submissao.TipoSubmissao.RELATORIO_FINAL_ESTAGIO, idEstagio, getCpfUsuarioLogado());
			
			if (submissao == null) {
				redirectAttributes.addFlashAttribute("error", "Acesso negado.");
				return REDIRECT_PAGINA_INICIAL_ESTAGIARIO;
			}
			
			submissao = new Submissao();
			Documento documento = new Documento();
			documento.setNome(TipoSubmissao.RELATORIO_FINAL_ESTAGIO + "_" + estagio.getEstagiario().getNomeCompleto().toUpperCase());
			documento.setExtensao(relatorio.getContentType());
			documento.setArquivo(relatorio.getBytes());
			submissao.setTipoSubmissao(TipoSubmissao.RELATORIO_FINAL_ESTAGIO);
			submissao.setDocumento(documento);
			submissao.setSubmetidoEm(new Date());
			submissao.setStatusEntrega(StatusEntrega.SUBMETIDO);
			estagioService.submeter(submissao);
			
			
		} catch (IOException e) {
			return "redirect:/500";
		}

		return REDIRECT_ACOMPANHAMENTO_ESTAGIO + idEstagio;
	}
	
	@RequestMapping(value = "/Acompanhamento/{idEstagio}/EditarRelatorio", method = RequestMethod.POST)
	public String editarRelatorio(Long idEstagio, MultipartFile relatorio, RedirectAttributes redirectAttributes) throws Exception{
		
		if(!validarArquivo(relatorio)){
			redirectAttributes.addFlashAttribute("error", "Escolha um arquivo pdf.");
			return ACOMPANHAMENTO_ESTAGIO;
		}
		
		Submissao submissao = estagioService.buscarSubmissaoPorTipoSubmissaoEEstagioIdECpf(Submissao.TipoSubmissao.RELATORIO_FINAL_ESTAGIO, idEstagio, getCpfUsuarioLogado());
		
		if(submissao == null){
			redirectAttributes.addFlashAttribute("error", "Acesso negado.");
			return REDIRECT_PAGINA_INICIAL_ESTAGIARIO;
		}
		
		submissao.getDocumento().setArquivo(relatorio.getBytes());
		estagioService.editarSubmissao(submissao);
		redirectAttributes.addFlashAttribute("msg", "Relatorio editado com sucesso.");
		return REDIRECT_ACOMPANHAMENTO_ESTAGIO + idEstagio;
		
	}
	
	private void inserirNomeUsuarioNaSessao(HttpSession session) {
		if (session.getAttribute(NOME_USUARIO) == null) {
			session.setAttribute(NOME_USUARIO, usuarioService.getByCpf(getCpfUsuarioLogado()).getNome());
		}
	}
	
	private String getCpfUsuarioLogado() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}
	
	//Retorna true se o arquivo for válido.
	private boolean validarArquivo(MultipartFile anexo){
		
		if(anexo == null || !anexo.getContentType().equals("application/pdf")){
			return false;
		}	
		return true;
	}
}