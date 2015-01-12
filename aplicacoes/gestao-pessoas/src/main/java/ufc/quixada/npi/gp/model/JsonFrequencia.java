package ufc.quixada.npi.gp.model;

import ufc.quixada.npi.gp.model.enums.StatusFrequencia;
import ufc.quixada.npi.gp.model.enums.TipoFrequencia;

public class JsonFrequencia {
	
	private Long idFrequencia;
	private String nomeEstagiario;
	private String observacao;
	private StatusFrequencia statusFrequencia;
	private TipoFrequencia tipoFrequencia;

	public Long getIdFrequencia() {
		return idFrequencia;
	}
	public void setIdFrequencia(Long idFrequencia) {
		this.idFrequencia = idFrequencia;
	}
	public String getNomeEstagiario() {
		return nomeEstagiario;
	}
	public void setNomeEstagiario(String nomeEstagiario) {
		this.nomeEstagiario = nomeEstagiario;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	public StatusFrequencia getStatusFrequencia() {
		return statusFrequencia;
	}
	public void setStatusFrequencia(StatusFrequencia statusFrequencia) {
		this.statusFrequencia = statusFrequencia;
	}
	public TipoFrequencia getTipoFrequencia() {
		return tipoFrequencia;
	}
	public void setTipoFrequencia(TipoFrequencia tipoFrequencia) {
		this.tipoFrequencia = tipoFrequencia;
	}

}
