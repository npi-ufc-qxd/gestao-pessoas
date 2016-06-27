package br.ufc.quixada.npi.ge.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufc.quixada.npi.ge.model.Expediente;

public interface ExpedienteRepository extends JpaRepository<Expediente, Long> {
}
