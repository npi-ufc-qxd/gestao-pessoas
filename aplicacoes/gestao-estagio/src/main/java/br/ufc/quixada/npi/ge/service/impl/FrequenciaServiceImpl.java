package br.ufc.quixada.npi.ge.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Named;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.springframework.beans.factory.annotation.Autowired;

import br.ufc.quixada.npi.ge.model.Estagio;
import br.ufc.quixada.npi.ge.model.Expediente;
import br.ufc.quixada.npi.ge.model.Frequencia;
import br.ufc.quixada.npi.ge.model.Frequencia.TipoFrequencia;
import br.ufc.quixada.npi.ge.model.Presenca;
import br.ufc.quixada.npi.ge.repository.FrequenciaRepository;
import br.ufc.quixada.npi.ge.service.FrequenciaService;

@Named
public class FrequenciaServiceImpl implements FrequenciaService {

	@Autowired
	private FrequenciaRepository frequenciaRepository;

	private final static int TOLERANCIA_MINUTOS = 10;

	@Override
	public List<Presenca> permitirPresencaEstagio(Estagio estagio) {

		List<Presenca> presencas = new ArrayList<Presenca>();

		Frequencia frequencia = frequenciaRepository.findFrequenciaDeHojeByEstagioETipo(estagio, Frequencia.TipoFrequencia.NORMAL);

		if (frequencia == null) {
			frequencia = new Frequencia();
			frequencia.setTipo(Frequencia.TipoFrequencia.NORMAL);
			presencas.add(liberarPresencaEntrada(frequencia, estagio));
		} else if (Frequencia.StatusFrequencia.AGUARDO_SAIDA.equals(frequencia.getStatus())) {
			presencas.add(liberarPresencaSaida(frequencia, estagio));
		}

		Frequencia frequenciaReposicao = frequenciaRepository.findFrequenciaDeHojeByEstagioETipo(estagio, Frequencia.TipoFrequencia.REPOSICAO);

		if (frequenciaReposicao != null) {
			if (frequenciaReposicao.getStatus() == null) {
				presencas.add(liberarPresencaEntrada(frequenciaReposicao, estagio));
			}

			if (Frequencia.StatusFrequencia.AGUARDO_SAIDA.equals(frequenciaReposicao.getStatus())) {
				presencas.add(liberarPresencaSaida(frequenciaReposicao, estagio));
			}
		}

		return presencas;
	}

	private Presenca liberarPresencaEntrada(Frequencia frequencia, Estagio estagio) {

		Presenca presenca = new Presenca();
		presenca.setFrequencia(frequencia);

		presenca.setPermissaoSaida(false);

		Expediente expediente = getExpedienteDoDia(estagio);
		presenca.setPermissaoEntrada(isHoraEntrada(expediente.getHoraInicio(), 1));
		
		return presenca;
	}

	private Presenca liberarPresencaSaida(Frequencia frequencia, Estagio estagio) {
		Presenca presenca = new Presenca();
		presenca.setFrequencia(frequencia);

		presenca.setPermissaoEntrada(false);
		
		Expediente expediente = getExpedienteDoDia(estagio);
		presenca.setPermissaoSaida(isHoraSaida(expediente.getHoraTermino(), 1));

		return presenca;
	}

	private Expediente getExpedienteDoDia(Estagio estagio) {
		
		List<Expediente> expedientes = new ArrayList<Expediente>();
		
		if(estagio.getExpedientes() != null && estagio.getExpedientes().size() > 0) {
			expedientes = estagio.getExpedientes(); 
		} else if (estagio.getTurma().getExpedientes() != null  && estagio.getTurma().getExpedientes().size() > 0) {
			expedientes = estagio.getTurma().getExpedientes(); 
		}

		LocalDate dia = new LocalDate();

		for (Expediente expediente : expedientes) {
			if (expediente.getDiaSemana().getDia() == dia.getDayOfWeek()) {
				return expediente;
			}
		}

		return null;
	}

	private boolean isHoraEntrada(Date horaEntrada, int tolerancia) {

		LocalTime inicio = new LocalTime(horaEntrada);
		LocalTime horaAtual = new LocalTime();
		
		if ( horaAtual.equals(inicio.minusMinutes(tolerancia)) || horaAtual.isAfter(inicio.minusMinutes(tolerancia))) {
			return true;
		}

		return false;
	}
	
	
	private boolean isHoraSaida(Date horaSaida, int tolerancia) {

		LocalTime termino = new LocalTime(horaSaida);
		LocalTime horaAtual = new LocalTime();
		
		if ( horaAtual.equals(termino.plusMinutes(tolerancia)) || horaAtual.isBefore(termino.plusMinutes(tolerancia))) {
			return true;
		}

		return false;
	}	
	
	@Override
	public boolean realizarEntrada(Estagio estagio) {

		Frequencia frequencia = frequenciaRepository.findFrequenciaDeHojeByEstagioETipo(estagio, Frequencia.TipoFrequencia.NORMAL);
		
		final int TOLERANCIA_MINUTOS = 10;

		if (frequencia == null) {
			Expediente expediente = getExpedienteDoDia(estagio);

			if(isHoraEntrada(expediente.getHoraInicio(), TOLERANCIA_MINUTOS)) {
				frequencia = new Frequencia();
				frequencia.setEstagio(estagio);
				frequencia.setStatus(Frequencia.StatusFrequencia.AGUARDO_SAIDA);
				frequencia.setData(new Date());
				frequencia.setHoraEntrada(new Date());
				frequencia.setTipo(TipoFrequencia.NORMAL);

				frequenciaRepository.save(frequencia);
				return true;
				
			}
		}
		return false;

	}

	@Override
	public boolean realizarSaida(Estagio estagio) {
		Frequencia frequencia = frequenciaRepository.findFrequenciaDeHojeByEstagio(estagio);

		if (frequencia != null && Frequencia.StatusFrequencia.AGUARDO_SAIDA.equals(frequencia.getStatus())) {

			final int TOLERANCIA_MINUTOS = 10;

			Expediente expediente = getExpedienteDoDia(estagio);

			if(isHoraSaida(expediente.getHoraTermino(), TOLERANCIA_MINUTOS)) {

				frequencia.setStatus(Frequencia.StatusFrequencia.PRESENTE);
				frequencia.setHoraSaida(new Date());
				frequenciaRepository.save(frequencia);

				return true;
			}
		}

		return false;

	}
	
	@Override
	public boolean realizarEntradaReposicao(Frequencia frequencia) {
		if (isHoraEntrada(frequencia.getHoraAgendamentoEntrada(), TOLERANCIA_MINUTOS)) {
			frequencia = new Frequencia();
			frequencia.setStatus(Frequencia.StatusFrequencia.AGUARDO_SAIDA);
			frequencia.setData(new Date());
			frequencia.setHoraEntrada(new Date());
			frequencia.setTipo(TipoFrequencia.NORMAL);
			frequenciaRepository.save(frequencia);
			return true;

		}
		return false;

	}

	@Override
	public boolean realizarSaidaReposicao(Frequencia frequencia) {
		if (frequencia != null && Frequencia.StatusFrequencia.AGUARDO_SAIDA.equals(frequencia.getStatus())) {
			if(isHoraSaida(frequencia.getHoraAgendamentoSaida(), TOLERANCIA_MINUTOS)) {
				frequencia.setStatus(Frequencia.StatusFrequencia.PRESENTE);
				frequencia.setHoraSaida(new Date());
				frequenciaRepository.save(frequencia);
				return true;
			}
		}
		return false;

	}

}
