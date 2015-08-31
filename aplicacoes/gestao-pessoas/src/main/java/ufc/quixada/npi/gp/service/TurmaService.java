package ufc.quixada.npi.gp.service;

import java.util.List;

import ufc.quixada.npi.gp.model.Turma;
import ufc.quixada.npi.gp.model.enums.StatusTurma;
import br.ufc.quixada.npi.service.GenericService;

public interface TurmaService extends GenericService<Turma> {

	Turma getTurmaEmAndamentoByEstagiarioId(Long idEstagiario);

	List<Turma> getTurmasBySupervisorId(Long idSupervisor);

	List<Turma> getTurmasBySupervisorIdAndStatus(StatusTurma statusTurma, Long idSupervisor);

	Turma getTurmaByIdAndSupervisorById(Long idTurma, Long idSupervisor);

}
