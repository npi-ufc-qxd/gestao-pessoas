package ufc.quixada.npi.gp.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;

import ufc.quixada.npi.gp.model.Documento;
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
		for (Documento documento : documentos) {
			documentoRepository.save(documento);
		}
	}

	@Override
	public Documento getDocumentoById(Long id) {
		return documentoRepository.find(Documento.class, id);
	}

	@Override
	public List<Documento> getDocumentosByEstagiarioId(Long idEstagiario, Long idTurma) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("idPessoa", idEstagiario);
		params.put("idTurma", idTurma);
		@SuppressWarnings("unchecked")
		List<Documento> documentos = find(QueryType.JPQL, "select d from Documento d where d.pessoa.id = :idPessoa and d.turma.id = :idTurma ORDER BY data ASC", params);

		return documentos;
	}

	@Override
	public void remover(Documento documento) {
		documentoRepository.delete(documento);
	}

}
