package br.ufc.quixada.npi.gp.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Estagio {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	private Turma turma; 
	
	@ManyToOne
	private Estagiario estagiario;
	
	@OneToMany (mappedBy = "estagio")
	private List<Frequencia> frequencias;

	@OneToMany (mappedBy = "estagio")
	private List<Submissao> submissoes;
	
	@OneToOne
	private AvaliacaoRendimento avaliacaoRendimento;

	private double nota;

	@Enumerated(EnumType.STRING)
	private Resultado resultado;

	@Enumerated(EnumType.STRING)
	private Situacao situacao;
	
	private String comentarioSituacao;

	@Enumerated(EnumType.STRING)
	private TipoEstagio estagio;

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

	public List<Frequencia> getFrequencias() {
		return frequencias;
	}

	public List<Submissao> getSubmissoes() {
		return submissoes;
	}

	public AvaliacaoRendimento getAvaliacaoRendimento() {
		return avaliacaoRendimento;
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
		EM_ANDAMENTO("Em andamento"),
		CONCLUIDO("Concluído"),
		DESISTENCIA("Desistência"),
		DESLIGADO("Desligado");

		private String descricao;
		
		private Situacao(String descricao){
			this.descricao = descricao;
		}
		
		public String getDescricao() {
			return descricao;
		}

	}

	public enum TipoEstagio {
		
		ESTAGIOI("Estágio I"),
		ESTAGIOII("Estágio II");
		
		private String descricao;
		
		private TipoEstagio(String descricao){
			this.descricao = descricao;
		}
		
		public String getDescricao() {
			return descricao;
		}
	}

}
