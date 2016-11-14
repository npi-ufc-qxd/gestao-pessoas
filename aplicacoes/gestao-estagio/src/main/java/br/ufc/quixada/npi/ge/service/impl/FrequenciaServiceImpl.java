package br.ufc.quixada.npi.ge.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Named;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.Period;
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
			Presenca presenca = liberarPresencaEntrada(frequencia, estagio);
			if(presenca != null) {
				presencas.add(presenca);
			}
		} else if (Frequencia.StatusFrequencia.AGUARDO_SAIDA.equals(frequencia.getStatus())) {
			Presenca presenca = liberarPresencaSaida(frequencia, estagio);
			if(presenca != null) {
				presencas.add(presenca);
			}
		}

		Frequencia frequenciaReposicao = frequenciaRepository.findFrequenciaDeHojeByEstagioETipo(estagio, Frequencia.TipoFrequencia.REPOSICAO);

		if (frequenciaReposicao != null) {
			if (frequenciaReposicao.getStatus() == null) {
				Presenca presenca = liberarPresencaEntradaReposicao(frequenciaReposicao, estagio); 
				if(presenca != null) {
					presencas.add(presenca);
				}
			}

			if (Frequencia.StatusFrequencia.AGUARDO_SAIDA.equals(frequenciaReposicao.getStatus())) {
				Presenca presenca = liberarPresencaSaidaReposicao(frequenciaReposicao, estagio); 
				if(presenca != null) {
					presencas.add(presenca);
				}
			}
		}

		return presencas;
	}

	private Presenca liberarPresencaEntrada(Frequencia frequencia, Estagio estagio) {

		Expediente expediente = getExpedienteDoDia(estagio);
		
		if(expediente != null) {
			Presenca presenca = new Presenca();
			presenca.setFrequencia(frequencia);
			presenca.setPermissaoSaida(false);
			presenca.setPermissaoEntrada(isHoraEntrada(expediente.getHoraInicio(), TOLERANCIA_MINUTOS));

			return presenca;
		}

		return null;
	}

	private Presenca liberarPresencaEntradaReposicao(Frequencia frequencia, Estagio estagio) {

		boolean horaEntrada = isHoraEntrada(frequencia.getHoraAgendamentoEntrada(), TOLERANCIA_MINUTOS);

		if(horaEntrada) {
			Presenca presenca = new Presenca();
			presenca.setFrequencia(frequencia);
			presenca.setPermissaoSaida(false);
			presenca.setPermissaoEntrada(horaEntrada);
			return presenca;
		}
		
		return null;
		
	}

	private Presenca liberarPresencaSaida(Frequencia frequencia, Estagio estagio) {
		Expediente expediente = getExpedienteDoDia(estagio);

		if(expediente != null) {
			Presenca presenca = new Presenca();
			presenca.setFrequencia(frequencia);
			presenca.setPermissaoEntrada(false);
			presenca.setPermissaoSaida(isHoraSaida(expediente.getHoraTermino(), TOLERANCIA_MINUTOS));
			return presenca;
		}

		return null;
	}

	private Presenca liberarPresencaSaidaReposicao(Frequencia frequencia, Estagio estagio) {
		boolean horaSaida = isHoraSaida(frequencia.getHoraAgendamentoSaida(), TOLERANCIA_MINUTOS);
		
		if(horaSaida) {
			Presenca presenca = new Presenca();
			presenca.setFrequencia(frequencia);
			presenca.setPermissaoEntrada(false);
			presenca.setPermissaoSaida(horaSaida);
			return presenca;
		}
		
		return null;

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
		Frequencia frequencia = frequenciaRepository.findFrequenciaDeHojeByEstagioETipo(estagio, Frequencia.TipoFrequencia.NORMAL);


		if (frequencia != null && Frequencia.StatusFrequencia.AGUARDO_SAIDA.equals(frequencia.getStatus())) {

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
			frequencia.setStatus(Frequencia.StatusFrequencia.AGUARDO_SAIDA);
			frequencia.setHoraEntrada(new Date());
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
	
	private int calcularAtrasoDaSaidaAntecipada(LocalTime horaTerminoExpediente, LocalTime horaSaida) {

		LocalTime horaTerminoMenosTolerancia = horaTerminoExpediente.minusMinutes(TOLERANCIA_MINUTOS);

		int atrasoMinutos = 0;

		if (!horaSaida.isAfter(horaTerminoMenosTolerancia)) {
			Period period = new Period(horaSaida, horaTerminoMenosTolerancia);
			atrasoMinutos = period.getMinutes() + converterHorasEmMinutos(period.getHours());
		}

		return atrasoMinutos;
	}

	private int calcularAtrasoEntrada(LocalTime horaInicioExpediente, LocalTime horaEntrada) {

		LocalTime horaInicioMaisTolerancia = horaInicioExpediente.plusMinutes(TOLERANCIA_MINUTOS);

		int atrasoMinutos = 0;

		if (!horaEntrada.isBefore(horaInicioMaisTolerancia)) {
			Period period = new Period(horaInicioMaisTolerancia, horaEntrada);
			atrasoMinutos = period.getMinutes() + converterHorasEmMinutos(period.getHours());
		}

		return atrasoMinutos;
	}

	private static int converterHorasEmMinutos(int horas) {
		return horas * 60;
	}
	
	@Override
	public Frequencia buscarReposicao(Estagio estagio) {
		Frequencia frequenciaReposicao = frequenciaRepository.findFrequenciaDeHojeByEstagioETipo(estagio, Frequencia.TipoFrequencia.REPOSICAO);
		return frequenciaReposicao;
	}

}
