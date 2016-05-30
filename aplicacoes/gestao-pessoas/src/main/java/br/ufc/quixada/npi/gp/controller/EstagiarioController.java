package br.ufc.quixada.npi.gp.controller;

import static br.ufc.quixada.npi.gp.utils.Constants.ACOMPANHAMENTO_ESTAGIO;
import static br.ufc.quixada.npi.gp.utils.Constants.FORMULARIO_EDITAR_ESTAGIARIO;
import static br.ufc.quixada.npi.gp.utils.Constants.NOME_USUARIO;
import static br.ufc.quixada.npi.gp.utils.Constants.PAGINA_INICIAL_ESTAGIARIO;
import static br.ufc.quixada.npi.gp.utils.Constants.REDIRECT_PAGINA_INICIAL_ESTAGIARIO;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.ufc.quixada.npi.gp.model.Estagiario;
import br.ufc.quixada.npi.gp.model.Frequencia;
import br.ufc.quixada.npi.ldap.service.UsuarioService;

@Controller
@RequestMapping("Estagiario")
public class EstagiarioController {
	

//	@Inject
//	private PessoaService pessoaService;
//
//	@Inject
//	private EstagioService estagioService;
	
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

//		Estagio estagio = estagioService.getEstagioByIdAndEstagiarioCpf(idEstagio, getCpf());
//		
//		if(estagio == null) {
//			redirectAttributes.addAttribute("error", "Estagio inexistente");
//			return "redirect:/Estagiario/MinhasTurmas";
//		}
//		
//		model.addAttribute("estagio", estagio);

		return ACOMPANHAMENTO_ESTAGIO;
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
		
	/**
	@RequestMapping(value = "/Acompanhamento/{idEstagio}/SubmeterPlano", method = RequestMethod.POST)
	public String submeterPlano(@Valid @RequestParam("anexo") MultipartFile anexo, @PathVariable("idEstagio") Long idEstagio, RedirectAttributes redirectAttributes ){
		try {
			Estagio estagio = estagioService.getEstagioByIdAndEstagiarioCpf(idEstagio, getCpf());

			if (estagio != null) {
				Submissao submissao = estagioService.getSubmissaoByEstagioIdAndTipo(idEstagio, TipoSubmissao.PLANO_ESTAGIO);

				if(submissao != null){
					estagioService.editarSubmissao(Submissao submissao);
				}

			}

			if(anexo == null || !anexo.getContentType().equals("application/pdf")){
				redirectAttributes.addFlashAttribute("error", "Escolha um arquivo pdf.");
				return "";
			}
			
			submissao = new Submissao();
			Documento documento = new Documento();
			documento.setNome(TipoSubmissao.PLANO_ESTAGIO + "_" + estagio.getEstagiario().getNomeCompleto().toUpperCase());
			documento.setExtensao(anexo.getContentType());
			documento.setArquivo(anexo.getBytes());
			submissao.setTipoSubmissao(TipoSubmissao.PLANO_ESTAGIO);
			submissao.setDocumento(documento);
			submissao.setData(new Date());
			submissao.setHora(new Date());
			submissao.setStatusEntrega(StatusEntrega.SUBMETIDO);
			estagioService.submeterPlano(submissao);
			
		} catch (IOException e) {
			return "redirect:/500";
		}

		return "redirect:/Acompanhamento/Estagio/" + idEstagio;
	}
	
	@RequestMapping(value = "/Acompanhamento/Estagio/{idEstagio}/SubmeterRelatorio", method = RequestMethod.POST)
	public String postSubmeterRelatorio(@Valid @RequestParam("anexo") MultipartFile anexo, @PathVariable("idEstagio") Long idEstagio, RedirectAttributes redirectAttributes ){

		try {
			Estagio estagio = estagioService.getEstagioByIdAndEstagiarioCpf(idEstagio, getCpf());
			Submissao submissao = estagioService.getSubmissaoByEstagioIdAndTipo(idEstagio, TipoSubmissao.RELATORIO_FINAL_ESTAGIO);
			
			if(submissao != null){
				redirectAttributes.addFlashAttribute("error", "Não é possível realizar submissão.");
				return "";
			}

			if(anexo == null || !anexo.getContentType().equals("application/pdf")){
				redirectAttributes.addFlashAttribute("error", "Escolha um arquivo pdf.");
				return "";
			}
			
			submissao = new Submissao();
			Documento documento = new Documento();
			documento.setNome(TipoSubmissao.RELATORIO_FINAL_ESTAGIO + "_" + estagio.getEstagiario().getNomeCompleto().toUpperCase());
			documento.setExtensao(anexo.getContentType());
			documento.setArquivo(anexo.getBytes());
			submissao.setTipoSubmissao(TipoSubmissao.RELATORIO_FINAL_ESTAGIO);
			submissao.setDocumento(documento);
			submissao.setData(new Date());
			submissao.setHora(new Date());
			submissao.setStatusEntrega(StatusEntrega.SUBMETIDO);
			estagioService.submeterRelatorio(submissao);
			
		} catch (IOException e) {
			return "redirect:/500";
		}

		return "redirect:/Acompanhamento/Estagio/" + idEstagio;
	}
	
	 */
	

	private void inserirNomeUsuarioNaSessao(HttpSession session) {
		if (session.getAttribute(NOME_USUARIO) == null) {
			session.setAttribute(NOME_USUARIO, usuarioService.getByCpf(getCpfUsuarioLogado()).getNome());
		}
	}
	
	private String getCpfUsuarioLogado() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}
}