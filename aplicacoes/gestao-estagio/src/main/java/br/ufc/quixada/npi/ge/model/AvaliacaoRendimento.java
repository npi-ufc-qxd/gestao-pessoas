package br.ufc.quixada.npi.ge.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class AvaliacaoRendimento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	@JoinColumn(name = "estagio_id")
	private Estagio estagio;

	@OneToOne
	private Servidor criadaPor;

	@OneToOne
	private Servidor atualizadaPor;

	@Enumerated(EnumType.STRING)
	private Modo modo;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "documento_id")
	private Documento documento;

	private double nota;
	
	private String comentarioFinal;

	private double notaSeminario;

	private String comentarioSeminario;

	private String atividadeCurricular;

	@Temporal(TemporalType.DATE)
	private Date inicioAvaliacao;

	@Temporal(TemporalType.DATE)
	private Date terminoAvaliacao;

	@Temporal(TemporalType.DATE)
	private Date dataAvaliacao;

	private String objetivoEstagio;

	@Enumerated(EnumType.STRING)
	private Frequencia frequencia;

	@Enumerated(EnumType.STRING)
	private Permanencia permanencia;

	@Enumerated(EnumType.STRING)
	private DisciplinaQuantoAoCumprimentoDasNormas disciplina;
	
	private String fatorAssuidadeDisciplinaComentario;

	@Enumerated(EnumType.STRING)
	private Iniciativa iniciativa;

	@Enumerated(EnumType.STRING)
	private QuantidadeDeTrabalho quantidadeTrabalho;

	@Enumerated(EnumType.STRING)
	private QualidadeDoTrabalho qualidadeTrabalho;

	@Enumerated(EnumType.STRING)
	private CumprimentoPrazos cumprimentoPrazos;
	
	private String fatorIniciativaProdutividadeComentario;
	
	@Enumerated(EnumType.STRING)
	private ComprometimentoComTrabalho comprometimento;

	@Enumerated(EnumType.STRING)
	private CuidadoMateriaisEEquipamentos cuidadoMateriais;

	private String fatorResponsabilidadeComentario;

	@Enumerated(EnumType.STRING)
	private RelacionamentoGerenciaEFuncionarios relacionamento;

	@Enumerated(EnumType.STRING)
	private TrabalhoEmEquipe trabalhoEquipe;

	private String fatorRelacionamentoComentario;

	private Boolean confirmadoNoEstagio;

	private Boolean fatorAssuidade;
	
	private Boolean fatorDisciplina;

	private Boolean fatorIniciativaProdutividade;

	private Boolean fatorCapacidadeIniciativa;

	private Boolean fatorProdutividade;

	private Boolean fatorResponsabilidade;

	private Boolean outrosMotivos;

	private String especificacaoMotivo;

	private String observacaoParecer;

	private Boolean necessidadeTreinamento;

	private Boolean caraterTreinamentoImportante;
	
	private Boolean caraterTreinamentoUrgente;

	private String especificacaoTreinamento;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Estagio getEstagio() {
		return estagio;
	}

	public void setEstagio(Estagio estagio) {
		this.estagio = estagio;
	}

	public Servidor getCriadaPor() {
		return criadaPor;
	}

	public void setCriadaPor(Servidor criadaPor) {
		this.criadaPor = criadaPor;
	}

	public Servidor getAtualizadaPor() {
		return atualizadaPor;
	}

	public void setAtualizadaPor(Servidor atualizadaPor) {
		this.atualizadaPor = atualizadaPor;
	}

	public Modo getModo() {
		return modo;
	}

	public void setModo(Modo modo) {
		this.modo = modo;
	}

	public Documento getDocumento() {
		return documento;
	}

	public void setDocumento(Documento documento) {
		this.documento = documento;
	}

	public double getNota() {
		return nota;
	}

	public void setNota(double nota) {
		this.nota = nota;
	}

	public String getComentarioFinal() {
		return comentarioFinal;
	}

	public void setComentarioFinal(String comentarioFinal) {
		this.comentarioFinal = comentarioFinal;
	}

	public double getNotaSeminario() {
		return notaSeminario;
	}

	public void setNotaSeminario(double notaSeminario) {
		this.notaSeminario = notaSeminario;
	}

	public String getComentarioSeminario() {
		return comentarioSeminario;
	}

	public void setComentarioSeminario(String comentarioSeminario) {
		this.comentarioSeminario = comentarioSeminario;
	}

	public String getAtividadeCurricular() {
		return atividadeCurricular;
	}

	public void setAtividadeCurricular(String atividadeCurricular) {
		this.atividadeCurricular = atividadeCurricular;
	}

	public Date getInicioAvaliacao() {
		return inicioAvaliacao;
	}

	public void setInicioAvaliacao(Date inicioAvaliacao) {
		this.inicioAvaliacao = inicioAvaliacao;
	}

	public Date getTerminoAvaliacao() {
		return terminoAvaliacao;
	}

	public void setTerminoAvaliacao(Date terminoAvaliacao) {
		this.terminoAvaliacao = terminoAvaliacao;
	}

	public Date getDataAvaliacao() {
		return dataAvaliacao;
	}

	public void setDataAvaliacao(Date dataAvaliacao) {
		this.dataAvaliacao = dataAvaliacao;
	}

	public String getObjetivoEstagio() {
		return objetivoEstagio;
	}

	public void setObjetivoEstagio(String objetivoEstagio) {
		this.objetivoEstagio = objetivoEstagio;
	}

	public Frequencia getFrequencia() {
		return frequencia;
	}

	public void setFrequencia(Frequencia frequencia) {
		this.frequencia = frequencia;
	}

	public Permanencia getPermanencia() {
		return permanencia;
	}

	public void setPermanencia(Permanencia permanencia) {
		this.permanencia = permanencia;
	}

	public DisciplinaQuantoAoCumprimentoDasNormas getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(DisciplinaQuantoAoCumprimentoDasNormas disciplina) {
		this.disciplina = disciplina;
	}

	public String getFatorAssuidadeDisciplinaComentario() {
		return fatorAssuidadeDisciplinaComentario;
	}

	public void setFatorAssuidadeDisciplinaComentario(String fatorAssuidadeDisciplinaComentario) {
		this.fatorAssuidadeDisciplinaComentario = fatorAssuidadeDisciplinaComentario;
	}

	public Iniciativa getIniciativa() {
		return iniciativa;
	}

	public void setIniciativa(Iniciativa iniciativa) {
		this.iniciativa = iniciativa;
	}

	public QuantidadeDeTrabalho getQuantidadeTrabalho() {
		return quantidadeTrabalho;
	}

	public void setQuantidadeTrabalho(QuantidadeDeTrabalho quantidadeTrabalho) {
		this.quantidadeTrabalho = quantidadeTrabalho;
	}

	public QualidadeDoTrabalho getQualidadeTrabalho() {
		return qualidadeTrabalho;
	}

	public void setQualidadeTrabalho(QualidadeDoTrabalho qualidadeTrabalho) {
		this.qualidadeTrabalho = qualidadeTrabalho;
	}

	public CumprimentoPrazos getCumprimentoPrazos() {
		return cumprimentoPrazos;
	}

	public void setCumprimentoPrazos(CumprimentoPrazos cumprimentoPrazos) {
		this.cumprimentoPrazos = cumprimentoPrazos;
	}

	public String getFatorIniciativaProdutividadeComentario() {
		return fatorIniciativaProdutividadeComentario;
	}

	public void setFatorIniciativaProdutividadeComentario(String fatorIniciativaProdutividadeComentario) {
		this.fatorIniciativaProdutividadeComentario = fatorIniciativaProdutividadeComentario;
	}

	public ComprometimentoComTrabalho getComprometimento() {
		return comprometimento;
	}

	public void setComprometimento(ComprometimentoComTrabalho comprometimento) {
		this.comprometimento = comprometimento;
	}

	public CuidadoMateriaisEEquipamentos getCuidadoMateriais() {
		return cuidadoMateriais;
	}

	public void setCuidadoMateriais(CuidadoMateriaisEEquipamentos cuidadoMateriais) {
		this.cuidadoMateriais = cuidadoMateriais;
	}

	public String getFatorResponsabilidadeComentario() {
		return fatorResponsabilidadeComentario;
	}

	public void setFatorResponsabilidadeComentario(String fatorResponsabilidadeComentario) {
		this.fatorResponsabilidadeComentario = fatorResponsabilidadeComentario;
	}

	public RelacionamentoGerenciaEFuncionarios getRelacionamento() {
		return relacionamento;
	}

	public void setRelacionamento(RelacionamentoGerenciaEFuncionarios relacionamento) {
		this.relacionamento = relacionamento;
	}

	public TrabalhoEmEquipe getTrabalhoEquipe() {
		return trabalhoEquipe;
	}

	public void setTrabalhoEquipe(TrabalhoEmEquipe trabalhoEquipe) {
		this.trabalhoEquipe = trabalhoEquipe;
	}

	public String getFatorRelacionamentoComentario() {
		return fatorRelacionamentoComentario;
	}

	public void setFatorRelacionamentoComentario(String fatorRelacionamentoComentario) {
		this.fatorRelacionamentoComentario = fatorRelacionamentoComentario;
	}

	public Boolean getConfirmadoNoEstagio() {
		return confirmadoNoEstagio;
	}

	public void setConfirmadoNoEstagio(Boolean confirmadoNoEstagio) {
		this.confirmadoNoEstagio = confirmadoNoEstagio;
	}

	public Boolean getFatorAssuidade() {
		return fatorAssuidade;
	}

	public void setFatorAssuidade(Boolean fatorAssuidade) {
		this.fatorAssuidade = fatorAssuidade;
	}

	public Boolean getFatorDisciplina() {
		return fatorDisciplina;
	}

	public void setFatorDisciplina(Boolean fatorDisciplina) {
		this.fatorDisciplina = fatorDisciplina;
	}

	public Boolean getFatorIniciativaProdutividade() {
		return fatorIniciativaProdutividade;
	}

	public void setFatorIniciativaProdutividade(Boolean fatorIniciativaProdutividade) {
		this.fatorIniciativaProdutividade = fatorIniciativaProdutividade;
	}

	public Boolean getFatorCapacidadeIniciativa() {
		return fatorCapacidadeIniciativa;
	}

	public void setFatorCapacidadeIniciativa(Boolean fatorCapacidadeIniciativa) {
		this.fatorCapacidadeIniciativa = fatorCapacidadeIniciativa;
	}

	public Boolean getFatorProdutividade() {
		return fatorProdutividade;
	}

	public void setFatorProdutividade(Boolean fatorProdutividade) {
		this.fatorProdutividade = fatorProdutividade;
	}

	public Boolean getFatorResponsabilidade() {
		return fatorResponsabilidade;
	}

	public void setFatorResponsabilidade(Boolean fatorResponsabilidade) {
		this.fatorResponsabilidade = fatorResponsabilidade;
	}

	public Boolean getOutrosMotivos() {
		return outrosMotivos;
	}

	public void setOutrosMotivos(Boolean outrosMotivos) {
		this.outrosMotivos = outrosMotivos;
	}

	public String getEspecificacaoMotivo() {
		return especificacaoMotivo;
	}

	public void setEspecificacaoMotivo(String especificacaoMotivo) {
		this.especificacaoMotivo = especificacaoMotivo;
	}

	public String getObservacaoParecer() {
		return observacaoParecer;
	}

	public void setObservacaoParecer(String observacaoParecer) {
		this.observacaoParecer = observacaoParecer;
	}

	public Boolean getNecessidadeTreinamento() {
		return necessidadeTreinamento;
	}

	public void setNecessidadeTreinamento(Boolean necessidadeTreinamento) {
		this.necessidadeTreinamento = necessidadeTreinamento;
	}

	public Boolean getCaraterTreinamentoImportante() {
		return caraterTreinamentoImportante;
	}

	public void setCaraterTreinamentoImportante(Boolean caraterTreinamentoImportante) {
		this.caraterTreinamentoImportante = caraterTreinamentoImportante;
	}

	public Boolean getCaraterTreinamentoUrgente() {
		return caraterTreinamentoUrgente;
	}

	public void setCaraterTreinamentoUrgente(Boolean caraterTreinamentoUrgente) {
		this.caraterTreinamentoUrgente = caraterTreinamentoUrgente;
	}

	public String getEspecificacaoTreinamento() {
		return especificacaoTreinamento;
	}

	public void setEspecificacaoTreinamento(String especificacaoTreinamento) {
		this.especificacaoTreinamento = especificacaoTreinamento;
	}

	/**
	 * 
	 * @Fator: ASSIDUIDADE E DISCIPLINA
	 *
	 */

	
	public enum Frequencia {
		PESSIMA("Falta constantemente ao estágio (menos de 75% de presença)"),
		IRREGULAR("Falta algumas vezes ao estágio (75 à 90% de presença)"), 
		BOA("Raramente falta ao estágio (90 à 100% de presença)"),
		OTIMA("Não falta ao estágio"); 
		
		private String descricaoItem;
		
		private Frequencia(String descricaoItem) {
			setDescricaoItem(descricaoItem);
		}

		public String getDescricaoItem() {
			return descricaoItem;
		}

		public void setDescricaoItem(String descricaoItem) {
			this.descricaoItem = descricaoItem;
		}
		
	}
	public enum Permanencia {
		PESSIMA("Não permanece no local do estágio"), 
		IRREGULAR("Com frequência ausenta-se do local do estágio"), 
		BOA("Raramente ausenta-se do local do estágio"), 
		OTIMA("Permanece no local do estágio");

		private String descricaoItem;

		private Permanencia(String descricaoItem) {
			setDescricaoItem(descricaoItem);
		}

		public String getDescricaoItem() {
			return descricaoItem;
		}

		public void setDescricaoItem(String descricaoItem) {
			this.descricaoItem = descricaoItem;
		}

	}
		
	public enum DisciplinaQuantoAoCumprimentoDasNormas {
		PESSIMA("Não cumpre as normas estabelecidas pelo estágio, o que vem prejudicando seu trabalho no estágio."), 
		IRREGULAR("Com frequência precisa ser cobrado quanto ao não cumprimento das normas estabelecidas pelo estágio."), 
		REGULAR("Ocasionalmente não segue as normas estabelecidas pelo estágio, embora este fato não chegue a comprometer os trabalhos desenvolvidos na disciplina."), 
		OTIMA("Procura cumprir as normas estabelecidas pela instituição.");

		private String descricaoItem;

		private DisciplinaQuantoAoCumprimentoDasNormas(String descricaoItem) {
			setDescricaoItem(descricaoItem);
		}

		public String getDescricaoItem() {
			return descricaoItem;
		}

		public void setDescricaoItem(String descricaoItem) {
			this.descricaoItem = descricaoItem;
		}

	}	

	/**
	 * 
	 * @Fator: INICIATIVA E PRODUTIVIDADE
	 *
	 */

	public enum Iniciativa {
		PESSIMA("Não apresenta qualquer iniciativa quanto à resolução dos problemas que encontra."),
		RARAMENTE("Eventualmente busca resolver os problemas por si mesmo. Falta-lhe maior iniciativa."),
		BOA("Busca soluções para os problemas que encontra e toma medidas adequadas, de modo a atender às necessidades do campo de estágio."),
		OTIMA("Frequentemente busca soluções por sua própria iniciativa. É capaz de avaliar bem as situações e tomar providências corretas, superando as expectativas e necessidades do campo de estágio.");
		
		private String descricaoItem;
		
		private Iniciativa(String descricaoItem){
			setDescricaoItem(descricaoItem);
		}

		public String getDescricaoItem() {
			return descricaoItem;
		}

		public void setDescricaoItem(String descricaoItem) {
			this.descricaoItem = descricaoItem;
		}
			
	
	}
	

	public enum QuantidadeDeTrabalho {
		PESSIMA("A quantidade de trabalho apresentada é insuficiente e, mesmo quando cobrado, não atende às exigências mínimas do campo de estágio."),
		IRREGULAR("A quantidade de trabalho apresentada é irregular, precisando ser cobrado para atender às exigências do campo de estágio."),
		REGULAR("A quantidade de trabalho apresentada atende às exigências do setor."),
		OTIMA("A quantidade de trabalho apresentada supera as expectativas e as exigências do campo de estágio.");
		
		private String descricaoItem;
		
		private QuantidadeDeTrabalho(String descricaoItem){
			setDescricaoItem(descricaoItem);
		}

		public String getDescricaoItem() {
			return descricaoItem;
		}

		public void setDescricaoItem(String descricaoItem) {
			this.descricaoItem = descricaoItem;
		}
		
	}

	public enum QualidadeDoTrabalho {
		PESSIMA("Seu trabalho é de baixa qualidade e, na maioria das vezes, tem que ser refeito. Não apresenta perspectiva de progresso."),
		IRREGULAR("Frequentemente seu trabalho precisa ser revisto, pois a qualidade do mesmo não atende às exigências do campo de estágio."),
		REGULAR("A qualidade de seu trabalho atende às necessidades de seu campo de trabalho."),
		OTIMA("Seu trabalho se sobressai por ser de ótima qualidade.");
		
		private String descricaoItem;
		
		private QualidadeDoTrabalho(String descricaoItem){
			setDescricaoItem(descricaoItem);
		}

		public String getDescricaoItem() {
			return descricaoItem;
		}

		public void setDescricaoItem(String descricaoItem) {
			this.descricaoItem = descricaoItem;
		}
		
	}

	public enum CumprimentoPrazos {
		PESSIMA("Não realiza as tarefas dentro do prazo estabelecido."),
		RUIM("Com frequência as tarefas não são entregues no prazo estabelecido."),
		BOA("Realiza as tarefas dentro do prazo."),
		OTIMA("Frequentemente realiza suas tarefas antes do prazo estabelecido.");
		
		private String descricaoItem;
		
		private CumprimentoPrazos(String descricaoItem){
			setDescricaoItem(descricaoItem);
		}

		public String getDescricaoItem() {
			return descricaoItem;
		}

		public void setDescricaoItem(String descricaoItem) {
			this.descricaoItem = descricaoItem;
		}
	}
	
	/**
	 * 
	 * @Fator: RESPONSABILIDADE
	 *
	 */
	
	public enum ComprometimentoComTrabalho {
		PESSIMA("Mostra-se descomprometido com o trabalho que lhe é designado no campo de estágio, realizando suas atividades de forma negligente."),
		IRREGULAR("Às vezes mostra-se descomprometido com o trabalho no campo de estágio."), 
		BOA("Mostra-se comprometido e empenhado na realização do trabalho que lhe é designado no campo de estágio."),
		OTIMA("Destaca-se pelo cumprimento e empenho com que realiza o trabalho que lhe é designado no campo de estágio."); 
		
		private String descricaoItem;
		
		private ComprometimentoComTrabalho(String descricaoItem) {
			setDescricaoItem(descricaoItem);
		}

		public String getDescricaoItem() {
			return descricaoItem;
		}

		public void setDescricaoItem(String descricaoItem) {
			this.descricaoItem = descricaoItem;
		}
	}	

	
	public enum CuidadoMateriaisEEquipamentos {
		PESSIMA("Descuidado, danifica com freqüência os materiais e equipamentos de trabalho. Desperdiça e gera prejuízos."),
		IRREGULAR("Precisa ser mais cuidadoso. Demonstra certa negligência com materiais e equipamentos de trabalho."), 
		BOA("Usa adequadamente os materiais e equipamentos de trabalho."),
		OTIMA("Preocupa-se e mantém seus materiais e equipamentos de trabalho em perfeito estado."); 
		
		private String descricaoItem;
		
		private CuidadoMateriaisEEquipamentos(String descricaoItem) {
			setDescricaoItem(descricaoItem);
		}

		public String getDescricaoItem() {
			return descricaoItem;
		}

		public void setDescricaoItem(String descricaoItem) {
			this.descricaoItem = descricaoItem;
		}
		
	}
	
	/**
	 * 
	 * @Fator: RELACIONAMENTO
	 *
	 */

	public enum RelacionamentoGerenciaEFuncionarios {
		PESSIMA("Constantemente apresenta dificuldades de relacionamento com gerência ou demais funcionários."), 
		RARAMENTE("Eventualmente apresenta dificuldade de relacionamento com gerência ou demais funcionários."), 
		BOA("Seu bom relacionamento com gerência e demais funcionários atende às expectativas."), 
		OTIMA("Destaca-se por desenvolver bom relacionamento com todos os membros de gerência e demais funcionários.");

		private String descricaoItem;

		private RelacionamentoGerenciaEFuncionarios(String descricaoItem) {
			setDescricaoItem(descricaoItem);
		}

		public String getDescricaoItem() {
			return descricaoItem;
		}

		public void setDescricaoItem(String descricaoItem) {
			this.descricaoItem = descricaoItem;
		}
	}

	public enum TrabalhoEmEquipe {
		PESSIMA("Seu estilo de trabalho compromete o trabalho em equipe."), 
		IRREGULAR("Seu estilo de trabalho pouco interfere na melhoria do desempenho da equipe."), 
		BOA("Agrega qualidades que ocasionam melhorias do desempenho da equipe de forma satisfatória."), 
		OTIMA("Suas contribuições para a equipe superam as expectativas superando as expectativas e necessidades do campo de estágio.");

		private String descricaoItem;

		private TrabalhoEmEquipe(String descricaoItem) {
			setDescricaoItem(descricaoItem);
		}

		public String getDescricaoItem() {
			return descricaoItem;
		}

		public void setDescricaoItem(String descricaoItem) {
			this.descricaoItem = descricaoItem;
		}

	}

	public enum Modo {
		FORMULARIO, 
		ARQUIVO; 
	}

}