package br.ufc.quixada.npi.ge.service;

public class ConsolidadoFrequencia {
	
	private int horasEstagiadas;
	
	private int totalPendencias;

	private int totalReposicoes;
	
	private double porcentagemFaltas;

	private double porcentagemPresencas;

	public int getHorasEstagiadas() {
		return horasEstagiadas;
	}

	public void setHorasEstagiadas(int horasEstagiadas) {
		this.horasEstagiadas = horasEstagiadas;
	}

	public int getTotalPendencias() {
		return totalPendencias;
	}

	public void setTotalPendencias(int totalPendecias) {
		this.totalPendencias = totalPendecias;
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