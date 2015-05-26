package ufc.quixada.npi.gp.service;

public class DadoConsolidado {
	private int faltas;
	private int diasTrabalhados;
	private double porcentagemFrequencia;

	public DadoConsolidado() {
		super();
		this.faltas = 0;
		this.diasTrabalhados = 0;
		this.porcentagemFrequencia = 0;
	}

	public DadoConsolidado(int faltas, int diasTrabalhados, double porcentagemFrequencia) {
		super();
		this.faltas = faltas;
		this.diasTrabalhados = diasTrabalhados;
		this.porcentagemFrequencia = porcentagemFrequencia;
	}

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