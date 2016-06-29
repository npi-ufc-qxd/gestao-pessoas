package br.ufc.quixada.npi.ge.service;

import java.util.List;

import br.ufc.quixada.npi.ge.model.Evento;
import br.ufc.quixada.npi.ge.model.Expediente;
import br.ufc.quixada.npi.ge.model.Turma;


public interface TurmaService {

	void adicionarTurma(Turma turma);

	void editarTurma(Turma turma);
	
	void excluirTurma(Long idTurma);
	
	Turma buscarTurmaPorId(Long idTurma);
	
	List<Turma> listarTurmas();

	List<Turma>  buscarTurmasSupervisorOuOrientador(Long idServidor);
	
	List <Turma> buscarTurmasEncerradasEAbertasSupervisouOuOrientador(Long idServidor);
	
	Turma buscarTurmaPorServidorId(Long idTurma, Long idServidor);
	
	void adicionarEvento(Evento evento);

	void editarEvento(Evento evento);
	
	void excluirEvento(Long idEvento);
	
	Evento buscarEventoPorId(Long idEvento);

	Expediente buscarHorarioPorIdETurmaId(Long idExpediente, Long idTurma);
	
	void adicionarExpediente(Expediente expediente);

	void excluirExpediente(Long idExpediente);

}