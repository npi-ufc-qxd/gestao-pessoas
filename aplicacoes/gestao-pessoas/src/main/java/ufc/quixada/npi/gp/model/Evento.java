package ufc.quixada.npi.gp.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Evento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	@NotEmpty(message = "Informe a descrição.")
	private String descricao;

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	@NotEmpty(message = "Informe a data incial.")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date inicio;

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	@NotEmpty(message = "Informe a data final.")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date termino;

	@ManyToOne
	@JoinColumn(name = "turma_id")
	private Turma turma;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
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

	public Turma getTurma() {
		return turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}

}
