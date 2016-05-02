package ufc.quixada.npi.gp.model.enums;

public enum TrabalhoEmEquipe {
	PESSIMA("Seu estilo de trabalho compromete o trabalho em equipe."),
	IRREGULAR("Seu estilo de trabalho pouco interfere na melhoria do desempenho da equipe."), 
	BOA("Agrega qualidades que ocasionam melhorias do desempenho da equipe de forma satisfatória."),
	OTIMA("Suas contribuições para a equipe superam as expectativas superando as expectativas e necessidades do campo de estágio."); 
	
	private String item;
	
	private TrabalhoEmEquipe(String item) {
		this.item = item;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}
	
}
