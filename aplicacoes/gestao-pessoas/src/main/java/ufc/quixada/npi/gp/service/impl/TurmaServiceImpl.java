package ufc.quixada.npi.gp.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import ufc.quixada.npi.gp.model.JsonTurma;
import ufc.quixada.npi.gp.model.Turma;
import ufc.quixada.npi.gp.repository.GenericRepository;
import ufc.quixada.npi.gp.repository.QueryType;
import ufc.quixada.npi.gp.service.TurmaService;

@Named
public class TurmaServiceImpl extends GenericServiceImpl<Turma> implements TurmaService {
	
	@Inject
	private GenericRepository<Turma> turmaRepository;	
	
	@Inject
	private GenericRepository<JsonTurma> jsonTurmaRepository;

//	public Periodo getPeriodo(Integer ano, String semestre) {
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("ano", ano);
//		params.put("semestre", semestre);
//		Periodo periodo = periodoRepository.findFirst(QueryType.JPQL,"select p from Periodo p where p.ano = :ano and p.semestre = :semestre", params, 0, 0);
//		return periodo;
//	}

	@Override
	public List<Turma> getTurmaPeriodo(Integer ano, String semestre) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ano", ano);
		params.put("semestre", semestre);
		List<Turma> turmas = turmaRepository.find(QueryType.JPQL,"select t from Turma t join t.periodo p where p.ano = :ano and p.semestre = :semestre", params);

		return turmas;
	}

	@Override
	public List<JsonTurma> getJsonTurmaPeriodo(Integer ano, String semestre) {
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ano", ano);
		params.put("semestre", semestre);
		
		List<JsonTurma> turmas = jsonTurmaRepository.find(QueryType.JPQL,"select t.id, t.codigo from Turma t join t.periodo p where p.ano = :ano and p.semestre = :semestre", params);

		return turmas;
	}


//	@Override
//	public Pessoa getPessoaByLogin(String login) {
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("login", login);
//		Pessoa usuariologado = pessoaRepository.find(QueryType.JPQL, "from Pessoa where login = :login", params).get(0);
//		return usuariologado;
//	}
}
