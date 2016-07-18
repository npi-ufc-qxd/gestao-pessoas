package br.ufc.quixada.npi.ge.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
public class Selecao {

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull (message = "Campo Obrigatório.")
	@Temporal (TemporalType.TIME)
	private Date inicioIncricao;
	
	@NotNull (message = "Campo Obrigatório.")
	@Temporal (TemporalType.TIME)
	private Date terminoIncricao;
	
	@Enumerated(EnumType.STRING)
	private Status status;
	
	@NotNull (message = "Campo Obrigatório.")
	private String preRequisitos;
	
	@NotNull (message = "Campo Obrigatório.")
	private Integer vagas;
	
	@ManyToMany
	private List<Curso> cursos;
	
	@OneToMany (mappedBy = "selecao")
	private List<Inscricao> inscricoes;
	
	@OneToOne
	private Turma turma;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getInicioIncricao() {
		return inicioIncricao;
	}

	public void setInicioIncricao(Date inicioIncricao) {
		this.inicioIncricao = inicioIncricao;
	}

	public Date getTerminoIncricao() {
		return terminoIncricao;
	}

	public void setTerminoIncricao(Date terminoIncricao) {
		this.terminoIncricao = terminoIncricao;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getPreRequisitos() {
		return preRequisitos;
	}

	public void setPreRequisitos(String preRequisitos) {
		this.preRequisitos = preRequisitos;
	}

	public int getVagas() {
		return vagas;
	}

	public void setVagas(int vagas) {
		this.vagas = vagas;
	}

	public List<Curso> getCursos() {
		return cursos;
	}

	public void setCursos(List<Curso> cursos) {
		this.cursos = cursos;
	}

	public List<Inscricao> getInscricoes() {
		return inscricoes;
	}

	public void setInscricoes(List<Inscricao> inscricoes) {
		this.inscricoes = inscricoes;
	}

	public Turma getTurma() {
		return turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}

	public enum Status {
		
		ABERTA("Aberta"),
		FECHADA("Fechada");
		
		private String descricao;
		
		private Status(String descricao) {
			this.descricao = descricao;
		}

		public String getDescricao() {
			return descricao;
		}
		
	}
	
}
