package ufc.quixada.npi.gp.utils;

import java.util.List;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import ufc.quixada.npi.gp.model.Horario;
import ufc.quixada.npi.gp.model.Turma;

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
	
	public static boolean isDiaTrabaho(List<Horario> horarios) {
		LocalDate dia = new LocalDate();

		for (Horario horario : horarios) {
			System.out.println("dia " + dia.getDayOfWeek());
			if (horario.getDia().getDia() == dia.getDayOfWeek()) {
				return true;
			}
		}
		
		return false;
	}

	public static boolean isDiaTrabahoTurma(List<Horario> horarios, LocalDate dia) {

		for (Horario horario : horarios) {
			System.out.println("dia " + dia.getDayOfWeek());
			if (horario.getDia().getDia() == dia.getDayOfWeek()) {
				return true;
			}
		}
		
		return false;
	}
	
	

}
