package ufc.quixada.npi.gp.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import br.ufc.quixada.npi.repository.GenericRepository;
import ufc.quixada.npi.gp.model.Avaliacao;
import ufc.quixada.npi.gp.model.Documento;
import ufc.quixada.npi.gp.service.AvaliacaoService;
import ufc.quixada.npi.gp.service.DocumentoService;

@Named
public class AvaliacaoServiceImpl implements AvaliacaoService {

	private GenericRepository<Avaliacao> avaliacaoRepository;

	@Inject
	private Avaliacao avaliacao;

	@Inject
	private DocumentoService documento;

	@Override
	public Avaliacao getAvaliacaoById(Long id) {
		return avaliacaoRepository.find(Avaliacao.class, id);
	}

	@Override
	public List<Avaliacao> getAvaliacaoByEstagiarioId(Long idEstagiario, Long idTurma) {
		List<Documento> documentos = documento.getDocumentosByEstagiarioId(idEstagiario, idTurma);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("idPessoa", idEstagiario);
		params.put("idTurma", idTurma);

		List<Avaliacao> avaliacoes = new ArrayList<Avaliacao>();

		for (Documento documento : documentos) {
			avaliacao.setDocumento(documento);
			avaliacoes.add(avaliacao);
		}
		return avaliacoes;
	}

}
