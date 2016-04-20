package ufc.quixada.npi.gp.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import br.ufc.quixada.npi.service.GenericService;
import ufc.quixada.npi.gp.model.Estagiario;
import ufc.quixada.npi.gp.model.Submissao;
import ufc.quixada.npi.gp.model.Turma;
import ufc.quixada.npi.gp.model.enums.StatusTurma;
import ufc.quixada.npi.gp.model.enums.Tipo;


public interface TurmaService extends GenericService<Turma> {

	List<Turma> getTurmasBySupervisorId(Long idSupervisor);

	List<Turma> getTurmasBySupervisorIdAndStatus(StatusTurma statusTurma, Long idSupervisor);

	List<Turma> getTurmasByEstagiarioIdAndStatus(StatusTurma statusTurma, Long idSupervisor);

	Turma getTurmaByIdAndEstagiarioId(Long idTurma, Long idEstagiario);

	Turma getTurmaByIdAndSupervisorById(Long idTurma, Long idSupervisor);

	List<Turma> getTurmasByEstagiarioId(Long idEstagiario);
	
	void submeterDocumento(Estagiario estagiario, Turma turma, Tipo tipo, MultipartFile anexo) throws IOException;
	
	//métodos que antes eram de submissaoService 
	void salvar(Submissao submissao);
	
	//void salvar(List<Submissao> submissoes);
	
	Submissao getSubmissaoById(Long id);
	
	void remover(Submissao submissao);
	
	Submissao getSubmissaoByEstagiarioIdAndIdTurmaAndTipo(Long idEstagiario, Long idTurma, Tipo tipo);
	
	List<Submissao> getSubmissoesByEstagiarioIdAndIdTurma(Long idEstagiario, Long idTurma);

}
