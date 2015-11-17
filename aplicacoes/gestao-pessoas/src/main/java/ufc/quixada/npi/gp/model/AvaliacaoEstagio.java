package ufc.quixada.npi.gp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class AvaliacaoEstagio {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private double nota;
	
	private double notaSeminario;
	
	private String comentarioSeminario;

	private String fatorAssiduidadeDisciplina;
	
	private String fatorIniciativaProdutividade;
	
	private String fatorResponsabilidade;
	
	private String fatorRelacionamento;

	@ManyToOne
	@JoinColumn(name = "submissao_id")
	private Submissao submissao;

	@ManyToOne
	@JoinColumn(name = "pessoa_id")
	private Pessoa pessoa;

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
	
	public Submissao getSubmissao() {
		return submissao;
	}

	public void setSubmissao(Submissao submissao) {
		this.submissao = submissao;
	}
	
	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
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
