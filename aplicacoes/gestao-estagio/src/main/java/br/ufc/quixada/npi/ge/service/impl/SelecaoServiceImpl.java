package br.ufc.quixada.npi.ge.service.impl;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import br.ufc.quixada.npi.ge.model.Selecao;
import br.ufc.quixada.npi.ge.repository.SelecaoRepository;
import br.ufc.quixada.npi.ge.service.SelecaoService;
@Named
public class SelecaoServiceImpl implements SelecaoService{

	@Inject
	private SelecaoRepository selecaoRepository;
	
	@Override
	public Selecao buscarSelecaoPorId(Long idSelecao) {
		return selecaoRepository.findOne(idSelecao);
	}

	@Override
	public List<Selecao> buscarTodasSelecoes() {
		return selecaoRepository.findAll();
	}

	@Override
	public void adicionarSelecao(Selecao selecao) {
		selecaoRepository.save(selecao);
	}

	@Override
	public void editarSelecao(Selecao selecao) {
		selecaoRepository.save(selecao);
	}

}
