package ufc.quixada.npi.gp.service;

import java.util.List;

import ufc.quixada.npi.gp.model.Documento;
import ufc.quixada.npi.gp.model.Frequencia;


public interface DocumentoService {
	
	void salvar(Documento documento);
	
	void salvar(List<Documento> documentos);
	
	Documento getDocumentoById(Long id);
	
	List<Documento> getDocumentosByEstagiarioId(Long idEstagiario, Long idTurma);
	
	void remover(Documento documento);
	
}
