package br.ufc.quixada.npi.gp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.ufc.quixada.npi.gp.model.Estagio;


public interface EstagioRepository extends JpaRepository<Estagio, Long> {
	
	@Query("select e from Estagio e where e.id = :idEstagio")
	Estagio findById(@Param("idEstagio") Long idEstagio);

	Estagio findByIdAndEstagiario_Pessoa_Cpf(Long idEstagio, String cpf);
	
	@Query("select e from Estagio e where e.estagiario.pessoa.cpf = :cpf")
	List<Estagio> findByEstagiarioCpf(@Param("cpf") String cpf);
	

}
