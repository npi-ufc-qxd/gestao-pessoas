package ufc.quixada.npi.gp.service;

import java.util.List;

import br.ufc.quixada.npi.service.GenericService;
import ufc.quixada.npi.gp.model.Evento;

public interface EventoService extends GenericService<Evento> {

	List<Evento> getEventosByTurma(Long idTurma);
}
