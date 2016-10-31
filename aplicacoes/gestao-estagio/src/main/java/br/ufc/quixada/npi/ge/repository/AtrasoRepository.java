package br.ufc.quixada.npi.ge.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufc.quixada.npi.ge.model.Atraso;

public interface AtrasoRepository extends JpaRepository<Atraso, Long> {
}
