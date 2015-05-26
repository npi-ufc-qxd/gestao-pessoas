package ufc.quixada.npi.gp.model.enums;

public enum TipoFrequencia {
	NORMAL("Normal"),
	REPOSICAO_FALTA("Reposição por falta"), 
	REPOSICAO_ATRASO("Reposiçao por atraso");
	
	private String label;
	
	private TipoFrequencia(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
}
