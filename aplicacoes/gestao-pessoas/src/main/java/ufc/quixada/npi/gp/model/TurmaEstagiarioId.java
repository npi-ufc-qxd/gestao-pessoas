package ufc.quixada.npi.gp.model;

import java.io.Serializable;

public class TurmaEstagiarioId implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long turma;
	private Long estagiario;
	
	public Long getTurma() {
		return turma;
	}
	public void setTurma(Long turma) {
		this.turma = turma;
	}
	public Long getEstagiario() {
		return estagiario;
	}
	public void setEstagiario(Long estagiario) {
		this.estagiario = estagiario;
	}
	
	@Override
	public int hashCode(){
		return (int) (turma + estagiario);
	}
	
	@Override
	public boolean equals(Object obj){
		if(obj instanceof TurmaEstagiarioId){
			TurmaEstagiarioId turmaEstagiarioId = (TurmaEstagiarioId) obj;
			return turmaEstagiarioId.turma == turma && turmaEstagiarioId.estagiario == estagiario;
		}
		return false;
	}
}
