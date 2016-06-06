package br.ufc.quixada.npi.gp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.ufc.quixada.npi.gp.model.Estagio;


public interface EstagioRepository extends JpaRepository<Estagio, Long> {
	
	@Query("select e from Estagio e where e.id = :idEstagio")
	Estagio findEstagioByEstagioId(@Param("idEstagio") Long idEstagio);
}
