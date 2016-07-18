package br.ufc.quixada.npi.ge.enums;

public enum TipoEstagio {

	ESTAGIOI("Estágio I"),
	ESTAGIOII("Estágio II");
	
	private String descricao;
	
	private TipoEstagio(String descricao){
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
}
