package ufc.quixada.npi.gp.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import br.ufc.quixada.npi.enumeration.QueryType;
import br.ufc.quixada.npi.service.impl.GenericServiceImpl;
import ufc.quixada.npi.gp.model.Evento;
import ufc.quixada.npi.gp.service.EventoService;

@Named
public class EventoServiceImpl extends GenericServiceImpl<Evento> implements EventoService {

	@Override
	public List<Evento> getEventosByTurma(Long idTurma) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("idTurma", idTurma);
		
		@SuppressWarnings("unchecked")
		List<Evento> eventos = find(QueryType.JPQL, "select e from Evento e where e.turma.id = :idTurma", params);

		return eventos;
	}
}
