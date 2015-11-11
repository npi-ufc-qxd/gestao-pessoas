package ufc.quixada.npi.gp.service;

import java.util.List;

import br.ufc.quixada.npi.service.GenericService;
import ufc.quixada.npi.gp.model.Documento;
import ufc.quixada.npi.gp.model.enums.Tipo;


public interface DocumentoService extends GenericService<Documento>  {
	
	void salvar(Documento documento);
	
	void salvar(List<Documento> documentos);
	
	Documento getDocumentoById(Long id);
	
	void remover(Documento documento);
	
	Documento getDocumentoByPessoaIdAndIdTurmaAndTipo(Long idPessoa, Long idTurma, Tipo tipo);
	
	List<Documento> getDocumentosByPessoaId(Long idPessoa);
	
}
