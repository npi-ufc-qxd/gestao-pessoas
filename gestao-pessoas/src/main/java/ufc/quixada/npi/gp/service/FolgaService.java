package ufc.quixada.npi.gp.service;

import java.util.List;

import ufc.quixada.npi.gp.model.Folga;
import br.ufc.quixada.npi.service.GenericService;

public interface FolgaService extends GenericService<Folga> {
	
	List<Folga> getFolgasByAno(int ano);
	
}
