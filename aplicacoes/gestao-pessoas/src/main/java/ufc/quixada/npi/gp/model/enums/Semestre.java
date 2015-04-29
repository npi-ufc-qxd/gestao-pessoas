package ufc.quixada.npi.gp.model.enums;

public enum Semestre {
	PRIMEIRO_SEMESTRE("1° Semestre"),
	SEGUNDO_SEMESTRE("2° Semestre"),
	TERCEIRO_SEMESTRE("3° Semestre"),
	QUARTO_SEMESTRE("4° Semestre"),
	QUINTO_SEMESTRE("5° Semestre"),
	SEXTO_SEMESTRE("6° Semestre"),
	SETIMO_SEMESTRE("7° Semestre"),
	OITAVO_SEMESTRE("8° Semestre"),
	NONO_SEMESTRE("9° Semestre"),
	DECIMO_SEMESTRE("10° Semestre");

	private String labelSemestre;

	private Semestre(String labelSemestre) {
		this.labelSemestre = labelSemestre;
	}

	public String getLabelSemestre() {
		return labelSemestre;
	}

	public void setLabelSemestre(String labelSemestre) {
		this.labelSemestre = labelSemestre;
	}
	
}
