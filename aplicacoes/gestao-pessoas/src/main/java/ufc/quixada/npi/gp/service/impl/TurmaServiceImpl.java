package ufc.quixada.npi.gp.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import ufc.quixada.npi.gp.model.Turma;
import ufc.quixada.npi.gp.model.enums.StatusPeriodo;
import ufc.quixada.npi.gp.service.TurmaService;
import br.ufc.quixada.npi.enumeration.QueryType;
import br.ufc.quixada.npi.repository.GenericRepository;
import br.ufc.quixada.npi.service.impl.GenericServiceImpl;

@Named
public class TurmaServiceImpl extends GenericServiceImpl<Turma> implements TurmaService {
	
	@Inject
	private GenericRepository<Turma> turmaRepository;	

	@Override
	public List<Turma> getTurmaPeriodo(String ano, String semestre) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ano", ano);
		params.put("semestre", semestre);
		params.put("supervisor", 1L);
		List<Turma> turmas = turmaRepository.find(QueryType.JPQL,"select t.id, t.nome from Turma t join t.periodo p where p.ano = :ano and p.semestre = :semestre and t.supervisor.id = :supervisor", params);

		return turmas;
	}

	@Override
	public List<Turma> getMinhasTurmaPeriodo(String ano, String semestre, Long idSupervisor) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ano", ano);
		params.put("semestre", semestre);
		params.put("idSupervisor", idSupervisor);
		List<Turma> turmas = turmaRepository.find(QueryType.JPQL,"select t from Turma t join t.periodo p where p.ano = :ano and p.semestre = :semestre and t.supervisor.id = :idSupervisor", params);

		return turmas;
	}

	@Override
	public List<Turma> getMinhasTurma(Long idSupervisor) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("idSupervisor", idSupervisor);
		List<Turma> turmas = turmaRepository.find(QueryType.JPQL,"select t from Turma t where t.supervisor.id = :idSupervisor", params);

		return turmas;
	}

	@Override
	public List<Turma> getTurmasAno(String ano, StatusPeriodo statusPeriodo) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ano", ano);
		params.put("status", statusPeriodo);
		List<Turma> turmas = turmaRepository.find(QueryType.JPQL,"select t from Turma t join t.periodo p where p.ano = :ano and p.statusPeriodo = :status", params);

		return turmas;
	}

}
