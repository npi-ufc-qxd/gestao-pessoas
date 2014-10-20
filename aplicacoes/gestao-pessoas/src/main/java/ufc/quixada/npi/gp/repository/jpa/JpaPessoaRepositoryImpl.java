package ufc.quixada.npi.gp.repository.jpa;

import javax.inject.Named;

import ufc.quixada.npi.gp.model.Pessoa;
import ufc.quixada.npi.gp.repository.PessoaRepository;

@Named
public class JpaPessoaRepositoryImpl extends JpaGenericRepositoryImpl<Pessoa> implements PessoaRepository{

}
