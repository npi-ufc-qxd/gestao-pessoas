package ufc.quixada.npi.gp.model;

import java.util.Date;

import ufc.quixada.npi.gp.model.enums.StatusFrequencia;

public class ReposicaoJson {

	private Long idEstagiario;
	
	private String dataReposicao;
	
	private StatusFrequencia status;

	public Long getIdEstagiario() {
		return idEstagiario;
	}

	public void setIdEstagiario(Long idEstagiario) {
		this.idEstagiario = idEstagiario;
	}

	public String getDataReposicao() {
		return dataReposicao;
	}

	public void setDataReposicao(String dataReposicao) {
		this.dataReposicao = dataReposicao;
	}

	public StatusFrequencia getStatus() {
		return status;
	}

	public void setStatus(StatusFrequencia status) {
		this.status = status;
	}
	
	
}
