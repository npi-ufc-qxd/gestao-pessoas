package ufc.quixada.npi.gp.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;

import br.ufc.quixada.npi.enumeration.QueryType;
import br.ufc.quixada.npi.repository.GenericRepository;
import br.ufc.quixada.npi.service.impl.GenericServiceImpl;
import ufc.quixada.npi.gp.model.Submissao;
import ufc.quixada.npi.gp.model.enums.Tipo;
import ufc.quixada.npi.gp.service.SubmissaoService;

@Named
public class SubmissaoServiceImpl extends GenericServiceImpl<Submissao> implements SubmissaoService {
	
	@Autowired
	private GenericRepository<Submissao> submissaoRepository;

	@Override
	public void salvar(Submissao submissao) {
		submissaoRepository.save(submissao);
	}
	
	@Override
	public void salvar(List<Submissao> submissaos) {
		for(Submissao submissao : submissaos) {
			submissaoRepository.save(submissao);
		}
	}

	@Override
	public Submissao getSubmissaoById(Long id) {
		return submissaoRepository.find(Submissao.class, id);
	}

	@Override
	public void remover(Submissao submissao) {
		submissaoRepository.delete(submissao);
	}
	
	@Override
	public Submissao getSubmissaoByPessoaIdAndIdTurmaAndTipo(Long idPessoa, Long idTurma, Tipo tipo) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("idPessoa", idPessoa);
		params.put("idTurma", idTurma);
		params.put("tipo", tipo);
		Submissao submissao = (Submissao) findFirst(QueryType.JPQL,"select s from Submissao s where s.pessoa.id = :idPessoa and s.turma.id = :idTurma and s.tipo = :tipo", params);

		return submissao;
	}
	
	@Override
	public Submissao getSubmissaoByPessoaIdAndIdTurma(Long idPessoa, Long idTurma) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("idPessoa", idPessoa);
		params.put("idTurma", idTurma);
		Submissao submissao = (Submissao) findFirst(QueryType.JPQL,"select s from Submissao s where s.pessoa.id = :idPessoa and s.turma.id = :idTurma", params);

		return submissao;
	}
	
	@Override
	public List<Submissao> getSubmissoesByPessoaIdAndIdTurma(Long idPessoa, Long idTurma) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("idPessoa", idPessoa);
		params.put("idTurma", idTurma);
		@SuppressWarnings("unchecked")
		List <Submissao> submissoes = find(QueryType.JPQL,"select s from Submissao s where s.pessoa.id = :idPessoa and s.turma.id = :idTurma", params);

		return submissoes;
	}
}
