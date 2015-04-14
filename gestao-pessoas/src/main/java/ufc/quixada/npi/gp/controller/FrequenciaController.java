package ufc.quixada.npi.gp.controller;

import static ufc.quixada.npi.gp.utils.Constants.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import net.objectlab.kit.datecalc.common.DateCalculator;
import net.objectlab.kit.datecalc.common.DefaultHolidayCalendar;
import net.objectlab.kit.datecalc.common.HolidayCalendar;
import net.objectlab.kit.datecalc.common.HolidayHandlerType;
import net.objectlab.kit.datecalc.joda.LocalDateKitCalculatorsFactory;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ufc.quixada.npi.gp.model.Estagiario;
import ufc.quixada.npi.gp.model.FiltroJson;
import ufc.quixada.npi.gp.model.Folga;
import ufc.quixada.npi.gp.model.Frequencia;
import ufc.quixada.npi.gp.model.Pessoa;
import ufc.quixada.npi.gp.model.ReposicaoJson;
import ufc.quixada.npi.gp.model.Turma;
import ufc.quixada.npi.gp.model.enums.StatusFrequencia;
import ufc.quixada.npi.gp.model.enums.TipoFrequencia;
import ufc.quixada.npi.gp.service.EstagiarioService;
import ufc.quixada.npi.gp.service.FrequenciaService;
import ufc.quixada.npi.gp.service.PessoaService;
import ufc.quixada.npi.gp.service.TurmaService;
import ufc.quixada.npi.gp.utils.UtilGestao;

@Controller
@RequestMapping("frequencia")
public class FrequenciaController {

	@Inject
	private EstagiarioService estagiarioService;

	@Inject
	private TurmaService turmaService;

	@Inject
	private FrequenciaService frequenciaService;

	@Inject
	private PessoaService pessoaService;

	@RequestMapping(value = "/frequencias", method = RequestMethod.GET)
	public String frequencia(ModelMap modelMap) {
		modelMap.addAttribute("frequencias", frequenciaService.find(Frequencia.class));
		return PAGINA_LISTAR_FREQUENCIAS;
	}	

	@RequestMapping(value = "/frequencias.json", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<Frequencia> getFrequencias(@RequestBody FiltroJson frequenciaJson, Model model) {
		List<Frequencia> frequencias = frequenciaService.getFrequencias(frequenciaJson.getData(), turmaService.find(Turma.class, frequenciaJson.getTurma()));
		return frequencias;
	}

	@RequestMapping(value = "/observacao", method = RequestMethod.POST)
	public String frequenciaObservar(@RequestParam("pk") Long idFrequencia, @RequestParam("value") String observacao, Model model) {

		Frequencia frequencia = frequenciaService.find(Frequencia.class, idFrequencia);
		frequencia.setObservacao(observacao);
		frequenciaService.update(frequencia);
		
		return PAGINA_LISTAR_FREQUENCIAS;
	}
	
	@RequestMapping(value = "/atualizarStatus", method = RequestMethod.POST)
	public String atualizarStatus(@RequestParam("pk") Long idFrequencia, @RequestParam("value") StatusFrequencia status, Model model, RedirectAttributes redirectAttributes) {
		Frequencia frequencia = frequenciaService.find(Frequencia.class, idFrequencia);
		
		if(frequencia != null){
			frequencia.setStatusFrequencia(status);
			frequenciaService.update(frequencia);
		}

		return PAGINA_LISTAR_FREQUENCIAS;
	}
	
	@RequestMapping(value = "/reposicao", method = RequestMethod.GET)
	public String reposicao(ModelMap model) {
		
		model.addAttribute("objeto", frequenciaService.getFrequenciaRepor());
		return PAGINA_REPOSICAO;
	}

	@RequestMapping(value = "/reposicao-atraso", method = RequestMethod.POST)
	public String reposicaoAtraso(ModelMap model, @RequestParam("turma") Long idTurma) {
		if (idTurma != null) {
			model.addAttribute("reposicao", "ATRASADO");
			model.addAttribute("objeto", frequenciaService.getReposicaoAtraso(idTurma));
		}
		return PAGINA_REPOSICAO;
	}

	@RequestMapping(value = "/reposicao-falta", method = RequestMethod.POST)
	public String reposicaoFalta(ModelMap model, @RequestParam("turma") Long idTurma) {
		if (idTurma != null) {
			model.addAttribute("reposicao", "FALTA");
			model.addAttribute("objeto", frequenciaService.getReposicaoFalta(idTurma));
		}
		return PAGINA_REPOSICAO;
	}

	@RequestMapping(value = "/agendar-reposicao.json", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Model agendarReposicao(@RequestBody ReposicaoJson reposicaoJson, Model model) {
		Date reposicao = null;
		try {
			if (!reposicaoJson.getDataReposicao().isEmpty()){
				DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("dd/MM/yyyy");
				DateTime date = dateTimeFormatter.parseDateTime(reposicaoJson.getDataReposicao());
				reposicao = date.toDate();
				SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
				Date today;
					today = format.parse(format.format(new Date()));
					if(reposicao.before(today)) {
						model.addAttribute("errorMessage", MENSAGEM_DATA_FUTURA);
						return model;
					}
			} else {
				model.addAttribute("errorMessage", "Campo Obrigatorio");
				return model;
			}
		} catch (Exception e) {
			model.addAttribute("errorMessage", "Data invalida");
			return model;
		}		

		Frequencia frequenciaAgendada = new Frequencia();

		if (reposicaoJson.getStatus().equals(StatusFrequencia.ATRASADO)) {
			List<Frequencia> frequencias = frequenciaService.getFrequenciaStatus(reposicaoJson.getIdEstagiario(), reposicaoJson.getStatus(), 2);

			if (frequencias.size() == 2) {

				frequenciaAgendada.setData(reposicao);
				frequenciaAgendada.setEstagiario(frequencias.get(0).getEstagiario());
				frequenciaAgendada.setTurma(frequencias.get(0).getTurma());
				frequenciaAgendada.setTipoFrequencia(TipoFrequencia.REPOSICAO_ATRASO);
				frequenciaAgendada.setStatusFrequencia(StatusFrequencia.AGUARDO);

				for (Frequencia frequenciaComAtraso : frequencias) {
					frequenciaComAtraso.setStatusFrequencia(StatusFrequencia.REPONDO_ATRASO);
					frequenciaService.update(frequenciaComAtraso);
				}
				frequenciaService.save(frequenciaAgendada);
				model.addAttribute("message", "A frequencia foi agendada com sucesso para a data, " + reposicaoJson.getDataReposicao());
			} else {
				model.addAttribute("message", "Não há atrasos suficientes para realizar o agendamento.");
			}
		}
		else if (reposicaoJson.getStatus().equals(StatusFrequencia.FALTA)) {
			List<Frequencia> frequencias = frequenciaService.getFrequenciaStatus(reposicaoJson.getIdEstagiario(), reposicaoJson.getStatus(), 1);

			if (frequencias.size() == 1) {
				frequenciaAgendada.setData(reposicao);
				frequenciaAgendada.setEstagiario(frequencias.get(0).getEstagiario());
				frequenciaAgendada.setTurma(frequencias.get(0).getTurma());
				frequenciaAgendada.setTipoFrequencia(TipoFrequencia.REPOSICAO_FALTA);
				frequenciaAgendada.setStatusFrequencia(StatusFrequencia.AGUARDO);

				for (Frequencia frequenciaComFalta : frequencias) {
					frequenciaComFalta.setStatusFrequencia(StatusFrequencia.REPONDO_FALTA);
					frequenciaService.update(frequenciaComFalta);
				}
				frequenciaService.save(frequenciaAgendada);
				model.addAttribute("message", "A frequencia foi agendada com sucesso para a data, " + reposicaoJson.getDataReposicao());
			}else{
				model.addAttribute("message", "Não há falta(s) registradas para realizar o agendamento.");
			}

		}
		return model;
	}
	@RequestMapping(value = "/minha-presenca", method = RequestMethod.GET)
	public String minhaPresenca(HttpSession session, Model model) {
		boolean liberarPresenca = false;
		Estagiario estagiario = estagiarioService.getEstagiarioByPessoaId(getUsuarioLogado(session).getId());

		Set<LocalDate> dataDosFeriados = new HashSet<LocalDate>();
		for (Folga folga : estagiario.getTurma().getPeriodo().getFolgas()) {
			dataDosFeriados.add(new LocalDate(folga.getData()));
		}
		
		HolidayCalendar<LocalDate> calendarioDeFeriados = new DefaultHolidayCalendar<LocalDate>(dataDosFeriados);
		LocalDateKitCalculatorsFactory.getDefaultInstance().registerHolidays("NPI", calendarioDeFeriados);
		DateCalculator<LocalDate> calendarioDeFeriadosNPI = LocalDateKitCalculatorsFactory.getDefaultInstance().getDateCalculator("NPI", HolidayHandlerType.FORWARD);

		boolean horarioDeTrabalho = UtilGestao.isHoraPermitida(estagiario.getTurma().getHorarios());
		boolean diaDeTrabalho = UtilGestao.isDiaTrabaho();
		LocalDate dia = new LocalDate();

		Frequencia frequenciaDeHoje = frequenciaService.getFrequenciaDeHojeByEstagiario(estagiario.getId());
		
		if (!calendarioDeFeriadosNPI.isNonWorkingDay(dia)) {
			if (frequenciaDeHoje != null) {
				model.addAttribute("message", frequenciaDeHoje.getStatusFrequencia().getMensagem());
			} else if (horarioDeTrabalho && diaDeTrabalho) {
				liberarPresenca = true;
			} else {
				model.addAttribute("message", "Sua presença não esta liberada.");
			}
		} else {
			model.addAttribute("message", "Hoje é um feriado.");
		}

		model.addAttribute("estagiario", estagiario);
		model.addAttribute("liberarPresenca", liberarPresenca);
		return PAGINA_MINHA_PRESENCA;
	}

	@RequestMapping(value = "/minha-presenca", method = RequestMethod.POST)
	public String presenteNPI(HttpSession session, @RequestParam("cpf") String cpf, @RequestParam("senha") String senha, Model model) {
		ShaPasswordEncoder encoder = new ShaPasswordEncoder(256);
		Estagiario estagiario = estagiarioService.getEstagiarioPesssoa(cpf, encoder.encodePassword(senha, ""));


		if(estagiario == null){
			model.addAttribute("estagiario", estagiarioService.getEstagiarioByPessoaId(getUsuarioLogado(session).getId()));
			model.addAttribute("error", "Usuario e/ou senha inválidos");
			model.addAttribute("liberarPresenca", true);
			return "estagiario/minha-presenca";
		}

		Set<LocalDate> dataDosFeriados = new HashSet<LocalDate>();
		for (Folga folga : estagiario.getTurma().getPeriodo().getFolgas()) {
			dataDosFeriados.add(new LocalDate(folga.getData()));
		}
		
		HolidayCalendar<LocalDate> calendarioDeFeriados = new DefaultHolidayCalendar<LocalDate>(dataDosFeriados);
		LocalDateKitCalculatorsFactory.getDefaultInstance().registerHolidays("NPI", calendarioDeFeriados);
		DateCalculator<LocalDate> calendario = LocalDateKitCalculatorsFactory.getDefaultInstance().getDateCalculator("NPI", HolidayHandlerType.FORWARD);

		boolean horarioDeTrabalho = UtilGestao.isHoraPermitida(estagiario.getTurma().getHorarios());
		boolean diaDeTrabalho = UtilGestao.isDiaTrabaho();
		
		LocalDate dia = new LocalDate();
		if( (!calendario.isNonWorkingDay(dia) ) && ( diaDeTrabalho && horarioDeTrabalho ))
		{
			Frequencia frequencia = new Frequencia();

			frequencia.setData(dia.toDate());
			frequencia.setEstagiario(estagiario);
			frequencia.setTurma(estagiario.getTurma());
			frequencia.setTipoFrequencia(TipoFrequencia.NORMAL);
			frequencia.setStatusFrequencia(StatusFrequencia.PRESENTE);

			frequenciaService.update(frequencia);
		}	

		return REDIRECT_MINHA_PRESENCA;
	}

	private Pessoa getUsuarioLogado(HttpSession session) {
		if (session.getAttribute(USUARIO_LOGADO) == null) {
			Pessoa pessoa = pessoaService.getPessoaByCPF(SecurityContextHolder.getContext().getAuthentication().getName());
			session.setAttribute(USUARIO_LOGADO, pessoa);
		}
		return (Pessoa) session.getAttribute(USUARIO_LOGADO);
	}	
	

}

