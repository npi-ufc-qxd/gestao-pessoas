package ufc.quixada.npi.gp.model.enums;

public enum Frequencia {
	PESSIMA("Falta constantemente ao estágio (menos de 75% de presença)"),
	IRREGULAR("Falta algumas vezes ao estágio (75 à 90% de presença)"), 
	BOA("Raramente falta ao estágio (90 à 100% de presença)"),
	OTIMA("Não falta ao estágio"); 
	
	private String item;
	
	private Frequencia(String item) {
		this.item = item;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}
	
}
