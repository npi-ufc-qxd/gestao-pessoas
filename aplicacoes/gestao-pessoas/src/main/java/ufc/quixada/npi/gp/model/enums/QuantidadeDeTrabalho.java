package ufc.quixada.npi.gp.model.enums;

public enum QuantidadeDeTrabalho {
	PESSIMA("A quantidade de trabalho apresentada é insuficiente e, mesmo quando cobrado, não atende às exigências mínimas do campo de estágio."),
	IRREGULAR("A quantidade de trabalho apresentada é irregular, precisando ser cobrado para atender às exigências do campo de estágio."),
	REGULAR("A quantidade de trabalho apresentada atende às exigências do setor."),
	OTIMA("A quantidade de trabalho apresentada supera as expectativas e as exigências do campo de estágio.");
	
	private String item;
	
	private QuantidadeDeTrabalho(String item){
		this.item = item;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}
	
}
