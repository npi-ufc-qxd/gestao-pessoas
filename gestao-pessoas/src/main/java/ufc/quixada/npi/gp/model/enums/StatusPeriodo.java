package ufc.quixada.npi.gp.model.enums;

public enum StatusPeriodo {
	EM_ESPERA("Em espera"), ABERTO("Aberto"), FECHADO("Fechado");
	
	private String label;

	private StatusPeriodo(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
}
