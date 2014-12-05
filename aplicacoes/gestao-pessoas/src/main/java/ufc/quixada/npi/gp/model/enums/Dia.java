package ufc.quixada.npi.gp.model.enums;

public enum Dia {
	SEGUNDA("Segunda-Feira"), TERCA("Terça-Feira"), QUARTA("Quarta-Feira"), QUINTA("Quinta-Feira"), SEXTA("Sexta-Feira");

	private String dia;

	private Dia(String dia) {
		this.dia = dia;
	}

	public String getDia() {
		return dia;
	}

	public void setDia(String dia) {
		this.dia = dia;
	}
}
