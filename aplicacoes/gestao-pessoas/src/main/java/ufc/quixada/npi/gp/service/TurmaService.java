package ufc.quixada.npi.gp.service;

import java.util.List;

import ufc.quixada.npi.gp.model.Turma;
import ufc.quixada.npi.gp.model.enums.StatusPeriodo;
import ufc.quixada.npi.gp.model.enums.StatusTurma;
import br.ufc.quixada.npi.service.GenericService;

public interface TurmaService extends GenericService<Turma> {

	List<Turma> getTurmaPeriodo(String ano, String semestre, Long idSupervisor);

	List<Turma> getMinhasTurmaPeriodo(String ano, String semestre, Long idSupervisor);

	List<Turma> getMinhasTurma(Long idSupervisor);

	List<Turma> getTurmasAno(String ano, StatusPeriodo statusPeriodo);

	List<Turma> getTurmasSupervisorByStatus(StatusTurma statusTurma, Long idSupervisor);

}
