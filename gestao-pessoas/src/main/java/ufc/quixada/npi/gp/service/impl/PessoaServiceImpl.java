package ufc.quixada.npi.gp.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import ufc.quixada.npi.gp.model.Estagiario;
import ufc.quixada.npi.gp.model.Papel;
import ufc.quixada.npi.gp.model.Pessoa;
import ufc.quixada.npi.gp.model.Servidor;
import ufc.quixada.npi.gp.service.PessoaService;
import br.ufc.quixada.npi.enumeration.QueryType;
import br.ufc.quixada.npi.repository.GenericRepository;
import br.ufc.quixada.npi.service.impl.GenericServiceImpl;

@Named
public class PessoaServiceImpl extends GenericServiceImpl<Pessoa> implements PessoaService {

	@Inject
	private GenericRepository<Pessoa> pessoaRepository;
	
	@Inject
	private GenericRepository<Papel> papelRepository;
	
	@Inject
	private GenericRepository<Servidor> servidorRepository;
	
	@Inject
	private GenericRepository<Estagiario> estagiarioRepository;

	@Override
	public Pessoa getPessoaByCpf(String cpf) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cpf", cpf);
		return (Pessoa) pessoaRepository.findFirst(QueryType.JPQL, "from Pessoa where cpf = :cpf", params);
	}

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

}
