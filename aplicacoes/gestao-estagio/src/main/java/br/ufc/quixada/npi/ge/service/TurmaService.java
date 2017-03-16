package br.ufc.quixada.npi.ge.service;

import java.util.Date;
import java.util.List;

import br.ufc.quixada.npi.ge.model.Evento;
import br.ufc.quixada.npi.ge.model.Expediente;
import br.ufc.quixada.npi.ge.model.Turma;
import br.ufc.quixada.npi.ge.model.Turma.TipoTurma;


public interface TurmaService {

	void adicionarTurma(Turma turma);

	void editarTurma(Turma turma);
	
	Turma buscarTurmaPorId(Long idTurma);
	
	List<Turma> buscarTurmaPorTipoEServidor(TipoTurma tipoTurma, Long idServidor);
	
	List<Turma> buscarTurmaPorTipoEServidorEStatus(TipoTurma tipoTurma, Long idServidor, Turma.StatusTurma statusTurma);

	List<Turma>  buscarTurmasSupervisorOuOrientador(Long idServidor);
	
	List <Turma> buscarTurmasEncerradasEAbertasSupervisouOuOrientador(Long idServidor);
	
	Turma buscarTurmaPorServidorId(Long idTurma, Long idServidor);
	
	void adicionarEvento(Evento evento);

	void excluirEvento(Long idEvento);
	
	void adicionarExpediente(Expediente expediente);

	void excluirExpediente(Long idExpediente);

	Expediente buscarExpedienteConflitantePorTurma(Long idTurma, Expediente.DiaDaSemana diaSemana , Date inicio, Date termino);

}