package br.ufc.quixada.npi.gp.service;

import java.util.List;

import br.ufc.quixada.npi.gp.model.Estagiario;
import br.ufc.quixada.npi.gp.model.Papel;
import br.ufc.quixada.npi.gp.model.Pessoa;
import br.ufc.quixada.npi.gp.model.Servidor;

public interface PessoaService {

	Pessoa buscarPessoaPorCpf(String cpf);
	
	Pessoa getPessoaById(Long idPessoa);
	
	void adicionarPessoa(Pessoa pessoa);

	boolean existePessoa(String cpf);

	Servidor buscarServidorPorCpf(String cpf); 

	Servidor buscarServidorPorId(Long idServidor);

	void adicionarServidor(Servidor servidor);
	
	void editarServidor(Servidor servidor);

	boolean existeServidor(String cpf);

	Estagiario buscarEstagiarioPorCpf(String cpf);

	Estagiario buscarEstagiarioPorId(Long idEstagiario);
	
	Estagiario buscarEstagiarioPorIdEstagio(Long idEstagio);

	List<Estagiario> buscarAniversariantesDoMesPorTurmaId(Long idTurma);

	void adicionarEstagiario(Estagiario estagiario);

	void editarEstagiario(Estagiario estagiario);
	
	boolean existeEstagiario(String cpf);

	Papel buscarPapelPorNome(String string);
}
