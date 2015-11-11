package ufc.quixada.npi.gp.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import ufc.quixada.npi.gp.model.Documento;
import ufc.quixada.npi.gp.model.enums.StatusFrequencia;
import ufc.quixada.npi.gp.model.enums.Tipo;
import ufc.quixada.npi.gp.service.DocumentoService;
import br.ufc.quixada.npi.enumeration.QueryType;
import br.ufc.quixada.npi.repository.GenericRepository;
import br.ufc.quixada.npi.service.impl.GenericServiceImpl;

@Named
public class DocumentoServiceImpl extends GenericServiceImpl<Documento> implements DocumentoService {
	
	@Autowired
	private GenericRepository<Documento> documentoRepository;

	@Override
	public void salvar(Documento documento) {
		documentoRepository.save(documento);
	}
	
	@Override
	public void salvar(List<Documento> documentos) {
		for(Documento documento : documentos) {
			documentoRepository.save(documento);
		}
	}

	@Override
	public Documento getDocumentoById(Long id) {
		return documentoRepository.find(Documento.class, id);
	}

	@Override
	public void remover(Documento documento) {
		documentoRepository.delete(documento);
	}
	
	@Override
	public Documento getDocumentoByPessoaIdAndIdTurmaAndTipo(Long idPessoa, Long idTurma, Tipo tipo) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("idPessoa", idPessoa);
		params.put("idTurma", idTurma);
		params.put("tipo", tipo);
		@SuppressWarnings("unchecked")
		Documento documento = (Documento) findFirst(QueryType.JPQL,"select d from Documento d where d.pessoa.id = :idPessoa and d.turma.id = :idTurma and d.tipo = :tipo", params);

		return documento;
	}
	
	@Override
	public List<Documento> getDocumentosByPessoaId(Long idPessoa) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("idPessoa", idPessoa);
		@SuppressWarnings("unchecked")
		List <Documento> documentos = find(QueryType.JPQL,"select d from Documento d where d.pessoa.id = :idPessoa", params);

		return documentos;
	}
}
