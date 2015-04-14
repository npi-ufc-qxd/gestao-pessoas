package ufc.quixada.npi.gp.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import ufc.quixada.npi.gp.model.Papel;
import ufc.quixada.npi.gp.service.PapelService;
import br.ufc.quixada.npi.enumeration.QueryType;
import br.ufc.quixada.npi.repository.GenericRepository;
import br.ufc.quixada.npi.service.impl.GenericServiceImpl;

@Named
public class PapelServiceImpl extends GenericServiceImpl<Papel> implements	PapelService {

	@Inject
	GenericRepository<Papel> papelRepository;

	@Override
	public Papel getPapel(String papel) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("papel", papel);
		Papel papelPessoa = papelRepository.find(QueryType.JPQL, "from Papel where nome = :papel", params).get(0);
		return papelPessoa;
	}

}
