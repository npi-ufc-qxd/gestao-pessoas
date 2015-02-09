package ufc.quixada.npi.gp.service;

import ufc.quixada.npi.gp.model.Periodo;
import br.ufc.quixada.npi.service.GenericService;

public interface PeriodoService extends GenericService<Periodo> {

	Periodo getPeriodo(Integer ano, String semestre);

}
