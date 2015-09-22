package ufc.quixada.npi.gp.service.impl;

import java.util.List;

import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;

import ufc.quixada.npi.gp.model.Documento;
import ufc.quixada.npi.gp.service.DocumentoService;
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

}
