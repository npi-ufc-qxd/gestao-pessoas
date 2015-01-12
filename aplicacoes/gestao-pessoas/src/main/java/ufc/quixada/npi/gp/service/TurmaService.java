package ufc.quixada.npi.gp.service;

import java.util.List;

import ufc.quixada.npi.gp.model.JsonTurma;
import ufc.quixada.npi.gp.model.Turma;

public interface TurmaService extends GenericService<Turma> {

	List<Turma> getTurmaPeriodo(Integer ano, String semestre);

	List<JsonTurma> getJsonTurmaPeriodo(Integer ano, String semestre);

}
