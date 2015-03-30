package ufc.quixada.npi.gp.model.enums;

public enum Estado {
	ACRE("Acre"), ALAGOAS("Alagoas"), AMAPA("Amapa"), AMAZONAS("Amazonas"), BAHIA("Bahia"), CEARA("Ceará"), DISTRITO_FEDERAL("Distrito Federal"),
	ESPIRITO_SANTO("Espirito Santo"), GOIAS("Goiás"), MARANHAO("Maranhão"), MATO_GROSSO("Mato Grosso"), MATO_GROSSO_DO_SUL("Mato Grosso do Sul"),
	MINAS_GERAIS("Minas Gerais"), PARA("Pará"), PARAIBA("Paraíba"), PARANA("Paraná"), PERNAMBUCO("Pernambuco"), PIAUI("Piauí"),
	RIO_DE_JANEIRO("Rio de Janeiro"), RIO_GRANDE_DO_NORTE("Rio Grande do Norte"), RIO_GRANDE_DO_SUL("Rio Grande do Sul"), RONDONIA("Rondonia"), Roraima("Roraima"),
	SANTA_CATARINA("Santa Catarina"), SAO_PAULO("São Paulo"), SERGIPE("Sergipe"), TOCANTINS("Tocantins");

	private String estado;
	
	Estado(String estado){
		this.estado = estado;
	}

	public String getEstado(){
		return estado;
	}
}
