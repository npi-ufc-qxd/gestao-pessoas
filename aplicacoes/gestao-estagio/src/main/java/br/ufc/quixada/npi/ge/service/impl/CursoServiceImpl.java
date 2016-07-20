package br.ufc.quixada.npi.ge.service.impl;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import br.ufc.quixada.npi.ge.model.Curso;
import br.ufc.quixada.npi.ge.repository.CursoRepository;
import br.ufc.quixada.npi.ge.service.CursoService;

@Named
public class CursoServiceImpl implements CursoService{

	@Inject
	private CursoRepository cursoRepository;
	
	@Override
	public List<Curso> buscarCursos() {
		return cursoRepository.findAll();
	}

}
