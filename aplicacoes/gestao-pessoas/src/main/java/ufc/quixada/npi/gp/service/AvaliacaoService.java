package ufc.quixada.npi.gp.service;

import ufc.quixada.npi.gp.model.Avaliacao;

public interface AvaliacaoService {
	
	Avaliacao getAvaliacaoById(Long id);
	
	Avaliacao getAvaliacaoByEstagiarioId (Long idEstagiario);
}
