package ufc.quixada.npi.gp.model.enums;

public enum StatusFrequencia {
	AGUARDO("Aguardando dia"), 
	PRESENTE("Presente"), 
	FALTA("Falta"), 
	ATRASADO("Atrasado"), 

	AGENDAMENTO_ATRASO("Agendamento para atraso"), 
	REPOSICAO_ATRASO("Reposição para atraso"), 
	AGENDAMENTO_FALTA("Agendamento para falta"),
	REPOSICAO_FALTA("Reposição para falta"), 

	FERIADO("Feriado");	
	
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
