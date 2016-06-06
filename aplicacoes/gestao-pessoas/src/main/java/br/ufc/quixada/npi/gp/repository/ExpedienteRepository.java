package br.ufc.quixada.npi.gp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.ufc.quixada.npi.gp.model.Expediente;
import br.ufc.quixada.npi.gp.model.Turma;

public interface ExpedienteRepository extends JpaRepository<Expediente, Long> {
	
	@Query("select e from Expediente e where e.turma = :turma")
	Expediente findExpedienteByTurma(@Param("turma") Turma turma); 

}
