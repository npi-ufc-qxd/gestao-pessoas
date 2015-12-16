package ufc.quixada.npi.gp.service;

import java.util.List;

import br.ufc.quixada.npi.service.GenericService;
import ufc.quixada.npi.gp.model.Submissao;
import ufc.quixada.npi.gp.model.SubmissaoAvaliacaoRendimento;
import ufc.quixada.npi.gp.model.enums.Tipo;


public interface AvaliacaoRendimentoService extends GenericService<SubmissaoAvaliacaoRendimento>  {
	
	void salvar(SubmissaoAvaliacaoRendimento avaliacaoRendimento);
	
	void salvar(List<SubmissaoAvaliacaoRendimento> avaliacoesRendimento);
	
	SubmissaoAvaliacaoRendimento getAvaliacaoRendimentoById(Long id);
	
	void remover(SubmissaoAvaliacaoRendimento avaliacaoRendimento);
	
	List<SubmissaoAvaliacaoRendimento> getAvaliacoesRendimentoByTurmaIdAndEstagiarioId(Long idPessoa, Long idTurma);
	
	SubmissaoAvaliacaoRendimento getAvaliacaoRendimentoByTurmaIdAndEstagiarioId(Long idTurma, Long idEstagiario);
	
}
