package ufc.quixada.npi.gp.model.enums;

public enum StatusFrequencia {
	PRESENTE("Presente", 100), ATRASADO("Atrasado", 50), FALTA("Falta", 0), AGUARDO("Aguardando dia", -1);	
	
	private String label;
	
	private int porcentagem;
	
	private StatusFrequencia(String label, int porcentagem) {
		this.label = label;
		this.porcentagem = porcentagem;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public int getPorcentagem() {
		return porcentagem;
	}


	public void setPorcentagem(int porcentagem) {
		this.porcentagem = porcentagem;
	}
	
}
