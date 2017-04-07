package ufc.quixada.npi.gp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

@Entity
public class AvaliacaoEstagio {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private double nota;

	private double notaSeminario;

	private String comentarioSeminario;

	@Lob
	private String fatorAssiduidadeDisciplina;

	@Lob
	private String fatorIniciativaProdutividade;

	@Lob
	private String fatorResponsabilidade;

	@Lob
	private String fatorRelacionamento;

	@ManyToOne
	@JoinColumn(name = "turma_id")
	private Turma turma;

	@ManyToOne
	@JoinColumn(name = "supervisor_id")
	private Pessoa supervisor;

	@ManyToOne
	@JoinColumn(name = "estagiario_id")
	private Estagiario estagiario;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getNota() {
		return nota;
	}

	public void setNota(double nota) {
		this.nota = nota;
	}

	public double getNotaSeminario() {
		return notaSeminario;
	}

	public void setNotaSeminario(double notaSeminario) {
		this.notaSeminario = notaSeminario;
	}

	public String getComentarioSeminario() {
		return comentarioSeminario;
	}

	public void setComentarioSeminario(String comentarioSeminario) {
		this.comentarioSeminario = comentarioSeminario;
	}

	public String getFatorAssiduidadeDisciplina() {
		return fatorAssiduidadeDisciplina;
	}

	public void setFatorAssiduidadeDisciplina(String fatorAssiduidadeDisciplina) {
		this.fatorAssiduidadeDisciplina = fatorAssiduidadeDisciplina;
	}

	public String getFatorIniciativaProdutividade() {
		return fatorIniciativaProdutividade;
	}

	public void setFatorIniciativaProdutividade(String fatorIniciativaProdutividade) {
		this.fatorIniciativaProdutividade = fatorIniciativaProdutividade;
	}

	public String getFatorResponsabilidade() {
		return fatorResponsabilidade;
	}

	public void setFatorResponsabilidade(String fatorResponsabilidade) {
		this.fatorResponsabilidade = fatorResponsabilidade;
	}

	public String getFatorRelacionamento() {
		return fatorRelacionamento;
	}

	public void setFatorRelacionamento(String fatorRelacionamento) {
		this.fatorRelacionamento = fatorRelacionamento;
	}

	public Turma getTurma() {
		return turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}

	public Pessoa getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(Pessoa supervisor) {
		this.supervisor = supervisor;
	}

	public Estagiario getEstagiario() {
		return estagiario;
	}

	public void setEstagiario(Estagiario estagiario) {
		this.estagiario = estagiario;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof AvaliacaoEstagio) {
			AvaliacaoEstagio other = (AvaliacaoEstagio) obj;
			if (other != null && other.getId() != null && this.id != null && other.getId().equals(this.id)) {
				return true;
			}
		}
		return false;
	}

}

