package br.ufc.quixada.npi.ge.service.impl;

import java.util.Date;
import java.util.List;

import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;

import br.ufc.quixada.npi.ge.model.Evento;
import br.ufc.quixada.npi.ge.model.Expediente;
import br.ufc.quixada.npi.ge.model.Turma;
import br.ufc.quixada.npi.ge.model.Turma.TipoTurma;
import br.ufc.quixada.npi.ge.repository.EventoRepository;
import br.ufc.quixada.npi.ge.repository.ExpedienteRepository;
import br.ufc.quixada.npi.ge.repository.TurmaRepository;
import br.ufc.quixada.npi.ge.service.TurmaService;

@Named
public class TurmaServiceImpl implements TurmaService {
	
	@Autowired
	private TurmaRepository turmaRepository;

	@Autowired
	private ExpedienteRepository expedienteRepository;
	
	@Autowired 
	private EventoRepository eventoRepository;

	@Override
	public void adicionarTurma(Turma turma) {
		turmaRepository.save(turma);
	}

	@Override
	public void editarTurma(Turma turma) {
		turmaRepository.save(turma);
	}

	@Override
	public Turma buscarTurmaPorId(Long idTurma) {
		return turmaRepository.findOne(idTurma);
	}

	@Override
	public List<Turma> buscarTurmaPorTipoEServidor(TipoTurma tipoTurma, Long idServidor) {
		return turmaRepository.findByTipoTurma(tipoTurma, idServidor);
	}

	@Override
	public List<Turma> buscarTurmaPorTipoEServidorEStatus(TipoTurma tipoTurma, Long idServidor, Turma.StatusTurma statusTurma){
		return turmaRepository.findByTipoTurmaAndStatus(tipoTurma, idServidor, statusTurma);
	}
	
	@Override
	public List<Turma> buscarTurmasSupervisorOuOrientador(Long idServidor) {
		return turmaRepository.findByOrientador_IdOrSupervisores_Id(idServidor);
	}

	@Override
	public Turma buscarTurmaPorServidorId(Long idTurma, Long idServidor) {
		return turmaRepository.findByIdAndServidor(idTurma, idServidor);
	}
	
	@Override
	public List<Turma> buscarTurmasEncerradasEAbertasSupervisouOuOrientador(Long idServidor) {
		return turmaRepository.findByServidorIdAndStatusAndTermino(idServidor);
	}

	@Override
	public void adicionarEvento(Evento evento) {
		eventoRepository.save(evento);
	}

	@Override
	public void excluirEvento(Long idEvento) {
		eventoRepository.delete(idEvento);
	}
	
	@Override
	public void adicionarExpediente(Expediente expediente) {
		expedienteRepository.save(expediente);
	}

	@Override
	public void excluirExpediente(Long idExpediente) {
		expedienteRepository.delete(idExpediente);
		
	}

	@Override
	public Expediente buscarExpedienteConflitantePorTurma(Long idTurma, Expediente.DiaDaSemana diaSemana, Date inicio, Date termino) {
		return expedienteRepository.findExpedienteByTurmaId(idTurma, diaSemana, inicio, termino);
	}

}

