package br.ufc.quixada.npi.ge.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Named;

import org.joda.time.Hours;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;

import br.ufc.quixada.npi.ge.model.AvaliacaoRendimento;
import br.ufc.quixada.npi.ge.model.Estagiario;
import br.ufc.quixada.npi.ge.model.Estagio;
import br.ufc.quixada.npi.ge.model.Evento;
import br.ufc.quixada.npi.ge.model.Expediente;
import br.ufc.quixada.npi.ge.model.Frequencia;
import br.ufc.quixada.npi.ge.model.Frequencia.StatusFrequencia;
import br.ufc.quixada.npi.ge.model.Frequencia.TipoFrequencia;
import br.ufc.quixada.npi.ge.model.Presenca;
import br.ufc.quixada.npi.ge.model.Submissao;
import br.ufc.quixada.npi.ge.model.Submissao.StatusEntrega;
import br.ufc.quixada.npi.ge.model.Submissao.TipoSubmissao;
import br.ufc.quixada.npi.ge.model.Turma;
import br.ufc.quixada.npi.ge.model.Turma.StatusTurma;
import br.ufc.quixada.npi.ge.repository.AvaliacaoRendimentoRepository;
import br.ufc.quixada.npi.ge.repository.EstagiarioRepository;
import br.ufc.quixada.npi.ge.repository.EstagioRepository;
import br.ufc.quixada.npi.ge.repository.EventoRepository;
import br.ufc.quixada.npi.ge.repository.FrequenciaRepository;
import br.ufc.quixada.npi.ge.repository.SubmissaoRepository;
import br.ufc.quixada.npi.ge.repository.TurmaRepository;
import br.ufc.quixada.npi.ge.service.ConsolidadoFrequencia;
import br.ufc.quixada.npi.ge.service.EstagioService;
import br.ufc.quixada.npi.ge.utils.UtilGestao;

@Named
public class EstagioServiceImpl implements EstagioService {

	@Autowired
	private FrequenciaRepository frequenciaRepository;

	@Autowired
	private SubmissaoRepository submissaoRepository;

	@Autowired
	private AvaliacaoRendimentoRepository avaliacaoRepository;

	@Autowired
	private EstagioRepository estagioRepository;

	@Autowired
	private EstagiarioRepository estagiarioRepository;

	@Autowired
	private TurmaRepository turmaRepository;

	@Autowired
	private EventoRepository eventoRepository;

	@Override
	public void desvincularEstagiario(Long idEstagio) {
		estagioRepository.delete(idEstagio);
	}

	@Override
	public void vincularEstagiario(Long idTurma, Long idEstagiario) {
		Estagio estagio = new Estagio();

		Turma turma = turmaRepository.findOne(idTurma);
		Estagiario estagiario = estagiarioRepository.findOne(idEstagiario);
		estagio.setTurma(turma);
		estagio.setEstagiario(estagiario);
		estagio.setSituacao(Estagio.Situacao.EM_AVALIAÇÃO);

		estagioRepository.save(estagio);

	}

	@Override
	public List<Estagiario> buscarEstagiariosSemVinculoComTurma(Long turmaId) {
		return estagiarioRepository.findByIdNotInTurma(turmaId);
	}

	@Override
	public List<Estagiario> buscarEstagiariosSemVinculoComTurmaPorNomeEstagiario(Long idTurma, String nomeEstagiario) {
		if (idTurma == null || nomeEstagiario == null || nomeEstagiario.isEmpty())
			return null;
		return estagiarioRepository.finByIdNotInTurmaQueryByNome(idTurma, nomeEstagiario.toUpperCase());
	}

	@Override
	public Estagio buscarEstagioPorIdEstagio(Long idEstagio) {
		return estagioRepository.findById(idEstagio);
	}

	@Override
	public Estagio buscarEstagioPorIdEstagiarioAndTurmaId(Long idEstagiario, Long idTurma) {
		return estagioRepository.findByIdEstagiarioAndIdTurma(idEstagiario, idTurma);
	}

	@Override
	public Estagio buscarEstagioPorId(Long idEstagio) {
		return estagioRepository.findOne(idEstagio);
	}

	@Override
	public List<Estagio> buscarEstagiosPorEstagiarioCpf(String cpf) {
		return estagioRepository.findByEstagiarioCpf(cpf);
	}

	@Override
	public Estagio buscarEstagioPorIdEEstagiarioCpf(Long idEstagio, String cpf) {
		return estagioRepository.findByIdAndEstagiario_Pessoa_Cpf(idEstagio, cpf);
	}

	@Override
	public void submeter(Submissao submissao) {
		submissaoRepository.save(submissao);
	}

	@Override
	public void editarSubmissao(Submissao submissao) throws Exception {
		if(StatusEntrega.SUBMETIDO.equals(submissao.getStatusEntrega()) || StatusEntrega.CORRECAO.equals(submissao.getStatusEntrega())){
			submissaoRepository.delete(submissao.getId());
			Submissao s = new Submissao(submissao);
			submissaoRepository.save(s);
		}else{
			throw new Exception();
		}
	}

	@Override
	public void avaliarSubmissao(Submissao submissao) {
		submissaoRepository.updateSubmissaoById(submissao.getId(), submissao.getStatusEntrega(), submissao.getNota(),
				submissao.getComentario());
	}

	@Override
	public Submissao buscarSubmissaoPorTipoSubmissaoEEstagioIdECpf(TipoSubmissao tipoSubmissao, Long idEstagio,
			String cpf) {
		return submissaoRepository.findByTipoSubmissaoAndEstagio_IdAndEstagio_Estagiario_Pessoa_Cpf(tipoSubmissao,
				idEstagio, cpf);
	}

	@Override
	public void adicionarAvaliacaoRendimento(AvaliacaoRendimento avaliacaoRendimento) {
		// TODO Auto-generated method stub
		avaliacaoRepository.save(avaliacaoRendimento);
	}

	@Override
	public Frequencia buscarFrequenciaPorDataEEstagioId(Date data, Long idEstagio) {
		return frequenciaRepository.findFrequenciaByDataAndEstagioId(data, idEstagio);
	}

	@Override
	public Frequencia buscarFrequenciaDeHojePorEstagio(Estagio estagio) {
		return frequenciaRepository.findFrequenciaDeHojeByEstagio(estagio);
	}

	@Override
	public List<Frequencia> buscarFrequenciasPorDataETurmaId(Date data, Long idTurma) {
		return frequenciaRepository.findFrequenciasByDataAndTurmaId(data, idTurma);
	}

	@Override
	public List<Frequencia> buscarFrequenciasPendentes(Estagio estagio) {
		LocalDate inicioPeriodoTemporario = new LocalDate(estagio.getTurma().getInicio());
		LocalDate fimPeriodo = new LocalDate(new Date());

		List<Frequencia> frequenciasPendentes = new ArrayList<Frequencia>();

		while (!inicioPeriodoTemporario.isAfter(fimPeriodo)) {
			if (UtilGestao.isDiaDeTrabahoDaTurma(estagio.getTurma().getExpedientes(), inicioPeriodoTemporario)) {
				if (!frequenciaRepository.existeFrequenciaByDataAndEstagioId(inicioPeriodoTemporario.toDate(),
						estagio.getId())) {
					Frequencia frequencia = new Frequencia();
					frequencia.setData(inicioPeriodoTemporario.toDate());
					frequenciasPendentes.add(frequencia);
				}
			}
			inicioPeriodoTemporario = inicioPeriodoTemporario.plusDays(1);
		}
		return frequenciasPendentes;
	}

	@Override
	public boolean liberarPresenca(Turma turma) {
		return (UtilGestao.hojeEDiaDeTrabahoDaTurma(turma.getExpedientes())
				&& UtilGestao.isHoraPermitida(turma.getExpedientes()));
	}

	public boolean liberarReposicao(Frequencia frequencia) {
		return (frequencia.getTipo() == TipoFrequencia.REPOSICAO && frequencia.getStatus() == StatusFrequencia.AGUARDO);
	}

	@Override
	public List<Presenca> permitirPresencaEstagio(List<Estagio> estagios) {

		List<Presenca> presencas = new ArrayList<Presenca>();

		for (Estagio estagio : estagios) {
			Frequencia frequencia = buscarFrequenciaDeHojePorEstagio(estagio);

			if (frequencia == null) {
				presencas.add(new Presenca(liberarPresenca(estagio.getTurma()), estagio));

			} else {
				presencas.add(new Presenca(liberarReposicao(frequencia), estagio));
			}
		}

		return presencas;
	}

	@Override
	public boolean realizarPresenca(Estagio estagio) {

		Frequencia frequencia = buscarFrequenciaDeHojePorEstagio(estagio);

		if (frequencia == null) {
			if (liberarPresenca(estagio.getTurma())) {

				frequencia = new Frequencia();

				frequencia.setEstagio(estagio);
				frequencia.setStatus(StatusFrequencia.PRESENTE);
				frequencia.setData(new Date());
				frequencia.setHorario(new Date());
				frequencia.setTipo(TipoFrequencia.NORMAL);

				frequenciaRepository.save(frequencia);
				return true;
			}
		} else if (liberarReposicao(frequencia)) {

			frequencia.setEstagio(estagio);
			frequencia.setStatus(StatusFrequencia.PRESENTE);
			frequencia.setData(new Date());
			frequencia.setHorario(new Date());
			frequencia.setTipo(TipoFrequencia.REPOSICAO);

			frequenciaRepository.save(frequencia);
			return true;
		}

		return false;

	}

	@Override
	public void adicionarFrequencia(Frequencia frequencia) {
		frequenciaRepository.save(frequencia);
	}

	@Override
	public Submissao buscarSubmissaoPorTipoSubmissaoEEstagioId(TipoSubmissao tipoSubmissao, Long idEstagio) {
		return submissaoRepository.findByIdETipo(tipoSubmissao, idEstagio);
	}

	@Override
	public Estagio buscarEstagioPorIdEServidorId(Long idEstagio, Long idServidor) {
		return estagioRepository.findByIdAndOrientadorOrSupervisor(idEstagio, idServidor);
	}

	@Override
	public ConsolidadoFrequencia consolidarFrequencias(Estagio estagio) {

		int totalDeFrequenciasDaTurma = calcularTotalDeFrequenciasDaTurma(estagio.getTurma().getInicio(),
				estagio.getTurma().getTermino(), estagio.getTurma().getExpedientes());

		int totalDeFrequenciasDaTurmaHoje = calcularTotalDeFrequenciasDaTurma(estagio.getTurma().getInicio(),
				new Date(), estagio.getTurma().getExpedientes());

		int totalPresencas = frequenciaRepository.buscarTotalByStatus(estagio.getId(),
				Frequencia.StatusFrequencia.PRESENTE);
		int totalFaltas = frequenciaRepository.buscarTotalByStatus(estagio.getId(), Frequencia.StatusFrequencia.FALTA);

		int horasEstagiadas = totalPresencas * calcularCargaHorariaExpediente(estagio.getTurma().getExpedientes());

		int totalPendencias = totalDeFrequenciasDaTurmaHoje
				- frequenciaRepository.buscarTotalByTipo(estagio.getId(), Frequencia.TipoFrequencia.NORMAL);

		int totalAtrasos = frequenciaRepository.buscarTotalByStatus(estagio.getId(),
				Frequencia.StatusFrequencia.ATRASADO);

		int totalReposicoes = frequenciaRepository.buscarTotalByTipo(estagio.getId(),
				Frequencia.TipoFrequencia.REPOSICAO);

		double porcentagemFaltas = 0.0;

		if (totalDeFrequenciasDaTurma > 0) {
			porcentagemFaltas = (totalFaltas * 100) / totalDeFrequenciasDaTurma;
		}

		double porcentagemPresencas = 100 - porcentagemFaltas;

		ConsolidadoFrequencia consolidadoFrequencia = new ConsolidadoFrequencia();

		consolidadoFrequencia.setHorasEstagiadas(horasEstagiadas);
		consolidadoFrequencia.setTotalPendecias(totalPendencias);
		consolidadoFrequencia.setTotalAtrasos(totalAtrasos);
		consolidadoFrequencia.setTotalReposicoes(totalReposicoes);
		consolidadoFrequencia.setPorcentagemFaltas(porcentagemFaltas);
		consolidadoFrequencia.setPorcentagemPresencas(porcentagemPresencas);

		return consolidadoFrequencia;
	}

	private int calcularCargaHorariaExpediente(List<Expediente> expedientes) {
		int cargaHorariaExpediente = 0;
		for (Expediente expediente : expedientes) {
			LocalDate inicio = new LocalDate(expediente.getHoraInicio());
			LocalDate fim = new LocalDate(expediente.getHoraTermino());

			cargaHorariaExpediente += Hours.hoursBetween(inicio, fim).getHours();
		}
		return cargaHorariaExpediente;
	}

	private int calcularTotalDeFrequenciasDaTurma(Date inicio, Date fim, List<Expediente> expedientes) {

		LocalDate inicioPeriodoTemporario = new LocalDate(inicio);
		LocalDate fimPeriodo = new LocalDate(fim);
		int totalDeFrequenciasDaTurma = 0;

		while (!inicioPeriodoTemporario.isAfter(fimPeriodo)) {

			if (UtilGestao.isDiaDeTrabahoDaTurma(expedientes, inicioPeriodoTemporario)) {
				totalDeFrequenciasDaTurma++;
			}
			inicioPeriodoTemporario = inicioPeriodoTemporario.plusDays(1);
		}

		return totalDeFrequenciasDaTurma;
	}

	@Override
	public void agendarReposicao(Estagio estagio, Date date) {
		Frequencia frequencia = new Frequencia();

		frequencia.setEstagio(estagio);
		frequencia.setData(date);
		frequencia.setTipo(Frequencia.TipoFrequencia.REPOSICAO);
		frequencia.setStatus(Frequencia.StatusFrequencia.AGUARDO);

		frequenciaRepository.save(frequencia);
	}

	@Override
	public Estagio buscarEstagioPorIdEOrientadorOuSupervisor(Long idEstagio, Long idServidor) {
		return estagiarioRepository.findByIdAndOrientadorOrSupervisor(idEstagio, idServidor);
	}

	@Override
	public boolean isEstagioAcessoSupervisorOuOrientador(Long idEstagio, Long idServidor) {
		return estagioRepository.isByIdAndOrientadorOrSupervisor(idEstagio, idServidor);
	}

	@Override
	public Long buscarIdSubmissaoPorTipoSubmissaoEEstagioId(TipoSubmissao tipoSubmissao, Long idEstagio) {
		return submissaoRepository.findIdByIdETipo(tipoSubmissao, idEstagio);
	}

	@Override
	public boolean existeFrequenciaPorDataEEstagioId(Date data, Long idEstagio) {
		return frequenciaRepository.existeFrequenciaByDataAndEstagioId(data, idEstagio);
	}

	@Override
	public Frequencia buscarFrequenciaPorIdETipoEStatus(Long idEstagio, TipoFrequencia tipoFrequencia,
			StatusFrequencia statusFrequencia) {
		return frequenciaRepository.findByIdAndTipoAndStatus(idEstagio, tipoFrequencia, statusFrequencia);
	}

	@Override
	public void excluirFrequencia(Frequencia frequencia) {
		frequenciaRepository.delete(frequencia);
	}

	@Override
	public Estagio buscarEstagioPorIdEEstagiarioIdTurma(Long idEstagiario, Long idTurma) {
		return estagioRepository.findByIdEstagiarioAndIdTurma(idEstagiario, idTurma);
	}

	@Override
	public Estagio salvarEstagio(Estagio estagio) {
		return estagioRepository.save(estagio);
	}
	
	@Override
	public List<Evento> buscarEventosEstagiario(List<Estagio> estagios) {
		List<Evento> listaEventos = new ArrayList<Evento>();

		for (Estagio estagio : estagios) {
			estagio.getTurma().getStatus();
			List<Evento> eventos = eventoRepository.buscarEventoPorTurma(estagio.getTurma().getId(), StatusTurma.ABERTA);
			if (!eventos.isEmpty()) {
				listaEventos.addAll(eventos);
			}
		}
		return listaEventos;
	}

}
