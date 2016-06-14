package br.ufc.quixada.npi.gp.service.impl;

import java.util.List;

import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;

import br.ufc.quixada.npi.gp.model.Estagiario;
import br.ufc.quixada.npi.gp.model.Papel;
import br.ufc.quixada.npi.gp.model.Pessoa;
import br.ufc.quixada.npi.gp.model.Servidor;
import br.ufc.quixada.npi.gp.repository.EstagiarioRepository;
import br.ufc.quixada.npi.gp.repository.PapelRepository;
import br.ufc.quixada.npi.gp.repository.PessoaRepository;
import br.ufc.quixada.npi.gp.repository.ServidorRepository;
import br.ufc.quixada.npi.gp.service.PessoaService;

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Servidor buscarServidorPorId(Long idServidor) {
		// TODO Auto-generated method stub
		return null;
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

	


	
	/**
	@Autowired
	private EstagiarioRepository estagiarioRepository;
	
	@Override
	public Estagiario getEstagiarioByPessoaCpf(String cpf) {
		return estagiarioRepository.findByCpf(cpf);
	}
	
	
	
	@Inject
	private GenericRepository<Pessoa> pessoaRepository;
	
	@Inject
	private GenericRepository<Papel> papelRepository;
	
	@Inject
	private GenericRepository<Servidor> servidorRepository;
	
	@Inject
	private GenericRepository<Estagiario> estagiarioRepository;
	

	@Override
	public Pessoa getPessoaById(Long id) {
		return pessoaRepository.find(Pessoa.class, id);
	}

	@Override
	public List<Papel> getPapeis(String cpf) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cpf", cpf);
		return papelRepository.find(QueryType.JPQL, "select p.papeis FROM Pessoa p WHERE p.cpf = :cpf", params);
	}

	@Override
	public boolean isPessoa(String cpf) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cpf", cpf);
		
		Pessoa pessoa = (Pessoa) pessoaRepository.findFirst(QueryType.JPQL, "from Pessoa where cpf = :cpf", params);

		if(pessoa !=null){
			return true;
		}
	
		return false;
	}	
	
	@Override
	public boolean isServidor(String cpf) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cpf", cpf);
		
		Servidor servidor = (Servidor) servidorRepository.findFirst(QueryType.JPQL, "select s FROM Servidor s WHERE s.pessoa.cpf = :cpf", params);

		if(!servidor.equals(null)){
			return true;
		}

		return false;
	}

	@Override
	public boolean isEstagiario(String cpf) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cpf", cpf);
		
		Estagiario estagiario = (Estagiario) estagiarioRepository.findFirst(QueryType.JPQL, "select e FROM Estagiario e WHERE e.pessoa.cpf = :cpf", params);

		if( estagiario != null ){
			return true;
		}

		return false;
	}
			
	@Override
	public Estagiario getEstagiarioByPessoaCpf(String cpf) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cpf", cpf);

		Estagiario estagiario = (Estagiario) estagiarioRepository.findFirst(QueryType.JPQL, "select e from Estagiario e where e.pessoa.cpf = :cpf", params);
		
		return estagiario;
	}
	
	public Papel getPapel(String papel) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("papel", papel);

		Papel papelPessoa = (Papel) papelRepository.findFirst(QueryType.JPQL, "from Papel where nome = :papel", params);

		return papelPessoa;
	}

	@Override
	public Estagiario getEstagiarioByPessoa(Long idPessoa) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("idPessoa", idPessoa);

		Estagiario estagiario = (Estagiario) estagiarioRepository.findFirst(QueryType.JPQL, "select e from Estagiario e where e.pessoa.id = :idPessoa", params);
		
		return estagiario;
	}

	@Override
	public Servidor getServidorByPessoa(Long idPessoa) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("idPessoa", idPessoa);

		Servidor servidor = (Servidor) servidorRepository.findFirst(QueryType.JPQL, "select s from Servidor s where s.pessoa.id = :idPessoa", params);
		
		return servidor;
	}

	@Override
	public Servidor getServidorByPessoaCpf(String cpf) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cpf", cpf);

		Servidor servidor = (Servidor) servidorRepository.findFirst(QueryType.JPQL, "select s from Servidor s where s.pessoa.cpf = :cpf", params);
		
		return servidor;
	}

	@Override
	public void editarEstagiario(Estagiario estagiario) {
		estagiarioRepository.update(estagiario);
		
	}
	 * 
	 */

}
