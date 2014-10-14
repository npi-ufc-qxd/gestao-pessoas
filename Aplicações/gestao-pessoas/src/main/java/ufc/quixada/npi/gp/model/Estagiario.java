package ufc.quixada.npi.gp.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Estagiario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nomeCompleto;
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date dataNascimento;
	private String nomeMae;
	private String endereco;
	private String cep;
	private String cidade;
	private String uf;
	private String telefone;
	private String curso;
	private String semestre;
	private int matricula;
	private String contaRedmine;
	private String contaGithub;
	private String contaHangout;
	
	@OneToOne
	private Pessoa pessoa;
	
	public Estagiario(){
		super();
	}

	public Estagiario(Long id, String nomeCompleto, Date dataNascimento,
			String nomeMae, String endereco, String cep, String cidade,
			String uf, String telefone, String curso, String semestre,
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

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getCurso() {
		return curso;
	}

	public void setCurso(String curso) {
		this.curso = curso;
	}

	public String getSemestre() {
		return semestre;
	}

	public void setSemestre(String semestre) {
		this.semestre = semestre;
	}

	public int getMatricula() {
		return matricula;
	}

	public void setMatricula(int matricula) {
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



	@Override
	public String toString() {
		return "Estagiario [id=" + id + ", nomeCompleto=" + nomeCompleto
				+ ", dataNascimento=" + dataNascimento + ", nomeMae=" + nomeMae
				+ ", endereco=" + endereco + ", cep=" + cep + ", cidade="
				+ cidade + ", uf=" + uf + ", telefone=" + telefone + ", curso="
				+ curso + ", semestre=" + semestre + ", matricula=" + matricula
				+ ", contaRedmine=" + contaRedmine + ", contaGithub="
				+ contaGithub + ", contaHangout=" + contaHangout + ", pessoa="
				+ pessoa + "]";
	}

	
}
