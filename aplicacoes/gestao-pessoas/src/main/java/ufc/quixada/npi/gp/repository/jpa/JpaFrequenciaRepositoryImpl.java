package ufc.quixada.npi.gp.repository.jpa;

import java.util.Map;

import javax.inject.Named;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

import ufc.quixada.npi.gp.model.Frequencia;
import ufc.quixada.npi.gp.repository.FrequenciaRepository;

@Named
public class JpaFrequenciaRepositoryImpl extends JpaGenericRepositoryImpl<Frequencia> implements FrequenciaRepository {

	@Transactional	
	public void updateStatus(String queryName, Map<String, Object> namedParams) {
		Query query = super.em.createNativeQuery(queryName);//native
		query.executeUpdate();
	}

}
