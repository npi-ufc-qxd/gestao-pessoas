package ufc.quixada.npi.gp.service;

import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ufc.quixada.npi.gp.model.Frequencia;
import ufc.quixada.npi.gp.model.Turma;
import br.ufc.quixada.npi.service.GenericService;

public interface FrequenciaService extends GenericService<Frequencia>{

	Frequencia getFrequencia();

	Frequencia getFrequenciaDeHojeByEstagiario(Long id);

	List<Frequencia> getFrequenciaTurma(Turma turma);

	List<Frequencia> getFrequencias(Date data, Turma turma);

	@Transactional
	void atualizarStatus();
                                                                                                                  
}
