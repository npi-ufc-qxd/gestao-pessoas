package ufc.quixada.npi.gp.service;

import ufc.quixada.npi.gp.model.Horario;
import br.ufc.quixada.npi.service.GenericService;

public interface HorarioService extends GenericService<Horario> {

	Horario getHorarioTurmaById(Long idHorario, Long idTurma);
	
}
