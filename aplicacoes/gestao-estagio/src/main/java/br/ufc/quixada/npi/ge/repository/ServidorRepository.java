package br.ufc.quixada.npi.ge.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufc.quixada.npi.ge.model.Servidor;

public interface ServidorRepository extends JpaRepository<Servidor, Long> {
	
	Servidor findByPessoa_Cpf(String cpf);
	
}
