package ufc.quixada.npi.gp.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "pessoa", uniqueConstraints=@UniqueConstraint(columnNames = {"id", "cpf"}))
public class Pessoa {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	@NotEmpty(message = "Campo Obrigatorio")
	@Size(min = 11, message = "11 Caracteres")
	private String cpf;
	
	@Column(nullable = false)
	@NotEmpty(message = "Campo Obrigatorio")
	private String password;
	
	@Column(nullable = false)
	private boolean habilitado;
	
	@ManyToMany
	@JoinTable(name = "papel_pessoa", joinColumns = @JoinColumn(name = "pessoa_id"), inverseJoinColumns = @JoinColumn(name = "papel_id"))
	private List<Papel> papeis;
	
//	@OneToMany(mappedBy = "")	
//	private List<Projeto> projetos;
	
	@NotEmpty(message = "Campo Obrigatorio")
	private String nome;
	
	@NotEmpty(message = "Campo Obrigatorio")
	@Email(message = "E-mail invalido")
	private String email;

	public Pessoa(){
		super();
	}
	public Pessoa(String cpf, String nome, String sobrenome,
			String email, String senha) {
		super();
		this.cpf = cpf;
		this.nome = nome;
		
		this.email = email;
		this.password = senha;
	}
	
	
//	public List<Projeto> getProjetos() {
//		return projetos;
//	}
//	public void setProjetos(List<Projeto> projetos) {
//		this.projetos = projetos;
//	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public boolean isHabilitado() {
		return habilitado;
	}
	public void setHabilitado(boolean habilitado) {
		this.habilitado = habilitado;
	}
	public List<Papel> getPapeis() {
		return papeis;
	}
	public void setPapeis(List<Papel> papeis) {
		this.papeis = papeis;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Pessoa) {
			Pessoa other = (Pessoa) obj;
			if (other != null && other.getId() != null && this.id != null && other.getId().equals(this.id)) {
				return true;
			}
		}
		return false;
	}
	
}

