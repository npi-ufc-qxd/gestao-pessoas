package ufc.quixada.npi.gp.model.enums;

public enum Iniciativa {
	PESSIMA("Não apresenta qualquer iniciativa quanto à resolução dos problemas que encontra."),
	RARAMENTE("Eventualmente busca resolver os problemas por si mesmo. Falta-lhe maior iniciativa."),
	BOA("Busca soluções para os problemas que encontra e toma medidas adequadas, de modo a atender às necessidades do campo de estágio."),
	OTIMA("Frequentemente busca soluções por sua própria iniciativa. É capaz de avaliar bem as situações e tomar providências corretas, superando as expectativas e necessidades do campo de estágio.");
	
	private String item;
	
	private Iniciativa(String item){
		this.item = item;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}
	
}
