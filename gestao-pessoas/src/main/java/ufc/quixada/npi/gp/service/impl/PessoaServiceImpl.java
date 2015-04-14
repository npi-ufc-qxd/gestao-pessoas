package ufc.quixada.npi.gp.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import ufc.quixada.npi.gp.model.Pessoa;
import ufc.quixada.npi.gp.service.PessoaService;
import br.ufc.quixada.npi.enumeration.QueryType;
import br.ufc.quixada.npi.repository.GenericRepository;
import br.ufc.quixada.npi.service.impl.GenericServiceImpl;

@Named
public class PessoaServiceImpl extends GenericServiceImpl<Pessoa> implements
		PessoaService {

	@Inject
	GenericRepository<Pessoa> pessoaRepository;

	@Override
	public Pessoa getPessoaByCPF(String cpf) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cpf", cpf);
		Pessoa usuariologado = pessoaRepository.find(QueryType.JPQL, "from Pessoa where cpf = :cpf", params).get(0);
		return usuariologado;
	}
}
