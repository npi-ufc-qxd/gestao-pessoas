package br.ufc.quixada.npi.ge.service;

public class ConsolidadoFrequencia {
	
	private int minutosATrabalhar;

	private int minutosPresentes;

	private int minutosFaltas;

	private int minutosAbonados;

	private int minutosReposicao;

	private double porcentagemFaltas;

	private double porcentagemFrequencia;

	public double getPorcentagemFaltas() {
		porcentagemFaltas = (minutosFaltas * 100) / minutosATrabalhar;

		return porcentagemFaltas;
	}

	public double getPorcentagemFrequencia() {
		porcentagemFrequencia = 100 - getPorcentagemFaltas();
		return porcentagemFrequencia;
	}
	
	public String getHorasEstagiadas(){
		return getHorasFormatadas(minutosPresentes);
	}

	public String getHorasATrabalhar(){
		return getHorasFormatadas(minutosATrabalhar);
	}
	
	public String getHorasFaltas(){
		return getHorasFormatadas(minutosFaltas);
	}
	
	public String getHorasAbonadas() {
		return getHorasFormatadas(minutosAbonados);
	}
	
	public String getHorasReposicao() {
		return getHorasFormatadas(minutosReposicao);
	}

	public String getBancoHoras() {
		return getHorasFormatadas(getTotalMinutosBanco());
	}

	public int getTotalMinutosBanco() {
		return (minutosPresentes + minutosAbonados) - minutosATrabalhar;
	}	

	public void setMinutosPresentes(int minutosPresentes) {
		this.minutosPresentes = minutosPresentes;
	}

	public void setMinutosATrabalhar(int minutosATrabalhar) {
		this.minutosATrabalhar = minutosATrabalhar;
	}

	public void setMinutosFaltas(int minutosFaltas) {
		this.minutosFaltas = minutosFaltas;
	}

	public void setMinutosAbonados(int minutosAbonados) {
		this.minutosAbonados = minutosAbonados;
	}

	public void setMinutosReposicao(int minutosReposicao) {
		this.minutosReposicao = minutosReposicao;
	}

	private String getHorasFormatadas(int minutos) {
		return minutos / 60 +"h" + minutos % 60 + "min";
	}

}