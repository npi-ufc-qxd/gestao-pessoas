package ufc.quixada.npi.gp.model.enums;

public enum Curso {
	SISTEMAS_INFORMAÇÃO("Sistemas de Informação"),
	REDES_COMPUTADORES("Redes de Computadores"),
	ENGENHARIA_SOFTWARE("Engenharia de Software"),
	CIÊNCIA_COMPUTAÇÃO("Ciência da Computação"),
	DESIGN_DIGITAL("Design Digital"),
	ENGENHARIA_COMPUTAÇÃO("Engenharia da Computação");	

	private String labelCurso;

	private Curso(String labelCurso) {
		this.labelCurso = labelCurso;
	}

	public String getLabelCurso() {
		return labelCurso;
	}

	public void setLabelCurso(String labelCurso) {
		this.labelCurso = labelCurso;
	}

}
