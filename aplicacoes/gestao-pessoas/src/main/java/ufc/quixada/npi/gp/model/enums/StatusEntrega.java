package ufc.quixada.npi.gp.model.enums;

public enum StatusEntrega {
	ACEITA("Aceita"), REJEITADA("Rejeitada"), ENVIADA("Enviada");
	
	private String label;

	private StatusEntrega(String label) {
		this.label= label;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
}
