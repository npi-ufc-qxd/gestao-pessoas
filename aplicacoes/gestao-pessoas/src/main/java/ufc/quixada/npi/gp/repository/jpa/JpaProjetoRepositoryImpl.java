package ufc.quixada.npi.gp.repository.jpa;

import javax.inject.Named;

import ufc.quixada.npi.gp.model.Projeto;
import ufc.quixada.npi.gp.repository.ProjetoRepository;

@Named
public class JpaProjetoRepositoryImpl extends JpaGenericRepositoryImpl<Projeto> implements ProjetoRepository {
	
}