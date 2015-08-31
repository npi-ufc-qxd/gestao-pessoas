package ufc.quixada.npi.gp.controller;

import static ufc.quixada.npi.gp.utils.Constants.PAGINA_AVALIACAO;
import static ufc.quixada.npi.gp.utils.Constants.PAGINA_FORM_ESTAGIARIO;
import static ufc.quixada.npi.gp.utils.Constants.PAGINA_INICIAL_ESTAGIARIO;
import static ufc.quixada.npi.gp.utils.Constants.PAGINA_MEU_PROJETO;
import static ufc.quixada.npi.gp.utils.Constants.PAGINA_MINHA_PRESENCA;
import static ufc.quixada.npi.gp.utils.Constants.REDIRECT_PAGINA_INICIAL_ESTAGIARIO;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import net.objectlab.kit.datecalc.common.DefaultHolidayCalendar;
import net.objectlab.kit.datecalc.common.HolidayCalendar;
import net.objectlab.kit.datecalc.joda.LocalDateKitCalculatorsFactory;

import org.joda.time.LocalDate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ufc.quixada.npi.gp.model.Estagiario;
import ufc.quixada.npi.gp.model.Folga;
import ufc.quixada.npi.gp.model.Frequencia;
import ufc.quixada.npi.gp.model.Pessoa;
import ufc.quixada.npi.gp.model.enums.StatusFrequencia;
import ufc.quixada.npi.gp.model.enums.TipoFrequencia;
import ufc.quixada.npi.gp.service.DadoConsolidado;
import ufc.quixada.npi.gp.service.EstagiarioService;
import ufc.quixada.npi.gp.service.FolgaService;
import ufc.quixada.npi.gp.service.FrequenciaService;
import ufc.quixada.npi.gp.service.PessoaService;
import ufc.quixada.npi.gp.utils.Constants;
import ufc.quixada.npi.gp.utils.UtilGestao;
import br.ufc.quixada.npi.ldap.service.UsuarioService;

@Controller
@RequestMapping("estagiario")
public class EstagiarioController {

	@Inject
	private PessoaService pessoaService;

	@Inject
	private EstagiarioService estagiarioService;

	@Inject
	private FrequenciaService frequenciaService;
	
	@Inject
	private UsuarioService usuarioService;
	
	@Inject
	private FolgaService folgaService;

	@RequestMapping(value = {"/",""}, method = RequestMethod.GET)
	public String paginaInicial(Model model, HttpSession session) {
		Pessoa pessoa = getUsuarioLogado(session);
		
		if(!estagiarioService.possuiTurmaAtiva(pessoa.getCpf())){
			model.addAttribute("possuiTurma", false);
		}

		return PAGINA_INICIAL_ESTAGIARIO;
	}

	@RequestMapping(value = "/meus-dados", method = RequestMethod.GET)
	public String paginaPerfilEstagiario(Model model) {
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

	@RequestMapping(value = "/editar-perfil", method = RequestMethod.GET)
	public String paginaEditarPerfil(Model model) {
		String cpf = SecurityContextHolder.getContext().getAuthentication().getName();
		model.addAttribute("estagiario", estagiarioService.getEstagiarioByPessoaCpf(cpf));
		model.addAttribute("action", "editar");

		return PAGINA_FORM_ESTAGIARIO;
	}

	@RequestMapping(value = "/editar-perfil", method = RequestMethod.POST)
	public String adicionarEstagiario(@Valid @ModelAttribute("estagiario") Estagiario estagiario, BindingResult result, HttpSession session, RedirectAttributes redirect, Model model) {
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

	@RequestMapping(value = "/minha-presenca", method = RequestMethod.GET)
	public String minhaPresenca(HttpSession session, Model model) {
		Pessoa pessoa = getUsuarioLogado(session);
		
		Estagiario estagiario = estagiarioService.getEstagiarioByPessoaId(pessoa.getId());

		boolean liberarPresenca = false;

		boolean possuiTurma  = estagiario.getTurma() != null ? true : false;
		
		if(possuiTurma) {
			
			boolean frequenciaNaoRealizada = frequenciaService.getFrequenciaDeHojeByEstagiarioId(estagiario.getId()) == null ? true : false;

			
			if(frequenciaService.liberarPreseca(estagiario.getTurma()) && frequenciaNaoRealizada){
				liberarPresenca = true;
			}

			List<Frequencia> frequencias = frequenciaService.getFrequenciasByEstagiarioId(estagiario.getId());

			LocalDate inicioPeriodoTemporario;
			if(!frequenciaNaoRealizada){
				inicioPeriodoTemporario = new LocalDate(new Date()).plusDays(1);
			}else{
				inicioPeriodoTemporario = new LocalDate(new Date());
			}

			LocalDate fimPeriodo = new LocalDate(estagiario.getTurma().getTermino());
			
			List<Frequencia> frequenciasEmAguardo = gerarFrequencia(estagiario, inicioPeriodoTemporario, fimPeriodo); 
			
			frequencias.addAll(frequenciasEmAguardo);

			DadoConsolidado dadosConsolidados = frequenciaService.calcularDadosConsolidados(frequencias);

			model.addAttribute("frequencias", frequencias);
			model.addAttribute("dadosConsolidados", dadosConsolidados);		
			model.addAttribute("dadosConsolidados", dadosConsolidados);		
			model.addAttribute("estagiario", estagiario);
			model.addAttribute("liberarPresenca", liberarPresenca);
			model.addAttribute("frequenciaNaoRealizada", frequenciaNaoRealizada);
		}

		model.addAttribute("possuiTurma", possuiTurma);

		return PAGINA_MINHA_PRESENCA;
	}

	@RequestMapping(value = "/minha-presenca", method = RequestMethod.POST)
	public String cadastrarFrequencia(HttpSession session, @RequestParam("senha") String senha, RedirectAttributes redirectAttributes) {
		Pessoa pessoa = getUsuarioLogado(session);
		Estagiario estagiario = estagiarioService.getEstagiarioByPessoaId(pessoa.getId());
		
		boolean estagiarioValido = usuarioService.autentica(pessoa.getCpf(), senha);
		
		boolean presencaLiberada = false;
		if(estagiario.getTurma() != null) {
			presencaLiberada = frequenciaService.liberarPreseca(estagiario.getTurma());
		}

		boolean frequenciaNaoRealizada = frequenciaService.getFrequenciaDeHojeByEstagiarioId(estagiario.getId()) == null ? true : false;

		if(estagiarioValido && presencaLiberada && frequenciaNaoRealizada){
			Frequencia frequencia = new Frequencia();

			frequencia.setEstagiario(estagiario);
			frequencia.setTurma(estagiario.getTurma());

			frequencia.setData(new Date());
			frequencia.setStatusFrequencia(StatusFrequencia.PRESENTE);
			frequencia.setTempo(new Date());
			
			frequenciaService.save(frequencia);
		}

		return "redirect:/estagiario/minha-presenca";
	}
	
	@RequestMapping(value = "/meu-projeto", method = RequestMethod.GET)
	public String meuProjeto(HttpSession session, Model model) {
		Estagiario estagiario = estagiarioService.getEstagiarioByPessoaId(getUsuarioLogado(session).getId());

		if (estagiario.getProjeto() != null) {
			model.addAttribute("projeto", estagiario.getProjeto());
		}

		return PAGINA_MEU_PROJETO;
	}

	@RequestMapping(value = "/minha-avaliacao", method = RequestMethod.GET)
	public String avaliacao(HttpSession session, Model model) {
		return PAGINA_AVALIACAO;
	}
	
	private Pessoa getUsuarioLogado(HttpSession session) {
		Pessoa pessoa = (Pessoa) session.getAttribute(Constants.USUARIO_LOGADO);
		
		if (pessoa == null || pessoa.getNome() == null) {
			pessoa = pessoaService.getPessoaByCpf(SecurityContextHolder.getContext().getAuthentication().getName());
			session.setAttribute(Constants.USUARIO_LOGADO, pessoa);
		}

		return pessoa;
	}

	private List<Frequencia> gerarFrequencia(Estagiario estagiario, LocalDate inicioPeriodoTemporario, LocalDate fimPeriodo) {

		List<Folga> folgas = folgaService.find(Folga.class);
		
		Set<LocalDate> dataDosFeriados = new HashSet<LocalDate>();

		if (folgas != null) {
			for (Folga folga : folgas) {
				dataDosFeriados.add(new LocalDate(folga.getData()));
			}
		}

		HolidayCalendar<LocalDate> calendarioDeFeriados = new DefaultHolidayCalendar<LocalDate>(dataDosFeriados);
		LocalDateKitCalculatorsFactory.getDefaultInstance().registerHolidays("NPI", calendarioDeFeriados);

		List<Frequencia> frequencias = new ArrayList<Frequencia>();
		
		while (!inicioPeriodoTemporario.isAfter(fimPeriodo)) {

			if (UtilGestao.isDiaDeTrabahoDaTurma(estagiario.getTurma().getHorarios(), inicioPeriodoTemporario)) {
				Frequencia frequencia = new Frequencia();
				frequencia.setTipoFrequencia(TipoFrequencia.NORMAL);
				frequencia.setData(inicioPeriodoTemporario.toDate());
				frequencia.setStatusFrequencia(StatusFrequencia.AGUARDO);

				frequencias.add(frequencia);
			}
			inicioPeriodoTemporario = inicioPeriodoTemporario.plusDays(1);
		}

		return frequencias;
	}

}