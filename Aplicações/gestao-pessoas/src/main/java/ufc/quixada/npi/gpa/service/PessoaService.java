package ufc.quixada.npi.gpa.service;

import java.util.List;

import ufc.quixada.npi.gpa.model.Pessoa;

public interface PessoaService extends GenericService<Pessoa> {

	Pessoa getUsuarioByLogin(String login);

	List<Pessoa> getPareceristas(Long id);

	boolean isDiretor(Pessoa usuario);

	Pessoa getDiretor();

	List<Pessoa> getParticipantes();

	Pessoa getPessoaByNome(String nome);
}
