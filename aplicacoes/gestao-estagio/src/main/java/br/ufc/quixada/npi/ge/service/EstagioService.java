package br.ufc.quixada.npi.ge.service;

import java.util.Date;
import java.util.List;

import br.ufc.quixada.npi.ge.model.AvaliacaoRendimento;
import br.ufc.quixada.npi.ge.model.Estagiario;
import br.ufc.quixada.npi.ge.model.Estagio;
import br.ufc.quixada.npi.ge.model.Frequencia;
import br.ufc.quixada.npi.ge.model.Presenca;
import br.ufc.quixada.npi.ge.model.Submissao;
import br.ufc.quixada.npi.ge.model.Turma;

public interface EstagioService {

	 
	Estagio buscarEstagioPorIdEEstagiarioId(Long idEstagio, Long idEstagiario);

	Estagio buscarEstagioPorId(Long idEstagio);

	Estagio buscarEstagioPorIdEServidorId(Long idEstagio, Long idServidor);
		
	Estagio buscarEstagioPorIdEstagiarioAndTurmaId(Long idEstagiario, Long idTurma);
	
	void desvincularEstagiario(Long idEstagio);
	
	void vincularEstagiario(Long idTurma, Long idEstagiario);
	
	List<Estagio> buscarEstagiosPorEstagiarioCpf(String cpf);
	
	Estagio buscarEstagioPorIdEEstagiarioCpf(Long idEstagio, String cpf);
	
	List<Estagiario> buscarEstagiariosSemVinculoComTurma(Long idTurma);
	
	List<Estagiario> buscarEstagiariosSemVinculoComTurmaPorNomeEstagiario(Long idTurma, String nomeEstagiario);

	Estagio buscarEstagioPorIdEstagio(Long idEstagio);

	Estagio buscarEstagioPorIdEOrientadorOuSupervisor(Long idEstagio, Long idServidor);
	
	void submeter(Submissao submissao);

	void editarSubmissao(Submissao submissao)  throws Exception;
	
	void editarRelatorio(Submissao submissao) throws Exception;

	void avaliarSubmissao(Submissao submissao);

	Submissao buscarSubmissaoPorTipoSubmissaoEEstagioIdECpf(Submissao.TipoSubmissao tipoSubmissao, Long idEstagio, String cpf);

	Submissao buscarSubmissaoPorTipoSubmissaoEEstagioId(Submissao.TipoSubmissao tipoSubmissao, Long idEstagio);
	
	Long buscarIdSubmissaoPorTipoSubmissaoEEstagioId(Submissao.TipoSubmissao tipoSubmissao, Long idEstagio);
	
	void adicionarAvaliacaoRendimento(AvaliacaoRendimento avaliacaoRendimento);
	
	void editarAvaliacaoRendimento(AvaliacaoRendimento avaliacaoRendimento);

	List<Frequencia> buscarFrequenciaPorEstagioId(Long idEstagio);
	
	Frequencia buscarFrequenciaPorDataEEstagioId(Date data, Long idEstagio);
	
	List<Frequencia> buscarFrequenciasPorDataETurmaId(Date data, Long idTurma);

	ConsolidadoFrequencia calcularDadosConsolidados(List<Frequencia> frequencia);

	List<Frequencia> gerarFrequencia(Turma turma, Estagiario estagiario);
	
	List<Frequencia> buscarFrequenciasPendentes(Turma turma, Estagiario estagiario);

	boolean liberarPresenca(Turma turma);
	
	public List<Presenca> permitirPresencaEstagio(List<Estagio> estagios);
	
	boolean realizarPresenca(Estagio estagio);
	
	void adicionarFrequencia(Frequencia frequencia);

	void editarStatusFrequencia();

	void adicionarObservacaoFrequencia();

	Frequencia buscarFrequenciaDeHojePorEstagio(Estagio estagio);

	ConsolidadoFrequencia consolidarFrequencias(Estagio estagio);
	
	void agendarReposicao(Estagio estagio, Date date);

	boolean isEstagioAcessoSupervisorOuOrientador(Long idEstagio, Long idServidor);
	
}
