package ufc.quixada.npi.gp.service.impl;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import ufc.quixada.npi.gp.model.Documento;
import ufc.quixada.npi.gp.model.Estagiario;
import ufc.quixada.npi.gp.model.Submissao;
import ufc.quixada.npi.gp.model.Turma;
import ufc.quixada.npi.gp.model.enums.StatusEntrega;
import ufc.quixada.npi.gp.model.enums.StatusTurma;
import ufc.quixada.npi.gp.model.enums.Tipo;
import ufc.quixada.npi.gp.service.TurmaService;
import br.ufc.quixada.npi.enumeration.QueryType;
import br.ufc.quixada.npi.repository.GenericRepository;
import br.ufc.quixada.npi.service.impl.GenericServiceImpl;

@Named
public class TurmaServiceImpl extends GenericServiceImpl<Turma> implements TurmaService {
	
	@Autowired
	private GenericRepository<Submissao> submissaoRepository;
	
/*	@Override
	public List<Turma> getTurmaPeriodo(String ano, String semestre, Long idSupervisor) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ano", ano);
		params.put("semestre", semestre);
		params.put("supervisor", idSupervisor);
		@SuppressWarnings("unchecked")
		List<Turma> turmas = find(QueryType.JPQL,"select t.id, t.nome from Turma t join t.periodo p where p.ano = :ano and p.semestre = :semestre and t.supervisor.id = :supervisor", params);

		return turmas;
	}

	@Override
	public List<Turma> getMinhasTurmaPeriodo(String ano, String semestre, Long idSupervisor) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ano", ano);
		params.put("semestre", semestre);
		params.put("idSupervisor", idSupervisor);
		@SuppressWarnings("unchecked")
		List<Turma> turmas = find(QueryType.JPQL,"select t from Turma t join t.periodo p where p.ano = :ano and p.semestre = :semestre and t.supervisor.id = :idSupervisor", params);

		return turmas;
	}
	
	@Override
	public List<Turma> getTurmasAno(String ano, StatusPeriodo statusPeriodo) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ano", ano);
		params.put("status", statusPeriodo);
		@SuppressWarnings("unchecked")
		List<Turma> turmas = find(QueryType.JPQL,"select t from Turma t join t.periodo p where p.ano = :ano and p.statusPeriodo = :status", params);
		return turmas;
	}
 */

	@Override
	public List<Turma> getTurmasBySupervisorId(Long idSupervisor) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("idSupervisor", idSupervisor);
		@SuppressWarnings("unchecked")
		List<Turma> turmas = find(QueryType.JPQL,"select t from Turma t where t.supervisor.id = :idSupervisor", params);

		return turmas;
	}


	@Override
	public List<Turma> getTurmasBySupervisorIdAndStatus(StatusTurma statusTurma, Long idSupervisor) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("statusTurma", statusTurma);
		params.put("idSupervisor", idSupervisor);
		@SuppressWarnings("unchecked")
		List<Turma> turmas = find(QueryType.JPQL,"select t from Turma t where t.statusTurma = :statusTurma and t.supervisor.id = :idSupervisor", params);

		return turmas;
	}

	@Override
	public Turma getTurmaByIdAndSupervisorById(Long idTurma, Long idSupervisor) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("idTurma", idTurma);
		params.put("idSupervisor", idSupervisor);

		Turma turma = (Turma) findFirst(QueryType.JPQL,"select t from Turma t where t.id = :idTurma and t.supervisor.id = :idSupervisor", params);

		return turma;
	}

	@Override
	public List<Turma> getTurmasByEstagiarioIdAndStatus(StatusTurma statusTurma, Long idEstagiario) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("idEstagiario", idEstagiario);
		params.put("statusTurma", statusTurma);
		List<Turma> turmas =  find(QueryType.JPQL, "select t from Turma t where :idEstagiario member of t.estagiarios and t.statusTurma = :statusTurma", params);


		return turmas;
	}
	
	@Override
	public List<Turma> getTurmasByEstagiarioId(Long idEstagiario) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("idEstagiario", idEstagiario);
		List<Turma> turmas =  find(QueryType.JPQL, "select t from Turma t where :idEstagiario member of t.estagiarios", params);

		return turmas;
	}

	@Override
	public Turma getTurmaByIdAndEstagiarioId(Long idTurma, Long idEstagiario) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("idEstagiario", idEstagiario);
		params.put("idTurma", idTurma);
		
		Turma turma =  (Turma) findFirst(QueryType.JPQL, "select t from Turma t where t.id = :idTurma and :idEstagiario member of t.estagiarios", params);


		return turma;
	}
	/* Metodos de submissão */
	
	
		public void salvar(Submissao submissao) {
			submissaoRepository.save(submissao);
		}
		
		
		public void salvar(List<Submissao> submissaos) {
			for(Submissao submissao : submissaos) {
				submissaoRepository.save(submissao);
			}
		}
	
		
		public Submissao getSubmissaoById(Long id) {
			return submissaoRepository.find(Submissao.class, id);
		}
	
		
		public void remover(Submissao submissao) {
			submissaoRepository.delete(submissao);
		}
		
		public Submissao getSubmissaoByEstagiarioIdAndIdTurmaAndTipo(Long idEstagiario, Long idTurma, Tipo tipo) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("idEstagiario", idEstagiario);
			params.put("idTurma", idTurma);
			params.put("tipo", tipo);
			@SuppressWarnings("unchecked")
			Submissao submissao = (Submissao) findFirst(QueryType.JPQL,"select s from Turma t join t.submissoes s where s.estagiario.id = :idEstagiario and t.id = :idTurma and s.tipo = :tipo", params);
			
			return submissao;
		}
				
		public List<Submissao> getSubmissoesByEstagiarioIdAndIdTurma(Long idEstagiario, Long idTurma) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("idEstagiario", idEstagiario);
			params.put("idTurma", idTurma);
			
			@SuppressWarnings("unchecked")
			List <Submissao> submissoes = find(QueryType.JPQL,"select s from Turma t join t.submissoes s where s.estagiario.id = :idEstagiario and t.id = :idTurma", params);
			
			return submissoes;			
			
		}
		


		public void submeterDocumento(Estagiario estagiario, Turma turma, Tipo tipo, MultipartFile anexo) throws IOException {
			Submissao submissao = getSubmissaoByEstagiarioIdAndIdTurmaAndTipo(estagiario.getId(), turma.getId(), tipo);
			
			if(submissao == null) {
				submissao = new Submissao();
			}

			if(StatusEntrega.ENVIADO.equals(submissao.getStatusEntrega())) {
				
				if(anexo.getBytes() != null && anexo.getBytes().length != 0 && anexo.getContentType().equals("application/pdf")){
					Documento documento = new Documento();
					documento.setNome(tipo+"_"+estagiario.getNomeCompleto().toUpperCase());
					documento.setExtensao(anexo.getContentType());
					documento.setArquivo(anexo.getBytes());

					
					submissao.setData(new Date());
					submissao.setHorario(new Date());
					submissao.setStatusEntrega(StatusEntrega.ENVIADO);
					submissao.setTipo(tipo);
					submissao.setEstagiario(estagiario);
					submissao.setDocumento(documento);
					
					turma.getSubmissoes().add(submissao);
					
					update(turma);
				}
			}
			
		}
		/* fim métodos de submissão */

}
