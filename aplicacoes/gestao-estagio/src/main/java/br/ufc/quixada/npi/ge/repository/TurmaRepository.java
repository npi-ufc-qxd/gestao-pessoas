package br.ufc.quixada.npi.ge.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.ufc.quixada.npi.ge.model.Turma;

public interface TurmaRepository extends JpaRepository<Turma, Long> {

	@Query("select t from Turma t where t.id = :idTurma")
	Turma findTurmaById(@Param("idTurma") Long idTurma);

	@Query("SELECT DISTINCT t from Turma t WHERE t.orientador.id = :idServidor OR :idServidor MEMBER OF t.supervisores")
	List<Turma> findByOrientador_IdOrSupervisores_Id(@Param("idServidor") Long idServidor);

	@Query("SELECT DISTINCT t from Turma t WHERE t.id = :idTurma AND (t.orientador.id = :idServidor OR :idServidor MEMBER OF t.supervisores)")
	Turma findByIdAndServidor(@Param("idTurma") Long idTurma, @Param("idServidor") Long idServidor);
	
	@Query("select t from Turma t where t.orientador.id = :idServidor and t.status = 'ABERTA' and CURRENT_DATE > t.termino")
	List<Turma> findByServidor_IdAndStatusAndTermino(@Param("idServidor") Long idServidor);

}
