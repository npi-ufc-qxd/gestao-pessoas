package br.ufc.quixada.npi.gp.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Estagiario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(cascade = CascadeType.REFRESH)
	private Pessoa pessoa;

	@OneToMany(mappedBy="estagiario")
	private List<Estagio> estagios;
	
	@NotNull(message = "Campo obrigatório.")
	@NotEmpty(message = "Campo obrigatório.")
	private String nomeMae;

	@NotNull(message = "Campo obrigatório.")
	@NotEmpty(message = "Campo obrigatório.")
	private String nomeCompleto;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	private Date dataNascimento;

	@Enumerated(EnumType.STRING)
	private Curso curso;

	@NotNull(message = "Campo obrigatório.")
	private Integer matricula;

	@NotNull(message = "Campo obrigatório.")
	@Min(value = 1, message = "Informe um semestre valido")
	@Max(value = 12, message = "Informe um semestre valido")
	private Integer semestre;

	@NotNull(message = "Campo obrigatório.")
	@NotEmpty(message = "Campo obrigatório.")
	private String telefone;

	@NotNull(message = "Campo obrigatório")
	@NotEmpty(message = "Campo obrigatório.")
	private String usuarioGithub;
	
	@Email(message = "Informe um e-mail valido")
	private String email;

	@NotNull(message = "Campo obrigatório.")
	@NotEmpty(message = "Campo obrigatório.")
	private String endereco;

	@NotNull(message = "Campo obrigatório.")
	@NotEmpty(message = "Campo obrigatório.")
	private String cep;

	@NotNull(message = "Campo obrigatório.")
	@NotEmpty(message = "Campo obrigatório.")
	private String cidade;

	@Enumerated(EnumType.STRING)
	private Estado uf;
	
	public List<Estagio> getEstagios() {
		return estagios;
	}

	public void setEstagios(List<Estagio> estagios) {
		this.estagios = estagios;
	}

	public Estagiario() {
		super();
	}

	public Estagiario(Long id, String nomeCompleto, Date dataNascimento,
			String nomeMae, String endereco, String cep, String cidade,
			Estado uf, String telefone, Curso curso, Integer semestre,
			int matricula, String usuarioGithub,
			String email, Pessoa pessoa) {
		super();
		this.id = id;
		this.nomeCompleto = nomeCompleto;
		this.dataNascimento = dataNascimento;
		this.nomeMae = nomeMae;
		this.endereco = endereco;
		this.cep = cep;
		this.cidade = cidade;
		this.uf = uf;
		this.telefone = telefone;
		this.curso = curso;
		this.semestre = semestre;
		this.matricula = matricula;
		this.usuarioGithub = usuarioGithub;
		this.email = email;
		this.pessoa = pessoa;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeCompleto() {
		return nomeCompleto;
	}

	public void setNomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getNomeMae() {
		return nomeMae;
	}

	public void setNomeMae(String nomeMae) {
		this.nomeMae = nomeMae;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public Estado getUf() {
		return uf;
	}

	public void setUf(Estado uf) {
		this.uf = uf;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public Integer getSemestre() {
		return semestre;
	}

	public void setSemestre(Integer semestre) {
		this.semestre = semestre;
	}

	public Integer getMatricula() {
		return matricula;
	}

	public void setMatricula(Integer matricula) {
		this.matricula = matricula;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public String getUsuarioGithub() {
		return usuarioGithub;
	}

	public void setUsuarioGithub(String usuarioGithub) {
		this.usuarioGithub = usuarioGithub;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public enum Curso {
		SISTEMAS_INFORMAÇÃO("Sistemas de Informação"),
		REDES_COMPUTADORES("Redes de Computadores"),
		ENGENHARIA_SOFTWARE("Engenharia de Software"),
		CIÊNCIA_COMPUTAÇÃO("Ciência da Computação"),
		DESIGN_DIGITAL("Design Digital"),
		ENGENHARIA_COMPUTAÇÃO("Engenharia da Computação");

		private String descricao;

		private Curso(String descricao) {
			this.descricao = descricao;
		}

		public String getDescricao() {
			return descricao;
		}

	}
	public enum Estado {
		ACRE("Acre"), 
		ALAGOAS("Alagoas"), 
		AMAPA("Amapa"), 
		AMAZONAS("Amazonas"), 
		BAHIA("Bahia"), 
		CEARA("Ceará"), 
		DISTRITO_FEDERAL("Distrito Federal"), 
		ESPIRITO_SANTO("Espirito Santo"), 
		GOIAS("Goiás"), 
		MARANHAO("Maranhão"), 
		MATO_GROSSO("Mato Grosso"), 
		MATO_GROSSO_DO_SUL("Mato Grosso do Sul"), 
		MINAS_GERAIS("Minas Gerais"), 
		PARA("Pará"), 
		PARAIBA("Paraíba"), 
		PARANA("Paraná"), 
		PERNAMBUCO("Pernambuco"), 
		PIAUI("Piauí"), 
		RIO_DE_JANEIRO("Rio de Janeiro"), 
		RIO_GRANDE_DO_NORTE("Rio Grande do Norte"), 
		RIO_GRANDE_DO_SUL("Rio Grande do Sul"), 
		RONDONIA("Rondonia"), 
		Roraima("Roraima"), 
		SANTA_CATARINA("Santa Catarina"), 
		SAO_PAULO("São Paulo"), 
		SERGIPE("Sergipe"), 
		TOCANTINS("Tocantins");

		private String descricao;

		private Estado(String descricao) {
			this.descricao = descricao;
		}

		public String getDescricao() {
			return descricao;
		}
	}
}
