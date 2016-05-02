package ufc.quixada.npi.gp.model.enums;

public enum QualidadeDeTrabalho {
	PESSIMA("Seu trabalho é de baixa qualidade e, na maioria das vezes, tem que ser refeito. Não apresenta perspectiva de progresso."),
	IRREGULAR("Frequentemente seu trabalho precisa ser revisto, pois a qualidade do mesmo não atende às exigências do campo de estágio."),
	REGULAR("A qualidade de seu trabalho atende às necessidades de seu campo de trabalho."),
	OTIMA("Seu trabalho se sobressai por ser de ótima qualidade.");
	
	private String item;
	
	private QualidadeDeTrabalho(String item){
		this.item = item;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}
	
}
