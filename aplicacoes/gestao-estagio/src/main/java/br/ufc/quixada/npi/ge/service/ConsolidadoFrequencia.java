package br.ufc.quixada.npi.ge.service;

public class ConsolidadoFrequencia {
	private int faltas;
	private int diasTrabalhados;
	private double porcentagemFrequencia;

	public int getFaltas() {
		return faltas;
	}

	public void setFaltas(int faltas) {
		this.faltas = faltas;
	}

	public int getDiasTrabalhados() {
		return diasTrabalhados;
	}

	public void setDiasTrabalhados(int diasTrabalhados) {
		this.diasTrabalhados = diasTrabalhados;
	}

	public double getPorcentagemFrequencia() {
		return porcentagemFrequencia;
	}

	public void setPorcentagemFrequencia(double porcentagemFrequencia) {
		this.porcentagemFrequencia = porcentagemFrequencia;
	}

}