package ufc.quixada.npi.gp.service;

import java.util.List;

import ufc.quixada.npi.gp.model.Turma;
import br.ufc.quixada.npi.service.GenericService;

public interface TurmaService extends GenericService<Turma> {

	List<Turma> getTurmaPeriodo(Integer ano, String semestre);

	List<Turma> getMinhasTurmaPeriodo(Integer ano, String semestre, Long idSupervisor);

	List<Turma> getMinhasTurma(Long idSupervisor);

	List<Turma> getTurmasAno(int ano);

}
