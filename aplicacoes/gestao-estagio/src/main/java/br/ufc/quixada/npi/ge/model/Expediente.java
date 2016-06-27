package br.ufc.quixada.npi.ge.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Expediente {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private Turma turma;

	@NotNull(message = "Dia Da semana tem que ser preenchido.")
	@Enumerated(EnumType.STRING)
	private DiaDaSemana diaSemana;

	@NotNull (message = "Horário tem que ser preenchido.")
	@Temporal(TemporalType.TIME)
	@DateTimeFormat(pattern = "HH:mm")
	private Date horaInicio;

	@NotNull(message = "Horário tem que ser preenchido.")
	@Temporal(TemporalType.TIME)
	@DateTimeFormat(pattern = "HH:mm")
	private Date horaTermino;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Turma getTurma() {
		return turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}

	public DiaDaSemana getDiaSemana() {
		return diaSemana;
	}

	public void setDiaSemana(DiaDaSemana diaSemana) {
		this.diaSemana = diaSemana;
	}

	public Date getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(Date horaInicio) {
		this.horaInicio = horaInicio;
	}

	public Date getHoraTermino() {
		return horaTermino;
	}

	public void setHoraTermino(Date horaTermino) {
		this.horaTermino = horaTermino;
	}

	public enum DiaDaSemana {
		SEGUNDA("Segunda-Feira", 1), 
		TERCA("Terça-Feira", 2), 
		QUARTA("Quarta-Feira", 3), 
		QUINTA("Quinta-Feira", 4), 
		SEXTA("Sexta-Feira", 5);

		private String descricao;
		private int dia;

		private DiaDaSemana(String descricao, int dia) {
			this.descricao = descricao;
			this.dia = dia;
		}

		public String getDescricao() {
			return descricao;
		}

		public int getDia() {
			return dia;
		}

	}
}
