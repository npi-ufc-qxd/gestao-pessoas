package ufc.quixada.npi.gp.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import ufc.quixada.npi.gp.model.Folga;
import ufc.quixada.npi.gp.service.FolgaService;
import br.ufc.quixada.npi.enumeration.QueryType;
import br.ufc.quixada.npi.service.impl.GenericServiceImpl;

@Named
public class FolgaServiceImpl extends GenericServiceImpl<Folga> implements FolgaService {

	@Override
	public List<Folga> getFolgasByAno(int ano) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ano", ano);

		@SuppressWarnings("unchecked")
		List<Folga> folgas= find(QueryType.JPQL, "select f from Folga f where YEAR(f.data) = :ano", params);
		
		return folgas;
	}
}
