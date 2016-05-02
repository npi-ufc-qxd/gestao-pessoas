package ufc.quixada.npi.gp.model.enums;

public enum Permanencia {
	PESSIMA("Não permanece no local do estágio"),
	IRREGULAR("Com frequência ausenta-se do local do estágio"), 
	BOA("Raramente ausenta-se do local do estágio"),
	OTIMA("Permanece no local do estágio"); 
	
	private String item;
	
	private Permanencia(String item) {
		this.item = item;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}
	
}
