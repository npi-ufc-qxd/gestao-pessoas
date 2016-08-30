package br.ufc.quixada.npi.ge.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Submissao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private Estagio estagio;

	private double nota;
	
	private String comentario;

	@Enumerated(EnumType.STRING)
	private TipoSubmissao tipoSubmissao;
	
	@Enumerated(EnumType.STRING)
	private StatusEntrega statusEntrega;

	@OneToOne(cascade = CascadeType.ALL)	
	@JoinColumn(name = "documento_id")
	private Documento documento;

	@Temporal(TemporalType.DATE)
	private Date submetidoEm;

	@Temporal(TemporalType.DATE)
	private Date atualizadoEm;
	
	public Submissao(){}
	
	public Submissao(Submissao submissao){
		this.setEstagio(submissao.getEstagio());
		Documento documento = new Documento();
		documento.setNome(submissao.getDocumento().getNome());
		documento.setExtensao(submissao.getDocumento().getExtensao());
		documento.setArquivo(submissao.getDocumento().getArquivo());
		documento.setCaminho(submissao.getDocumento().getCaminho());
		this.setTipoSubmissao(submissao.getTipoSubmissao());
		this.setDocumento(documento);
		this.setSubmetidoEm(new Date());
		this.setStatusEntrega(submissao.getStatusEntrega());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Estagio getEstagio() {
		return estagio;
	}

	public void setEstagio(Estagio estagio) {
		this.estagio = estagio;
	}

	public double getNota() {
		return nota;
	}

	public void setNota(double nota) {
		this.nota = nota;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public TipoSubmissao getTipoSubmissao() {
		return tipoSubmissao;
	}

	public void setTipoSubmissao(TipoSubmissao tipoSubmissao) {
		this.tipoSubmissao = tipoSubmissao;
	}

	public StatusEntrega getStatusEntrega() {
		return statusEntrega;
	}

	public void setStatusEntrega(StatusEntrega statusEntrega) {
		this.statusEntrega = statusEntrega;
	}

	public Documento getDocumento() {
		return documento;
	}

	public void setDocumento(Documento documento) {
		this.documento = documento;
	}

	public Date getSubmetidoEm() {
		return submetidoEm;
	}

	public void setSubmetidoEm(Date submetidoEm) {
		this.submetidoEm = submetidoEm;
	}

	public Date getAtualizadoEm() {
		return atualizadoEm;
	}

	public void setAtualizadoEm(Date atualizadoEm) {
		this.atualizadoEm = atualizadoEm;
	}

	public enum TipoSubmissao {
		PLANO_ESTAGIO("Plano de Estágio"),
		RELATORIO_FINAL_ESTAGIO("Relatório Final de Estágio");

		private String descricao;

		private TipoSubmissao(String descricao) {
			this.descricao = descricao;
		}

		public String getDescricao() {
			return descricao;
		}
	}
	
	public enum StatusEntrega {
		SUBMETIDO("Submetido"), 
		CORRECAO("Correção"), 
		REJEITADO("Rejeitado"), 
		AVALIADO("Avaliado");

		private String descricao;

		private StatusEntrega(String descricao) {
			this.descricao = descricao;
		}

		public String getDescricao() {
			return descricao;
		}

	}
}
