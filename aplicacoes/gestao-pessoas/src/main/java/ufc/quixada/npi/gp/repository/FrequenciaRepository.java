package ufc.quixada.npi.gp.repository;

import java.util.Map;

import ufc.quixada.npi.gp.model.Frequencia;

public interface FrequenciaRepository extends GenericRepository<Frequencia> {

	void updateStatus(String queryName, Map<String, Object> namedParams);

}
