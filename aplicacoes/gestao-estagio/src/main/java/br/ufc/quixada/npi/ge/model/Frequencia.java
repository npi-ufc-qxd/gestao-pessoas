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

@Entity
public class Frequencia {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private Estagio estagio;

	@Temporal(TemporalType.DATE)
	private Date data;
	
	@Temporal(TemporalType.TIME)
	private Date horaEntrada;
	
	@Enumerated(EnumType.STRING)
	private StatusFrequencia statusEntrada;
	
	@Temporal(TemporalType.TIME)
	private Date horaSaida;
	
	@Enumerated(EnumType.STRING)
	private StatusFrequencia statusSaida;
	
	@Enumerated(EnumType.STRING)
	private TipoFrequencia tipo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Estagio getEstagio() {
		return estagio;
	}

	public void setEstagio(Estagio estagio) {
		this.estagio = estagio;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}
	
	public Date getHoraEntrada() {
		return horaEntrada;
	}

	public void setHoraEntrada(Date horarioEntrada) {
		this.horaEntrada = horarioEntrada;
	}

	public StatusFrequencia getStatusEntrada() {
		return statusEntrada;
	}

	public void setStatusEntrada(StatusFrequencia statusEntrada) {
		this.statusEntrada = statusEntrada;
	}

	public Date getHoraSaida() {
		return horaSaida;
	}

	public void setHoraSaida(Date horarioSaida) {
		this.horaSaida = horarioSaida;
	}

	public StatusFrequencia getStatusSaida() {
		return statusSaida;
	}

	public void setStatusSaida(StatusFrequencia statusSaida) {
		this.statusSaida = statusSaida;
	}

	public TipoFrequencia getTipo() {
		return tipo;
	}

	public void setTipo(TipoFrequencia tipo) {
		this.tipo = tipo;
	}

	public enum StatusFrequencia {
		PRESENTE("Presente"),
		FALTA("Falta"), 
		ATRASADO("Atrasado"), 
		ABONADO("Abonado"),
		AGUARDO ("Aguardo");
		

		private String descricao;
		
		private StatusFrequencia(String descricao) {
			this.descricao = descricao;
		}

		public String getDescricao() {
			return descricao;
		}
	}
	
	public enum TipoFrequencia {
		NORMAL("Normal"),
		REPOSICAO("Reposição");

		private String descricao;
		
		private TipoFrequencia(String descricao) {
			this.descricao = descricao;
		}

		public String getDescricao() {
			return descricao;
		}

	}
	
}
