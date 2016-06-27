package br.ufc.quixada.npi.ge.utils;

import java.util.List;

import br.ufc.quixada.npi.ge.model.Estagiario;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRAbstractBeanDataSourceProvider;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class EstagiarioDataSource extends JRAbstractBeanDataSourceProvider {

	public EstagiarioDataSource() {
		super(Estagiario.class);
	}

	private List<Estagiario> listEstagiario;

	@Override
	public JRDataSource create(JasperReport jrReport) throws JRException {
		return new JRBeanCollectionDataSource(null);
	}

	@Override
	public void dispose(JRDataSource jrds) throws JRException {
		listEstagiario.clear();
		listEstagiario = null;
	}
}