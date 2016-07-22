package br.ufc.quixada.npi.ge.service;

import java.util.List;

import br.ufc.quixada.npi.ge.model.Selecao;

public interface SelecaoService {

	Selecao buscarSelecaoPorId(Long idSelecao);
	
	List<Selecao> buscarTodasSelecoes();
	
	void adicionarSelecao(Selecao selecao);
	
	void editarSelecao(Selecao selecao);
	
}
