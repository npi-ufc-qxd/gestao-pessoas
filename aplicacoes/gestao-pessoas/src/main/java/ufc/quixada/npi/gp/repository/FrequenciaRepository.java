package ufc.quixada.npi.gp.repository;

import java.util.Date;
import java.util.Map;

import br.ufc.quixada.npi.repository.GenericRepository;
import ufc.quixada.npi.gp.model.Frequencia;

public interface FrequenciaRepository extends GenericRepository<Frequencia> {

	void updateStatus(String queryName, Map<String, Object> namedParams);
	
	Frequencia findFrequenciaByDataByTurmaByEstagiario(Date data, Long turma, Long estagiario);

}
