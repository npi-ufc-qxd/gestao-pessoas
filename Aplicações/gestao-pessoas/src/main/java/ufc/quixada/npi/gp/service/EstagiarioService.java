package ufc.quixada.npi.gp.service;

import java.util.List;

import ufc.quixada.npi.gp.model.Estagiario;

public interface EstagiarioService extends GenericService<Estagiario>{

	List<Estagiario> estagiarioCadastrado(Long id);
	
}
