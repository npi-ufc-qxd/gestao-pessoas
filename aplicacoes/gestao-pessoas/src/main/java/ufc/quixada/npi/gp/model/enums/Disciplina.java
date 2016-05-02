package ufc.quixada.npi.gp.model.enums;

public enum Disciplina {
	PESSIMA("Não cumpre as normas estabelecidas pelo estágio, o que vem prejudicando seu trabalho no estágio."),
	IRREGULAR("Com frequência precisa ser cobrado quanto ao não cumprimento das normas estabelecidas pelo estágio."),
	REGULAR("Ocasionalmente não segue as normas estabelecidas pelo estágio, embora este fato não chegue a comprometer os trabalhos desenvolvidos na disciplina."),
	OTIMA("Procura cumprir as normas estabelecidas pela instituição.");
	
	private String item;
	
	private Disciplina(String item){
		this.item = item;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}
	
}
