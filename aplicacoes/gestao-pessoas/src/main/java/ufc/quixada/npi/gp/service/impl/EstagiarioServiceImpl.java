package ufc.quixada.npi.gp.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import ufc.quixada.npi.gp.model.Estagiario;
import ufc.quixada.npi.gp.model.Turma;
import ufc.quixada.npi.gp.service.EstagiarioService;
import br.ufc.quixada.npi.enumeration.QueryType;
import br.ufc.quixada.npi.service.impl.GenericServiceImpl;

@Named
public class EstagiarioServiceImpl extends GenericServiceImpl<Estagiario> implements EstagiarioService {
	
	@Override
	public Estagiario getEstagiarioByPessoaId(Long id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		
		Estagiario estagiario = (Estagiario) findFirst(QueryType.JPQL, "select e from Estagiario e where e.pessoa.id = :id", params);
		
		return estagiario;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Estagiario> getEstagiarioByTurmaId(Long id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		
		List<Estagiario> estagiarios = find(QueryType.JPQL, "select e from Estagiario e where e.turma.id = :id", params);

		return estagiarios;
	}

	@Override
	public Estagiario getEstagiarioByPessoaCpf(String cpf) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cpf", cpf);

		Estagiario estagiario = (Estagiario) findFirst(QueryType.JPQL, "select e from Estagiario e where e.pessoa.cpf = :cpf", params);
		
		return estagiario;
	}

	@Override
	public boolean possuiTurmaAtiva(String cpf) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cpf", cpf);

		Turma turma = (Turma) findFirst(QueryType.JPQL, "select t from Estagiario e join e.turmas t where e.pessoa.cpf = :cpf and t IS NOT NULL", params);

		if (turma != null) {
			return true;
		}

		return false;
	}

}
