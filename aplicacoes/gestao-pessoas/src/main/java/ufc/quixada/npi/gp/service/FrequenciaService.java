package ufc.quixada.npi.gp.service;

import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import br.ufc.quixada.npi.service.GenericService;
import ufc.quixada.npi.gp.model.Estagiario;
import ufc.quixada.npi.gp.model.Frequencia;
import ufc.quixada.npi.gp.model.Turma;

public interface FrequenciaService extends GenericService<Frequencia>{
	
	@Transactional
	void atualizarStatus();

	boolean liberarPreseca(Turma turma);
	
	Frequencia getFrequenciaDeHojeByEstagiarioId(Long id);

	List<Frequencia> getFrequenciasByEstagiarioId(Long idEstagiario, Long idTurma);

	Frequencia getFrequenciaByDataByTurmaByEstagiario(Date data, Long turma, Long estagiario);

	List<Frequencia> getFrequenciasByTurmaIdAndData(Date data, Long idTurma);

	DadoConsolidado calcularDadosConsolidados(List<Frequencia> frequencia);
	
	List<Estagiario> getEstagiariosSemFrequencia(Date data, Long idTurma);
	
	List<Frequencia> gerarFrequencia(Turma turma, Estagiario estagiario);
}
