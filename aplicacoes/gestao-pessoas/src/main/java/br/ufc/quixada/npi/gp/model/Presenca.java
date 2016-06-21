package br.ufc.quixada.npi.gp.model;

public class Presenca {
	
	private boolean permissao;
	private Estagio estagio;
	
	public boolean isPermissao() {
		return permissao;
	}
	public void setPermissao(boolean permissao) {
		this.permissao = permissao;
	}
	public Estagio getEstagio() {
		return estagio;
	}
	public void setEstagio(Estagio estagio) {
		this.estagio = estagio;
	}
	
	public Presenca(boolean permissao, Estagio estagio) {
		super();
		this.permissao = permissao;
		this.estagio = estagio;
	}
	
	

}
