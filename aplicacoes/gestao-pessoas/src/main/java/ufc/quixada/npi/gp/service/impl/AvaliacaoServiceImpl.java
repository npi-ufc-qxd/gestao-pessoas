package ufc.quixada.npi.gp.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import ufc.quixada.npi.gp.model.AvaliacaoEstagio;
import ufc.quixada.npi.gp.service.AvaliacaoService;
import br.ufc.quixada.npi.enumeration.QueryType;
import br.ufc.quixada.npi.service.impl.GenericServiceImpl;

@Named
public class AvaliacaoServiceImpl extends GenericServiceImpl<AvaliacaoEstagio>implements AvaliacaoService{

	@Override
	public List<AvaliacaoEstagio> getAvaliacaoBySupervisorId(Long idSupervisor) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("idSupervisor", idSupervisor);
		@SuppressWarnings("unchecked")
		List<AvaliacaoEstagio> avaliacoes = find(QueryType.JPQL,"select ae from AvaliacaoEstagio ae where ae.supervisor.id = :idSupervisor", params);

		return avaliacoes;
	}

	@Override
	public List<AvaliacaoEstagio> getAvaliacaoByEstagiarioId(Long idEstagiario) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("idEstagiario", idEstagiario);
		@SuppressWarnings("unchecked")
		List<AvaliacaoEstagio> avaliacoes = find(QueryType.JPQL,"select ae from AvaliacaoEstagio ae where ae.estagiario.id = :idEstagiario", params);

		return avaliacoes;
	}

	@Override
	public AvaliacaoEstagio getAvaliacaoEstagioById(Long idAvaliacao) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("idAvaliacao", idAvaliacao);
		@SuppressWarnings("unchecked")
		AvaliacaoEstagio avaliacao = (AvaliacaoEstagio) find(QueryType.JPQL,"select * from AvaliacaoEstagio ae where ae.id = :idAvaliacao", params);

		return avaliacao;
	}

	@Override
	public AvaliacaoEstagio getAvaliacaoByIdAndSupervisorById(Long idAvaliacao, Long idSupervisor) {
		// TODO Auto-generated method stub
		return null;
	}

}
