package br.ufc.quixada.npi.ge.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Turma {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty(message = "Informe o nome")
	private String nome;

	@NotEmpty(message = "Informe o semestre")
	private String semestre;

	@Temporal(TemporalType.DATE)
	@NotNull(message = "Informe a data incial")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date inicio;

	@Temporal(TemporalType.DATE)
	@NotNull(message = "Informe a data final")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date termino;

	@Enumerated(EnumType.STRING)
	private StatusTurma status;

	@Enumerated(EnumType.STRING)
	private TipoTurma tipoTurma;

	@OneToOne
	private Servidor orientador;

	@ManyToMany
	private List<Servidor> supervisores;

	@OneToMany
	@JoinColumn(name = "turma_id")
	private List<Expediente> expedientes;

	@OneToMany(mappedBy = "turma")
	private List<Evento> eventos;

	@OneToMany(mappedBy = "turma")
	private List<Estagio> estagios;

	private String nomeSeguradora;

	private String apolice;

	private String cargaHorariaSemanal;

	private String seguroMorteAcidental;

	private String seguroInvalidezPermanente;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date inicioVigencia;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date terminoVigencia;

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

	public StatusTurma getStatus() {
		return status;
	}

	public void setStatus(StatusTurma status) {
		this.status = status;
	}

	public TipoTurma getTipoTurma() {
		return tipoTurma;
	}

	public void setTipoTurma(TipoTurma tipoTurma) {
		this.tipoTurma = tipoTurma;
	}

	public Servidor getOrientador() {
		return orientador;
	}

	public void setOrientador(Servidor orientador) {
		this.orientador = orientador;
	}

	public List<Servidor> getSupervisores() {
		return supervisores;
	}

	public void setSupervisores(List<Servidor> supervisores) {
		this.supervisores = supervisores;
	}

	public List<Expediente> getExpedientes() {
		return expedientes;
	}

	public void setExpedientes(List<Expediente> expedientes) {
		this.expedientes = expedientes;
	}

	public List<Evento> getEventos() {
		return eventos;
	}

	public void setEventos(List<Evento> eventos) {
		this.eventos = eventos;
	}

	public List<Estagio> getEstagios() {
		return estagios;
	}

	public void setEstagios(List<Estagio> estagios) {
		this.estagios = estagios;
	}

	public String getNomeSeguradora() {
		return nomeSeguradora;
	}

	public void setNomeSeguradora(String nomeSeguradora) {
		this.nomeSeguradora = nomeSeguradora;
	}

	public String getApolice() {
		return apolice;
	}

	public void setApolice(String apolice) {
		this.apolice = apolice;
	}

	public String getCargaHorariaSemanal() {
		return cargaHorariaSemanal;
	}

	public void setCargaHorariaSemanal(String cargaHorariaSemanal) {
		this.cargaHorariaSemanal = cargaHorariaSemanal;
	}

	public String getSeguroMorteAcidental() {
		return seguroMorteAcidental;
	}

	public void setSeguroMorteAcidental(String seguroMorteAcidental) {
		this.seguroMorteAcidental = seguroMorteAcidental;
	}

	public String getSeguroInvalidezPermanente() {
		return seguroInvalidezPermanente;
	}

	public void setSeguroInvalidezPermanente(String seguroInvalidezPermanente) {
		this.seguroInvalidezPermanente = seguroInvalidezPermanente;
	}

	public Date getInicioVigencia() {
		return inicioVigencia;
	}

	public void setInicioVigencia(Date inicioVigencia) {
		this.inicioVigencia = inicioVigencia;
	}

	public Date getTerminoVigencia() {
		return terminoVigencia;
	}

	public String getEmails() {
		String emails = "";
		for (Estagio estagio : estagios) {
			emails += " " + estagio.getEstagiario().getEmail();
		}

		return emails;

	}

	public void setTerminoVigencia(Date terminoVigencia) {
		this.terminoVigencia = terminoVigencia;
	}

	public enum TipoTurma {
		NPI("NPI"), 
		EMPRESA("Empresa");
		
		private String descricao;

		private TipoTurma(String descricao) {
			this.descricao = descricao;
		}

		public String getDescricao() {
			return descricao;
		}
	}

	public enum StatusTurma {
		ABERTA("Aberta"), 
		CONCLUIDA("Concluída");

		private String descricao;

		private StatusTurma(String descricao) {
			this.descricao = descricao;
		}

		public String getDescricao() {
			return descricao;
		}
	}
	
	
}
