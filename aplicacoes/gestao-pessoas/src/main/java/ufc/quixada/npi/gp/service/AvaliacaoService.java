package ufc.quixada.npi.gp.service;

import java.util.List;

import ufc.quixada.npi.gp.model.Avaliacao;

public interface AvaliacaoService {
	
	Avaliacao getAvaliacaoById(Long id);
	
	List<Avaliacao> getAvaliacaoByEstagiarioId (Long idEstagiario,Long idTurma);
}
