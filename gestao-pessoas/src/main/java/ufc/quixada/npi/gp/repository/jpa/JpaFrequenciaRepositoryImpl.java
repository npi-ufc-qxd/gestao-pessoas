package ufc.quixada.npi.gp.repository.jpa;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Named;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

import br.ufc.quixada.npi.enumeration.QueryType;
import br.ufc.quixada.npi.repository.jpa.JpaGenericRepositoryImpl;
import ufc.quixada.npi.gp.model.Frequencia;
import ufc.quixada.npi.gp.repository.FrequenciaRepository;

@Named
public class JpaFrequenciaRepositoryImpl extends JpaGenericRepositoryImpl<Frequencia> implements FrequenciaRepository {

	@Transactional	
	public void updateStatus(String queryName, Map<String, Object> namedParams) {
		Query query = super.em.createNativeQuery(queryName);//native
		query.executeUpdate();
	}

	@Override
	public Frequencia findFrequenciaByDataByTurmaByEstagiario(Date data, Long turma, Long estagiario) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("data", data);
		params.put("turma", turma);
		params.put("estagiario", estagiario);
		
		Frequencia frequencia = (Frequencia) findFirst(QueryType.JPQL, "select f from Frequencia f where f.data =:data and f.estagiario.id = :estagiario and f.turma.id = :turma", params);

		return frequencia;
	}

}
