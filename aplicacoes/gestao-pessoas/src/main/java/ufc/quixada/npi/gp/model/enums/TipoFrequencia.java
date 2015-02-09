package ufc.quixada.npi.gp.model.enums;

public enum TipoFrequencia {
	NORMAL("Normal"), REPOSICAO("Reposiçao");
	
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
