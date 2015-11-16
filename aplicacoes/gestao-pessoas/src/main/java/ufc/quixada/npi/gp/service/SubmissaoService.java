package ufc.quixada.npi.gp.service;

import java.util.List;

import br.ufc.quixada.npi.service.GenericService;
import ufc.quixada.npi.gp.model.Submissao;
import ufc.quixada.npi.gp.model.enums.Tipo;


public interface SubmissaoService extends GenericService<Submissao>  {
	
	void salvar(Submissao documento);
	
	void salvar(List<Submissao> documentos);
	
	Submissao getSubmissaoById(Long id);
	
	void remover(Submissao documento);
	
	Submissao getSubmissaoByPessoaIdAndIdTurmaAndTipo(Long idPessoa, Long idTurma, Tipo tipo);
	
	List<Submissao> getSubmissoesByPessoaIdAndIdTurma(Long idPessoa, Long idTurma);
	
}
