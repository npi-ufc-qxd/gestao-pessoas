package ufc.quixada.npi.gp.model;

import java.util.Date;

public class FrequenciaJson {

	private Integer ano;

	private String semestre;

	private Long turma;
	
	private Date data;

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public String getSemestre() {
		return semestre;
	}

	public void setSemestre(String semestre) {
		this.semestre = semestre;
	}

	public Long getTurma() {
		return turma;
	}

	public void setTurma(Long turma) {
		this.turma = turma;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

}
