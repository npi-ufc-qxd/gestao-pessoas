package br.ufc.quixada.npi.ge.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.ufc.quixada.npi.ge.model.Evento;
import br.ufc.quixada.npi.ge.model.Turma.StatusTurma;

public interface EventoRepository extends JpaRepository<Evento, Long> {
	@Query("SELECT DISTINCT e FROM Evento e WHERE e.turma.id = :idTurma AND (CURRENT_DATE <= e.termino) AND e.turma.status = :statusTurma")
	List<Evento> buscarEventoPorTurma(@Param("idTurma")Long idTurma, @Param("statusTurma")StatusTurma statusTurma);
}
