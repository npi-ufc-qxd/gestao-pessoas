package ufc.quixada.npi.gp.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import ufc.quixada.npi.gp.model.enums.TipoEstagio;

@Entity
@IdClass(TurmaEstagiarioId.class)
public class Estagio {
	
	private String local;
	
	@Enumerated(EnumType.STRING)
	private TipoEstagio tipoEstagio;
	
	@Id
	@ManyToOne
	@JoinColumn(name="turma_id")
	private Turma turma; 
	
	@Id
	@ManyToOne
	@JoinColumn(name="estagiario_id")
	private Estagiario estagiario;

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public TipoEstagio getTipoEstagio() {
		return tipoEstagio;
	}

	public void setEstagio(TipoEstagio tipoEstagio) {
		this.tipoEstagio = tipoEstagio;
	}

	public Turma getTurma() {
		return turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}

	public Estagiario getEstagiario() {
		return estagiario;
	}

	public void setEstagiario(Estagiario estagiario) {
		this.estagiario = estagiario;
	}
	
	
}
