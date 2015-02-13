package ufc.quixada.npi.gp.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.transaction.annotation.Transactional;

import ufc.quixada.npi.gp.model.Frequencia;
import ufc.quixada.npi.gp.model.Turma;
import ufc.quixada.npi.gp.model.enums.StatusFrequencia;
import ufc.quixada.npi.gp.repository.FrequenciaRepository;
import ufc.quixada.npi.gp.service.FrequenciaService;
import br.ufc.quixada.npi.enumeration.QueryType;
import br.ufc.quixada.npi.service.impl.GenericServiceImpl;

@Named
public class FrequenciaServiceImpl extends GenericServiceImpl<Frequencia> implements FrequenciaService {

	@Inject
	FrequenciaRepository frequenciaRepository;

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
	public List<Frequencia> getFrequenciaRepor() {
		// SELECT f, COUNT(statusFrequencia) FROM Frequencia f where statusFrequencia = :statusFrequencia GROUP BY statusFrequencia HAVING COUNT(statusFrequencia)>1
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", StatusFrequencia.ATRASADO);
		params.put("turma", 1L);
//		List<Frequencia> frequencias = frequenciaRepository.find(QueryType.JPQL,"select count(f.statusFrequencia) from Frequencia f join f.turma t where t.id = :turma and f.statusFrequencia = :status group by f.id, f.statusFrequencia having count(f.statusFrequencia) > 1", params);

		List<Frequencia> frequencias = frequenciaRepository.find(QueryType.JPQL,
				"select f.estagiario.id, f.estagiario, count(f.statusFrequencia) from Frequencia f where f.turma.id = :turma and f.statusFrequencia = :status group by f.estagiario.id, f.estagiario having count(f.statusFrequencia) > 1", params);
		/*
		 * select f, count(f.statusFrequencia) from Frequencia f where f.turma.id = :turma and f.statusFrequencia = :status group by f having count(f.statusFrequencia) > 1;*/
		return frequencias;
	}


}
