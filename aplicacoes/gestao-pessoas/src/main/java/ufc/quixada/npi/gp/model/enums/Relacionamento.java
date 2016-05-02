package ufc.quixada.npi.gp.model.enums;

public enum Relacionamento {
	PESSIMA("Constantemente apresenta dificuldades de relacionamento com gerência ou demais funcionários."),
	RARAMENTE("Eventualmente apresenta dificuldade de relacionamento com gerência ou demais funcionários."),
	BOA("Seu bom relacionamento com gerência e demais funcionários atende às expectativas."),
	OTIMA("Destaca-se por desenvolver bom relacionamento com todos os membros de gerência e demais funcionários.");
	
	private String item;
	
	private Relacionamento(String item){
		this.item = item;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}
}
