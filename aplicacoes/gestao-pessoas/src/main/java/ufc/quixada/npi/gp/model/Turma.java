package ufc.quixada.npi.gp.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import ufc.quixada.npi.gp.model.enums.Dia;

@Entity
public class Turma {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String codigo;

	@Enumerated(EnumType.STRING)
	private Dia inicioSemana;
	
	@Enumerated(EnumType.STRING)
	private Dia fimSemana;
	
	@Temporal(TemporalType.TIME)
	@DateTimeFormat(pattern = "HH:mm")
	private Date horaInicio;
	
	@Temporal(TemporalType.TIME)
	@DateTimeFormat(pattern = "HH:mm")
	private Date horaFinal;

	@OneToOne(fetch = FetchType.EAGER)
	private Pessoa supervisor;

	@ManyToOne()
	private Periodo periodo;

	@OneToMany(cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
	@JoinColumn(name= "turma_id")
	private List<Frequencia> frequencias;
	
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

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Dia getInicioSemana() {
		return inicioSemana;
	}

	public void setInicioSemana(Dia inicioSemana) {
		this.inicioSemana = inicioSemana;
	}

	public Dia getFimSemana() {
		return fimSemana;
	}

	public void setFimSemana(Dia fimSemana) {
		this.fimSemana = fimSemana;
	}

	public Date getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(Date horaInicio) {
		this.horaInicio = horaInicio;
	}

	public Date getHoraFinal() {
		return horaFinal;
	}

	public void setHoraFinal(Date horaFinal) {
		this.horaFinal = horaFinal;
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
