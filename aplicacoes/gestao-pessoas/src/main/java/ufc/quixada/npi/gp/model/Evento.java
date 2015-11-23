package ufc.quixada.npi.gp.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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

	
	@NotEmpty(message = "Informe a turma.")
	
	private Turma turma;

	
	

}
