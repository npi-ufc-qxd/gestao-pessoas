package br.ufc.quixada.npi.ge.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import br.ufc.quixada.npi.ge.enums.TipoEstagio;

@Entity
public class Inscricao {

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private Selecao selecao;
	
	@ManyToOne
	private Estagiario estagiario;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date criadoEm;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date validadaEm;

	@NotNull (message = "Campo Obrigatório.")
	private Boolean atendeRequisito;

	private Boolean confirmarRequisito;

	private Boolean confirmarInscricao;

	private Double iraGeral;

	@NotEmpty (message = "Campo Obrigatório.")
	private String descricaoProfissional;

	@NotEmpty (message = "Campo Obrigatório.")
	@Enumerated (EnumType.STRING)
	private TipoEstagio estagio;

	@Enumerated (EnumType.STRING)
	private Resultado resultado; 

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCriadoEm() {
		return criadoEm;
	}

	public void setCriadoEm(Date criadoEm) {
		this.criadoEm = criadoEm;
	}

	public Date getValidadaEm() {
		return validadaEm;
	}

	public void setValidadaEm(Date validadaEm) {
		this.validadaEm = validadaEm;
	}

	public Boolean isAtendeRequisito() {
		return atendeRequisito;
	}

	public void setAtendeRequisito(Boolean atendeRequisito) {
		this.atendeRequisito = atendeRequisito;
	}

	public Boolean isConfirmarRequisito() {
		return confirmarRequisito;
	}

	public void setConfirmarRequisito(Boolean confirmarRequisito) {
		this.confirmarRequisito = confirmarRequisito;
	}

	public Boolean isConfirmarInscricao() {
		return confirmarInscricao;
	}

	public void setConfirmarInscricao(Boolean confirmarInscricao) {
		this.confirmarInscricao = confirmarInscricao;
	}

	public double getIraGeral() {
		return iraGeral;
	}

	public void setIraGeral(Double iraGeral) {
		this.iraGeral = iraGeral;
	}

	public String getDescricaoProfissional() {
		return descricaoProfissional;
	}

	public void setDescricaoProfissional(String descricaoProfissional) {
		this.descricaoProfissional = descricaoProfissional;
	}

	public TipoEstagio getEstagio() {
		return estagio;
	}

	public void setEstagio(TipoEstagio estagio) {
		this.estagio = estagio;
	}

	public Resultado getResultado() {
		return resultado;
	}

	public void setResultado(Resultado resultado) {
		this.resultado = resultado;
	}

	public Estagiario getEstagiario() {
		return estagiario;
	}

	public void setEstagiario(Estagiario estagiario) {
		this.estagiario = estagiario;
	}

	public Selecao getSelecao() {
		return selecao;
	}

	public void setSelecao(Selecao selecao) {
		this.selecao = selecao;
	}

	public enum Resultado {

		CLASSIFICADO("Classificado"),
		CLASSIFICAVEL("Classificável"),
		NAO_CLASSIFICADO("Não classificado");

		private String descricao;

		private Resultado(String descricao){
			this.descricao = descricao;
		}

		public String getDescricao() {
			return descricao;
		}
	}
}
