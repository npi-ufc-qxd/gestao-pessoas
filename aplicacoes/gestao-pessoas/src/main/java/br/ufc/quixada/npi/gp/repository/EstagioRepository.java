package br.ufc.quixada.npi.gp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.ufc.quixada.npi.gp.model.Estagio;

public interface EstagioRepository extends JpaRepository<Estagio, Long> {

	Estagio findByIdAndEstagiario_Pessoa_Cpf(Long idEstagio, String cpf);
	
}
