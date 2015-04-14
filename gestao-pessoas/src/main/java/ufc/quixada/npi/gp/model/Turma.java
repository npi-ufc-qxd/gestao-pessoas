package ufc.quixada.npi.gp.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Turma {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nome;

	@OneToMany(cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.PERSIST})
	@JoinColumn(name= "turma_id")
	List<Horario> horarios;

	@OneToOne//(fetch = FetchType.LAZY)
	private Pessoa supervisor;

	public List<Horario> getHorarios() {
		return horarios;
	}

	public void setHorarios(List<Horario> horarios) {
		this.horarios = horarios;
	}

	@ManyToOne//(fetch = FetchType.LAZY)
	private Periodo periodo;

	@OneToMany(cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.PERSIST})//, fetch = FetchType.LAZY)
	@JoinColumn(name= "turma_id")
	private List<Frequencia> frequencias;

	//fetch = FetchType.LAZY,
	@OneToMany(cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.PERSIST})	@JoinColumn(name= "turma_id")
	private List<Estagiario> estagiarios;
	
	public Periodo getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Periodo periodo) {
		this.periodo = periodo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Pessoa getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(Pessoa supervisor) {
		this.supervisor = supervisor;
	}

	public List<Estagiario> getEstagiarios() {
		return estagiarios;
	}

	public void setEstagiarios(List<Estagiario> estagiarios) {
		this.estagiarios = estagiarios;
	}

	/**
	 * @return the frequencias
	 */
	public List<Frequencia> getFrequencias() {
		return frequencias;
	}

	/**
	 * @param frequencias the frequencias to set
	 */
	public void setFrequencias(List<Frequencia> frequencias) {
		this.frequencias = frequencias;
	}
}
