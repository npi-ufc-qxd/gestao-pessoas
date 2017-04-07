package ufc.quixada.npi.gp.service;

import java.util.List;

import ufc.quixada.npi.gp.model.Turma;
import ufc.quixada.npi.gp.model.enums.StatusTurma;
import br.ufc.quixada.npi.service.GenericService;

public interface TurmaService extends GenericService<Turma> {

	List<Turma> getTurmasBySupervisorId(Long idSupervisor);

	List<Turma> getTurmasBySupervisorIdAndStatus(StatusTurma statusTurma, Long idSupervisor);

	List<Turma> getTurmasByEstagiarioIdAndStatus(StatusTurma statusTurma, Long idSupervisor);

	Turma getTurmaByIdAndEstagiarioId(Long idTurma, Long idEstagiario);

	Turma getTurmaByIdAndSupervisorById(Long idTurma, Long idSupervisor);

	List<Turma> getTurmasByEstagiarioId(Long idEstagiario);

}
