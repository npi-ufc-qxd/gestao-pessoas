package ufc.quixada.npi.gp.model.enums;

public enum StatusEntrega {
	ACEITO("Aceito"), REJEITADO("Rejeitado"), ENVIADO("Enviado");
	
	private String label;

	private StatusEntrega(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
}
