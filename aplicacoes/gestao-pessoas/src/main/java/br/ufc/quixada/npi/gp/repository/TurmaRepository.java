package br.ufc.quixada.npi.gp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import br.ufc.quixada.npi.gp.model.Turma;

public interface TurmaRepository extends JpaRepository<Turma, Long> {

	@Query("select t from Turma t where t.id = :idTurma")
	Turma findTurmaById(@Param("idTurma") Long idTurma);
	
	List<Turma> findByorientador_id(Long idServidor);
}
