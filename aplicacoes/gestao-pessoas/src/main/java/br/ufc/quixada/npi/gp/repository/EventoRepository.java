package br.ufc.quixada.npi.gp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufc.quixada.npi.gp.model.Evento;

public interface EventoRepository extends JpaRepository<Evento, Long> {
}
