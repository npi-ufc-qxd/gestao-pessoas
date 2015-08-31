package ufc.quixada.npi.gp.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import ufc.quixada.npi.gp.model.Turma;
import ufc.quixada.npi.gp.model.enums.StatusTurma;
import ufc.quixada.npi.gp.service.TurmaService;
import br.ufc.quixada.npi.enumeration.QueryType;
import br.ufc.quixada.npi.service.impl.GenericServiceImpl;

@Named
public class TurmaServiceImpl extends GenericServiceImpl<Turma> implements TurmaService {

//	@Override
//	public List<Turma> getTurmaPeriodo(String ano, String semestre, Long idSupervisor) {
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("ano", ano);
//		params.put("semestre", semestre);
//		params.put("supervisor", idSupervisor);
//		@SuppressWarnings("unchecked")
//		List<Turma> turmas = find(QueryType.JPQL,"select t.id, t.nome from Turma t join t.periodo p where p.ano = :ano and p.semestre = :semestre and t.supervisor.id = :supervisor", params);
//
//		return turmas;
//	}
//
//	@Override
//	public List<Turma> getMinhasTurmaPeriodo(String ano, String semestre, Long idSupervisor) {
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("ano", ano);
//		params.put("semestre", semestre);
//		params.put("idSupervisor", idSupervisor);
//		@SuppressWarnings("unchecked")
//		List<Turma> turmas = find(QueryType.JPQL,"select t from Turma t join t.periodo p where p.ano = :ano and p.semestre = :semestre and t.supervisor.id = :idSupervisor", params);
//
//		return turmas;
//	}

	@Override
	public List<Turma> getTurmasBySupervisorId(Long idSupervisor) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("idSupervisor", idSupervisor);
		@SuppressWarnings("unchecked")
		List<Turma> turmas = find(QueryType.JPQL,"select t from Turma t where t.supervisor.id = :idSupervisor", params);

		return turmas;
	}

//	@Override
//	public List<Turma> getTurmasAno(String ano, StatusPeriodo statusPeriodo) {
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("ano", ano);
//		params.put("status", statusPeriodo);
//		@SuppressWarnings("unchecked")
//		List<Turma> turmas = find(QueryType.JPQL,"select t from Turma t join t.periodo p where p.ano = :ano and p.statusPeriodo = :status", params);
//
//		return turmas;
//	}

	@Override
	public List<Turma> getTurmasBySupervisorIdAndStatus(StatusTurma statusTurma, Long idSupervisor) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("statusTurma", statusTurma);
		params.put("idSupervisor", idSupervisor);
		@SuppressWarnings("unchecked")
		List<Turma> turmas = find(QueryType.JPQL,"select t from Turma t where t.statusTurma = :statusTurma and t.supervisor.id = :idSupervisor", params);

		return turmas;
	}

	@Override
	public Turma getTurmaByIdAndSupervisorById(Long idTurma, Long idSupervisor) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("idTurma", idTurma);
		params.put("idSupervisor", idSupervisor);

		Turma turma = (Turma) findFirst(QueryType.JPQL,"select t from Turma t where t.id = :idTurma and t.supervisor.id = :idSupervisor", params);

		return turma;
	}

	@Override
	public Turma getTurmaEmAndamentoByEstagiarioId(Long idEstagiario) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("idEstagiario", idEstagiario);
		params.put("statusTurma", StatusTurma.ABERTA);

		Turma turma = (Turma) findFirst(QueryType.JPQL, "select t from Turma t join t.estagiarios e where e.id = :idEstagiario and t.statusTurma = :statusTurma ", params);

		return turma;
	}

}
