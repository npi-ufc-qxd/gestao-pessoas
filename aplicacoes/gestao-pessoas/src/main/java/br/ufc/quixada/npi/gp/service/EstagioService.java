package br.ufc.quixada.npi.gp.service;

import java.util.Date;
import java.util.List;

import br.ufc.quixada.npi.gp.model.AvaliacaoRendimento;
import br.ufc.quixada.npi.gp.model.Estagiario;
import br.ufc.quixada.npi.gp.model.Estagio;
import br.ufc.quixada.npi.gp.model.Frequencia;
import br.ufc.quixada.npi.gp.model.Submissao;
import br.ufc.quixada.npi.gp.model.Turma;

public interface EstagioService {

	Estagio buscarEstagioPorIdEEstagiarioId(Long idEstagio, Long idEstagiario);

	List<Estagio> buscarEstagiosPorEstagiarioCpf(Long idEstagiario);
	
	Estagio buscarEstagioPorIdEEstagiarioCpf(Long idEstagio, String cpf);

	void submeterPlano(Submissao submissao);

	void editarPlano(Submissao submissao)  throws Exception;
	
	void submeterRelatorio(Submissao submissao);
	
	void editarRelatorio(Submissao submissao) throws Exception;

	void avaliarSubmissao(Submissao submissao);

	Submissao buscarSubmissaoPorEstagioIdETipo(Long idEstagio, Submissao.TipoSubmissao tipoSubmissao);
	
	void adicionarAvaliacaoRendimento(AvaliacaoRendimento avaliacaoRendimento);
	
	void editarAvaliacaoRendimento(AvaliacaoRendimento avaliacaoRendimento);

	List<Frequencia> buscarFrequenciaPorEstagioId(Long idEstagio);

	Frequencia buscarFrequenciaPorDataEEstagioId(Date data, Long idEstagio);

	List<Frequencia> buscarFrequenciasPorDataETurmaId(Date data, Long idTurma);

	ConsolidadoFrequencia calcularDadosConsolidados(List<Frequencia> frequencia);

	List<Frequencia> gerarFrequencia(Turma turma, Estagiario estagiario);
	
	List<Frequencia> buscarFrequenciasPendentes(Turma turma, Estagiario estagiario);

	boolean liberarPreseca(Turma turma);
	
	boolean permitirPresenca(Estagio estagio);
	
	void realizarPresenca(Estagio estagio);
	
	void adicionarFrequencia(Frequencia frequencia);

	void editarStatusFrequencia();

	void adicionarObservacaoFrequencia();

}
