package br.ufc.quixada.npi.gp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.ufc.quixada.npi.gp.model.Papel;

public interface PapelRepository extends JpaRepository<Papel, Long> {

	@Query("select p from Papel p where p.nome = :nome")
	Papel findByNome(@Param("nome") String nome);

}
