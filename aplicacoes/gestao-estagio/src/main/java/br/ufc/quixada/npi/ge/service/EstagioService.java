package br.ufc.quixada.npi.ge.service;

import java.util.Date;
import java.util.List;

import br.ufc.quixada.npi.ge.model.AvaliacaoRendimento;
import br.ufc.quixada.npi.ge.model.Estagiario;
import br.ufc.quixada.npi.ge.model.Estagio;
import br.ufc.quixada.npi.ge.model.Evento;
import br.ufc.quixada.npi.ge.model.Frequencia;
import br.ufc.quixada.npi.ge.model.Presenca;
import br.ufc.quixada.npi.ge.model.Submissao;
import br.ufc.quixada.npi.ge.model.Turma;

public interface EstagioService {

	Estagio buscarEstagioPorId(Long idEstagio);

	Estagio salvarEstagio(Estagio estagio);

	Estagio buscarEstagioPorIdEServidorId(Long idEstagio, Long idServidor);
		
	Estagio buscarEstagioPorIdEstagiarioAndTurmaId(Long idEstagiario, Long idTurma);
	
	void desvincularEstagiario(Long idEstagio);
	
	void vincularEstagiario(Long idTurma, Long idEstagiario);
	
	List<Estagio> buscarEstagiosPorEstagiarioCpf(String cpf);
	
	Estagio buscarEstagioPorIdEEstagiarioCpf(Long idEstagio, String cpf);
	
	Estagio buscarEstagioPorIdEEstagiarioIdTurma(Long idEstagiario, Long idTurma);
	
	List<Estagiario> buscarEstagiariosSemVinculoComTurma(Long idTurma);
	
	List<Estagiario> buscarEstagiariosSemVinculoComTurmaPorNomeEstagiario(Long idTurma, String nomeEstagiario);

	Estagio buscarEstagioPorIdEstagio(Long idEstagio);

	Estagio buscarEstagioPorIdEOrientadorOuSupervisor(Long idEstagio, Long idServidor);
	
	void submeter(Submissao submissao);

	void editarSubmissao(Submissao submissao)  throws Exception;

	void avaliarSubmissao(Submissao submissao);

	Submissao buscarSubmissaoPorTipoSubmissaoEEstagioIdECpf(Submissao.TipoSubmissao tipoSubmissao, Long idEstagio, String cpf);

	Submissao buscarSubmissaoPorTipoSubmissaoEEstagioId(Submissao.TipoSubmissao tipoSubmissao, Long idEstagio);
	
	Long buscarIdSubmissaoPorTipoSubmissaoEEstagioId(Submissao.TipoSubmissao tipoSubmissao, Long idEstagio);
	
	void adicionarAvaliacaoRendimento(AvaliacaoRendimento avaliacaoRendimento);

	Frequencia buscarFrequenciaPorIdETipoEStatus(Long idEstagio, Frequencia.TipoFrequencia tipoFrequencia, Frequencia.StatusFrequencia statusFrequencia);
	
	Frequencia buscarFrequenciaPorDataEEstagioId(Date data, Long idEstagio);
	
	void excluirFrequencia(Frequencia frequencia);
	
	boolean existeFrequenciaPorDataEEstagioId(Date data, Long idEstagio);
	
	List<Frequencia> buscarFrequenciasPorDataETurmaId(Date data, Long idTurma);
	
	List<Frequencia> buscarFrequenciasPendentes(Estagio estagio);

	boolean liberarPresenca(Turma turma);
	
	public List<Presenca> permitirPresencaEstagio(List<Estagio> estagios);
	
	boolean realizarPresenca(Estagio estagio);
	
	void adicionarFrequencia(Frequencia frequencia);

	Frequencia buscarFrequenciaDeHojePorEstagio(Estagio estagio);

	ConsolidadoFrequencia consolidarFrequencias(Estagio estagio);
	
	void agendarReposicao(Estagio estagio, Date date);

	boolean isEstagioAcessoSupervisorOuOrientador(Long idEstagio, Long idServidor);

	List<Evento> buscarEventosEstagiario(List<Estagio> estagios);
	
}
