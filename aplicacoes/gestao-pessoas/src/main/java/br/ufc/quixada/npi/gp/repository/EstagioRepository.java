package br.ufc.quixada.npi.gp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.ufc.quixada.npi.gp.model.Estagio;

public interface EstagioRepository extends JpaRepository<Estagio, Long> {
	
	@Query("select e from Estagio e join e.estagiario estagiario where e.id = :idEstagio and estagiario.pessoa.cpf = :cpf") 
	Estagio buscarEstagioPorIdEEstagiarioCpf(@Param("idEstagio") Long idEstagio, @Param("cpf") String cpf);
	
}
