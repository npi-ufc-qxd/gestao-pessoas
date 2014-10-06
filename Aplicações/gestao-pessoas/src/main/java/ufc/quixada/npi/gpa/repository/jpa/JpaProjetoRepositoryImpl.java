package ufc.quixada.npi.gpa.repository.jpa;

import javax.inject.Named;

import ufc.quixada.npi.gpa.model.Projeto;
import ufc.quixada.npi.gpa.repository.ProjetoRepository;

@Named
public class JpaProjetoRepositoryImpl extends JpaGenericRepositoryImpl<Projeto> implements ProjetoRepository {
	
}