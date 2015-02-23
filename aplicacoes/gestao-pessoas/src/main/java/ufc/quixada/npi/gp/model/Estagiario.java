package ufc.quixada.npi.gp.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import ufc.quixada.npi.gp.model.enums.Curso;
import ufc.quixada.npi.gp.model.enums.Estado;

@Entity
public class Estagiario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull(message = "Campo obrigatório.")
	@NotEmpty(message = "Campo obrigatório.")
	private String nomeMae;

	@NotNull(message = "Campo obrigatório.")
	@NotEmpty(message = "Campo obrigatório.")
	private String nomeCompleto;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date dataNascimento;

	@Enumerated(EnumType.STRING)
	private Curso curso;

	@NotNull(message = "Campo obrigatório.")
	private Integer matricula;

	@NotNull(message = "Campo obrigatório.")
	@NotEmpty(message = "Campo obrigatório.")
	private String semestre;

	@NotNull(message = "Campo obrigatório.")
	@NotEmpty(message = "Campo obrigatório.")
	private String telefone;

	private String contaRedmine;
	private String contaGithub;
	private String contaHangout;

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
	
	@Basic(fetch = FetchType.LAZY)
	@ManyToOne
	private Projeto projeto;
	
	@Basic(fetch = FetchType.LAZY)
	@ManyToOne
	private Turma turma;
	
	@Basic(fetch = FetchType.LAZY)
	@OneToOne(cascade=CascadeType.REFRESH) 
	private Pessoa pessoa;

	@Basic(fetch = FetchType.LAZY)
	@OneToMany(cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.PERSIST})
	@JoinColumn(name= "estagiario_id")
	private List<Frequencia> frequencias;

	public List<Frequencia> getFrequencias() {
		return frequencias;
	}

	/**
	 * @param frequencias the frequencias to set
	 */
	public void setFrequencias(List<Frequencia> frequencias) {
		this.frequencias = frequencias;
	}

	public Turma getTurma() {
		return turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}
	
	public Estagiario(){
		super();
	}

	public Estagiario(Long id, String nomeCompleto, Date dataNascimento,
			String nomeMae, String endereco, String cep, String cidade,
			Estado uf, String telefone, Curso curso, String semestre,
			int matricula, String contaRedmine, String contaGithub,
			String contaHangout, Pessoa pessoa) {
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
		this.contaRedmine = contaRedmine;
		this.contaGithub = contaGithub;
		this.contaHangout = contaHangout;
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

	public String getSemestre() {
		return semestre;
	}

	public void setSemestre(String semestre) {
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

	public String getContaRedmine() {
		return contaRedmine;
	}

	public void setContaRedmine(String contaRedmine) {
		this.contaRedmine = contaRedmine;
	}

	public String getContaGithub() {
		return contaGithub;
	}

	public void setContaGithub(String contaGithub) {
		this.contaGithub = contaGithub;
	}

	public String getContaHangout() {
		return contaHangout;
	}

	public void setContaHangout(String contaHangout) {
		this.contaHangout = contaHangout;
	}
	
	public Projeto getProjeto() {
		return projeto;
	}

	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
	}

//	@Override
//	public String toString() {
//		return "Estagiario [id=" + id + ", nomeCompleto=" + nomeCompleto
//				+ ", dataNascimento=" + dataNascimento + ", nomeMae=" + nomeMae
//				+ ", endereco=" + endereco + ", cep=" + cep + ", cidade="
//				+ cidade + ", uf=" + uf + ", telefone=" + telefone + ", curso="
//				+ curso + ", semestre=" + semestre + ", matricula=" + matricula
//				+ ", contaRedmine=" + contaRedmine + ", contaGithub="
//				+ contaGithub + ", contaHangout=" + contaHangout + ", pessoa="
//				+ pessoa + "]";
//	}	
}
