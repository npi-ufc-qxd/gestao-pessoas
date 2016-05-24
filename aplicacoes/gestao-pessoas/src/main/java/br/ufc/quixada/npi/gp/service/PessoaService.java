package br.ufc.quixada.npi.gp.service;

import java.util.List;

import br.ufc.quixada.npi.gp.model.Estagiario;
import br.ufc.quixada.npi.gp.model.Papel;
import br.ufc.quixada.npi.gp.model.Pessoa;
import br.ufc.quixada.npi.gp.model.Servidor;

public interface PessoaService {

	Papel buscarPapelPorNome(String string);

	Pessoa buscarPessoaPorCpf(String cpf);

	void adicionarPessoa(Pessoa pessoa);

	void adicionarServidor(Servidor servidor);

	/**
	Estagiario getEstagiarioByPessoaCpf(String cpf);
	
	
	Pessoa getPessoaById(Long id);

	List<Papel> getPapeis(String cpf);

	boolean isServidor(String cpf);
	
	boolean isEstagiario(String cpf);

	boolean isPessoa(String cpf);
	
	Papel getPapel(String papel);
	
	Estagiario getEstagiarioByPessoaCpf(String cpf);
			
	Estagiario getEstagiarioByPessoa(Long idPessoa);
	
	void editarEstagiario(Estagiario estagiario);
	
	Servidor getServidorByPessoa(Long idPessoa);
	
	Servidor getServidorByPessoaCpf(String cpf);
	 */
	
}
