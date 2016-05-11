package ufc.quixada.npi.gp.controller;

import static ufc.quixada.npi.gp.utils.Constants.PAGINA_FORM_ESTAGIARIO;
import static ufc.quixada.npi.gp.utils.Constants.PAGINA_INICIAL_ESTAGIARIO;
import static ufc.quixada.npi.gp.utils.Constants.REDIRECT_PAGINA_INICIAL_ESTAGIARIO;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ufc.quixada.npi.gp.model.Estagiario;
import ufc.quixada.npi.gp.model.Frequencia;
import ufc.quixada.npi.gp.model.Pessoa;
import ufc.quixada.npi.gp.model.Submissao;
import ufc.quixada.npi.gp.model.Turma;
import ufc.quixada.npi.gp.model.enums.StatusFrequencia;
import ufc.quixada.npi.gp.model.enums.Tipo;
import ufc.quixada.npi.gp.service.EstagiarioService;
import ufc.quixada.npi.gp.service.FrequenciaService;
import ufc.quixada.npi.gp.service.PessoaService;
import ufc.quixada.npi.gp.service.TurmaService;
import ufc.quixada.npi.gp.utils.Constants;

@Controller
@RequestMapping("estagiario")
public class EstagiarioController {

	@Inject
	private PessoaService pessoaService;

	@Inject
	private EstagiarioService estagiarioService;

	@Inject
	private TurmaService turmaService;

	@Inject
	private FrequenciaService frequenciaService;

	@RequestMapping(value = "/MinhasTurmas", method = RequestMethod.GET)
	public String getTurmas(Model model, HttpSession session) {
		Pessoa pessoa = getUsuarioLogado(session);

		Estagiario estagiario = estagiarioService.getEstagiarioByPessoaId(pessoa.getId());

		List<Turma> turmas = turmaService.getTurmasByEstagiarioId(estagiario.getId());

		model.addAttribute("turmas", turmas);


		if(!estagiarioService.possuiTurmaAtiva(pessoa.getCpf())){
			model.addAttribute("possuiTurma", false);
		}
		
		
		return PAGINA_INICIAL_ESTAGIARIO;
	}

	@RequestMapping(value = "/MeusDados", method = RequestMethod.GET)
	public String getMeusDados(Model model) {
		
		String cpf = SecurityContextHolder.getContext().getAuthentication().getName();

		Estagiario estagiario = estagiarioService.getEstagiarioByPessoaCpf(cpf);

		if (estagiario == null) {
			return "redirect:/home/meu-cadastro";
		} else {
			model.addAttribute("action", "editar");
			model.addAttribute("estagiario", estagiario);
		}

		return PAGINA_FORM_ESTAGIARIO;
	}

	@RequestMapping(value = "/MeusDados", method = RequestMethod.POST)
	public String postMeusDados(@Valid @ModelAttribute("estagiario") Estagiario estagiario, BindingResult result, HttpSession session, RedirectAttributes redirect, Model model) {
		model.addAttribute("action", "editar");

		if (result.hasErrors()) {
			return PAGINA_FORM_ESTAGIARIO;
		}

		Pessoa pessoa = getUsuarioLogado(session);

		estagiario.setPessoa(pessoa);
		estagiarioService.update(estagiario);

		getUsuarioLogado(session);

		redirect.addFlashAttribute("info", "Parabéns, " + pessoa.getNome() + ", seu cadastro foi realizado com sucesso!");

		return REDIRECT_PAGINA_INICIAL_ESTAGIARIO;
	}
	
	@RequestMapping(value = "/Sobre", method = RequestMethod.GET)
	public String getSobre(){
		return "";
	}
	
	@RequestMapping(value = "/Presenca/Estagio/{id}", method = RequestMethod.POST)
	public String postPresenca(HttpSession session, @ModelAttribute("idTurma") Long idTurma, RedirectAttributes redirectAttributes) {
		Pessoa pessoa = getUsuarioLogado(session);
		Estagiario estagiario = estagiarioService.getEstagiarioByPessoaId(pessoa.getId());

		Turma turma = turmaService.getTurmaByIdAndEstagiarioId(idTurma, estagiario.getId());

		boolean presencaLiberada = false;
		if(turma != null) {
			presencaLiberada = frequenciaService.liberarPreseca(turma);
		}

		boolean frequenciaNaoRealizada = frequenciaService.getFrequenciaDeHojeByEstagiarioId(estagiario.getId()) == null ? true : false;

		if(presencaLiberada && frequenciaNaoRealizada){
			Frequencia frequencia = new Frequencia();

			frequencia.setEstagiario(estagiario);
			frequencia.setTurma(turma);

			frequencia.setData(new Date());
			frequencia.setStatusFrequencia(StatusFrequencia.PRESENTE);
			frequencia.setHorario(new Date());

			frequenciaService.save(frequencia);
		}

		return "redirect:/estagiario/minha-frequencia/turma/" + idTurma;
	}
	
	@RequestMapping(value = "/Acompanhamento/Estagio/{id}", method = RequestMethod.GET)
	public String getAcompanhamento(@PathVariable("idTurma") Long idTurma, Model model, HttpSession session) {
		Pessoa pessoa = getUsuarioLogado(session);

		Estagiario estagiario = estagiarioService.getEstagiarioByPessoaId(pessoa.getId());

		List<Submissao> submissoes = turmaService.getSubmissoesByEstagiarioIdAndIdTurma(estagiario.getId(), idTurma);

		model.addAttribute("submissoes", submissoes);
		model.addAttribute("estagiarioNome", estagiario.getNomeCompleto());
		model.addAttribute("turma", turmaService.getTurmaByIdAndEstagiarioId(idTurma, estagiario.getId()));

		return "estagiario/info-turma";
	}
		
	@RequestMapping(value = "/Acompanhamento/Estagio/{id}/SubmeterPlano", method = RequestMethod.POST)
	public String postSubmeterPlano(@Valid @RequestParam("anexo") MultipartFile anexo, HttpSession session, Model model, @ModelAttribute("idTurma") Long idTurma, RedirectAttributes redirectAttributes ){
							
		try {
			if(!anexo.getContentType().equals("application/pdf")){
				redirectAttributes.addFlashAttribute("error", "Escolha um arquivo pdf.");
				return "redirect:/estagiario/turma/" + idTurma;
			}
			Pessoa pessoa = getUsuarioLogado(session);
			
			Estagiario estagiario = estagiarioService.getEstagiarioByPessoaId(pessoa.getId());
			
			Turma turma = turmaService.getTurmaByIdAndEstagiarioId(idTurma, estagiario.getId());
			
			Tipo tipo = Tipo.PLANO_ESTAGIO;
			
			turmaService.submeterDocumento(estagiario, turma, tipo, anexo);
			
		} catch (IOException e) {
			return "redirect:/500";
		}

		return "redirect:/estagiario/turma/" + idTurma;
	}
	
	@RequestMapping(value = "/Acompanhamento/Estagio/{id}/SubmeterRelatorio", method = RequestMethod.POST)
	public String postSubmeterRelatorio(@Valid @RequestParam("anexo") MultipartFile anexo, HttpSession session, Model model, @ModelAttribute("idTurma") Long idTurma, RedirectAttributes redirectAttributes ){

		try {
			if(!anexo.getContentType().equals("application/pdf")){
				redirectAttributes.addFlashAttribute("error", "Escolha um arquivo pdf.");
				return "redirect:/estagiario/turma/" + idTurma;
			}

			Pessoa pessoa = getUsuarioLogado(session);
			
			Estagiario estagiario = estagiarioService.getEstagiarioByPessoaId(pessoa.getId());
			
			Turma turma = turmaService.getTurmaByIdAndEstagiarioId(idTurma, estagiario.getId());
			
			Tipo tipo = Tipo.RELATORIO_FINAL_ESTAGIO;
								
			turmaService.submeterDocumento(estagiario, turma, tipo, anexo);
			
		} catch (IOException e) {
			return "redirect:/500";
		}

		return "redirect:/estagiario/turma/" + idTurma;
	}

	@RequestMapping(value = "/Acompanhamento/Estagio/{id}/EditarRelatorio", method = RequestMethod.POST)
	public String postEditarRelatorio(){
		return "";
	}
	
	@RequestMapping(value = "/Acompanhamento/Estagio/{id}/EditarPlano", method = RequestMethod.POST)
	public String postEditarPlano(){
		return "";
	}
	
	private Pessoa getUsuarioLogado(HttpSession session) {
		Pessoa pessoa = (Pessoa) session.getAttribute(Constants.USUARIO_LOGADO);

		if (pessoa == null || pessoa.getNome() == null) {
			pessoa = pessoaService.getPessoaByCpf(SecurityContextHolder.getContext().getAuthentication().getName());
			session.setAttribute(Constants.USUARIO_LOGADO, pessoa);
		}

		return pessoa;
	}
}