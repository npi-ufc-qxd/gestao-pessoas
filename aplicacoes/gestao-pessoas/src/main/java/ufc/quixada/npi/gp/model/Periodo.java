package ufc.quixada.npi.gp.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Periodo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Integer ano;

	private String semestre;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date inicio;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date termino;
	
	@OneToMany(cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.PERSIST})
	@JoinColumn(name= "periodo_id")
	private List<Turma> turmas;
	
	@OneToMany(cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.PERSIST})
	@JoinColumn(name= "periodo_id")
	private List<Folga> folgas;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public Date getInicio() {
		return inicio;
	}

	public void setInicio(Date inicio) {
		this.inicio = inicio;
	}

	public Date getTermino() {
		return termino;
	}

	public void setTermino(Date termino) {
		this.termino = termino;
	}

	public List<Turma> getTurmas() {
		return turmas;
	}

	public void setTurmas(List<Turma> turmas) {
		this.turmas = turmas;
	}

	public void addTurma(Turma turma) {
		this.turmas.add(turma);
	}

	public List<Folga> getFolgas() {
		return folgas;
	}

	public void setFolgas(List<Folga> folgas) {
		this.folgas = folgas;
	}
	
}
