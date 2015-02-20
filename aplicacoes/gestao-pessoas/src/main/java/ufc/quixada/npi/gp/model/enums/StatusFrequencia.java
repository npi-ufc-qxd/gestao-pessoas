package ufc.quixada.npi.gp.model.enums;

public enum StatusFrequencia {
	PRESENTE("Presente"), ATRASADO("Atrasado"), FALTA("Falta"), AGUARDO("Aguardando dia"), REPOSICAO_ATRASO("Reposição para atraso"), REPOSICAO_FALTA("Reposição para falta");	
	
	private String label;

	private StatusFrequencia(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

}
