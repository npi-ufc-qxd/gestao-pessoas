package ufc.quixada.npi.gp.model.enums;

public enum CuidadoMateriais {
	PESSIMA("Descuidado, danifica com freqüência os materiais e equipamentos de trabalho. Desperdiça e gera prejuízos."),
	IRREGULAR("Precisa ser mais cuidadoso. Demonstra certa negligência com materiais e equipamentos de trabalho."), 
	BOA("Usa adequadamente os materiais e equipamentos de trabalho."),
	OTIMA("Preocupa-se e mantém seus materiais e equipamentos de trabalho em perfeito estado."); 
	
	private String item;
	
	private CuidadoMateriais(String item) {
		this.item = item;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}
	
}
