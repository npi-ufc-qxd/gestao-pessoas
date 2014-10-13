package ufc.quixada.npi.gp.repository.jpa;

import javax.inject.Named;

import ufc.quixada.npi.gp.model.Estagiario;
import ufc.quixada.npi.gp.repository.EstagiarioRepository;

@Named
public class JpaEstagiarioRepositoryImpl extends JpaGenericRepositoryImpl<Estagiario> implements EstagiarioRepository{

}
