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

	Estagio buscarEstagioPorId(Long idEstagio);
	
	List<Estagio> buscarEstagiosPorEstagiarioCpf(String cpf);
	
	Estagio buscarEstagioPorIdEEstagiarioCpf(Long idEstagio, String cpf);
	
	void submeter(Submissao submissao);

	void editarSubmissao(Submissao submissao)  throws Exception;
	
	void editarRelatorio(Submissao submissao) throws Exception;

	void avaliarSubmissao(Submissao submissao);

	Submissao buscarSubmissaoPorTipoSubmissaoEEstagioIdECpf(Submissao.TipoSubmissao tipoSubmissao, Long idEstagio, String cpf);

	Submissao buscarSubmissaoPorTipoSubmissaoEEstagioId(Submissao.TipoSubmissao tipoSubmissao, Long idEstagio);
	
	void adicionarAvaliacaoRendimento(AvaliacaoRendimento avaliacaoRendimento);
	
	void editarAvaliacaoRendimento(AvaliacaoRendimento avaliacaoRendimento);

	List<Frequencia> buscarFrequenciaPorEstagioId(Long idEstagio);
	
	Frequencia buscarFrequenciaPorDataEEstagioId(Date data, Long idEstagio);
	
	List<Frequencia> buscarFrequenciasPorDataETurmaId(Date data, Long idTurma);

	ConsolidadoFrequencia calcularDadosConsolidados(List<Frequencia> frequencia);

	List<Frequencia> gerarFrequencia(Turma turma, Estagiario estagiario);
	
	List<Frequencia> buscarFrequenciasPendentes(Turma turma, Estagiario estagiario);

	boolean liberarPresenca(Turma turma);
	
	boolean permitirPresenca(Estagio estagio);
	
	boolean realizarPresenca(Estagio estagio);
	
	void adicionarFrequencia(Frequencia frequencia);

	void editarStatusFrequencia();

	void adicionarObservacaoFrequencia();

	Frequencia buscarFrequenciaDeHojePorEstagio(Estagio estagio);

	Estagio buscarEstagioPorIdEstagio(Long idEstagio);

}
