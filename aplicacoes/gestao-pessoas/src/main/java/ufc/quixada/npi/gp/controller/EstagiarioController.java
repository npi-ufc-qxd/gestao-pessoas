package ufc.quixada.npi.gp.controller;

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

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ufc.quixada.npi.gp.model.Estagiario;
import ufc.quixada.npi.gp.model.Folga;
import ufc.quixada.npi.gp.model.Frequencia;
import ufc.quixada.npi.gp.model.Pessoa;
import ufc.quixada.npi.gp.model.Turma;
import ufc.quixada.npi.gp.model.enums.StatusFrequencia;
import ufc.quixada.npi.gp.model.enums.TipoFrequencia;
import ufc.quixada.npi.gp.service.EstagiarioService;
import ufc.quixada.npi.gp.service.GenericService;
import ufc.quixada.npi.gp.service.PessoaService;
import ufc.quixada.npi.gp.utils.Constants;

@Controller
@RequestMapping("estagiario")
public class EstagiarioController {
	@Inject
	private PessoaService servicePessoa;

	@Inject
	private EstagiarioService serviceEstagiario;
	
	@Inject
	private GenericService<Turma> serviceTurma;

	@Inject
	private GenericService<Frequencia> serviceFrequencia;

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(ModelMap modelMap, HttpSession session) {
		return "redirect:/estagiario/inicial";
	}

	@RequestMapping(value = "/inicial")
	public String inicial(ModelMap modelMap, HttpSession session) {

		modelMap.addAttribute("usuario", SecurityContextHolder.getContext().getAuthentication().getName());
		getUsuarioLogado(session);
		modelMap.addAttribute("estagiario", serviceEstagiario.getEstagiario(getUsuarioLogado(session).getId()));
		
		return "estagiario/inicial";
	}

	@RequestMapping(value = "/cadastrar", method = RequestMethod.GET)
	public String cadastro(Model model) {
		model.addAttribute("estagiario", new Estagiario());
		model.addAttribute("turmas" , serviceTurma.find(Turma.class));
		return "estagiario/cadastrar";
	}
	
	@RequestMapping(value = "/presenca", method = RequestMethod.GET)
	public String presenca(HttpSession session) {
		Estagiario estagiario = serviceEstagiario.getEstagiario(getUsuarioLogado(session).getId()).get(0);	
		
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
				serviceFrequencia.update(frequencia);				
			}
			
		}
		return "redirect:/estagiario/inicial";
	}

	@RequestMapping(value = "/a", method = RequestMethod.GET)
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

	@RequestMapping(value = "/cadastrar", method = RequestMethod.POST)
	public String adicionarEstagiario(@Valid @ModelAttribute("estagiario") Estagiario estagiario, BindingResult result, HttpSession session, RedirectAttributes redirect) {

		
		
		estagiario.setPessoa(getUsuarioLogado(session));
		estagiario.setTurma(serviceTurma.find(Turma.class, estagiario.getTurma().getId()));

		serviceEstagiario.save(estagiario);
		
		List<Frequencia> frequencias = geraFrequencia(estagiario);
		
		estagiario.setFrequencias(frequencias);
		
		//estagiario.getTurma().setFrequencias(estagiario.getFrequencias());

		serviceEstagiario.update(estagiario);
		
		redirect.addFlashAttribute("info", "Estagiário cadastrado com sucesso.");
		return "redirect:/estagiario/inicial";
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

	@RequestMapping(value = "/{id}/contaspessoais", method = RequestMethod.GET)
	public String contasPessoais(@PathVariable("id") long id, Model model,
			HttpSession session, RedirectAttributes redirectAttributes) {

		Estagiario estagiario = serviceEstagiario.find(Estagiario.class, id);
		Pessoa pessoa = getUsuarioLogado(session);

		if (estagiario == null) {
			redirectAttributes.addFlashAttribute("erro",
					"Estagiario inexistente.");
			return "redirect:/estagiario/inicial";
		}
		if (pessoa.getId() == estagiario.getPessoa().getId()) {
			model.addAttribute("estagiario", estagiario);
			model.addAttribute("action", "contaspessoais");
			return "estagiario/contaspessoais";
		}
		return "redirect:/estagiario/inicial";
	}

	@RequestMapping(value = "/{id}/contaspessoais", method = RequestMethod.POST)
	public String atualizarEstagiario(
			@PathVariable("id") Long id,
			@Valid @ModelAttribute(value = "estagiario") Estagiario estagiarioAtualizado,
			BindingResult result, Model model, HttpSession session,
			RedirectAttributes redirect) {

		Estagiario estagiario = serviceEstagiario.find(Estagiario.class, id);
		estagiario.setContaRedmine(estagiarioAtualizado.getContaRedmine());
		estagiario.setContaGithub(estagiarioAtualizado.getContaGithub());
		estagiario.setContaHangout(estagiarioAtualizado.getContaHangout());
		this.serviceEstagiario.update(estagiario);

		return "redirect:/estagiario/inicial";
	}

	private Pessoa getUsuarioLogado(HttpSession session) {
		if (session.getAttribute(Constants.USUARIO_LOGADO) == null) {
			Pessoa pessoa = servicePessoa
					.getPessoaByLogin(SecurityContextHolder.getContext()
							.getAuthentication().getName());
			session.setAttribute(Constants.USUARIO_LOGADO, pessoa);
		}
		return (Pessoa) session.getAttribute(Constants.USUARIO_LOGADO);
	}
}