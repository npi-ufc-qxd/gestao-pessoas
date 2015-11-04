package ufc.quixada.npi.gp.service.impl;

import br.ufc.quixada.npi.repository.GenericRepository;
import ufc.quixada.npi.gp.model.Avaliacao;
import ufc.quixada.npi.gp.service.AvaliacaoService;

public class AvaliacaoServiceImpl implements AvaliacaoService {

	private GenericRepository<Avaliacao> avaliacaoRepository;

	@Override
	public Avaliacao getAvaliacaoById(Long id) {
		return avaliacaoRepository.find(Avaliacao.class, id);
	}

}
