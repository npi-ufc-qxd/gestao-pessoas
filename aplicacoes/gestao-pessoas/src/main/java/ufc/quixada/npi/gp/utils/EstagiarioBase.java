package ufc.quixada.npi.gp.utils;

import java.util.ArrayList;
import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import ufc.quixada.npi.gp.model.Estagiario;
import ufc.quixada.npi.gp.model.Pessoa;
import ufc.quixada.npi.gp.model.enums.Curso;
import ufc.quixada.npi.gp.model.enums.Estado;

public class EstagiarioBase implements JRDataSource {
	private List<Estagiario> listEstagiario;
	private int index = -1;

	public EstagiarioBase() {
		super();
		prepareDataSource();
	}

	public void prepareDataSource() {
		listEstagiario = new ArrayList<Estagiario>();
		Pessoa pessoa = new Pessoa("123456", "Jefferson", "Barbosa",
				"jefferson@hotmail.com", "6507687");
		listEstagiario.add(new Estagiario((long) 1, "Jefferson1", null,
				"Algum", "teste", "63900056", "Quixadá", Estado.CEARA, "36720000",
				Curso.CIÊNCIA_COMPUTAÇÃO, "8", 338888, "redmine", "github", "hagout",
				pessoa));
		listEstagiario.add(new Estagiario((long) 2, "Jefferson2", null,
				"Algum", "teste", "63900056", "Quixadá", Estado.CEARA, "36720000",
				Curso.CIÊNCIA_COMPUTAÇÃO, "8", 338888, "redmine", "github", "hagout",
				pessoa));
		listEstagiario.add(new Estagiario((long) 3, "Jefferson3", null,
				"Algum", "teste", "63900056", "Quixadá", Estado.CEARA, "36720000",
				Curso.CIÊNCIA_COMPUTAÇÃO, "8", 338888, "redmine", "github", "hagout",
				pessoa));
	}

	@Override
	public Object getFieldValue(JRField field) throws JRException {
		Estagiario estagiario = listEstagiario.get(index);
		if (field.getName().equals("stuId")) {
			return estagiario.getId();
		} else if (field.getName().equals("name")) {
			return estagiario.getNomeCompleto();
		} else if (field.getName().equals("course")) {
			return estagiario.getCurso();
		} else if (field.getName().equals("semestre")) {
			return estagiario.getSemestre();
		}
		return null;
	}

	@Override
	public boolean next() throws JRException {
		if (index < listEstagiario.size() - 1) {
			index++;
			return true;
		}
		return false;
	}

}
