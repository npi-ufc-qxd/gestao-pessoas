package ufc.quixada.npi.gp.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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
import org.joda.time.LocalTime;
import org.junit.Before;
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
import ufc.quixada.npi.gp.model.enums.TipoFrequencia;
import ufc.quixada.npi.gp.service.DocumentoService;
import ufc.quixada.npi.gp.service.EstagiarioService;
import ufc.quixada.npi.gp.service.FrequenciaService;
import ufc.quixada.npi.gp.service.PapelService;
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
	
	@Inject
	private DocumentoService documentoService;

	@Inject
	private PapelService papelService;

	@Before
	@RequestMapping(value = {"/index", "/inicial"}, method = RequestMethod.GET)
	public String inicial(ModelMap modelMap, HttpSession session) { 
		modelMap.addAttribute("usuario", SecurityContextHolder.getContext().getAuthentication().getName());
		Estagiario estagiario = estagiarioService.getEstagiarioByPessoaId(getUsuarioLogado(session).getId());
		String ano = "" + new DateTime().getYear();
		List<Turma> turmas = turmaService.getTurmasAno(ano);
		
		if(estagiario == null){
			modelMap.addAttribute("resultado", true);
			modelMap.addAttribute("estagiario", new Estagiario());
		}else{
			modelMap.addAttribute("resultado", false);
			modelMap.addAttribute("estagiario", estagiario);
		}
		modelMap.addAttribute("turmas", turmas);
		return "estagiario/inicial";
	}
	
	@RequestMapping(value = "/meu-cadastro-npi", method = RequestMethod.POST)
	public String adicionarEstagiario(@Valid @ModelAttribute("estagiario") Estagiario estagiario, BindingResult result, HttpSession session, RedirectAttributes redirect) {
		
		if (result.hasErrors()) {
			return "estagiario/meu-cadastro-npi";
		}
		
		Pessoa pessoa = getUsuarioLogado(session);
		
		estagiario.setPessoa(pessoa);
		//estagiario.setTurma(turmaService.find(Turma.class, estagiario.getTurma().getId()));

		estagiarioService.save(estagiario);
		
		//List<Frequencia> frequencias = geraFrequencia(estagiario);
		
		//estagiario.setFrequencias(frequencias);
		
		//estagiario.getTurma().setFrequencias(estagiario.getFrequencias());

		//estagiarioService.update(estagiario);
		
		redirect.addFlashAttribute("info", "Parabéns, " + pessoa.getNome() + ", seu cadastro foi realizado com sucesso!");
		return "redirect:/estagiario/inicial";
	}	
	
	@RequestMapping(value = "/minha-presenca", method = RequestMethod.GET)
	public String minhaPresenca(HttpSession session, Model model) {
		boolean liberarPresenca = true;

		Frequencia frequencia = frequenciaService.getFrequenciaDeHojeByEstagiario(estagiarioService.getEstagiarioByPessoaId(getUsuarioLogado(session).getId()).getId());
		
		if(frequencia == null || frequencia.getStatusFrequencia() != StatusFrequencia.AGUARDO){
			model.addAttribute("msg", "Sua presença não esta liberada, procure o coordenador.");
			liberarPresenca = false;
		}
		model.addAttribute("liberarPresenca", liberarPresenca);
		return "estagiario/minha-presenca";
	}
	
	@RequestMapping(value = "/minha-presenca", method = RequestMethod.POST)
	public String presenteNPI(HttpSession session, @RequestParam("login") String login, @RequestParam("senha") String senha) {
		ShaPasswordEncoder encoder = new ShaPasswordEncoder(256);
		Estagiario estagiario = estagiarioService.getEstagiarioPesssoa(login, encoder.encodePassword(senha, ""));

		Set<LocalDate> dataDosFeriados = new HashSet<LocalDate>();
		for (Folga folga : estagiario.getTurma().getPeriodo().getFolgas()) {
			dataDosFeriados.add(new LocalDate(folga.getData()));
		}
		
		HolidayCalendar<LocalDate> calendarioDeFeriados = new DefaultHolidayCalendar<LocalDate>(dataDosFeriados);
		LocalDateKitCalculatorsFactory.getDefaultInstance().registerHolidays("NPI", calendarioDeFeriados);
		DateCalculator<LocalDate> calendario = LocalDateKitCalculatorsFactory.getDefaultInstance().getDateCalculator("NPI", HolidayHandlerType.FORWARD);
		
		boolean horarioDeTrabalho = isHoraPermitida(estagiario.getTurma().getHoraInicio(), estagiario.getTurma().getHoraFinal());
		LocalDate dia = new LocalDate();
		boolean diaDeTrabalho = isDiaTrabaho(estagiario.getTurma().getInicioSemana().getDia(), estagiario.getTurma().getFimSemana().getDia(), dia.getDayOfWeek());
		
		Frequencia frequencia = new Frequencia();
		if( (!calendario.isNonWorkingDay(dia) ) && ( diaDeTrabalho && horarioDeTrabalho ))
		{
			for (Frequencia f : estagiario.getFrequencias()) {
				LocalDate data  = new LocalDate(f.getData());
				if(data.equals(dia)){
					frequencia = f;
					break;
				}
			}
			
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

//	UTILS
	private boolean isHoraPermitida(Date horaInicio, Date horaFinal ){
		LocalTime inicio = new LocalTime(horaInicio);
		LocalTime fim = new LocalTime(horaFinal);

		LocalTime horaAtual = new LocalTime();
		
		return (horaAtual.equals(inicio) || horaAtual.isAfter(inicio)) && 
				(horaAtual.equals(fim) || horaAtual.isBefore(fim));
	}
	
	private boolean isDiaTrabaho(int inicio, int fim, int dia){
		return (inicio <= dia && dia <= fim);
	}

	private List<Frequencia> geraFrequencia(Estagiario estagiario){
		Set<LocalDate> dataDosFeriados = new HashSet<LocalDate>();

		for (Folga folga : estagiario.getTurma().getPeriodo().getFolgas()) {
		    dataDosFeriados.add(new LocalDate(folga.getData()));
		}
		
		HolidayCalendar<LocalDate> calendarioDeFeriados = new DefaultHolidayCalendar<LocalDate>(dataDosFeriados);
		LocalDateKitCalculatorsFactory.getDefaultInstance().registerHolidays("NPI", calendarioDeFeriados);
		DateCalculator<LocalDate> calendario = LocalDateKitCalculatorsFactory.getDefaultInstance().getDateCalculator("NPI", HolidayHandlerType.FORWARD);
		
		LocalDate dataInicialTemporaria = new LocalDate(estagiario.getTurma().getPeriodo().getInicio());
		LocalDate dataFinalTemporaria = new LocalDate(estagiario.getTurma().getPeriodo().getTermino());
				
		int inicio = estagiario.getTurma().getInicioSemana().getDia();
		int fim = estagiario.getTurma().getFimSemana().getDia();
		List<Frequencia> frequencias = new ArrayList<Frequencia>();
		while (!dataInicialTemporaria.isAfter(dataFinalTemporaria)) {
			int dia = dataInicialTemporaria.getDayOfWeek();
			
			if ((!calendario.isNonWorkingDay(dataInicialTemporaria)) && isDiaTrabaho(inicio, fim, dia)) {
				Frequencia frequencia = new Frequencia();				
				
				frequencia.setData(dataInicialTemporaria.toDate());
				frequencia.setEstagiario(estagiario);
				frequencia.setTurma(estagiario.getTurma());
				frequencia.setTipoFrequencia(TipoFrequencia.NORMAL);
				frequencia.setStatusFrequencia(StatusFrequencia.AGUARDO);
				
				frequencias.add(frequencia);

				System.out.println("Dia de Estagio" + dataInicialTemporaria.getDayOfWeek() + " - " + dataInicialTemporaria.toString() );
			}
			dataInicialTemporaria = dataInicialTemporaria.plusDays(1);
		}		
		
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