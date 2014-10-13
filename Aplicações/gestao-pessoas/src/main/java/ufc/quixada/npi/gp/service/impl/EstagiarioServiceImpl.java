package ufc.quixada.npi.gp.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import ufc.quixada.npi.gp.model.Estagiario;
import ufc.quixada.npi.gp.repository.EstagiarioRepository;
import ufc.quixada.npi.gp.repository.QueryType;
import ufc.quixada.npi.gp.service.EstagiarioService;

@Named
public class EstagiarioServiceImpl extends GenericServiceImpl<Estagiario> implements EstagiarioService{

	
	@Inject
	private EstagiarioRepository estagiarioRepository;
	
	@Override
	public List<Estagiario> estagiarioCadastrado(Long id) {
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		List<Estagiario> estagiarioCadastrado = estagiarioRepository.find(QueryType.JPQL,
				"from Estagiario where pessoa_id = :id", params);
		return estagiarioCadastrado;
	}

}
