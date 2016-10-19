package br.ufc.quixada.npi.ge.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.ufc.quixada.npi.ge.model.Estagio;
import br.ufc.quixada.npi.ge.model.Frequencia;

public interface FrequenciaRepository extends JpaRepository<Frequencia, Long> {
	
	@Query("select f from Frequencia f where f.estagio = :estagio and f.data = CURRENT_DATE")
	Frequencia findFrequenciaDeHojeByEstagio(@Param("estagio") Estagio estagio);
	
	@Query("select f from Frequencia f where f.estagio.turma.id = :idTurma and f.data = :data")
	List<Frequencia> findFrequenciasByDataAndTurmaId(@Param("data") Date data,  @Param("idTurma") Long idTurma);
	
	@Query("select f from Frequencia f where f.estagio.id = :idEstagio and f.data = :data ")
	Frequencia findFrequenciaByDataAndEstagioId(@Param("data") Date data,  @Param("idEstagio") Long idEstagio, @Param("horaEntrada") Date horaEntradaReposicao, @Param("horaSaida") Date horaSaidaReposicao);

	@Query("select case when count(f) > 0 then true else false end from Frequencia f where f.estagio.id = :idEstagio and f.data = :data")
	boolean existeFrequenciaByDataAndEstagioId(@Param("data") Date data,  @Param("idEstagio") Long idEstagio);

	/*@Query("select COUNT(f) from Frequencia f where f.estagio.id = :idEstagio and f.status = :status")
	int buscarTotalByStatus(@Param("idEstagio") Long idEstagio,  @Param("status") Frequencia.StatusFrequencia statusFrequencia);
*/
	@Query("select COUNT(f) from Frequencia f where f.estagio.id = :idEstagio and f.tipo = :tipo")
	int buscarTotalByTipo(@Param("idEstagio") Long idEstagio, @Param("tipo") Frequencia.TipoFrequencia tipoFrequencia);

	/*Frequencia findByIdAndTipoAndStatus(Long idEstagio, Frequencia.TipoFrequencia tipoFrequencia, Frequencia.StatusFrequencia statusFrequencia);*/

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
