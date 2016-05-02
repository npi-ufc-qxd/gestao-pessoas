package ufc.quixada.npi.gp.model;

public enum TipoTurma {
	
	NPI("NPI"), EMPRESA("Empresa");
	
	private String label;

	private TipoTurma(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
}
