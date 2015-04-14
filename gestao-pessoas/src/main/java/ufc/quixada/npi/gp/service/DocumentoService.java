package ufc.quixada.npi.gp.service;

import java.util.List;

import ufc.quixada.npi.gp.model.Documento;
import ufc.quixada.npi.gp.model.Projeto;


public interface DocumentoService {
	
	void salvar(Documento documento);
	
	void salvar(List<Documento> documentos);
	
	Documento getDocumentoById(Long id);
	
	void remover(Documento documento);
	
	List<Documento> getDocumentoByProjeto(Projeto projeto);
	
}
