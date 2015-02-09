package ufc.quixada.npi.gp.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;

@Entity
public class Projeto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Size(min = 2, message = "Mínimo 2 caracteres")
	private String nome;
	
	@Column(columnDefinition="TEXT")
    @Size(min = 5, message = "Mínimo 5 caracteres")
	private String descricao;
	
//	@OneToMany(cascade = CascadeType.REFRESH, mappedBy = "projeto" )
	@OneToMany(cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.PERSIST})
	@JoinColumn(name= "projeto_id")
	List<Estagiario> membros;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}
	
	public List<Estagiario> getMembros() {
		return membros;
	}

	public void setMembros(List<Estagiario> membros) {
		this.membros = membros;
	}
}
