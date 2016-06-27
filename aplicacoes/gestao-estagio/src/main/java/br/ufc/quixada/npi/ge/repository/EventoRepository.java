package br.ufc.quixada.npi.ge.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufc.quixada.npi.ge.model.Evento;

public interface EventoRepository extends JpaRepository<Evento, Long> {
}
