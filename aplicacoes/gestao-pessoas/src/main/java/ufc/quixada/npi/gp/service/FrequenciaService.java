package ufc.quixada.npi.gp.service;

import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ufc.quixada.npi.gp.model.Frequencia;
import ufc.quixada.npi.gp.model.Horario;
import ufc.quixada.npi.gp.model.Turma;
import br.ufc.quixada.npi.service.GenericService;

public interface FrequenciaService extends GenericService<Frequencia>{
	
	@Transactional
	void atualizarStatus();

	boolean liberarPreseca(Turma turma);
	
	Frequencia getFrequenciaDeHojeByEstagiarioId(Long id);

	List<Frequencia> getFrequenciasByEstagiarioId(Long idEstagiario, Long idTurma);

	List<Frequencia> getFrequenciaTurma(Turma turma);

	List<Frequencia> getFrequenciasByTurmaIdAndData(Date data, Long idTurma);

	DadoConsolidado calcularDadosConsolidados(List<Frequencia> frequencia);

	List<Frequencia> gerarFrequencia(Date inicio, Date data, List<Horario> horarios);
}
