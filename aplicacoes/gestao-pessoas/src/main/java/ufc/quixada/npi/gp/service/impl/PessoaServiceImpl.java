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
	public boolean isCoordenador(Pessoa usuario) {
		List<Papel> papeis = usuario.getPapeis();
		for (Papel p : papeis) {
			if (p.getNome().equals("ROLE_COORDENADOR")) {
				return true;
			}
		}
		return false;
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
