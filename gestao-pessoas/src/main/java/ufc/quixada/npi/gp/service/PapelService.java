package ufc.quixada.npi.gp.service;

import ufc.quixada.npi.gp.model.Papel;
import br.ufc.quixada.npi.service.GenericService;

public interface PapelService extends GenericService<Papel> {

	Papel getPapel(String papel);

}
