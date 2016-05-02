package ufc.quixada.npi.gp.model.enums;

public enum CumprimentoPrazos {
	PESSIMA("Não realiza as tarefas dentro do prazo estabelecido."),
	RUIM("Com frequência as tarefas não são entregues no prazo estabelecido."),
	BOA("Realiza as tarefas dentro do prazo."),
	OTIMA("Frequentemente realiza suas tarefas antes do prazo estabelecido.");
	
	private String item;
	
	private CumprimentoPrazos(String item){
		this.item = item;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}
}
