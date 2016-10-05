package br.ufc.quixada.npi.ge.model;

public class Presenca {

	private boolean permissaoEntrada;
	private boolean permissaoSaida;
	private Frequencia frequencia;

	public boolean isPermissaoEntrada() {
		return permissaoEntrada;
	}
	public void setPermissaoEntrada(boolean permissaoEntrada) {
		this.permissaoEntrada = permissaoEntrada;
	}
	public boolean isPermissaoSaida() {
		return permissaoSaida;
	}
	public void setPermissaoSaida(boolean permissaoSaida) {
		this.permissaoSaida = permissaoSaida;
	}

	public Frequencia getFrequencia() {
		return frequencia;
	}
	public void setFrequencia(Frequencia frequencia) {
		this.frequencia = frequencia;
	}

}
