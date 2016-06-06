package br.ufc.quixada.npi.gp.service.impl;

import java.util.Date;
import java.util.List;

import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;

import br.ufc.quixada.npi.gp.model.AvaliacaoRendimento;
import br.ufc.quixada.npi.gp.model.Estagiario;
import br.ufc.quixada.npi.gp.model.Estagio;
import br.ufc.quixada.npi.gp.model.Frequencia;
import br.ufc.quixada.npi.gp.model.Submissao;
import br.ufc.quixada.npi.gp.model.Submissao.StatusEntrega;
import br.ufc.quixada.npi.gp.model.Submissao.TipoSubmissao;
import br.ufc.quixada.npi.gp.model.Turma;
import br.ufc.quixada.npi.gp.repository.EstagioRepository;
import br.ufc.quixada.npi.gp.repository.SubmissaoRepository;
import br.ufc.quixada.npi.gp.service.ConsolidadoFrequencia;
import br.ufc.quixada.npi.gp.service.EstagioService;
@Named
public class EstagioServiceImpl implements EstagioService {

	@Autowired
	private EstagioRepository estagioRepository;
	
	@Autowired
	private SubmissaoRepository submissaoRepository;
	
	@Override
	public Estagio buscarEstagioPorIdEEstagiarioId(Long idEstagio, Long idEstagiario) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Estagio> buscarEstagiosPorEstagiarioCpf(Long idEstagiario) {
		// TODO Auto-generated method stub
		return null;
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
			submissaoRepository.save(submissao);
		}else{
			throw new Exception();
		}
	}

	@Override
	public void editarRelatorio(Submissao submissao) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void avaliarSubmissao(Submissao submissao) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Submissao buscarSubmissaoPorTipoSubmissaoEEstagioIdECpf(TipoSubmissao tipoSubmissao, Long idEstagio, String cpf) {
		return submissaoRepository.findByTipoSubmissaoAndEstagio_IdAndEstagio_Estagiario_Pessoa_Cpf(tipoSubmissao, idEstagio, cpf);
	}

	@Override
	public void adicionarAvaliacaoRendimento(AvaliacaoRendimento avaliacaoRendimento) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void editarAvaliacaoRendimento(AvaliacaoRendimento avaliacaoRendimento) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Frequencia> buscarFrequenciaPorEstagioId(Long idEstagio) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Frequencia buscarFrequenciaPorDataEEstagioId(Date data, Long idEstagio) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Frequencia> buscarFrequenciasPorDataETurmaId(Date data, Long idTurma) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ConsolidadoFrequencia calcularDadosConsolidados(List<Frequencia> frequencia) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Frequencia> gerarFrequencia(Turma turma, Estagiario estagiario) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Frequencia> buscarFrequenciasPendentes(Turma turma, Estagiario estagiario) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean liberarPresenca(Turma turma) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean permitirPresenca(Estagio estagio) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void realizarPresenca(Estagio estagio) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void adicionarFrequencia(Frequencia frequencia) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void editarStatusFrequencia() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void adicionarObservacaoFrequencia() {
		// TODO Auto-generated method stub
		
	}
	
	
	
	/**
	 * 
	
	@Inject
	private GenericRepository<Submissao> submissaoRepository;
	
	@Inject
	private FrequenciaRepository frequenciaRepository;

	@Inject
	private GenericRepository<Estagio> estagioRepository;
	
	@Inject 
	private GenericRepository<AvaliacaoRendimento> avaliacaoRendimentoRepository;
	
	
	
	// INICIO FREQUENCIA

	@Transactional
	public List<Frequencia> getFrequenciasByTurmaIdAndData(Date data, Long idTurma) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("data", data);
		params.put("turma", idTurma);
		
		@SuppressWarnings("unchecked")
		List<Frequencia> frequencias = frequenciaRepository.find(QueryType.JPQL, "select f.id, f.estagiario.nomeCompleto, f.observacao, f.statusFrequencia, f.tipoFrequencia, f.horario from Frequencia f join f.turma t where t.id = :turma and f.data = :data", params);
		return frequencias;
	}

	@Transactional
	public List<Frequencia> getFrequenciaTurma(Turma turma) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("turma", turma.getId());
		
		@SuppressWarnings("unchecked")
		List<Frequencia> frequencias = frequenciaRepository.find(QueryType.JPQL, "select distinct f from Frequencia f join f.turma t where t.id = :turma", params);

		return frequencias;
	}

	
	@Transactional
	public void atualizarStatus() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("statusAtualizado", StatusFrequencia.FALTA);
		
		//frequenciaRepository.updateStatus("update Frequencia f set statusFrequencia ='FALTA' where f.data = CURRENT_DATE and f.statusFrequencia = 'AGUARDO' ", params);
	}

	@Override
	public Frequencia getFrequenciaDeHojeByEstagiarioId(Long id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		
		Frequencia frequencia = (Frequencia) frequenciaRepository.findFirst(QueryType.JPQL, "select f from Frequencia f where f.data = CURRENT_DATE and f.estagiario.id = :id", params);

		return frequencia;
	}

	@Override
	public List<Frequencia> getFrequenciasByEstagiarioId(Long idEstagiario, Long idTurma) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("idEstagiario", idEstagiario);
		params.put("idTurma", idTurma);
		
		@SuppressWarnings("unchecked")
		List<Frequencia> frequencias = frequenciaRepository.find(QueryType.JPQL, "select f from Frequencia f where f.estagiario.id = :idEstagiario and f.turma.id = :idTurma ORDER BY data ASC", params);

		return frequencias;
	}

	public DadoConsolidado calcularDadosConsolidados(List<Frequencia> frequencia) {
		int faltas = frequenciaFalta(frequencia);
		int diasTrabalhados = frequenciaDiasTrabalhados(frequencia);
		double porcentagemFrequencia = frequenciaPorcentagem(diasTrabalhados, faltas);
		DadoConsolidado dadosConsolidados = new DadoConsolidado(faltas, diasTrabalhados, porcentagemFrequencia);
		return dadosConsolidados;
	}

	private int frequenciaFalta(List<Frequencia> frequencia) {
		int faltas = 0;

		for (Frequencia frequenciaFaltas : frequencia) {
			if (frequenciaFaltas.getStatusFrequencia() != null && frequenciaFaltas.getStatusFrequencia().equals(StatusFrequencia.FALTA)) {
				faltas++;
			}
		}
		return faltas;
	}

	private int frequenciaDiasTrabalhados(List<Frequencia> frequencia) {
		int diasTrabalhados = 0;

		for (Frequencia frequenciaFaltas : frequencia) {
			if (frequenciaFaltas.getStatusFrequencia() != null &&  frequenciaFaltas.getStatusFrequencia().equals(StatusFrequencia.PRESENTE)) {
				diasTrabalhados++;
			}
		}

		return diasTrabalhados;
	}

	private int frequenciaPorcentagem(int diasTrabalhados, int faltas) {
		if(diasTrabalhados != 0) {
			return (diasTrabalhados * 100) / (diasTrabalhados + faltas);		
		}
		else {
			return 0;
		}
	}

	@Override
	public boolean liberarPreseca(Turma turma ) {
		if(UtilGestao.hojeEDiaDeTrabahoDaTurma(turma.getHorarios()) && UtilGestao.isHoraPermitida(turma.getHorarios())){
			return true;
		}

		return false;
	}
	@Override
	public List<Frequencia> gerarFrequencia(Turma turma, Estagiario estagiario) {

		LocalDate inicioPeriodoTemporario = new LocalDate(turma.getInicio());
		LocalDate fimPeriodo = new LocalDate(new Date());

		List<Frequencia> frequencias = new ArrayList<Frequencia>();
		
		while (!inicioPeriodoTemporario.isAfter(fimPeriodo)) {

			if (UtilGestao.isDiaDeTrabahoDaTurma(turma.getHorarios(), inicioPeriodoTemporario)) {
				Frequencia frequencia = getFrequenciaByDataByTurmaByEstagiario(inicioPeriodoTemporario.toDate(), turma.getId(), estagiario.getId());

				if(frequencia == null){
					frequencia = new Frequencia();
					frequencia.setTipoFrequencia(TipoFrequencia.NORMAL);
					frequencia.setData(inicioPeriodoTemporario.toDate());
				}

				frequencias.add(frequencia);
			}
			inicioPeriodoTemporario = inicioPeriodoTemporario.plusDays(1);
		}

		return frequencias;
	}
		
	public List<Estagiario> getEstagiariosSemFrequencia(Date data, Long idTurma){
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("data", data);
		params.put("idTurma", idTurma);
		
		@SuppressWarnings("unchecked")
		List<Estagiario> frequencias = frequenciaRepository.find(QueryType.JPQL, "select e from Estagiario as e "
				+ "where  e.id in (select e.id from Estagiario as e, Frequencia as f  "
				+ "where f.turma.id = :idTurma and e.id = f.estagiario.id group by e.id) "
				+ "and e.id not in (select f.estagiario.id from Frequencia as f "
				+ "where f.turma.id = :idTurma and f.data = :data)", params);

		return frequencias;
	}
	
	@Override
	public Frequencia getFrequenciaByDataByTurmaByEstagiario(Date data, Long turma, Long estagiario) {
		return null;// frequenciaRepository.findFrequenciaByDataByTurmaByEstagiario(data, turma, estagiario);
	}
		
	public List<Frequencia> frequenciaPendente(Turma turma, Estagiario estagiario){
		List<Frequencia> frequenciaTotal = gerarFrequencia(turma, estagiario);
		
		List<Frequencia> frequenciaPendentes = new ArrayList<Frequencia>() ;
		for (Frequencia frequencia : frequenciaTotal) {
			if(frequencia.getStatusFrequencia() == null){
				frequenciaPendentes.add(frequencia);
			}
		}
		return frequenciaPendentes;
	}
	
	// NOVOS METODOS
	public void submeterPlano(Submissao submissao){
		submissaoRepository.save(submissao);
	}
	public void submeterRelatorio(Submissao submissao){
		submissaoRepository.save(submissao);
	}
	public void editarPlano(Submissao submissao) throws Exception{
		if(StatusEntrega.SUBMETIDO.equals(submissao.getStatusEntrega()) || StatusEntrega.CORRECAO.equals(submissao.getStatusEntrega())){
			submissaoRepository.update(submissao);
		}else{
			throw new Exception();
		}
	}
	public void editarRelatorio(Submissao submissao) throws Exception{
		if(StatusEntrega.SUBMETIDO.equals(submissao.getStatusEntrega()) || StatusEntrega.CORRECAO.equals(submissao.getStatusEntrega())){
			submissaoRepository.update(submissao);
		}else{
			throw new Exception();
		}
	}
	public void realizarPresenca(Estagio estagio){
		
	}

	public Submissao getSubmissaoByEstagioIdAndTipo(Long idEstagio, TipoSubmissao tipoSubmissao) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("idEstagio", idEstagio);
		params.put("tipoSubmissao", tipoSubmissao);
		
		Submissao submissao = (Submissao) submissaoRepository.findFirst(QueryType.JPQL, "select s from Submissao s where s.tipoSubmissao = :tipoSubmissao and s.estagio.id = :idEstagio", params);

		return submissao;
	}

	@Override
	public void avaliarSubmissao(Submissao submissao) {
		submissaoRepository.update(submissao);		
	}
	
	@Override
	public void realizarAvaliacaoRendimento(AvaliacaoRendimento avaliacaoRendimento) {
		avaliacaoRendimentoRepository.save(avaliacaoRendimento);
	}

	@Override
	public List<Estagiario> getAniversariantesMesByTurmaId(Long id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		
		List<Estagiario> estagiarios = estagioRepository.find(QueryType.JPQL, "select e from Estagiario e join e.turmas t where t.id = :id and month(e.dataNascimento) = month(current_date())", params);

		return estagiarios;
	}

	@Override
	public Estagio getEstagioByIdAndEstagiarioId(Long idEstagio, Long idEstagiario) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("idEstagio", idEstagio);
		params.put("idEstagiario", idEstagiario);

		Estagio estagio = (Estagio) estagioRepository.findFirst(QueryType.JPQL, "select e from Estagio e where e.id = :idEstagio and e.estagiario.id = :idEstagiario ", params);

		return estagio;
	}

	@Override
	public void adicionarFrequencia(Frequencia frequencia) {
		frequenciaRepository.save(frequencia);		
	}

	@Override
	public boolean permitirPresenca(Estagio estagio) {
		if (estagio != null){
			Frequencia frequencia = null;//getFrequenciaByData(new Date(), estagio.getId());
			if(frequencia == null) {
				return liberarPreseca(estagio.getTurma());
			}
		}
		return false;
	}

	@Override
	public Estagio getEstagioByIdAndEstagiarioCpf(Long idEstagio, String cpf) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("idEstagio", idEstagio);
		params.put("cpf", cpf);

		Estagio estagio = (Estagio) estagioRepository.findFirst(QueryType.JPQL, "select e from Estagio e where e.id = :idEstagio and e.estagiario.cpf = :cpf ", params);

		return estagio;
	}
	
	// FIM NOVOS METODOS 
	 */

}
