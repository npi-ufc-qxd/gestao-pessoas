package ufc.quixada.npi.gp.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import ufc.quixada.npi.gp.model.Estagiario;
import ufc.quixada.npi.gp.model.Frequencia;
import ufc.quixada.npi.gp.repository.EstagiarioRepository;
import ufc.quixada.npi.gp.repository.GenericRepository;
import ufc.quixada.npi.gp.repository.QueryType;
import ufc.quixada.npi.gp.service.EstagiarioService;
import ufc.quixada.npi.gp.service.FrequenciaService;

@Named
public class FrequenciaServiceImpl extends GenericServiceImpl<Frequencia> implements FrequenciaService {

	@Inject
	private GenericRepository<Frequencia> frequenciaRepository;

	@Override
	public Frequencia getFrequencia() {
		List<Frequencia> frequencia = frequenciaRepository.find(QueryType.JPQL,
				"select f from Frequencia f where f.data = CURRENT_DATE", null);
		return frequencia.get(0);
	}

}
