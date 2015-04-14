package ufc.quixada.npi.gp.model;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import ufc.quixada.npi.gp.model.enums.Dia;

@Entity
public class Horario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Enumerated(EnumType.STRING)
	private Dia dia;
	
	@Temporal(TemporalType.TIME)
	@DateTimeFormat(pattern = "HH:mm")
	private Date inicioExpediente;
	
	@Temporal(TemporalType.TIME)
	@DateTimeFormat(pattern = "HH:mm")
	private Date finalExpediente;
	
	@Basic(fetch = FetchType.LAZY)
	@ManyToOne
	private Turma turma;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Dia getDia() {
		return dia;
	}

	public void setDia(Dia dia) {
		this.dia = dia;
	}

	public Date getInicioExpediente() {
		return inicioExpediente;
	}

	public void setInicioExpediente(Date inicioExpediente) {
		this.inicioExpediente = inicioExpediente;
	}

	public Date getFinalExpediente() {
		return finalExpediente;
	}

	public void setFinalExpediente(Date finalExpediente) {
		this.finalExpediente = finalExpediente;
	}
	
	public Turma getTurma() {
		return turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}
}
