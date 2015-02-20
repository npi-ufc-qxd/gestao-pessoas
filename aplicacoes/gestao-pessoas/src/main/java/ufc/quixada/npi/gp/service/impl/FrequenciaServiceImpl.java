package ufc.quixada.npi.gp.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.transaction.annotation.Transactional;

import ufc.quixada.npi.gp.model.Estagiario;
import ufc.quixada.npi.gp.model.Frequencia;
import ufc.quixada.npi.gp.model.Turma;
import ufc.quixada.npi.gp.model.enums.StatusFrequencia;
import ufc.quixada.npi.gp.repository.FrequenciaRepository;
import ufc.quixada.npi.gp.service.FrequenciaService;
import br.ufc.quixada.npi.enumeration.QueryType;
import br.ufc.quixada.npi.repository.GenericRepository;
import br.ufc.quixada.npi.service.impl.GenericServiceImpl;

@Named
public class FrequenciaServiceImpl extends GenericServiceImpl<Frequencia> implements FrequenciaService {

	@Inject
	FrequenciaRepository frequenciaRepository;
	
	@Inject
	GenericRepository<Object> estagiarioRepository;

	@Transactional
	public Frequencia getFrequencia() {
		List<Frequencia> frequencia = frequenciaRepository.find(QueryType.JPQL, "select f from Frequencia f where f.data = CURRENT_DATE", null);
		return frequencia.get(0);
	}
	
	@Transactional
	public List<Frequencia> getFrequencias(Date data, Turma turma) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("data", data);
		params.put("turma", turma.getId());
		List<Frequencia> frequencias = frequenciaRepository.find(QueryType.JPQL,"select f.id, f.observacao, f.statusFrequencia, f.tipoFrequencia, f.estagiario.nomeCompleto from Frequencia f join f.turma t where t.id = :turma and f.data = :data", params);
		return frequencias;
	}

	@Transactional
	public List<Frequencia> getFrequenciaTurma(Turma turma) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("turma", turma.getId());
		List<Frequencia> frequencias = frequenciaRepository.find(QueryType.JPQL,"select distinct f from Frequencia f join f.turma t where t.id = :turma", params);
		return frequencias;
	}

	//@Scheduled(cron = "0 0 23 ? * MON,TUE,WED,THU,FRI *")
	@Transactional
	public void atualizarStatus() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", StatusFrequencia.AGUARDO);
		params.put("statusAtualizado", StatusFrequencia.FALTA);
		frequenciaRepository.updateStatus("update Frequencia f set statusFrequencia ='FALTA' where f.data = CURRENT_DATE and f.statusFrequencia = 'AGUARDO' ", params);
	}

	@Override
	public Frequencia getFrequenciaDeHojeByEstagiario(Long id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		Frequencia frequencia = frequenciaRepository.findFirst(QueryType.JPQL, "select f from Frequencia f where f.data = CURRENT_DATE and f.estagiario.id = :id", params, 0);
		return frequencia;
	}

	@Override
	public List<Object> getFrequenciaRepor() {
		// SELECT f, COUNT(statusFrequencia) FROM Frequencia f where statusFrequencia = :statusFrequencia GROUP BY statusFrequencia HAVING COUNT(statusFrequencia)>1
		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("status", StatusFrequencia.ATRASADO);
//		Long turma = new Long(1);
//		params.put("turma", turma);
//		List<Frequencia> frequencias = frequenciaRepository.find(QueryType.JPQL,"select count(f.statusFrequencia) from Frequencia f join f.turma t where t.id = :turma and f.statusFrequencia = :status group by f.id, f.statusFrequencia having count(f.statusFrequencia) > 1", params);

		List<Frequencia> frequencias = null;
//		frequencias = frequenciaRepository.find(QueryType.JPQL,
//				"select e from Estagiario e join e.frequencias f where f.statusFrequencia = :status", params);

		List<Object> estagiarios = estagiarioRepository.find(QueryType.NATIVE,
				"select e.matricula, e.nomeCompleto ,count(f.statusfrequencia) "
				+ "from estagiario e join frequencia f "
				+ "on e.turma_id = f.turma_id and e.id = f.estagiario_id "
				+ "WHERE e.turma_id = 1 and f.statusfrequencia = 'ATRASADO' "
				+ "group by f.estagiario_id, e.nomeCompleto, e.matricula "
				+ "having count(statusfrequencia) > 1", params);
//		"select e, count(statusFrequencia) from Estagiario e join Frequencia f on e.turma.id=f.turma.id and e.id=f.estagiario.id WHERE e.turma.id = :turma and tatusFrequencia = :status group by  f.estagiario.id, e having count(statusFrequencia) > 1", params);
//		
				estagiarios.toArray();
//		"select e, count(f.statusFrequencia) from Frequencia f join f.estagiario e where f.turma.id = :turma and f.statusFrequencia = :status group by e.id having count(f.statusFrequencia) > 1", params);
//		"select e, count(f.statusFrequencia) from Estagiario e join e.frequencias f
		
		//from Frequencia f join Es where f.turma.id = :turma and f.statusFrequencia = :status group by f.estagiario having count(f.statusFrequencia) > 1", params);
//		 select e from Estagiario e join e.frequencias f
		/*
		 * select f, count(f.statusFrequencia) from Frequencia f where f.turma.id = :turma and f.statusFrequencia = :status group by f.estagiario.id having count(f.statusFrequencia) > 1;*/
		return estagiarios;
	}

	@Override
	public List<Object> getReposicaoAtraso(Long idTurma) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("turma", idTurma);

		List<Object> estagiarios = estagiarioRepository.find(QueryType.NATIVE,
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

		List<Object> estagiarios = estagiarioRepository.find(QueryType.NATIVE,
				"SELECT e.matricula, e.nomeCompleto, COUNT(f.statusfrequencia), e.id "
				+ "FROM estagiario e JOIN frequencia f "
				+ "ON e.turma_id = f.turma_id AND e.id = f.estagiario_id "
				+ "WHERE e.turma_id = :turma AND f.statusfrequencia = 'FALTA' "
				+ "GROUP BY  f.estagiario_id, e.nomeCompleto, e.matricula, e.id "
				+ "ORDER BY f.estagiario_id", params);

		return estagiarios;
	}

	@Override
	public List<Frequencia> getFrequenciaStatus(Long turma, Long estagiario, StatusFrequencia statusFrequencia, int limite) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("turma", turma);
		params.put("estagiario", estagiario);
		params.put("status", statusFrequencia);
//		params.put("limite", limite);

//		List<Frequencia> frequencias = frequenciaRepository.find(QueryType.JPQL,"select f from Frequencia f where f.turma.id = :turma and f.estagiario.id = :estagiario and f.statusFrequencia = :status LIMIT :limite", params);
		List<Frequencia> frequencias = frequenciaRepository.find(QueryType.JPQL,"select f from Frequencia f where f.turma.id = :turma and f.estagiario.id = :estagiario and f.statusFrequencia = :status ORDER BY data ASC", params, 0, limite);
		
		return frequencias;
	}


}
