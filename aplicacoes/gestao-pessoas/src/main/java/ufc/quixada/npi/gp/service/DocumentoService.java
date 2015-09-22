package ufc.quixada.npi.gp.service;

import java.util.List;

import ufc.quixada.npi.gp.model.Documento;


public interface DocumentoService {
	
	void salvar(Documento documento);
	
	void salvar(List<Documento> documentos);
	
	Documento getDocumentoById(Long id);
	
	void remover(Documento documento);
	
}
