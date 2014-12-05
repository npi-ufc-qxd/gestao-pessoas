package ufc.quixada.npi.gp.service;

import ufc.quixada.npi.gp.model.Pessoa;

public interface PessoaService extends GenericService<Pessoa> {

	public abstract Pessoa getPessoaByLogin(String login);

}
