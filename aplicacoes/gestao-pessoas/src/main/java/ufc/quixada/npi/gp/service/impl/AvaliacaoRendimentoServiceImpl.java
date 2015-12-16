package ufc.quixada.npi.gp.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;

import br.ufc.quixada.npi.enumeration.QueryType;
import br.ufc.quixada.npi.repository.GenericRepository;
import br.ufc.quixada.npi.service.impl.GenericServiceImpl;
import ufc.quixada.npi.gp.model.Submissao;
import ufc.quixada.npi.gp.model.SubmissaoAvaliacaoRendimento;
import ufc.quixada.npi.gp.service.AvaliacaoRendimentoService;

@Named
public class AvaliacaoRendimentoServiceImpl extends GenericServiceImpl<SubmissaoAvaliacaoRendimento> implements AvaliacaoRendimentoService {
	
	@Autowired
	private GenericRepository<SubmissaoAvaliacaoRendimento> avaliacaoRendimentoRepository;

	@Override
	public void salvar(SubmissaoAvaliacaoRendimento avaliacaoRendimento) {
		avaliacaoRendimentoRepository.save(avaliacaoRendimento);
	}
	
	@Override
	public void salvar(List<SubmissaoAvaliacaoRendimento> avaliacoesRendimento) {
		for(SubmissaoAvaliacaoRendimento avaliacao : avaliacoesRendimento) {
			avaliacaoRendimentoRepository.save(avaliacao);
		}
	}

	@Override
	public SubmissaoAvaliacaoRendimento getAvaliacaoRendimentoById(Long id) {
		return avaliacaoRendimentoRepository.find(SubmissaoAvaliacaoRendimento.class, id);
	}

	@Override
	public void remover(SubmissaoAvaliacaoRendimento avaliacaoRendimento) {
		avaliacaoRendimentoRepository.delete(avaliacaoRendimento);
	}
	
	@Override
	public SubmissaoAvaliacaoRendimento getAvaliacaoRendimentoByTurmaIdAndEstagiarioId(Long idTurma, Long idEstagiario) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("idTurma", idTurma);
		params.put("idEstagiario", idEstagiario);
		SubmissaoAvaliacaoRendimento avaliacaoRendimento = (SubmissaoAvaliacaoRendimento) findFirst(QueryType.JPQL,"select s from SubmissaoAvaliacaoRendimento s where s.estagiario.id = :idEstagiario and s.turma.id = :idTurma", params);

		return avaliacaoRendimento;
	}
	
	@Override
	public List<SubmissaoAvaliacaoRendimento> getAvaliacoesRendimentoByTurmaIdAndEstagiarioId(Long idTurma, Long idEstagiario) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("idEstagiario", idEstagiario);
		params.put("idTurma", idTurma);
		@SuppressWarnings("unchecked")
		List <SubmissaoAvaliacaoRendimento> avaliacoes = find(QueryType.JPQL,"select s from SubmissaoAvaliacaoRendimento s where s.estagiario.id = :idEstagiario and s.turma.id = :idTurma", params);

		return avaliacoes;
	}
	
}
