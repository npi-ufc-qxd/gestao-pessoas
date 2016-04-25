package ufc.quixada.npi.gp.service;

import java.util.List;

import ufc.quixada.npi.gp.model.AvaliacaoRendimento;
import br.ufc.quixada.npi.service.GenericService;

public interface AvaliacaoService extends GenericService<AvaliacaoRendimento>{

	List<AvaliacaoRendimento> getAvaliacaoBySupervisorId(Long idSupervisor);

	List<AvaliacaoRendimento> getAvaliacaoByEstagiarioId(Long idEstagiario);

	AvaliacaoRendimento getAvaliacaoEstagioById(Long idAvaliacao);

	List<AvaliacaoRendimento> getAvaliacoesEstagioByEstagiarioIdAndTurmaById(Long idEstagiario, Long idTurma);

}
