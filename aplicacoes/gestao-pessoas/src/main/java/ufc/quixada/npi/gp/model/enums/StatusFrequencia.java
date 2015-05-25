package ufc.quixada.npi.gp.model.enums;

public enum StatusFrequencia {
	AGUARDO("Aguardando dia", ""), 

	PRESENTE("Presente", "Estou presente no NPI!!!"),
	FALTA("Falta", "Você não esta no NPI."), 
	ATRASADO("Atrasado", "Ops, você chegou atrasaso."), 

	FERIADO("Feriado", "Hoje é um feriado, descanse."),
	ABONADO("Abonado", ""),

	REPONDO_ATRASO("Reposição Agendada", ""),
	REPONDO_FALTA("Reposição Agendada", "");

	private String label;
	private String mensagem;

	private StatusFrequencia(String label, String mensagem) {
		this.label = label;
		this.mensagem = mensagem;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

}
