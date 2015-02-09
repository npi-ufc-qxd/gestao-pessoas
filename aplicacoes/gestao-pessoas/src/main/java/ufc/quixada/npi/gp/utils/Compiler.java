package ufc.quixada.npi.gp.utils;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;

public class Compiler {
	public static void main(String[] args) {
//		String sourceFileName = "C://Users/Admin/git/work/gestao-pessoas/aplicacoes/gestao-pessoas/src/main/resources/Termo_Compromisso.jrxml";
		String sourceFileName = "C://Users/Admin/git/work/gestao-pessoas/aplicacoes/gestao-pessoas/src/main/resources/Declaracao_Estagio.jrxml";
		System.out.println("Compiling Report Design ...");
		try {
			JasperCompileManager.compileReportToFile(sourceFileName);
		} catch (JRException e) {
			e.printStackTrace();
		}
		System.out.println("Completo");
	}
}
