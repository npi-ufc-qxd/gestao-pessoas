package ufc.quixada.npi.gp.model.enums;

public enum StatusTurma {
	ABERTA("Aberta"), FECHADA("Fechada");
	
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
