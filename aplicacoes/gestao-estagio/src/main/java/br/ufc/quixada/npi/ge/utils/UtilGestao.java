package br.ufc.quixada.npi.ge.utils;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.Calendar;
import java.util.List;

import org.joda.time.Hours;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.joda.time.Minutes;

import br.ufc.quixada.npi.ge.model.Expediente;

public class UtilGestao {
	public static boolean isHoraPermitida(List<Expediente> horarios) {
		LocalTime horaAtual = new LocalTime();

		for (Expediente horario : horarios) {
			
			LocalTime inicio = new LocalTime(horario.getHoraInicio());
			LocalTime fim = new LocalTime(horario.getHoraTermino());
			
			if ( horaAtual.equals(inicio) || horaAtual.isAfter(inicio) && 
			   ( horaAtual.equals(fim) 	  || horaAtual.isBefore(fim))) {
				return true;
			}
		}

		return false;
	}
	
	public static boolean hojeEDiaDeTrabahoDaTurma(List<Expediente> horarios) {
		LocalDate dia = new LocalDate();

		for (Expediente horario : horarios) {
			if (horario.getDiaSemana().getDia() == dia.getDayOfWeek()) {
				return true;
			}
		}
		
		return false;
	}

	public static boolean isDiaDeTrabahoDaTurma(List<Expediente> horarios, LocalDate dia) {
		for (Expediente horario : horarios) {
			if (horario.getDiaSemana().getDia() == dia.getDayOfWeek()) {
				return true;
			}
		}
		return false;
	}	


	public static Expediente getExpedientePorData(List<Expediente> horarios, LocalDate dia) {
		for (Expediente horario : horarios) {
			if (horario.getDiaSemana().getDia() == dia.getDayOfWeek()) {
				return horario;
			}
		}
		return null;
	}
	
	public static int getTotalMinutosDiaExpediente(List<Expediente> horarios, LocalDate dia) {
		for (Expediente horario : horarios) {
			if (horario.getDiaSemana().getDia() == dia.getDayOfWeek()) {
				LocalDateTime inicio = new LocalDateTime(horario.getHoraInicio());
				LocalDateTime termino = new LocalDateTime(horario.getHoraTermino());
				return Minutes.minutesBetween(inicio, termino).getMinutes();
			}
		}
		return 0;
	}

	public static int getTotalMinutosDiaExpediente(Expediente expediente) {
		LocalDateTime inicio = new LocalDateTime(expediente.getHoraInicio());
		LocalDateTime termino = new LocalDateTime(expediente.getHoraTermino());
		
		
		return Minutes.minutesBetween(inicio, termino).getMinutes();
	}

	public static String getTurnoExpediente(Expediente horario) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(horario.getHoraInicio());

		int hora = calendar.get(Calendar.HOUR_OF_DAY); 

		if (hora >= 6 && hora < 12) {
			return "MANHA";
		}
		else if (hora >= 12 && hora < 18) {
			return "TARDE";
		}
		else if (hora >= 18 && hora < 24) {
			return "NOITE";
		}
		
		return "";
	}

}
