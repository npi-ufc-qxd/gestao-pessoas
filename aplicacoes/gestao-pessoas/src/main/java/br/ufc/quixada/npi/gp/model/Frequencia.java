package br.ufc.quixada.npi.gp.model;

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
	private Turma turma;

	@ManyToOne
	private Estagio estagio;

	@Enumerated(EnumType.STRING)
	private StatusFrequencia status;

	@Temporal(TemporalType.DATE)
	private Date data;

	@Temporal(TemporalType.TIME)
	private Date horario;

	@Enumerated(EnumType.STRING)
	private TipoFrequencia tipo;

	private String observacao;

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

	public Estagio getEstagio() {
		return estagio;
	}

	public void setEstagio(Estagio estagio) {
		this.estagio = estagio;
	}

	public StatusFrequencia getStatus() {
		return status;
	}

	public void setStatus(StatusFrequencia status) {
		this.status = status;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Date getHorario() {
		return horario;
	}

	public void setHorario(Date horario) {
		this.horario = horario;
	}

	public TipoFrequencia getTipo() {
		return tipo;
	}

	public void setTipo(TipoFrequencia tipo) {
		this.tipo = tipo;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	
	public enum StatusFrequencia {
		PRESENTE("Presente"),
		FALTA("Falta"), 
		ATRASADO("Atrasado"), 
		FERIADO("Feriado"),
		ABONADO("Abonado"),
		JUSTIFICATIVA("Justificativa"),
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
