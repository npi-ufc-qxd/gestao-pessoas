package br.ufc.quixada.npi.ge.service;

import java.util.List;

import br.ufc.quixada.npi.ge.model.Estagiario;
import br.ufc.quixada.npi.ge.model.Papel;
import br.ufc.quixada.npi.ge.model.Pessoa;
import br.ufc.quixada.npi.ge.model.Servidor;

public interface PessoaService {

	Pessoa buscarPessoaPorCpf(String cpf);
	
	Pessoa getPessoaById(Long idPessoa);
	
	void adicionarPessoa(Pessoa pessoa);

	boolean existePessoa(String cpf);

	Servidor buscarServidorPorCpf(String cpf); 

	Servidor buscarServidorPorId(Long idServidor);
	
	List<Servidor> buscarServidores();

	void adicionarServidor(Servidor servidor);
	
	void editarServidor(Servidor servidor);

	boolean existeServidor(String cpf);

	Estagiario buscarEstagiarioPorCpf(String cpf);
	
	Estagiario buscarEstagiarioPorId(Long estagiarioId);

	List<Estagiario> buscarAniversariantesDoMesPorTurmaId(Long idTurma);
	
	void adicionarEstagiario(Estagiario estagiario);

	void editarEstagiario(Estagiario estagiario);
	
	boolean existeEstagiario(String cpf);

	Papel buscarPapelPorNome(String string);

	List<Papel> getPapeis(String cpf);
}
