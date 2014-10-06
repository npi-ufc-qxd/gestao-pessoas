package ufc.quixada.npi.gp.repository.jpa;

import javax.inject.Named;

import ufc.quixada.npi.gp.model.Documento;
import ufc.quixada.npi.gp.repository.DocumentoRepository;

@Named
public class DocumentoRepositoryImpl extends JpaGenericRepositoryImpl<Documento> implements DocumentoRepository{

}
