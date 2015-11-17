package ufc.quixada.npi.gp.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;

import ufc.quixada.npi.gp.model.Submissao;
import ufc.quixada.npi.gp.service.SubmissaoService;
import br.ufc.quixada.npi.enumeration.QueryType;
import br.ufc.quixada.npi.repository.GenericRepository;
import br.ufc.quixada.npi.service.impl.GenericServiceImpl;

@Named
public class SubmissaoServiceImpl extends GenericServiceImpl<Submissao> implements SubmissaoService {

	@Autowired
	private GenericRepository<Submissao> submissaoRepository;

	@Override
	public void salvar(Submissao submissao) {
		submissaoRepository.save(submissao);
	}

	@Override
	public void salvar(List<Submissao> submissoes) {
		for (Submissao submissao : submissoes) {
			submissaoRepository.save(submissao);
		}
	}

	@Override
	public Submissao getSubmissaoById(Long id) {
		return submissaoRepository.find(Submissao.class, id);
	}

	@Override
	public List<Submissao> getSubmissoesByEstagiarioId(Long idEstagiario, Long idTurma) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("idPessoa", idEstagiario);
		params.put("idTurma", idTurma);
		@SuppressWarnings("unchecked")
		List<Submissao> submissoes = find(QueryType.JPQL, "select d from Submissao d where d.pessoa.id = :idPessoa and d.turma.id = :idTurma ORDER BY data ASC", params);

		return submissoes;
	}

	@Override
	public void remover(Submissao submissao) {
		submissaoRepository.delete(submissao);
	}

}
