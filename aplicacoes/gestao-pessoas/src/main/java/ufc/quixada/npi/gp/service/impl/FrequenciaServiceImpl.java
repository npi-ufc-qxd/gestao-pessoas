package ufc.quixada.npi.gp.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
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

import ufc.quixada.npi.gp.model.Folga;
import ufc.quixada.npi.gp.model.Frequencia;
import ufc.quixada.npi.gp.model.Horario;
import ufc.quixada.npi.gp.model.Turma;
import ufc.quixada.npi.gp.model.enums.StatusFrequencia;
import ufc.quixada.npi.gp.model.enums.TipoFrequencia;
import ufc.quixada.npi.gp.repository.FrequenciaRepository;
import ufc.quixada.npi.gp.service.DadoConsolidado;
import ufc.quixada.npi.gp.service.FolgaService;
import ufc.quixada.npi.gp.service.FrequenciaService;
import ufc.quixada.npi.gp.utils.UtilGestao;
import br.ufc.quixada.npi.enumeration.QueryType;
import br.ufc.quixada.npi.service.impl.GenericServiceImpl;

@Named
public class FrequenciaServiceImpl extends GenericServiceImpl<Frequencia> implements FrequenciaService {
	
	@Inject
	FrequenciaRepository frequenciaRepository;
	
	@Inject
	private FolgaService folgaService;

//	@Transactional
//	public Frequencia getFrequencia() {
//		@SuppressWarnings("unchecked")
//		List<Frequencia> frequencia = find(QueryType.JPQL, "select f from Frequencia f where f.data = CURRENT_DATE", null);
//
//		return frequencia.get(0);
//	}

	@Transactional
	public List<Frequencia> getFrequenciasByTurmaIdAndData(Date data, Long idTurma) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("data", data);
		params.put("turma", idTurma);
		
		@SuppressWarnings("unchecked")
		List<Frequencia> frequencias = find(QueryType.JPQL, "select f.id, f.estagiario.nomeCompleto, f.observacao, f.statusFrequencia, f.tipoFrequencia from Frequencia f join f.turma t where t.id = :turma and f.data = :data", params);
		// SELECT e, p.name FROM Country c LEFT OUTER JOIN c.capital p

//		@SuppressWarnings("unused")
//		List<Object> frequenciass = find(QueryType.NATIVE,
//						"select f.id, e.nomeCompleto, f.observacao, f.statusFrequencia, f.tipoFrequencia  "
//								+ "from estagiario e left join frequencia f "
//								+ "on e.turma_id = f.turma_id and e.id = f.estagiario_id and f.data = :data "
//								+ "WHERE e.turma_id = :turma "
//								+ "group by f.id, e.nomeCompleto, f.observacao, f.statusFrequencia "
//								+ "order by e.nomeCompleto asc " + "", params);
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
		
		@SuppressWarnings("unchecked")
		List<Frequencia> frequencias = find(QueryType.JPQL, "select distinct f from Frequencia f join f.turma t where t.id = :turma", params);

		return frequencias;
	}

	// @Scheduled(cron = "0 0 23 ? * MON,TUE,WED,THU,FRI *")
	@Transactional
	public void atualizarStatus() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", StatusFrequencia.AGUARDO);
		params.put("statusAtualizado", StatusFrequencia.FALTA);
		
		frequenciaRepository.updateStatus("update Frequencia f set statusFrequencia ='FALTA' where f.data = CURRENT_DATE and f.statusFrequencia = 'AGUARDO' ", params);
	}

	@Override
	public Frequencia getFrequenciaDeHojeByEstagiarioId(Long id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		
		Frequencia frequencia = (Frequencia) findFirst(QueryType.JPQL, "select f from Frequencia f where f.data = CURRENT_DATE and f.estagiario.id = :id", params);

		return frequencia;
	}

//	@Override
//	public List<Object> getFrequenciaRepor() {
//		Map<String, Object> params = new HashMap<String, Object>();
//
//		@SuppressWarnings("unchecked")
//		List<Object> estagiarios = find(QueryType.NATIVE, 
//				"select e.matricula, e.nomeCompleto ,count(f.statusfrequencia) "
//				+ "from estagiario e join frequencia f "
//				+ "on e.turma_id = f.turma_id and e.id = f.estagiario_id "
//				+ "WHERE e.turma_id = 1 and f.statusfrequencia = 'ATRASADO' "
//				+ "group by f.estagiario_id, e.nomeCompleto, e.matricula "
//				+ "having count(statusfrequencia) > 1", params);
//
//		return estagiarios;
//	}
//
//	@Override
//	public List<Object> getReposicaoAtraso(Long idTurma) {
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("turma", idTurma);
//
//		@SuppressWarnings("unchecked")
//		List<Object> estagiarios = find(QueryType.NATIVE,
//						"SELECT e.matricula, e.nomeCompleto, COUNT(f.statusfrequencia), e.id "
//						+ "FROM estagiario e JOIN frequencia f "
//						+ "ON e.turma_id = f.turma_id AND e.id = f.estagiario_id "
//						+ "WHERE e.turma_id = :turma AND f.statusfrequencia = 'ATRASADO' "
//						+ "GROUP BY  f.estagiario_id, e.nomeCompleto, e.matricula, e.id "
//						+ "HAVING COUNT(statusfrequencia) > 1 "
//						+ "ORDER BY f.estagiario_id", params);
//
//		return estagiarios;
//	}

//	@Override
//	public List<Object> getReposicaoFalta(Long idTurma) {
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("turma", idTurma);
//
//		@SuppressWarnings("unchecked")
//		List<Object> estagiarios =  find(QueryType.NATIVE,
//						"SELECT e.matricula, e.nomeCompleto, COUNT(f.statusfrequencia), e.id "
//						+ "FROM estagiario e JOIN frequencia f "
//						+ "ON e.turma_id = f.turma_id AND e.id = f.estagiario_id "
//						+ "WHERE e.turma_id = :turma AND f.statusfrequencia = 'FALTA' "
//						+ "GROUP BY  f.estagiario_id, e.nomeCompleto, e.matricula, e.id "
//						+ "ORDER BY f.estagiario_id", params);
//
//		return estagiarios;
//	}
//
//	@Override
//	public List<Frequencia> getFrequenciaStatus(Long estagiario, StatusFrequencia statusFrequencia, int limite) {
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("estagiario", estagiario);
//		params.put("status", statusFrequencia);
//
//		@SuppressWarnings("unchecked")
//		List<Frequencia> frequencias = find(QueryType.JPQL, "select f from Frequencia f where f.estagiario.id = :estagiario and f.statusFrequencia = :status ORDER BY data ASC", params, 0, limite);
//
//		return frequencias;
//	}
//
//	@Override
//	public List<Object> getFrequenciass(Date data, Turma turma) {
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("data", data);
//		params.put("turma", turma.getId());
//
//		@SuppressWarnings("unchecked")
//		List<Object> frequencias = find(QueryType.NATIVE,
//						"select f.id, e.nomeCompleto, f.observacao, f.statusFrequencia, f.tipoFrequencia  "
//						+ "from estagiario e left join frequencia f "
//						+ "on e.turma_id = f.turma_id and e.id = f.estagiario_id and f.data = :data "
//						+ "WHERE e.turma_id = :turma "
//						+ "group by f.id, e.nomeCompleto, f.observacao, f.statusFrequencia "
//						+ "order by e.nomeCompleto asc " + "", params);
//
//		return frequencias;
//	}

	@Override
	public List<Frequencia> getFrequenciasByEstagiarioId(Long idEstagiario, Long idTurma) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("idEstagiario", idEstagiario);
		params.put("idTurma", idTurma);
		
		@SuppressWarnings("unchecked")
		List<Frequencia> frequencias = find(QueryType.JPQL, "select f from Frequencia f where f.estagiario.id = :idEstagiario and f.turma.id = :idTurma ORDER BY data ASC", params);

		return frequencias;
	}

	public DadoConsolidado calcularDadosConsolidados(List<Frequencia> frequencia) {
		int faltas = frequenciaFalta(frequencia);
		int diasTrabalhados = frequenciaDiasTrabalhados(frequencia);
		double porcentagemFrequencia = frequenciaPorcentagem(diasTrabalhados, faltas);
		DadoConsolidado dadosConsolidados = new DadoConsolidado(faltas, diasTrabalhados, porcentagemFrequencia);
		return dadosConsolidados;
	}

	private int frequenciaFalta(List<Frequencia> frequencia) {
		int faltas = 0;

		for (Frequencia frequenciaFaltas : frequencia) {
			if (frequenciaFaltas.getStatusFrequencia().equals(StatusFrequencia.FALTA)) {
				faltas++;
			}
		}

		return faltas;
	}

	private int frequenciaDiasTrabalhados(List<Frequencia> frequencia) {
		int diasTrabalhados = 0;

		for (Frequencia frequenciaFaltas : frequencia) {
			if (frequenciaFaltas.getStatusFrequencia().equals(StatusFrequencia.PRESENTE)) {
				diasTrabalhados++;
			}
		}

		return diasTrabalhados;
	}

	private int frequenciaPorcentagem(int diasTrabalhados, int faltas) {
		if(diasTrabalhados != 0) {
			return (diasTrabalhados * 100) / (diasTrabalhados + faltas);		
		}
		else {
			return 0;
		}
	}

	@Override
	public boolean liberarPreseca(Turma turma ) {
		List<Folga> folgas = folgaService.find(Folga.class);
		
		Set<LocalDate> dataDosFeriados = new HashSet<LocalDate>();

		for (Folga folga : folgas) {
			dataDosFeriados.add(new LocalDate(folga.getData()));
		}

		HolidayCalendar<LocalDate> calendarioDeFeriados = new DefaultHolidayCalendar<LocalDate>(dataDosFeriados);

		LocalDateKitCalculatorsFactory.getDefaultInstance().registerHolidays("NPI", calendarioDeFeriados);
		DateCalculator<LocalDate> calendarioDeFeriadosNPI = LocalDateKitCalculatorsFactory.getDefaultInstance().getDateCalculator("NPI", HolidayHandlerType.FORWARD);

		LocalDate dia = new LocalDate();

		if (!calendarioDeFeriadosNPI.isNonWorkingDay(dia)) {
			if(UtilGestao.hojeEDiaDeTrabahoDaTurma(turma.getHorarios()) && UtilGestao.isHoraPermitida(turma.getHorarios())){
				return true;
			}
		}

		return false;
	}
	@Override
	public List<Frequencia> gerarFrequencia(Date inicio, Date termino, List<Horario> horarios) {

		LocalDate inicioPeriodoTemporario = new LocalDate(inicio);
		LocalDate fimPeriodo = new LocalDate(termino);

		List<Folga> folgas = folgaService.getFolgasByAno(Calendar.getInstance().get(Calendar.YEAR));
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

			if (UtilGestao.isDiaDeTrabahoDaTurma(horarios, inicioPeriodoTemporario)) {
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
