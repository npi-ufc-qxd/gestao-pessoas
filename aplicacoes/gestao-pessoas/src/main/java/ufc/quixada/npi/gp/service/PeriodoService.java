package ufc.quixada.npi.gp.service;

import ufc.quixada.npi.gp.model.Periodo;

public interface PeriodoService extends GenericService<Periodo> {

	Periodo getPeriodo(Integer ano, String semestre);

}
