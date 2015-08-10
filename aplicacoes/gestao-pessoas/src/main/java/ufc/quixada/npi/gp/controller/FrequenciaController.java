package ufc.quixada.npi.gp.controller;

import static ufc.quixada.npi.gp.utils.Constants.MENSAGEM_DATA_FUTURA;
import static ufc.quixada.npi.gp.utils.Constants.PAGINA_LISTAR_FREQUENCIAS;
import static ufc.quixada.npi.gp.utils.Constants.PAGINA_REPOSICAO;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ufc.quixada.npi.gp.model.Frequencia;
import ufc.quixada.npi.gp.model.ReposicaoJson;
import ufc.quixada.npi.gp.model.enums.StatusFrequencia;
import ufc.quixada.npi.gp.model.enums.TipoFrequencia;
import ufc.quixada.npi.gp.service.EstagiarioService;
import ufc.quixada.npi.gp.service.FrequenciaService;
import ufc.quixada.npi.gp.service.PessoaService;
import ufc.quixada.npi.gp.service.TurmaService;

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
		return PAGINA_LISTAR_FREQUENCIAS;
	}

//	@RequestMapping(value = "/frequencias.json", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
//	@ResponseBody
//	public List<Frequencia> getFrequencias(@RequestBody FiltroJson frequenciaJson, Model model) {
//		List<Frequencia> frequencias = frequenciaService.getFrequencias(frequenciaJson.getData(), turmaService.find(Turma.class, frequenciaJson.getTurma()));
//		return frequencias;
//	}

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



}

