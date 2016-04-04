package ufc.quixada.npi.gp.service;

import java.util.List;

import ufc.quixada.npi.gp.model.Estagiario;
import ufc.quixada.npi.gp.model.Pessoa;
import br.ufc.quixada.npi.service.GenericService;

public interface EstagiarioService extends GenericService<Estagiario> {

	Estagiario getEstagiarioByPessoaId(Long id);
	
	Pessoa getPessoaByEstagiarioId(Long id);

	Estagiario getEstagiarioByPessoaCpf(String cpf);
	
	List<Estagiario> getEstagiarioByTurmaId(Long id);
	
	List<Estagiario> getEstagiarioByNotTurmaIdOrSemTurma(Long id);
	
	boolean possuiTurmaAtiva(String cpf);

}
