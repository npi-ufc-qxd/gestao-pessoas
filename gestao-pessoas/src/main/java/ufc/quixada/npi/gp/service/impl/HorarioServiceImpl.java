package ufc.quixada.npi.gp.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Named;

import ufc.quixada.npi.gp.model.Horario;
import ufc.quixada.npi.gp.service.HorarioService;
import br.ufc.quixada.npi.enumeration.QueryType;
import br.ufc.quixada.npi.service.impl.GenericServiceImpl;

@Named
public class HorarioServiceImpl extends GenericServiceImpl<Horario> implements HorarioService {

	@Override
	public Horario getHorarioTurmaById(Long idHorario, Long idTurma) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("idHorario", idHorario);
		params.put("idTurma", idTurma);
		
		Horario horario =  (Horario) findFirst(QueryType.JPQL, "select h from Horario h where h.turma.id = :idTurma and h.id = :idHorario", params);

		return horario;
	}
}
