package ufc.quixada.npi.gp.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import ufc.quixada.npi.gp.model.Papel;
import ufc.quixada.npi.gp.model.Pessoa;
import ufc.quixada.npi.gp.repository.PessoaRepository;
import ufc.quixada.npi.gp.repository.QueryType;
import ufc.quixada.npi.gp.service.PessoaService;

@Named
public class PessoaServiceImpl extends GenericServiceImpl<Pessoa> implements
		PessoaService {

	@Inject
	PessoaRepository pessoaRepository;

	@Override
	public Pessoa getUsuarioByLogin(String login) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("login", login);
		Pessoa usuariologado = pessoaRepository.find(QueryType.JPQL,
				"from Pessoa where login = :login", params).get(0);
		return usuariologado;
	}

	@Override
	public List<Pessoa> getPareceristas(Long id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		List<Pessoa> usuarios = pessoaRepository.find(QueryType.JPQL,
				"from Pessoa u where u.id != :id", params);
		return usuarios;
	}

	@Override
	public boolean isDiretor(Pessoa usuario) {
		List<Papel> papeis = usuario.getPapeis();
		for (Papel p : papeis) {
			if (p.getNome().equals("ROLE_DIRETOR")) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Pessoa getDiretor() {
		Pessoa diretor = new Pessoa();
		List<Pessoa> usuarios = find(Pessoa.class);
		for (Pessoa u : usuarios) {
			if (isDiretor(u)) {
				diretor = u;
				break;
			}
		}
		return diretor;
	}

	@Override
	public List<Pessoa> getParticipantes() {
		List<Pessoa> participantes = pessoaRepository.find(Pessoa.class);
		return participantes;
	}

	@Override
	public Pessoa getPessoaByNome(String nome) {
		Pessoa pessoa = null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("nome", nome);
		List<Pessoa> listaPessoas = pessoaRepository.find(QueryType.JPQL,
				"from Pessoa where nome = :nome", params);
		if (listaPessoas.isEmpty() == false) {
			pessoa = listaPessoas.get(0);
		}
		return pessoa;
	}
}
