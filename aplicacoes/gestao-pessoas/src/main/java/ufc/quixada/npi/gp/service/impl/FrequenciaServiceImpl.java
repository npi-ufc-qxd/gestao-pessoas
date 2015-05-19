package ufc.quixada.npi.gp.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import net.objectlab.kit.datecalc.common.DateCalculator;
import net.objectlab.kit.datecalc.common.DefaultHolidayCalendar;
import net.objectlab.kit.datecalc.common.HolidayCalendar;
import net.objectlab.kit.datecalc.common.HolidayHandlerType;
import net.objectlab.kit.datecalc.joda.LocalDateKitCalculatorsFactory;

import org.joda.time.LocalDate;
import org.springframework.transaction.annotation.Transactional;

import ufc.quixada.npi.gp.model.Estagiario;
import ufc.quixada.npi.gp.model.Folga;
import ufc.quixada.npi.gp.model.Frequencia;
import ufc.quixada.npi.gp.model.Turma;
import ufc.quixada.npi.gp.model.enums.StatusFrequencia;
import ufc.quixada.npi.gp.repository.FrequenciaRepository;
import ufc.quixada.npi.gp.service.DadoConsolidado;
import ufc.quixada.npi.gp.service.FrequenciaService;
import ufc.quixada.npi.gp.utils.UtilGestao;
import br.ufc.quixada.npi.enumeration.QueryType;
import br.ufc.quixada.npi.repository.GenericRepository;
import br.ufc.quixada.npi.service.impl.GenericServiceImpl;

@Named
public class FrequenciaServiceImpl extends GenericServiceImpl<Frequencia>
		implements FrequenciaService {

	@Inject
	FrequenciaRepository frequenciaRepository;

	@Inject
	GenericRepository<Object> objetoRepository;

	@Transactional
	public Frequencia getFrequencia() {
		List<Frequencia> frequencia = frequenciaRepository.find(QueryType.JPQL,
				"select f from Frequencia f where f.data = CURRENT_DATE", null);

		return frequencia.get(0);
	}

	@Transactional
	public List<Frequencia> getFrequencias(Date data, Turma turma) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("data", data);
		params.put("turma", turma.getId());
		List<Frequencia> frequencias = frequenciaRepository
				.find(QueryType.JPQL,
						"select f.id, f.estagiario.nomeCompleto, f.observacao, f.statusFrequencia, f.tipoFrequencia from Frequencia f join f.turma t where t.id = :turma and f.data = :data",
						params);

		// SELECT e, p.name FROM Country c LEFT OUTER JOIN c.capital p

		List<Object> frequenciass = objetoRepository
				.find(QueryType.NATIVE,
						"select f.id, e.nomeCompleto, f.observacao, f.statusFrequencia, f.tipoFrequencia  "
								+ "from estagiario e left join frequencia f "
								+ "on e.turma_id = f.turma_id and e.id = f.estagiario_id and f.data = :data "
								+ "WHERE e.turma_id = :turma "
								+ "group by f.id, e.nomeCompleto, f.observacao, f.statusFrequencia "
								+ "order by e.nomeCompleto asc " + "", params);
		// select e , f
		// from estagiario e left join frequencia f
		// on e.turma_id = f.turma_id and e.id = f.estagiario_id and f.data =
		// '2015-03-04'
		// WHERE e.turma_id = 1
		// group by e.*, f.*, f.id
		// order by f.id desc;

		// List<Object> estagiarios2 =
		// estagiarioRepository.find(QueryType.NATIVE,
		// "select f.id, e.nomeCompleto, f.observacao, f.status
		// + "from estagiario e left join frequencia f "
		// + "on e.turma_id = f.turma_id and e.id = f.estagiario_id "
		// + "WHERE e.turma_id = 1 and f.statusfrequencia = 'ATRASADO' "
		// + "group by f.estagiario_id, e.nomeCompleto, e.matricula "
		// + "having count(statusfrequencia) > 1", params);

		return frequencias;
	}

	@Transactional
	public List<Frequencia> getFrequenciaTurma(Turma turma) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("turma", turma.getId());
		List<Frequencia> frequencias = frequenciaRepository
				.find(QueryType.JPQL,
						"select distinct f from Frequencia f join f.turma t where t.id = :turma",
						params);
		return frequencias;
	}

	// @Scheduled(cron = "0 0 23 ? * MON,TUE,WED,THU,FRI *")
	@Transactional
	public void atualizarStatus() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", StatusFrequencia.AGUARDO);
		params.put("statusAtualizado", StatusFrequencia.FALTA);
		frequenciaRepository
				.updateStatus(
						"update Frequencia f set statusFrequencia ='FALTA' where f.data = CURRENT_DATE and f.statusFrequencia = 'AGUARDO' ",
						params);
	}

	@Override
	public Frequencia getFrequenciaDeHojeByEstagiario(Long id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		Frequencia frequencia = frequenciaRepository
				.findFirst(
						QueryType.JPQL,
						"select f from Frequencia f where f.data = CURRENT_DATE and f.estagiario.id = :id",
						params, 0);
		return frequencia;
	}

	@Override
	public List<Object> getFrequenciaRepor() {
		Map<String, Object> params = new HashMap<String, Object>();

		List<Frequencia> frequencias = null;

		List<Object> estagiarios = objetoRepository
				.find(QueryType.NATIVE,
						"select e.matricula, e.nomeCompleto ,count(f.statusfrequencia) "
								+ "from estagiario e join frequencia f "
								+ "on e.turma_id = f.turma_id and e.id = f.estagiario_id "
								+ "WHERE e.turma_id = 1 and f.statusfrequencia = 'ATRASADO' "
								+ "group by f.estagiario_id, e.nomeCompleto, e.matricula "
								+ "having count(statusfrequencia) > 1", params);

		return estagiarios;
	}

	@Override
	public List<Object> getReposicaoAtraso(Long idTurma) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("turma", idTurma);

		List<Object> estagiarios = objetoRepository
				.find(QueryType.NATIVE,
						"SELECT e.matricula, e.nomeCompleto, COUNT(f.statusfrequencia), e.id "
								+ "FROM estagiario e JOIN frequencia f "
								+ "ON e.turma_id = f.turma_id AND e.id = f.estagiario_id "
								+ "WHERE e.turma_id = :turma AND f.statusfrequencia = 'ATRASADO' "
								+ "GROUP BY  f.estagiario_id, e.nomeCompleto, e.matricula, e.id "
								+ "HAVING COUNT(statusfrequencia) > 1 "
								+ "ORDER BY f.estagiario_id", params);

		return estagiarios;
	}

	@Override
	public List<Object> getReposicaoFalta(Long idTurma) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("turma", idTurma);

		List<Object> estagiarios = objetoRepository
				.find(QueryType.NATIVE,
						"SELECT e.matricula, e.nomeCompleto, COUNT(f.statusfrequencia), e.id "
								+ "FROM estagiario e JOIN frequencia f "
								+ "ON e.turma_id = f.turma_id AND e.id = f.estagiario_id "
								+ "WHERE e.turma_id = :turma AND f.statusfrequencia = 'FALTA' "
								+ "GROUP BY  f.estagiario_id, e.nomeCompleto, e.matricula, e.id "
								+ "ORDER BY f.estagiario_id", params);

		return estagiarios;
	}

	@Override
	public List<Frequencia> getFrequenciaStatus(Long estagiario,
			StatusFrequencia statusFrequencia, int limite) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("estagiario", estagiario);
		params.put("status", statusFrequencia);
		List<Frequencia> frequencias = frequenciaRepository
				.find(QueryType.JPQL,
						"select f from Frequencia f where f.estagiario.id = :estagiario and f.statusFrequencia = :status ORDER BY data ASC",
						params, 0, limite);

		return frequencias;
	}

	@Override
	public List<Object> getFrequenciass(Date data, Turma turma) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("data", data);
		params.put("turma", turma.getId());

		List<Object> frequenciass = objetoRepository
				.find(QueryType.NATIVE,
						"select f.id, e.nomeCompleto, f.observacao, f.statusFrequencia, f.tipoFrequencia  "
								+ "from estagiario e left join frequencia f "
								+ "on e.turma_id = f.turma_id and e.id = f.estagiario_id and f.data = :data "
								+ "WHERE e.turma_id = :turma "
								+ "group by f.id, e.nomeCompleto, f.observacao, f.statusFrequencia "
								+ "order by e.nomeCompleto asc " + "", params);

		return frequenciass;
	}

	@Override
	public List<Frequencia> getFrequenciaByEstagiario(Long id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		List<Frequencia> frequencia = frequenciaRepository
				.find(QueryType.JPQL,
						"select f from Frequencia f where f.estagiario.id = :id ORDER BY data ASC",
						params);
		return frequencia;
	}

	public DadoConsolidado calcularDadosConsolidados(List<Frequencia> frequencia) {
		int faltas = frequenciaFalta(frequencia);
		int diasTrabalhados = frequenciaDiasTrabalhados(frequencia);
		double porcentagemFrequencia = frequenciaPorcentagem(diasTrabalhados,
				faltas);
		DadoConsolidado dadosConsolidados = new DadoConsolidado(faltas,
				diasTrabalhados, porcentagemFrequencia);
		return dadosConsolidados;
	}

	private int frequenciaFalta(List<Frequencia> frequencia) {
		int faltas = 0;
		for (Frequencia frequenciaFaltas : frequencia)
			if (frequenciaFaltas.getStatusFrequencia().equals(
					StatusFrequencia.FALTA))
				faltas++;

		return faltas;
	}

	private int frequenciaDiasTrabalhados(List<Frequencia> frequencia) {
		int diasTrabalhados = 0;
		for (Frequencia frequenciaFaltas : frequencia)
			if (frequenciaFaltas.getStatusFrequencia().equals(
					StatusFrequencia.PRESENTE))
				diasTrabalhados++;

		return diasTrabalhados;
	}

	private int frequenciaPorcentagem(int diasTrabalhados, int faltas) {
		return (diasTrabalhados * 100) / (diasTrabalhados + faltas);
	}

	@Override
	public boolean frequenciaAvaliable(Frequencia frequencia,
			Estagiario estagiario) {
		Set<LocalDate> dataDosFeriados = new HashSet<LocalDate>();
		for (Folga folga : estagiario.getTurma().getPeriodo().getFolgas()) {
			dataDosFeriados.add(new LocalDate(folga.getData()));
		}

		HolidayCalendar<LocalDate> calendarioDeFeriados = new DefaultHolidayCalendar<LocalDate>(
				dataDosFeriados);
		LocalDateKitCalculatorsFactory.getDefaultInstance().registerHolidays(
				"NPI", calendarioDeFeriados);
		DateCalculator<LocalDate> calendarioDeFeriadosNPI = LocalDateKitCalculatorsFactory
				.getDefaultInstance().getDateCalculator("NPI",
						HolidayHandlerType.FORWARD);
		// boolean horarioDeTrabalho =
		// UtilGestao.isHoraPermitida(estagiario.getTurma().getHorarios());
		// boolean diaDeTrabalho = UtilGestao.isDiaTrabaho();
		LocalDate dia = new LocalDate();

		if (!calendarioDeFeriadosNPI.isNonWorkingDay(dia)) {
			// if(horarioDeTrabalho && diaDeTrabalho)
			if (frequencia.getStatusFrequencia().equals(StatusFrequencia.AGUARDO)) {
				return true;
			}
		}
		return false;
	}
}
