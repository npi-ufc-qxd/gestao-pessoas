package br.ufc.quixada.npi.gp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufc.quixada.npi.gp.model.Servidor;

public interface ServidorRepository extends JpaRepository<Servidor, Long> {
	
	Servidor findByPessoa_Cpf(String cpf);
	
}
