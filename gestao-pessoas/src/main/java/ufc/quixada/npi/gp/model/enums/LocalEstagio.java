package ufc.quixada.npi.gp.model.enums;

public enum LocalEstagio {
	NPI("Nucleo de Praticas de Informatica"),
	EMPRESA("Empresa");	

	private String labelLocal;

	private LocalEstagio(String labelLocal) {
		this.labelLocal = labelLocal;
	}

	public String getLabelLocal() {
		return labelLocal;
	}

	public void setLabelLocal(String labelLocal) {
		this.labelLocal = labelLocal;
	}


}
