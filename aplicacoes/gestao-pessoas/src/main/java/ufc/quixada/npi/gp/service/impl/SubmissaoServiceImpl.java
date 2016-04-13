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

//migrar os métodos desse controller para o turmaService
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
	
	//verificar se essa consulta precisa ser mudada
	@Override
	public Submissao getSubmissaoByEstagiarioIdAndIdTurmaAndTipo(Long idEstagiario, Long idTurma, Tipo tipo) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("idEstagiario", idEstagiario);
		params.put("idTurma", idTurma);
		params.put("tipo", tipo);
		@SuppressWarnings("unchecked")
		Submissao submissao = (Submissao) findFirst(QueryType.JPQL,"select s from Subissao s where s.estagiario.id = :idEstagiario and s.turma.id = :idTurma and s.tipo = :tipo", params);

		return submissao;
	}
	
	//verificar se essa consulta precisa ser mudada
	@Override
	public List<Submissao> getSubmissoesByEstagiarioIdAndIdTurma(Long idEstagiario, Long idTurma) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("idEstagiario", idEstagiario);
		params.put("idTurma", idTurma);
		@SuppressWarnings("unchecked")
		List <Submissao> submissoes = find(QueryType.JPQL,"select s from Submissao s where s.estagiario.id = :idEstagiario and s.turma.id = :idTurma", params);

		return submissoes;
	}
}
