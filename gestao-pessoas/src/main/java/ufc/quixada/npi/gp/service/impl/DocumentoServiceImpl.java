package ufc.quixada.npi.gp.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;

import ufc.quixada.npi.gp.model.Documento;
import ufc.quixada.npi.gp.model.Projeto;
import ufc.quixada.npi.gp.service.DocumentoService;
import br.ufc.quixada.npi.enumeration.QueryType;
import br.ufc.quixada.npi.repository.GenericRepository;

@Named
public class DocumentoServiceImpl implements DocumentoService {
	
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
	public List<Documento> getDocumentoByProjeto(Projeto projeto) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", projeto.getId());
		return documentoRepository.find(QueryType.JPQL, "select d from Documento as d where d.pessoa.id = :id" , params);
	}
	

}
