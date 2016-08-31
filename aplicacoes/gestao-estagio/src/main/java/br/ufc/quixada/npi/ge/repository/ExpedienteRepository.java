package br.ufc.quixada.npi.ge.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.ufc.quixada.npi.ge.model.Expediente;

public interface ExpedienteRepository extends JpaRepository<Expediente, Long> {
	
	@Query("select t from Turma t join t.expedientes exp where t.id = :idTurma and exp.diaSemana = :diaSemana and exp.horaInicio = :horaInicio and exp.horaTermino = :horaTermino")
	Expediente findExpedienteByTurmaId(@Param("idTurma") Long idTurma, @Param("diaSemana") Expediente.DiaDaSemana diaSemana, @Param("horaInicio") Date horaInicio, @Param("horaTermino") Date horaTermino);
}
