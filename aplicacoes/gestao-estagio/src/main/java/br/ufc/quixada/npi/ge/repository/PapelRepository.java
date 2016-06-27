package br.ufc.quixada.npi.ge.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.ufc.quixada.npi.ge.model.Papel;

public interface PapelRepository extends JpaRepository<Papel, Long> {

	@Query("select p from Papel p where p.nome = :nome")
	Papel findByNome(@Param("nome") String nome);

	@Query("select p.papeis FROM Pessoa p WHERE p.cpf = :cpf")
	List<Papel> findByCpf(@Param("cpf") String cpf);

}
