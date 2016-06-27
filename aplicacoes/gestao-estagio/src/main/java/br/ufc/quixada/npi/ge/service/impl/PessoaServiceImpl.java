package br.ufc.quixada.npi.ge.service.impl;

import java.util.List;

import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;

import br.ufc.quixada.npi.ge.model.Estagiario;
import br.ufc.quixada.npi.ge.model.Papel;
import br.ufc.quixada.npi.ge.model.Pessoa;
import br.ufc.quixada.npi.ge.model.Servidor;
import br.ufc.quixada.npi.ge.repository.EstagiarioRepository;
import br.ufc.quixada.npi.ge.repository.PapelRepository;
import br.ufc.quixada.npi.ge.repository.PessoaRepository;
import br.ufc.quixada.npi.ge.repository.ServidorRepository;
import br.ufc.quixada.npi.ge.service.PessoaService;

@Named
public class PessoaServiceImpl implements PessoaService {

	@Autowired
	private PessoaRepository pessoaRepository;

	@Autowired
	private PapelRepository papelRepository;

	@Autowired
	private ServidorRepository servidorRepository;
	
	@Autowired
	private EstagiarioRepository estagiarioRepository;


	@Override
	public Papel buscarPapelPorNome(String nome) {
		return papelRepository.findByNome(nome);
	}

	@Override
	public Pessoa buscarPessoaPorCpf(String cpf) {
		return pessoaRepository.findByCpf(cpf);
	}

	@Override
	public void adicionarPessoa(Pessoa pessoa) {
		pessoaRepository.save(pessoa);		
	}

	@Override
	public void adicionarServidor(Servidor servidor) {
		servidorRepository.save(servidor);
	}

	@Override
	public Pessoa getPessoaById(Long idPessoa) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean existePessoa(String cpf) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Servidor buscarServidorPorCpf(String cpf) {
		return servidorRepository.findByPessoa_Cpf(cpf);
	}
	

	@Override
	public Servidor buscarServidorPorId(Long idServidor) {
		return servidorRepository.findOne(idServidor);
	}
	
	@Override
	public List<Servidor> buscarServidores() {
		return servidorRepository.findAll();
	}

	@Override
	public void editarServidor(Servidor servidor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean existeServidor(String cpf) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Estagiario buscarEstagiarioPorCpf(String cpf) {
		 
		return estagiarioRepository.findByPessoaByCpf(cpf);
	}
	@Override
	public Estagiario buscarEstagiarioPorId(Long estagiarioId) {
		 return estagiarioRepository.findOne(estagiarioId);
	}


	@Override
	public List<Estagiario> buscarAniversariantesDoMesPorTurmaId(Long idTurma) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void adicionarEstagiario(Estagiario estagiario) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void editarEstagiario(Estagiario estagiario) {
		estagiarioRepository.save(estagiario);
		
	}

	@Override
	public boolean existeEstagiario(String cpf) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Papel> getPapeis(String cpf) {
		return papelRepository.findByCpf(cpf);
	}
}
