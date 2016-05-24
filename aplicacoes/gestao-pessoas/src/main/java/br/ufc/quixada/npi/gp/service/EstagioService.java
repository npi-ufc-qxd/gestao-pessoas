package br.ufc.quixada.npi.gp.service;

public interface EstagioService {

	/**
	 * 
	Submissao getSubmissaoByEstagioIdAndTipo(Long idEstagio, TipoSubmissao tipoSubmissao);
	
	void avaliarSubmissao(Submissao submissao);
	void realizarAvaliacaoRendimento(AvaliacaoRendimento avaliacaoRendimento);
	
	@Transactional
	void atualizarStatus();

	boolean liberarPreseca(Turma turma);
	
	boolean permitirPresenca(Estagio estagio);
	
	Frequencia getFrequenciaDeHojeByEstagiarioId(Long id);

	List<Frequencia> getFrequenciasByEstagiarioId(Long idEstagiario, Long idTurma);

	Frequencia getFrequenciaByDataByTurmaByEstagiario(Date data, Long turma, Long estagiario);

	List<Frequencia> getFrequenciasByTurmaIdAndData(Date data, Long idTurma);

	DadoConsolidado calcularDadosConsolidados(List<Frequencia> frequencia);

	List<Frequencia> gerarFrequencia(Turma turma, Estagiario estagiario);
	
	List<Estagiario> getEstagiariosSemFrequencia(Date data, Long idTurma);
	
	List<Frequencia> frequenciaPendente(Turma turma, Estagiario estagiario);
	
	void adicionarFrequencia(Frequencia frequencia);
	
	List<Estagiario> getAniversariantesMesByTurmaId(Long id);
	
	Estagio getEstagioByIdAndEstagiarioId(Long idEstagio, Long idEstagiario);
			
	Estagio getEstagioByIdAndEstagiarioCpf(Long idEstagio, String cpf);
	
	void submeterPlano(Submissao submissao);
	void submeterRelatorio(Submissao submissao);
	void editarPlano(Submissao submissao)  throws Exception;
	void editarRelatorio(Submissao submissao) throws Exception;
	void realizarPresenca(Estagio estagio);

	 */
}
