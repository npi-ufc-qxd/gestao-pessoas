package br.ufc.quixada.npi.ge.service;

public class ConsolidadoFrequencia {
	
	private int horasEstagiadas;
	
	private int totalPendecias;

	private int totalAtrasos;

	private int totalReposicoes;
	
	private double porcentagemFaltas;

	private double porcentagemPresencas;

	public int getHorasEstagiadas() {
		return horasEstagiadas;
	}

	public void setHorasEstagiadas(int horasEstagiadas) {
		this.horasEstagiadas = horasEstagiadas;
	}

	public int getTotalPendecias() {
		return totalPendecias;
	}

	public void setTotalPendecias(int totalPendecias) {
		this.totalPendecias = totalPendecias;
	}

	public int getTotalAtrasos() {
		return totalAtrasos;
	}

	public void setTotalAtrasos(int totalAtrasos) {
		this.totalAtrasos = totalAtrasos;
	}

	public int getTotalReposicoes() {
		return totalReposicoes;
	}

	public void setTotalReposicoes(int totalReposicoes) {
		this.totalReposicoes = totalReposicoes;
	}

	public double getPorcentagemFaltas() {
		return porcentagemFaltas;
	}

	public void setPorcentagemFaltas(double porcentagemFaltas) {
		this.porcentagemFaltas = porcentagemFaltas;
	}

	public double getPorcentagemPresencas() {
		return porcentagemPresencas;
	}

	public void setPorcentagemPresencas(double porcentagemPresencas) {
		this.porcentagemPresencas = porcentagemPresencas;
	}
	
	
}