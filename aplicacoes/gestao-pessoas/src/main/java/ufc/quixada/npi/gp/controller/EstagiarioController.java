package ufc.quixada.npi.gp.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import net.objectlab.kit.datecalc.common.DateCalculator;
import net.objectlab.kit.datecalc.common.DefaultHolidayCalendar;
import net.objectlab.kit.datecalc.common.HolidayCalendar;
import net.objectlab.kit.datecalc.common.HolidayHandlerType;
import net.objectlab.kit.datecalc.joda.LocalDateKitCalculatorsFactory;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ufc.quixada.npi.gp.model.Documento;
import ufc.quixada.npi.gp.model.Estagiario;
import ufc.quixada.npi.gp.model.Folga;
import ufc.quixada.npi.gp.model.Frequencia;
import ufc.quixada.npi.gp.model.Papel;
import ufc.quixada.npi.gp.model.Pessoa;
import ufc.quixada.npi.gp.model.Turma;
import ufc.quixada.npi.gp.model.enums.StatusFrequencia;
import ufc.quixada.npi.gp.model.enums.StatusPeriodo;
import ufc.quixada.npi.gp.model.enums.TipoFrequencia;
import ufc.quixada.npi.gp.service.DocumentoService;
import ufc.quixada.npi.gp.service.EstagiarioService;
import ufc.quixada.npi.gp.service.FrequenciaService;
import ufc.quixada.npi.gp.service.PapelService;
import ufc.quixada.npi.gp.service.PessoaService;
import ufc.quixada.npi.gp.service.TurmaService;
import ufc.quixada.npi.gp.utils.Constants;
import ufc.quixada.npi.gp.utils.UtilGestao;

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
	
	@Inject
	private DocumentoService documentoService;

	@Inject
	private PapelService papelService;

	@RequestMapping(value = {"/index", "/inicial"}, method = RequestMethod.GET)
	public String inicial(ModelMap modelMap, HttpSession session) { 
		modelMap.addAttribute("usuario", SecurityContextHolder.getContext().getAuthentication().getName());
		Estagiario estagiario = estagiarioService.getEstagiarioByPessoaId(getUsuarioLogado(session).getId());
		
		if(estagiario == null){
			modelMap.addAttribute("estagiarioCadastrado", false);
			modelMap.addAttribute("estagiario", new Estagiario());
		}else{
			modelMap.addAttribute("estagiarioCadastrado", true);
			modelMap.addAttribute("estagiario", estagiario);

			if (estagiario.getTurma() == null) {
				List<Turma> turmas = turmaService.getTurmasAno(String.valueOf(new DateTime().getYear()), StatusPeriodo.ABERTO);
				modelMap.addAttribute("turmasSelect", turmas);
			}
			
		}
		return "estagiario/inicial";
	}
	
	@RequestMapping(value = "/meu-cadastro-npi", method = RequestMethod.POST)
	public String adicionarEstagiario(@Valid @ModelAttribute("estagiario") Estagiario estagiario, BindingResult result, HttpSession session, RedirectAttributes redirect, Model model) {
		
		if (result.hasErrors()) {
			return "estagiario/inicial";
		}
		
		Pessoa pessoa = getUsuarioLogado(session);
		estagiario.setPessoa(pessoa);

		estagiarioService.save(estagiario);
		
		redirect.addFlashAttribute("info", "Parabéns, " + pessoa.getNome() + ", seu cadastro foi realizado com sucesso!");
		model.addAttribute("estagiarioCadastrado", true);
		model.addAttribute("estagiario", estagiario);
		return "redirect:/estagiario/inicial";
	}	

	@RequestMapping(value = "/contas-turma", method = RequestMethod.POST)
	public String adicionarContasETurma(@Valid @ModelAttribute("estagiario") Estagiario estagiario, BindingResult result, HttpSession session, RedirectAttributes redirect, Model model) {
		
		model.addAttribute("estagiarioCadastrado", true);
		if (estagiario.getId() == null || estagiario.getTurma().getId() == null ||
				estagiario.getContaGithub().isEmpty() || 
				estagiario.getContaHangout().isEmpty() || 
				estagiario.getContaRedmine().isEmpty()
				) {
			model.addAttribute("erro", "Todos dos Campos são obrigatórios.");
			model.addAttribute("estagiario", estagiario);
			List<Turma> turmas = turmaService.getTurmasAno(String.valueOf(new DateTime().getYear()), StatusPeriodo.ABERTO);
			model.addAttribute("turmasSelect", turmas);
			
			return "estagiario/inicial";
		}
		
		Estagiario estagiarioBanco = estagiarioService.find(Estagiario.class, estagiario.getId());
		estagiarioBanco.setContaGithub(estagiario.getContaGithub());
		estagiarioBanco.setContaHangout(estagiario.getContaHangout());
		estagiarioBanco.setContaRedmine(estagiario.getContaRedmine());
		estagiarioBanco.setTurma(turmaService.find(Turma.class, estagiario.getTurma().getId()));

		List<Frequencia> frequencias = gerarFrequencia(estagiarioBanco);
		estagiarioBanco.setFrequencias(frequencias);
//		estagiarioBanco.getTurma().setFrequencias(estagiarioBanco.getFrequencias());

		estagiarioService.update(estagiarioBanco);
		
		redirect.addFlashAttribute("info", "Parabéns, " + estagiarioBanco.getPessoa().getNome() + ", seu cadastro foi realizado com sucesso!");
		model.addAttribute("estagiario", estagiarioBanco);
		return "redirect:/estagiario/inicial";
	}

	@RequestMapping(value = "/minha-presenca", method = RequestMethod.GET)
	public String minhaPresenca(HttpSession session, Model model) {
		boolean liberarPresenca = true;

		Frequencia frequencia = frequenciaService.getFrequenciaDeHojeByEstagiario(estagiarioService.getEstagiarioByPessoaId(getUsuarioLogado(session).getId()).getId());

		if (frequencia != null) {

			if(frequencia.getStatusFrequencia() == StatusFrequencia.AGUARDO) {
				liberarPresenca = true;
			}

			if(frequencia.getStatusFrequencia() == StatusFrequencia.PRESENTE) {
				model.addAttribute("msg", "Presença confirmada!!!");
				liberarPresenca = false;
			} 
			
		} else {
			model.addAttribute("msg", "Sua presença não esta liberada, procure o supervisor.");
			liberarPresenca = false;
		}

		model.addAttribute("liberarPresenca", liberarPresenca);
		return "estagiario/minha-presenca";
	}
	
	@RequestMapping(value = "/minha-presenca", method = RequestMethod.POST)
	public String presenteNPI(HttpSession session, @RequestParam("cpf") String cpf, @RequestParam("senha") String senha) {
		ShaPasswordEncoder encoder = new ShaPasswordEncoder(256);
		Estagiario estagiario = estagiarioService.getEstagiarioPesssoa(cpf, encoder.encodePassword(senha, ""));

		Set<LocalDate> dataDosFeriados = new HashSet<LocalDate>();
		for (Folga folga : estagiario.getTurma().getPeriodo().getFolgas()) {
			dataDosFeriados.add(new LocalDate(folga.getData()));
		}
		
		HolidayCalendar<LocalDate> calendarioDeFeriados = new DefaultHolidayCalendar<LocalDate>(dataDosFeriados);
		LocalDateKitCalculatorsFactory.getDefaultInstance().registerHolidays("NPI", calendarioDeFeriados);
		DateCalculator<LocalDate> calendario = LocalDateKitCalculatorsFactory.getDefaultInstance().getDateCalculator("NPI", HolidayHandlerType.FORWARD);

		boolean horarioDeTrabalho = UtilGestao.isHoraPermitida(estagiario.getTurma().getHorarios());
		boolean diaDeTrabalho = UtilGestao.isDiaTrabaho(estagiario.getTurma().getHorarios());
		
		LocalDate dia = new LocalDate();
		if( (!calendario.isNonWorkingDay(dia) ) && ( diaDeTrabalho && horarioDeTrabalho ))
		{
			Frequencia frequencia = frequenciaService.getFrequenciaDeHojeByEstagiario(estagiarioService.getEstagiarioByPessoaId(getUsuarioLogado(session).getId()).getId());

			if(frequencia.getStatusFrequencia().equals(StatusFrequencia.AGUARDO)){
				frequencia.setStatusFrequencia(StatusFrequencia.PRESENTE);
				frequenciaService.update(frequencia);
			}
			
		}

		return "redirect:/estagiario/minha-presenca";
	}
	
	@RequestMapping(value = "/meu-projeto", method = RequestMethod.GET)
	public String meuProjeto(HttpSession session, Model model) {
		Estagiario estagiario = estagiarioService.getEstagiarioByPessoaId(getUsuarioLogado(session).getId());
		
		if(estagiario.getProjeto() != null){
			model.addAttribute("projeto", estagiario.getProjeto());
		}
		
		return "estagiario/meu-projeto";
	}
	
	@RequestMapping(value = "/avaliacao", method = RequestMethod.GET)
	public String avaliacao(HttpSession session, Model model) {
		return "estagiario/avaliacao";
	}
	
	@RequestMapping(value = "/cadastre-se", method = RequestMethod.POST)
	public String cadastrarPessoa(HttpSession session, Model model, @Valid @ModelAttribute("pessoa") Pessoa pessoa, BindingResult result, RedirectAttributes redirect) {
		
		if (result.hasErrors()) {
			model.addAttribute("cadastro", true);
			return "login";
		}

		ShaPasswordEncoder encoder = new ShaPasswordEncoder(256);
		
		List<Papel> papeis = new ArrayList<Papel>();
		papeis.add(papelService.getPapel("ROLE_ESTAGIARIO"));

		pessoa.setPassword(encoder.encodePassword(pessoa.getPassword(), ""));
		pessoa.setHabilitado(true);
 		pessoa.setPapeis(papeis);
		
		pessoaService.save(pessoa);
		
		redirect.addFlashAttribute("info", "Parabéns, seu cadastro esta realizado.");
		return "redirect:/login";
	}

	@RequestMapping(value = "/teste", method = RequestMethod.GET)
	public void teste(HttpSession session, Model model) {
		gerarFrequencia(estagiarioService.find(Estagiario.class, 2L));
	}

	private List<Frequencia> gerarFrequencia(Estagiario estagiario){

		Set<LocalDate> dataDosFeriados = new HashSet<LocalDate>();

		if(estagiario.getTurma().getPeriodo().getFolgas() != null){
			for (Folga folga : estagiario.getTurma().getPeriodo().getFolgas()) {
			    dataDosFeriados.add(new LocalDate(folga.getData()));
			}
		}
		
		HolidayCalendar<LocalDate> calendarioDeFeriados = new DefaultHolidayCalendar<LocalDate>(dataDosFeriados);
		LocalDateKitCalculatorsFactory.getDefaultInstance().registerHolidays("NPI", calendarioDeFeriados);
		DateCalculator<LocalDate> calendario = LocalDateKitCalculatorsFactory.getDefaultInstance().getDateCalculator("NPI", HolidayHandlerType.FORWARD);
		
		LocalDate inicioPeriodoTemporario = new LocalDate(estagiario.getTurma().getPeriodo().getInicio());
		LocalDate fimPeriodo = new LocalDate(estagiario.getTurma().getPeriodo().getTermino());

		List<Frequencia> frequencias = new ArrayList<Frequencia>();
		int cont = 0;
		while (!inicioPeriodoTemporario.isAfter(fimPeriodo)) {
			System.out.println(!inicioPeriodoTemporario.isAfter(fimPeriodo));
			cont++;

			if (UtilGestao.isDiaTrabahoTurma(estagiario.getTurma().getHorarios(), inicioPeriodoTemporario)) {
				Frequencia frequencia = new Frequencia();				
				
				frequencia.setTipoFrequencia(TipoFrequencia.NORMAL);				
				frequencia.setData(inicioPeriodoTemporario.toDate());
				frequencia.setEstagiario(estagiario);
				frequencia.setTurma(estagiario.getTurma());
				
				if (calendario.isNonWorkingDay(inicioPeriodoTemporario)) {
					frequencia.setStatusFrequencia(StatusFrequencia.FERIADO);
				} else {
					frequencia.setStatusFrequencia(StatusFrequencia.AGUARDO);
				}
				
				frequencias.add(frequencia);

				System.out.println("Dia de Estagio" + inicioPeriodoTemporario.getDayOfWeek() + " - " + fimPeriodo.toString() );
			}
			inicioPeriodoTemporario = inicioPeriodoTemporario.plusDays(1);
			System.out.println("while cont " + inicioPeriodoTemporario.toString());
		}	
		System.out.println("while cont " + cont);
		
		return frequencias;
	}
	
	@RequestMapping(value = "/documentos", method = RequestMethod.GET)
	public String documento(HttpSession session, Model model) {
		return "estagiario/documentos";
	}

	@RequestMapping(value = "/cadastro-documento", method = RequestMethod.POST)
	public String cadastrarDocumentos(@RequestParam("documentos") List<MultipartFile> documentos,BindingResult result, HttpSession session, RedirectAttributes redirect, Model model) {
		
		Pessoa pessoa = getUsuarioLogado(session);
		
		if (result.hasErrors()) {
			return 	"";
		}
		
		List<Documento> documentosEstagio = new ArrayList<Documento>();
		if(documentos != null && !documentos.isEmpty()) {
			for(MultipartFile doc : documentos) {
				try {
					if(doc.getBytes() != null && doc.getBytes().length != 0) {
						Documento documento = new Documento();
						documento.setArquivo(doc.getBytes());
						documento.setNome(doc.getOriginalFilename());
						documento.setTipo(doc.getContentType());
						documento.setPessoa(pessoa);
						documentosEstagio.add(documento);
					}
				} catch (IOException e) {
					model.addAttribute("erro", "Erro ao fazer upload.");
					return "";
				}
			}
		}
		
		
		if(!documentos.isEmpty()) {
			documentoService.salvar(documentosEstagio);
		}
		
		redirect.addFlashAttribute("info", "");
		return "";
	}

	@RequestMapping(value = "/conta-github", method = RequestMethod.POST)
	public String contaGithub(HttpSession session, Model model, @RequestParam("pk") Long idEstagiario, @RequestParam("value") String contaGithub) {
		
		Estagiario estagiario = estagiarioService.find(Estagiario.class, idEstagiario);
		
		if (!contaGithub.isEmpty()) {
			estagiario.setContaGithub(contaGithub);
			estagiarioService.update(estagiario);
			return "estagiario/inicial";
		}
		return "estagiario/inicial";
	}
	
	private Pessoa getUsuarioLogado(HttpSession session) {
		if (session.getAttribute(Constants.USUARIO_LOGADO) == null) {
			Pessoa pessoa = pessoaService
					.getPessoaByCPF(SecurityContextHolder.getContext()
							.getAuthentication().getName());
			session.setAttribute(Constants.USUARIO_LOGADO, pessoa);
		}
		return (Pessoa) session.getAttribute(Constants.USUARIO_LOGADO);
	}
}