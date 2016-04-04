package ufc.quixada.npi.gp.utils;

import java.util.Calendar;
import java.util.List;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import ufc.quixada.npi.gp.model.Horario;

public class UtilGestao {
	public static boolean isHoraPermitida(List<Horario> horarios) {
		LocalTime horaAtual = new LocalTime();

		for (Horario horario : horarios) {
			
			LocalTime inicio = new LocalTime(horario.getInicioExpediente());
			LocalTime fim = new LocalTime(horario.getFinalExpediente());
			
			if ( horaAtual.equals(inicio) || horaAtual.isAfter(inicio) && 
			   ( horaAtual.equals(fim) 	  || horaAtual.isBefore(fim))) {
				return true;
			}
		}

		return false;
	}
	
	public static boolean hojeEDiaDeTrabahoDaTurma(List<Horario> horarios) {
		LocalDate dia = new LocalDate();

		for (Horario horario : horarios) {
			if (horario.getDia().getDia() == dia.getDayOfWeek()) {
				return true;
			}
		}
		
		return false;
	}

	public static boolean isDiaDeTrabahoDaTurma(List<Horario> horarios, LocalDate dia) {
		for (Horario horario : horarios) {
			if (horario.getDia().getDia() == dia.getDayOfWeek()) {
				return true;
			}
		}
		return false;
	}	

	public static String getTurnoExpediente(Horario horario) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(horario.getInicioExpediente());

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
