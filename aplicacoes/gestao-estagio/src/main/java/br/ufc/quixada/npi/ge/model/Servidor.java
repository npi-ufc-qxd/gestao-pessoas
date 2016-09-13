package br.ufc.quixada.npi.ge.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class Servidor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private Pessoa pessoa;
	
	@NotEmpty(message = "Campo obrigatório.")
	private String nome;
	
	@NotEmpty(message = "Campo obrigatório.")
	private String siape;
	
	@NotEmpty(message = "Campo obrigatório.")
	private String telefone;
	
	@NotEmpty(message = "Campo obrigatório.")
	private String cargo;
	
	@NotEmpty(message = "Campo obrigatório.")
	private String lotacao;
	
	@NotEmpty(message = "Campo obrigatório.")
	private String titulacao;
	
	@NotEmpty(message = "Campo obrigatório.")
	private String saudacao;
	
	@NotEmpty(message = "Campo obrigatório.")
	public String getSiape() {
		return siape;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setSiape(String siape) {
		this.siape = siape;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public String getLotacao() {
		return lotacao;
	}

	public void setLotacao(String lotacao) {
		this.lotacao = lotacao;
	}

	public String getTitulacao() {
		return titulacao;
	}

	public void setTitulacao(String titulacao) {
		this.titulacao = titulacao;
	}

	public String getSaudacao() {
		return saudacao;
	}

	public void setSaudacao(String saudacao) {
		this.saudacao = saudacao;
	}

}
