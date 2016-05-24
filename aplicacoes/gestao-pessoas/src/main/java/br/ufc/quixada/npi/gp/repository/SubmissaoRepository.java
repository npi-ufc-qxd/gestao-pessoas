package br.ufc.quixada.npi.gp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufc.quixada.npi.gp.model.Submissao;

public interface SubmissaoRepository extends JpaRepository<Submissao, Long> {
}
