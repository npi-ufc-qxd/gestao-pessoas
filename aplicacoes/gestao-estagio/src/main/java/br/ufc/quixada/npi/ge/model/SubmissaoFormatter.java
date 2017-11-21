package br.ufc.quixada.npi.ge.model;

import org.springframework.stereotype.Component;

@Component
public class SubmissaoFormatter {
	public String estagiario;
	public String curso;
	public String email;	
	public boolean planoEstagio;	
	public boolean relatorioFinal;
	public String getEstagiario() {
		return estagiario;
	}
	public void setEstagiario(String estagiario) {
		this.estagiario = estagiario;
	}
	public String getCurso() {
		return curso;
	}
	public void setCurso(String curso) {
		this.curso = curso;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public boolean isPlanoEstagio() {
		return planoEstagio;
	}
	public void setPlanoEstagio(boolean planoEstagio) {
		this.planoEstagio = planoEstagio;
	}
	public boolean isRelatorioFinal() {
		return relatorioFinal;
	}
	public void setRelatorioFinal(boolean relatorioFinal) {
		this.relatorioFinal = relatorioFinal;
	}

}
