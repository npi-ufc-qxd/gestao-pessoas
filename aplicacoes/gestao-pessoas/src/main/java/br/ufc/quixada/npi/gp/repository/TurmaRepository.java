package br.ufc.quixada.npi.gp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import br.ufc.quixada.npi.gp.model.Turma;

public interface TurmaRepository extends JpaRepository<Turma, Long> {

	@Query("select t from Turma t where t.id = :idTurma")
	Turma findTurmaById(@Param("idTurma") Long idTurma);
	
	@Query("select t from Turma t join t.estagios e where e.id = :idEstagio")
	Turma findTurmaByEstagioId(@Param("idEstagio") Long idEstagio);
}
