package ufc.quixada.npi.gp.model.enums;

public enum Tipo {
	PLANO_ESTAGIO("Plano de Estágio"),
	RELATORIO_FINAL_ESTAGIO("Relatório Final de Estágio"),
	AVALIACAO_RENDIMENTO("Avaliação de Rendimento");

	private String labelTipo;

	private Tipo(String labelTipo) {
		this.labelTipo = labelTipo;
	}

	public String getLabelTipo() {
		return labelTipo;
	}

	public void setLabelTipo (String labelTipo) {
		this.labelTipo = labelTipo;
	}

}
