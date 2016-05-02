package ufc.quixada.npi.gp.model.enums;

public enum CaraterTreinamento {
	URGENTE("Urgente"),
	IMPORTANTE("Importante");
	
	private String item;
	
	private CaraterTreinamento(String item) {
		this.item = item;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}
	
	
}
