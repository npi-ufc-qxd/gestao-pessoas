package ufc.quixada.npi.gp.model.enums;

public enum StatusTurma {
	ATIVA("Ativa"), INATIVA("Inativa");	
	
	private String label;

	private StatusTurma(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
}
