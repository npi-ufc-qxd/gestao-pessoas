package br.ufc.quixada.npi.ge.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.ufc.quixada.npi.ge.model.Turma;
import br.ufc.quixada.npi.ge.model.Turma.TipoTurma;

public interface TurmaRepository extends JpaRepository<Turma, Long> {

	@Query("SELECT DISTINCT t from Turma t WHERE t.orientador.id = :idServidor OR :idServidor MEMBER OF t.supervisores")
	List<Turma> findByOrientador_IdOrSupervisores_Id(@Param("idServidor") Long idServidor);

	@Query("SELECT DISTINCT t from Turma t WHERE t.id = :idTurma AND (t.orientador.id = :idServidor OR :idServidor MEMBER OF t.supervisores)")
	Turma findByIdAndServidor(@Param("idTurma") Long idTurma, @Param("idServidor") Long idServidor);
	
	@Query("select DISTINCT t from Turma t where (t.orientador.id = :idServidor or :idServidor MEMBER OF t.supervisores) and t.status = 'ABERTA' and CURRENT_DATE > t.termino")
	List<Turma> findByServidorIdAndStatusAndTermino(@Param("idServidor") Long idServidor);
	
	@Query("select DISTINCT t from Turma t where t.tipoTurma = :tipoTurma and (t.orientador.id = :idServidor or :idServidor MEMBER OF t.supervisores) order by t.semestre desc")
	List<Turma> findByTipoTurma(@Param("tipoTurma") TipoTurma tipoTurma, @Param("idServidor") Long idServidor);
	
}
