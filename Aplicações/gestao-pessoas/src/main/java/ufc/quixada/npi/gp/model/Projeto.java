package ufc.quixada.npi.gp.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Projeto implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Size(min = 2, message = "Mínimo 2 caracteres")
	private String nome;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date inicio;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date termino;
	
    @Column(columnDefinition="TEXT")
    @Size(min = 5, message = "Mínimo 5 caracteres")
	private String descricao;
	
	@ManyToOne
	private Pessoa autor;
	
	private String local;
	
	@Enumerated(EnumType.STRING)
	private StatusProjeto status;
	
	@ManyToMany
    @JoinTable(joinColumns = {@JoinColumn(name="projeto_id",referencedColumnName="id")}, inverseJoinColumns = {@JoinColumn(name="pessoa_id", referencedColumnName="id")})
    private List<Pessoa> participantes;
	
	public Projeto() {
		super();
	}

	public Projeto(Long id, String nome, Date inicio,
			Date termino, String descricao, Pessoa autor,
			String local, StatusProjeto status,
			List<Pessoa> participantes) {
		super();
		this.id = id;
		this.nome = nome;
		this.inicio = inicio;
		this.termino = termino;
		this.descricao = descricao;
		this.autor = autor;
		this.local = local;
		this.status = status;
		this.participantes = participantes;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public StatusProjeto getStatus() {
		return status;
	}

	public void setStatus(StatusProjeto status) {
		this.status = status;
	}

	public List<Pessoa> getParticipantes() {
        return participantes;
    }

    public void setParticipantes(List<Pessoa> participantes) {
        this.participantes = participantes;
    }

	public Pessoa getAutor() {
		return autor;
	}

	public void setAutor(Pessoa autor) {
		this.autor = autor;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Projeto) {
			Projeto other = (Projeto) obj;
			if (other != null && other.getId() != null && this.id != null
					&& other.getId().equals(this.id)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return "Projeto [id=" + id + ", nome=" + nome + ", inicio=" + inicio
				+ ", termino=" + termino + ", descricao=" + descricao
				+ ", autor=" + autor + ", local=" + local + ", status="
				+ status + ", participantes=" + participantes + "]";
	}

	public enum StatusProjeto {
		
		NOVO("NOVO"), ANDAMENTO("EM ANDAMENTO"), FECHADO("FECHADO"), INTERROMPIDO("INTERROMPIDO");
		
		private String descricao;
		
		private StatusProjeto(String descricao) {
			this.descricao = descricao;
		}
		
		public String getDescricao() {
			return this.descricao;
		}
	}

}
