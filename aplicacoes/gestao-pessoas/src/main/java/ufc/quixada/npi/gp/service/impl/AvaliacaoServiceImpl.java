package ufc.quixada.npi.gp.service.impl;

import java.util.HashMap;

import java.util.List;
import java.util.Map;

import javax.inject.Named;
import ufc.quixada.npi.gp.model.AvaliacaoRendimento;
import ufc.quixada.npi.gp.service.AvaliacaoService;
import br.ufc.quixada.npi.enumeration.QueryType;
import br.ufc.quixada.npi.service.impl.GenericServiceImpl;

@Named
public class AvaliacaoServiceImpl extends GenericServiceImpl<AvaliacaoRendimento> implements AvaliacaoService {

	@Override
	public List<AvaliacaoRendimento> getAvaliacaoBySupervisorId(Long idSupervisor) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("idSupervisor", idSupervisor);
		@SuppressWarnings("unchecked")
		List<AvaliacaoRendimento> avaliacoes = find(QueryType.JPQL,
				"select ar from AvaliacaoRendimento ar where ar.supervisor.id = :idSupervisor", params);

		return avaliacoes;
	}

	@Override
	public List<AvaliacaoRendimento> getAvaliacaoByEstagiarioId(Long idEstagiario) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("idEstagiario", idEstagiario);
		@SuppressWarnings("unchecked")
		List<AvaliacaoRendimento> avaliacoes = find(QueryType.JPQL,
				"select ar from AvaliacaoRendimento ar where ar.estagiario.id = :idEstagiario", params);

		return avaliacoes;
	}

	@Override
	public AvaliacaoRendimento getAvaliacaoEstagioById(Long idAvaliacao) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("idAvaliacao", idAvaliacao);
		@SuppressWarnings("unchecked")
		AvaliacaoRendimento avaliacao = (AvaliacaoRendimento) find(QueryType.JPQL,
				"select * from AvaliacaoRendimento ar where ar.id = :idAvaliacao", params);

		return avaliacao;
	}

	@Override
	public List<AvaliacaoRendimento> getAvaliacoesEstagioByEstagiarioIdAndTurmaById(Long idEstagiario, Long idTurma) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("idEstagiario", idEstagiario);
		params.put("idTurma", idTurma);
		@SuppressWarnings("unchecked")
		List<AvaliacaoRendimento> avaliacoes = find(QueryType.JPQL,
				"select ar from AvaliacaoRendimento ar where ar.estagiario.id = :idEstagiario and ar.turma.id = :idTurma",
				params);

		return avaliacoes;
	}

}
