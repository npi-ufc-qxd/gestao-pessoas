package br.ufc.quixada.npi.gp.service;

import java.util.List;

import br.ufc.quixada.npi.gp.model.Evento;
import br.ufc.quixada.npi.gp.model.Expediente;
import br.ufc.quixada.npi.gp.model.Turma;


public interface TurmaService {

	/**
	 * 
	
	List<Turma>  getTurmasBySupervisorOrOrientador(Long idServidor);

	Horario getHorarioTurmaById(Long idHorario, Long idTurma);
	
	List<Evento> getEventosByTurma(Long idTurma);
	
	//crud turma
	void adicionarTurma(Turma turma);
	void editarTurma(Turma turma);
	void removerTurma(Long idTurma);
	Turma getTurma(Long idTurma);
	List<Turma> getAllTurmas();
	
	//crud evento
	void adicionarEvento(Evento evento);
	void editarEvento(Evento evento);
	void removerEvento(Long idEvento);
	Evento getEvento(Long idEvento);
	List<Evento> getAllEventosByTurma(Long idTurma);
	
	//save delete horario
	void adicionarHorario(Horario horario);
	void removerHorario(Long idHorario);
	

	 */

}