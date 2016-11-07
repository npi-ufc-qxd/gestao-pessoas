package br.ufc.quixada.npi.ge.controller;

import static br.ufc.quixada.npi.ge.utils.Constants.ACOMPANHAMENTO_ESTAGIO;
import static br.ufc.quixada.npi.ge.utils.Constants.FORMULARIO_EDITAR_ESTAGIARIO;
import static br.ufc.quixada.npi.ge.utils.Constants.NOME_USUARIO;
import static br.ufc.quixada.npi.ge.utils.Constants.PAGINA_INICIAL_ESTAGIARIO;
import static br.ufc.quixada.npi.ge.utils.Constants.PASTA_DOCUMENTOS_GE;
import static br.ufc.quixada.npi.ge.utils.Constants.REDIRECT_ACOMPANHAMENTO_ESTAGIO;
import static br.ufc.quixada.npi.ge.utils.Constants.REDIRECT_PAGINA_INICIAL_ESTAGIARIO;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
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

import br.ufc.quixada.npi.ge.model.Documento;
import br.ufc.quixada.npi.ge.model.Estagiario;
import br.ufc.quixada.npi.ge.model.Estagio;
import br.ufc.quixada.npi.ge.model.Evento;
import br.ufc.quixada.npi.ge.model.Pessoa;
import br.ufc.quixada.npi.ge.model.Submissao;
import br.ufc.quixada.npi.ge.model.Submissao.StatusEntrega;
import br.ufc.quixada.npi.ge.model.Submissao.TipoSubmissao;
import br.ufc.quixada.npi.ge.service.EstagioService;
import br.ufc.quixada.npi.ge.service.PessoaService;
import br.ufc.quixada.npi.ldap.model.Usuario;

@Controller
@RequestMapping("Estagiario")
public class EstagiarioController {

	@Inject
	private EstagioService estagioService;
	
	@Autowired
	private PessoaService pessoaService;
	
	@RequestMapping(value = {"", "/", "/MinhasTurmas"}, method = RequestMethod.GET)
	public String listarTurmas(Model model, HttpSession session) {
		inserirNomeUsuarioNaSessao(session);
		
		List<Estagio> estagios = estagioService.buscarEstagiosPorEstagiarioCpf(getCpfUsuarioLogado());
		
		List<Evento> eventos = estagioService.buscarEventosEstagiario(estagios);
		
		model.addAttribute("eventos", eventos);
		model.addAttribute("presencas", estagioService.permitirPresencaEstagio(estagios));

		return PAGINA_INICIAL_ESTAGIARIO;
	}
	
	@RequestMapping(value = "/MeusDados", method = RequestMethod.GET)
	public String visualizarMeusDados(Model model) {

		Estagiario estagiario = pessoaService.buscarEstagiarioPorCpf(getCpfUsuarioLogado());
		estagiario.getId();
		model.addAttribute("estagiario",estagiario);
		
		return FORMULARIO_EDITAR_ESTAGIARIO;
	}

	@ModelAttribute("estados")
    public List<Estagiario.Estado> todosEstados() {
        return Arrays.asList(Estagiario.Estado.values());
    }
	
	@ModelAttribute("cursos")
    public List<Estagiario.Curso> todosCursos() {
        return Arrays.asList(Estagiario.Curso.values());
    }
	
	@RequestMapping(value = "/MeusDados", method = RequestMethod.POST)
	public String editarMeusDados(@Valid @ModelAttribute("estagiario") Estagiario paramEstagiario, BindingResult result, RedirectAttributes redirect) {

		if (result.hasErrors()) {
			return FORMULARIO_EDITAR_ESTAGIARIO;
		}
		
		Estagiario estagiario = pessoaService.buscarEstagiarioPorCpf(getCpfUsuarioLogado());
		Pessoa pessoa = pessoaService.buscarPessoaPorCpf(getCpfUsuarioLogado());
		
		estagiario.setNomeCompleto(paramEstagiario.getNomeCompleto());
		estagiario.setCep(paramEstagiario.getCep());
		estagiario.setCidade(paramEstagiario.getCidade());
		estagiario.setCurso(paramEstagiario.getCurso());
		estagiario.setDataNascimento(paramEstagiario.getDataNascimento());
		estagiario.setEmail(paramEstagiario.getEmail());
		estagiario.setEndereco(paramEstagiario.getEndereco());
		estagiario.setNomeMae(paramEstagiario.getNomeMae());
		estagiario.setMatricula(paramEstagiario.getMatricula());
		estagiario.setPessoa(pessoa);
		estagiario.setTelefone(paramEstagiario.getTelefone());
		estagiario.setSemestre(paramEstagiario.getSemestre());
		estagiario.setUsuarioGithub(paramEstagiario.getUsuarioGithub());
		estagiario.setUf(paramEstagiario.getUf());
		
		pessoaService.editarEstagiario(estagiario);
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
		model.addAttribute("consolidadoFrequencia", estagioService.consolidarFrequencias(estagio));
		

		return ACOMPANHAMENTO_ESTAGIO;
	}
	
	@RequestMapping(value="/Acompanhamento/{idEstagio}/DownloadPlano", method=RequestMethod.GET)
	@ResponseBody
	public Object downloadPlano(Model model,@PathVariable("idEstagio") Long idEstagio, RedirectAttributes redirectAttributes) {
		
		Submissao submissaoPlano = estagioService.buscarSubmissaoPorTipoSubmissaoEEstagioIdECpf(Submissao.TipoSubmissao.PLANO_ESTAGIO, idEstagio, getCpfUsuarioLogado());
	    
		if(submissaoPlano == null){
			return "Acesso negado.";
	    }
	    
		byte[] plano = submissaoPlano.getDocumento().getArquivo();
		String[] tipo = submissaoPlano.getDocumento().getExtensao().split("/");
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType(tipo[0], tipo[1]));
		headers.set("Content-Disposition", "attachment; filename=" + submissaoPlano.getDocumento().getNome() + ".pdf");
		headers.setContentLength(plano.length);

	    return new HttpEntity<byte[]>(plano, headers);
	}
	
	@RequestMapping(value="/Acompanhamento/{idEstagio}/DownloadRelatorio", method=RequestMethod.GET)
	@ResponseBody
	public Object downloadRelatorio(@PathVariable("idEstagio") Long idEstagio, RedirectAttributes redirectAttributes) {
		
		Submissao submissaoRelatorio = estagioService.buscarSubmissaoPorTipoSubmissaoEEstagioIdECpf(Submissao.TipoSubmissao.RELATORIO_FINAL_ESTAGIO, idEstagio, getCpfUsuarioLogado());

		if(submissaoRelatorio == null){
			return "Acesso negado.";
	    }
	    
		byte[] relatorio = submissaoRelatorio.getDocumento().getArquivo();
		String[] tipo = submissaoRelatorio.getDocumento().getExtensao().split("/");
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType(tipo[0], tipo[1]));
		headers.set("Content-Disposition", "attachment; filename=" + submissaoRelatorio.getDocumento().getNome() + ".pdf");
		headers.setContentLength(relatorio.length);

	    return new HttpEntity<byte[]>(relatorio, headers);
	}

	@RequestMapping(value = "/Acompanhamento/{idEstagio}/Presenca", method = RequestMethod.GET)
	public @ResponseBody boolean realizarPresenca(HttpSession session, @PathVariable("idEstagio") Long idEstagio) {

		Estagio estagio = estagioService.buscarEstagioPorIdEEstagiarioCpf(idEstagio, getCpfUsuarioLogado());

		if(estagio == null) {
			return false;
		}

		return estagioService.realizarPresenca(estagio);
	}
		
	@RequestMapping(value = "/Acompanhamento/{idEstagio}/SubmeterPlano", method = RequestMethod.POST)
	public String submeterPlano(@Valid @RequestParam("planoEstagio") MultipartFile planoEstagio, @PathVariable("idEstagio") Long idEstagio, RedirectAttributes redirectAttributes ) throws Exception{

		try {
			if(arquivoInvalido(planoEstagio)){
				redirectAttributes.addFlashAttribute("error", "Escolha um arquivo pdf.");
				return REDIRECT_ACOMPANHAMENTO_ESTAGIO + idEstagio;
			}

			Estagio estagio = estagioService.buscarEstagioPorIdEEstagiarioCpf(idEstagio, getCpfUsuarioLogado());
			
			if(estagio == null){
				redirectAttributes.addFlashAttribute("error", "Acesso negado.");
				return REDIRECT_PAGINA_INICIAL_ESTAGIARIO;
			}
			
			Submissao submissao = estagioService.buscarSubmissaoPorTipoSubmissaoEEstagioIdECpf(Submissao.TipoSubmissao.PLANO_ESTAGIO, idEstagio, getCpfUsuarioLogado());
			
			if (submissao != null) {
				redirectAttributes.addFlashAttribute("error", "Acesso negado.");
				return REDIRECT_PAGINA_INICIAL_ESTAGIARIO;
			}
			
			submissao = new Submissao();
			submissao.setEstagio(estagio);
			Documento documento = new Documento();
			documento.setNome(TipoSubmissao.PLANO_ESTAGIO + "_" + estagio.getEstagiario().getNomeCompleto().replace(' ', '_').toUpperCase());
			documento.setExtensao(planoEstagio.getContentType());
			documento.setArquivo(planoEstagio.getBytes());
			documento.setCaminho(PASTA_DOCUMENTOS_GE + "/GE_" + estagio.getId() + "/"+TipoSubmissao.PLANO_ESTAGIO+"_" + estagio.getId() + ".pdf");
			submissao.setTipoSubmissao(TipoSubmissao.PLANO_ESTAGIO);
			submissao.setDocumento(documento);
			submissao.setSubmetidoEm(new Date());
			submissao.setStatusEntrega(StatusEntrega.SUBMETIDO);
			estagioService.submeter(submissao);
			
		} catch (IOException e) {
			redirectAttributes.addFlashAttribute("error", "Tamanho do arquivo excede o limite de 5Mb.");
			return REDIRECT_ACOMPANHAMENTO_ESTAGIO + idEstagio;
		}

		redirectAttributes.addFlashAttribute("sucesso", "Plano submetido realizada com sucesso.");
		return REDIRECT_ACOMPANHAMENTO_ESTAGIO + idEstagio;
	}
	
	@RequestMapping(value = "/Acompanhamento/{idEstagio}/EditarPlano", method = RequestMethod.POST)
	public String editarPlano(@PathVariable("idEstagio") Long idEstagio, MultipartFile planoEstagio, RedirectAttributes redirectAttributes) throws Exception{
		
		if(arquivoInvalido(planoEstagio)){
			redirectAttributes.addFlashAttribute("error", "Escolha um arquivo pdf.");
			return REDIRECT_ACOMPANHAMENTO_ESTAGIO + idEstagio;
		}
		
		Submissao submissao = estagioService.buscarSubmissaoPorTipoSubmissaoEEstagioIdECpf(Submissao.TipoSubmissao.PLANO_ESTAGIO, idEstagio, getCpfUsuarioLogado());
		
		if(submissao == null){
			redirectAttributes.addFlashAttribute("error", "Você não tem permissão para editar este plano.");
			return REDIRECT_PAGINA_INICIAL_ESTAGIARIO;
		}
		
		submissao.getDocumento().setArquivo(planoEstagio.getBytes());
		estagioService.editarSubmissao(submissao);
		redirectAttributes.addFlashAttribute("sucesso", "Plano editado com sucesso.");
		return REDIRECT_ACOMPANHAMENTO_ESTAGIO + idEstagio;
	}
	
	@RequestMapping(value = "/Acompanhamento/{idEstagio}/SubmeterRelatorio", method = RequestMethod.POST)
	public String submeterRelatorio(@RequestParam("relatorio") MultipartFile relatorio, @PathVariable("idEstagio") Long idEstagio, RedirectAttributes redirectAttributes ) throws Exception{

		try {
			if(arquivoInvalido(relatorio)){
				redirectAttributes.addFlashAttribute("error", "Escolha um arquivo pdf.");
				return REDIRECT_ACOMPANHAMENTO_ESTAGIO + idEstagio;
			}
			
			Estagio estagio = estagioService.buscarEstagioPorIdEEstagiarioCpf(idEstagio, getCpfUsuarioLogado());
			
			if (estagio == null) {
				redirectAttributes.addFlashAttribute("error", "Acesso negado.");
				return REDIRECT_PAGINA_INICIAL_ESTAGIARIO;
			}
			
			Submissao submissao = estagioService.buscarSubmissaoPorTipoSubmissaoEEstagioIdECpf(Submissao.TipoSubmissao.RELATORIO_FINAL_ESTAGIO, idEstagio, getCpfUsuarioLogado());
			
			if (submissao != null) {
				redirectAttributes.addFlashAttribute("error", "Acesso negado.");
				return REDIRECT_PAGINA_INICIAL_ESTAGIARIO;
			}
			
			submissao = new Submissao();
			submissao.setEstagio(estagio);
			Documento documento = new Documento();
			documento.setNome(TipoSubmissao.RELATORIO_FINAL_ESTAGIO + "_" + estagio.getEstagiario().getNomeCompleto().replace(' ', '_').toUpperCase());
			documento.setExtensao(relatorio.getContentType());
			documento.setCaminho(PASTA_DOCUMENTOS_GE + "/GE_" + estagio.getId() + "/"+TipoSubmissao.RELATORIO_FINAL_ESTAGIO+"_" + estagio.getId() + ".pdf");
			documento.setArquivo(relatorio.getBytes());
			submissao.setTipoSubmissao(TipoSubmissao.RELATORIO_FINAL_ESTAGIO);
			submissao.setDocumento(documento);
			submissao.setSubmetidoEm(new Date());
			submissao.setStatusEntrega(StatusEntrega.SUBMETIDO);
			estagioService.submeter(submissao);
			
			
		} catch (IOException e) {
			return "redirect:/500";
		}

		redirectAttributes.addFlashAttribute("sucesso", "Relatório submetido realizada com sucesso.");
		return REDIRECT_ACOMPANHAMENTO_ESTAGIO + idEstagio;
	}
	
	@RequestMapping(value = "/Acompanhamento/{idEstagio}/EditarRelatorio", method = RequestMethod.POST)
	public String editarRelatorio(@PathVariable("idEstagio") Long idEstagio, MultipartFile relatorio, RedirectAttributes redirectAttributes) throws Exception{
		
		if(arquivoInvalido(relatorio)){
			redirectAttributes.addFlashAttribute("error", "Escolha um arquivo pdf.");
			return REDIRECT_ACOMPANHAMENTO_ESTAGIO + idEstagio;
		}
		
		Submissao submissao = estagioService.buscarSubmissaoPorTipoSubmissaoEEstagioIdECpf(Submissao.TipoSubmissao.RELATORIO_FINAL_ESTAGIO, idEstagio, getCpfUsuarioLogado());
		
		if(submissao == null){
			redirectAttributes.addFlashAttribute("error", "Acesso negado.");
			return REDIRECT_PAGINA_INICIAL_ESTAGIARIO;
		}
		
		submissao.getDocumento().setArquivo(relatorio.getBytes());
		estagioService.editarSubmissao(submissao);
		redirectAttributes.addFlashAttribute("sucesso", "Relatorio editado com sucesso.");
		return REDIRECT_ACOMPANHAMENTO_ESTAGIO + idEstagio;
		
	}
	
	private void inserirNomeUsuarioNaSessao(HttpSession session) {
		if (session.getAttribute(NOME_USUARIO) == null) {
			session.setAttribute(NOME_USUARIO, pessoaService.buscarEstagiarioPorCpf(getCpfUsuarioLogado()).getNomeCompleto());
		}
	}
	
	private String getCpfUsuarioLogado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = (Usuario) authentication.getPrincipal();
        return usuario.getCpf();
    }

	private boolean arquivoInvalido(MultipartFile anexo){
		if(anexo == null || !anexo.getContentType().equals("application/pdf")) {
			return true;
		}	
		return false;
	}
}