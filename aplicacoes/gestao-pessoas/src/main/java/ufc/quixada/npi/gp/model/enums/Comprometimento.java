package ufc.quixada.npi.gp.model.enums;

public enum Comprometimento {
	PESSIMA("Mostra-se descomprometido com o trabalho que lhe é designado no campo de estágio, realizando suas atividades de forma negligente."),
	IRREGULAR("Às vezes mostra-se descomprometido com o trabalho no campo de estágio."), 
	BOA("Mostra-se comprometido e empenhado na realização do trabalho que lhe é designado no campo de estágio."),
	OTIMA("Destaca-se pelo cumprimento e empenho com que realiza o trabalho que lhe é designado no campo de estágio."); 
	
	private String item;
	
	private Comprometimento(String item) {
		this.item = item;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}
	
}
