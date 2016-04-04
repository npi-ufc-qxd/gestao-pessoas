package ufc.quixada.npi.gp.model.enums;

public enum Dia {
	SEGUNDA("Segunda-Feira", 1), TERCA("Terça-Feira", 2), QUARTA("Quarta-Feira", 3), QUINTA("Quinta-Feira", 4), SEXTA("Sexta-Feira", 5);

	private String labelDia;
	private int dia;

	private Dia(String labeldia, int dia) {
		this.labelDia = labeldia;
		this.dia = dia;
	}

	public String getLabelDia() {
		return labelDia;
	}

	public void setLabelDia(String labelDia) {
		this.labelDia = labelDia;
	}

	public int getDia() {
		return dia;
	}

	public void setDia(int dia) {
		this.dia = dia;
	}

}
