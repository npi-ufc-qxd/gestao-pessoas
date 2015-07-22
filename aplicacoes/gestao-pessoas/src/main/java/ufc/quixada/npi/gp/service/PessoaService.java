package ufc.quixada.npi.gp.service;

import java.util.List;

import ufc.quixada.npi.gp.model.Papel;
import ufc.quixada.npi.gp.model.Pessoa;
import br.ufc.quixada.npi.service.GenericService;

public interface PessoaService  extends GenericService<Pessoa> {

	Pessoa getPessoaByCpf(String cpf);
	
	Pessoa getPessoaById(Long id);

	List<Papel> getPapeis(String cpf);
	
}
