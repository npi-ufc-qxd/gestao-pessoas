package ufc.quixada.npi.gp.service;

import java.util.List;

import ufc.quixada.npi.gp.model.AvaliacaoEstagio;
import br.ufc.quixada.npi.service.GenericService;

public interface AvaliacaoService extends GenericService<AvaliacaoEstagio>{

	List<AvaliacaoEstagio> getAvaliacaoBySupervisorId(Long idSupervisor);

	List<AvaliacaoEstagio> getAvaliacaoByEstagiarioId(Long idEstagiario);

	AvaliacaoEstagio getAvaliacaoEstagioById(Long idAvaliacao);

	AvaliacaoEstagio getAvaliacaoByIdAndSupervisorById(Long idAvaliacao, Long idSupervisor);

}
