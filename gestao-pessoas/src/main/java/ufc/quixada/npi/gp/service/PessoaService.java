package ufc.quixada.npi.gp.service;

import ufc.quixada.npi.gp.model.Pessoa;
import br.ufc.quixada.npi.service.GenericService;

public interface PessoaService extends GenericService<Pessoa> {

	Pessoa getPessoaByCPF(String cpf);

}
