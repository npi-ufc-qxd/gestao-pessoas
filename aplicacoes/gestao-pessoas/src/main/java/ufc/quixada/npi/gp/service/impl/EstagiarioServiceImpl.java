package ufc.quixada.npi.gp.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import ufc.quixada.npi.gp.model.Estagiario;
import ufc.quixada.npi.gp.service.EstagiarioService;
import br.ufc.quixada.npi.enumeration.QueryType;
import br.ufc.quixada.npi.repository.GenericRepository;
import br.ufc.quixada.npi.service.impl.GenericServiceImpl;

@Named
public class EstagiarioServiceImpl extends GenericServiceImpl<Estagiario> implements EstagiarioService{

	
	@Inject
	private GenericRepository<Estagiario> estagiarioRepository;

	@Override
	public Estagiario getEstagiarioByPessoaId(Long id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		Estagiario estagiario = estagiarioRepository.findFirst(QueryType.JPQL, "select e from Estagiario e where e.pessoa.id = :id", params, 0);
		
		return estagiario;
	}

	@Override
	public List<Estagiario> getEstagiarioTurma(Long id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		return estagiarioRepository.find(QueryType.JPQL, "select e from Estagiario e where e.turma.id = :id", params);
	}

	@Override
	public Estagiario getEstagiarioPesssoa(String cpf, String senha) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cpf", cpf);
		params.put("senha", senha);
		Estagiario estagiario = estagiarioRepository.findFirst(QueryType.JPQL, "select e from Estagiario e join e.pessoa p where p.cpf = :cpf and p.password = :senha", params, 0);
		
		return estagiario;
	}



}
