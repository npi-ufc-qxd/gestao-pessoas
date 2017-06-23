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
import javax.persistence.Transient;

import org.joda.time.LocalDateTime;
import org.joda.time.Minutes;

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
	private StatusFrequencia status;
	
	@Temporal(TemporalType.TIME)
	private Date horaSaida;

	@Enumerated(EnumType.STRING)
	private TipoFrequencia tipo;

	@Temporal(TemporalType.TIME)
	private Date horaAgendamentoEntrada;
	
	@Temporal(TemporalType.TIME)
	private Date horaAgendamentoSaida;

	public Frequencia() {
		LocalDateTime inicio = new LocalDateTime(horaEntrada);
		LocalDateTime termino = new LocalDateTime(horaSaida);
		minutosTrabalhados = Minutes.minutesBetween(inicio, termino).getMinutes();

	}

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

	public StatusFrequencia getStatus() {
		return status;
	}

	public void setStatus(StatusFrequencia status) {
		this.status = status;
	}

	public Date getHoraSaida() {
		return horaSaida;
	}

	public void setHoraSaida(Date horarioSaida) {
		this.horaSaida = horarioSaida;
	}

	public TipoFrequencia getTipo() {
		return tipo;
	}

	public void setTipo(TipoFrequencia tipo) {
		this.tipo = tipo;
	}

	public Date getHoraAgendamentoEntrada() {
		return horaAgendamentoEntrada;
	}

	public void setHoraAgendamentoEntrada(Date horaAgendamentoEntrada) {
		this.horaAgendamentoEntrada = horaAgendamentoEntrada;
	}

	public Date getHoraAgendamentoSaida() {
		return horaAgendamentoSaida;
	}

	public void setHoraAgendamentoSaida(Date horaAgendamentoSaida) {
		this.horaAgendamentoSaida = horaAgendamentoSaida;
	}
	
	@Transient
	private int minutosTrabalhados;

	public int getMinutosTrabalhados() {
		return minutosTrabalhados;
	}

	public enum StatusFrequencia {
		PRESENTE("Presente"),
		FALTA("Falta"), 
		ABONADO("Abonado"),
		AGUARDO_SAIDA("Aguardo Saída"),
		AGUARDO("Aguardo");
		

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
