package ufc.quixada.npi.gp.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import ufc.quixada.npi.gp.model.Periodo;
import ufc.quixada.npi.gp.service.PeriodoService;
import br.ufc.quixada.npi.enumeration.QueryType;
import br.ufc.quixada.npi.repository.GenericRepository;
import br.ufc.quixada.npi.service.impl.GenericServiceImpl;

@Named
public class PeriodoServiceImpl extends GenericServiceImpl<Periodo> implements PeriodoService {
	
	@Inject
	private GenericRepository<Periodo> periodoRepository;

	@Override
	public Periodo getPeriodo(Integer ano, String semestre) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ano", ano);
		params.put("semestre", semestre);
		Periodo periodo = periodoRepository.findFirst(QueryType.JPQL,"select p from Periodo p where p.ano = :ano and p.semestre = :semestre", params, 0);
		return periodo;
	}	
	

//	@Override
//	public Periodo getPeriodo(Integer ano, String semestre) {
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("ano", ano);
//		params.put("semestre", semestre);
//		Periodo periodo = periodoRepository.findFirst(QueryType.JPQL,"select p from Periodo p where p.ano = :ano and p.semestre = :semestre", params, 0, 0);
//		return periodo;
//	}


//	@Override
//	public Pessoa getPessoaByLogin(String login) {
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("login", login);
//		Pessoa usuariologado = pessoaRepository.find(QueryType.JPQL, "from Pessoa where login = :login", params).get(0);
//		return usuariologado;
//	}

}
