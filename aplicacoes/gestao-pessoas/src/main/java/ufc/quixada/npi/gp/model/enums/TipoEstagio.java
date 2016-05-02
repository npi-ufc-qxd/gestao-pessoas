package ufc.quixada.npi.gp.model.enums;

public enum TipoEstagio {
	
	ESTAGIOI("Estágio I"),
	ESTAGIOII("Estágio II");
	
	private String label;
	
	private TipoEstagio(String label){
		this.label = label;
	}
	
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	
}
