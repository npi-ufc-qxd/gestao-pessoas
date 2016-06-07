package br.ufc.quixada.npi.gp.utils;

import java.util.Calendar;
import java.util.List;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import br.ufc.quixada.npi.gp.model.Expediente;

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
