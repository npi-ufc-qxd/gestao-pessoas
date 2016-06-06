package br.ufc.quixada.npi.gp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.ufc.quixada.npi.gp.model.Estagiario;

public interface EstagiarioRepository extends JpaRepository<Estagiario, Long> {

	@Query("select e from Estagiario e where e.pessoa.cpf = :cpf")
	Estagiario findByPessoaByCpf(@Param("cpf") String cpf);

}
