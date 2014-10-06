package ufc.quixada.npi.gpa.repository.jpa;

import javax.inject.Named;

import ufc.quixada.npi.gpa.model.Documento;
import ufc.quixada.npi.gpa.repository.DocumentoRepository;

@Named
public class DocumentoRepositoryImpl extends JpaGenericRepositoryImpl<Documento> implements DocumentoRepository{

}
