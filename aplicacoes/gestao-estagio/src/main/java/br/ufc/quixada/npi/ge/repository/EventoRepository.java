package br.ufc.quixada.npi.ge.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufc.quixada.npi.ge.model.Evento;
import br.ufc.quixada.npi.ge.model.Turma;

public interface EventoRepository extends JpaRepository<Evento, Long> {
	
	
	List<Evento> findByTurmaLike(Turma id);
	
}
