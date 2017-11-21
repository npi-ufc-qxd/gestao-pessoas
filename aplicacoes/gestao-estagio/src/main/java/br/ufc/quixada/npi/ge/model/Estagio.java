package br.ufc.quixada.npi.ge.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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

import br.ufc.quixada.npi.ge.enums.TipoEstagio;

@Entity
public class Estagio implements Comparable<Estagio>{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	private Turma turma; 
	
	@ManyToOne
	private Estagiario estagiario;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date inicio;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date termino;

	@OneToMany
	@JoinColumn(name = "estagio_id")
	private List<Expediente> expedientes;

	@OneToMany (mappedBy = "estagio", cascade=CascadeType.REMOVE)
	private List<Frequencia> frequencias;

	@OneToMany (mappedBy = "estagio", cascade=CascadeType.REMOVE)
	private List<Submissao> submissoes;
	
	@OneToOne(cascade=CascadeType.REMOVE)
	private AvaliacaoRendimento avaliacaoRendimento;

	private double nota;

	@Enumerated(EnumType.STRING)
	private Resultado resultado;

	@Enumerated(EnumType.STRING)
	private Situacao situacao;
	
	private String comentarioSituacao;

	@Enumerated(EnumType.STRING)
	private TipoEstagio estagio;

	private int atraso;
	
	public int getAtraso() {
		return atraso;
	}

	public void setAtraso(int atraso) {
		this.atraso = atraso;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public List<Expediente> getExpedientes() {
		return expedientes;
	}

	public void setExpedientes(List<Expediente> expedientes) {
		this.expedientes = expedientes;
	}

	public List<Frequencia> getFrequencias() {
		return frequencias;
	}

	public void setFrequencias(List<Frequencia> frequencias) {
		this.frequencias = frequencias;
	}

	public List<Submissao> getSubmissoes() {
		return submissoes;
	}

	public void setSubmissoes(List<Submissao> submissoes) {
		this.submissoes = submissoes;
	}

	public AvaliacaoRendimento getAvaliacaoRendimento() {
		return avaliacaoRendimento;
	}

	public void setAvaliacaoRendimento(AvaliacaoRendimento avaliacaoRendimento) {
		this.avaliacaoRendimento = avaliacaoRendimento;
	}

	public double getNota() {
		return nota;
	}

	public void setNota(double nota) {
		this.nota = nota;
	}

	public Resultado getResultado() {
		return resultado;
	}

	public void setResultado(Resultado resultado) {
		this.resultado = resultado;
	}

	public Situacao getSituacao() {
		return situacao;
	}

	public void setSituacao(Situacao situacao) {
		this.situacao = situacao;
	}

	public String getComentarioSituacao() {
		return comentarioSituacao;
	}

	public void setComentarioSituacao(String comentarioSituacao) {
		this.comentarioSituacao = comentarioSituacao;
	}

	public TipoEstagio getEstagio() {
		return estagio;
	}

	public boolean getPlanoEstagio() {
		if (null != this.submissoes) {
			for (Submissao submissao : this.submissoes) {
				if (submissao.getTipoSubmissao().equals(Submissao.TipoSubmissao.PLANO_ESTAGIO)) {
					return true;
				}
			}
		}
		
		return false;
	}

	public boolean getRelatorioFinal() {
		if (null != this.submissoes) {
			for (Submissao submissao : this.submissoes) {
				if (submissao.getTipoSubmissao().equals(Submissao.TipoSubmissao.RELATORIO_FINAL_ESTAGIO)) {
					return true;
				}
			}
		}

		return false;
	}

	public void setEstagio(TipoEstagio estagio) {
		this.estagio = estagio;
	}

	public enum Resultado {
		APROVADO("Aprovado"), 
		REPROVADO("Reprovado");
		private String descricao;
		
		private Resultado(String descricao){
			this.descricao = descricao;
		}
		
		public String getDescricao() {
			return descricao;
		}
	}	
	
	public enum Situacao {
		EM_AVALIAÇÃO("Você está em avaliação"),
		CONCLUIDO("Estágio concluído"),
		DESISTENCIA("Você desistiu"),
		DESLIGADO("Você foi desligado");

		private String descricao;
		
		private Situacao(String descricao){
			this.descricao = descricao;
		}
		
		public String getDescricao() {
			return descricao;
		}

	}

	@Override
	public int compareTo(Estagio estagio) {
		String nomeDesteEstagiario = this.estagiario.getNomeCompleto();
		String nomeOutroEstagiario = estagio.getEstagiario().getNomeCompleto();
		
		return nomeDesteEstagiario.compareTo(nomeOutroEstagiario);
	}
}
