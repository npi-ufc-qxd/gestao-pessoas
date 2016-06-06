package br.ufc.quixada.npi.gp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufc.quixada.npi.gp.model.Estagiario;

public interface FrequenciaRepository extends JpaRepository<Estagiario, Long> {

//	void updateStatus(String queryName, Map<String, Object> namedParams);
//	
//	Frequencia findFrequenciaByDataByTurmaByEstagiario(Date data, Long turma, Long estagiario);

	/**
	 * impl
	 */
//	@Transactional	
//	public void updateStatus(String queryName, Map<String, Object> namedParams) {
//		Query query = super.em.createNativeQuery(queryName);//native
//		query.executeUpdate();
//	}
//
//	@Override
//	public Frequencia findFrequenciaByDataByTurmaByEstagiario(Date data, Long turma, Long estagiario) {
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("data", data);
//		params.put("turma", turma);
//		params.put("estagiario", estagiario);
//		
//		Frequencia frequencia = (Frequencia) findFirst(QueryType.JPQL, "select f from Frequencia f where f.data =:data and f.estagiario.id = :estagiario and f.turma.id = :turma", params);
//
//		return frequencia;
//	}

}
