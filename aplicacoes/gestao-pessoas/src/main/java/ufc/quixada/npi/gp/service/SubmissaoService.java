package ufc.quixada.npi.gp.service;

import java.util.List;

import ufc.quixada.npi.gp.model.Submissao;
import ufc.quixada.npi.gp.model.Frequencia;


public interface SubmissaoService {
	
	void salvar(Submissao submissao);
	
	void salvar(List<Submissao> submissoes);
	
	Submissao getSubmissaoById(Long id);
	
	List<Submissao> getSubmissoesByEstagiarioId(Long idEstagiario, Long idTurma);
	
	void remover(Submissao submissao);
	
}
