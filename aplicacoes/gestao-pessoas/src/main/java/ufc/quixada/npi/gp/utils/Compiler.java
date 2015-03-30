package ufc.quixada.npi.gp.utils;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;

public class Compiler {
	public static void main(String[] args) {
		String sourceFileName = "C://Users/Admin/git/gestao-pessoas/aplicacoes/gestao-pessoas/src/main/resources/Termo_Compromisso.jrxml";
		System.out.println("Compiling Report Design ...");
		try {
			/**
			 * Compile the report to a file name same as the JRXML file name
			 */
			JasperCompileManager.compileReportToFile(sourceFileName);
		} catch (JRException e) {
			e.printStackTrace();
		}
		System.out.println("Done compiling!!! ...");
	}
}

//public class Compiler {
//	public static void main(String[] args) {
////		String sourceFileName = "C://Users/Admin/git/work/gestao-pessoas/aplicacoes/gestao-pessoas/src/main/resources/Termo_Compromisso.jrxml";
//		String sourceFileName = "C://Users/Admin/git/gestao-pessoas/aplicacoes/gestao-pessoas/src/main/resources/Termo_Compromisso2.jrxml";
//		System.out.println("Compiling Report Design ...");
//		try {
//			JasperCompileManager.compileReportToFile(sourceFileName);
//		} catch (JRException e) {
//			e.printStackTrace();
//		}
//		System.out.println("Completo");
//	}
//}
