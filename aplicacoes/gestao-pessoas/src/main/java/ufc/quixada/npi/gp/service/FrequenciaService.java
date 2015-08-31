package ufc.quixada.npi.gp.service;

import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ufc.quixada.npi.gp.model.Frequencia;
import ufc.quixada.npi.gp.model.Turma;
import ufc.quixada.npi.gp.model.enums.StatusFrequencia;
import br.ufc.quixada.npi.service.GenericService;

public interface FrequenciaService extends GenericService<Frequencia>{

//	Frequencia getFrequencia();
//
	Frequencia getFrequenciaDeHojeByEstagiarioId(Long id);
//	
	List<Frequencia> getFrequenciasByEstagiarioId(Long id);
//
	List<Frequencia> getFrequenciaTurma(Turma turma);
//
	List<Frequencia> getFrequenciasByTurmaIdAndData(Date data, Long idTurma);
//
//	List<Object> getFrequenciass(Date data, Turma turma);
//
//	List<Object> getFrequenciaRepor();
//
//	List<Object> getReposicaoAtraso(Long idTurma);
//
//	List<Object> getReposicaoFalta(Long idTurma);
//
//	List<Frequencia> getFrequenciaStatus(Long estagiario, StatusFrequencia statusFrequencia, int limit);
//
	DadoConsolidado calcularDadosConsolidados(List<Frequencia> frequencia);
	
	
	@Transactional
	void atualizarStatus();

	boolean liberarPreseca(Turma turma);
	

                                                                                                                  
}
