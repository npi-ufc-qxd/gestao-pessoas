package ufc.quixada.npi.gp.service;

import java.util.List;

import ufc.quixada.npi.gp.model.Submissao;
import ufc.quixada.npi.gp.model.enums.Tipo;
import br.ufc.quixada.npi.service.GenericService;


public interface SubmissaoService extends GenericService<Submissao>  {
	
	void salvar(Submissao submissao);
	
	void salvar(List<Submissao> submissoes);
	
	Submissao getSubmissaoById(Long id);
	
	void remover(Submissao submissao);
	
	Submissao getSubmissaoByEstagiarioIdAndIdTurmaAndTipo(Long idEstagiario, Long idTurma, Tipo tipo);
	
	List<Submissao> getSubmissoesByEstagiarioIdAndIdTurma(Long idEstagiario, Long idTurma);
	
}
