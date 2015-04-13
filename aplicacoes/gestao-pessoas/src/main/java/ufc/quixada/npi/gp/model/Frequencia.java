package ufc.quixada.npi.gp.model;

import java.util.Date;

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
import javax.persistence.Transient;

import ufc.quixada.npi.gp.model.enums.StatusFrequencia;
import ufc.quixada.npi.gp.model.enums.TipoFrequencia;

@Entity
public class Frequencia {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	private StatusFrequencia statusFrequencia;

	@Temporal(TemporalType.DATE)
	private Date data;

	@Enumerated(EnumType.STRING)
	private TipoFrequencia tipoFrequencia;

	private String observacao;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Turma turma;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Estagiario estagiario;
	
	@Transient
	private int totalAtraso; 

	public int getTotalAtraso() {
		return totalAtraso;
	}

	public void setTotalAtraso(int totalAtraso) {
		this.totalAtraso = totalAtraso;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public StatusFrequencia getStatusFrequencia() {
		return statusFrequencia;
	}

	public void setStatusFrequencia(StatusFrequencia statusFrequencia) {
		this.statusFrequencia = statusFrequencia;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public TipoFrequencia getTipoFrequencia() {
		return tipoFrequencia;
	}

	public void setTipoFrequencia(TipoFrequencia tipoFrequencia) {
		this.tipoFrequencia = tipoFrequencia;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Turma getTurma() {
		return turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}

	public Estagiario getEstagiario() {
		return estagiario;
	}

	public void setEstagiario(Estagiario estagiario) {
		this.estagiario = estagiario;
	}

	
	
}
