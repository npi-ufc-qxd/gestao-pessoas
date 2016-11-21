package br.ufc.quixada.npi.ge.model;

import static br.ufc.quixada.npi.ge.utils.Constants.EXCEPTION_BUSCAR_ARQUIVO;
import static br.ufc.quixada.npi.ge.utils.Constants.EXCEPTION_SALVAR_ARQUIVO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.persistence.PostLoad;
import javax.persistence.PrePersist;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import br.ufc.quixada.npi.ge.exception.GestaoEstagioException;


public class DocumentoEntityListener implements ApplicationContextAware{
	
	private static ApplicationContext context;

	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		DocumentoEntityListener.context = context;
	}
	
	public ApplicationContext getApplicationContext(){
		return context;
	}

	@PrePersist
	public void salvarArquivo(Documento documento) throws GestaoEstagioException{
		
		context.getAutowireCapableBeanFactory().autowireBean(this);
		
		String caminhoDiretorio = getDiretorioDocumento(documento);
		File diretorio = new File(System.getProperty("user.home"),caminhoDiretorio);
		diretorio.mkdirs();	
		
		try {
			File arquivo = new File(diretorio, getNomeArquivo(documento));
			FileOutputStream fop = new FileOutputStream(arquivo);
			arquivo.createNewFile();
			fop.write(documento.getArquivo());
			fop.flush();
			fop.close();
		} catch (IOException ex) {
			throw new GestaoEstagioException(EXCEPTION_SALVAR_ARQUIVO);
		}
	}
	
	private String getDiretorioDocumento(Documento documento) {
		//retorna a parte da string caminho, do começo da string até a ultima /, que é o diretório
		return documento.getCaminho().substring(0, documento.getCaminho().lastIndexOf("/"));
	}
	
	private String getNomeArquivo(Documento documento){
		//retorna a parte da string caminho, da a ultima / até o fim da string, que é o nome do arquivo
		return documento.getCaminho().substring(documento.getCaminho().lastIndexOf("/"), documento.getCaminho().length());
	} 
	
	@PostLoad
	public void carregarArquivo(Documento documento) throws GestaoEstagioException{
		FileInputStream fileInputStream = null;
		File file = new File(System.getProperty("user.home"),documento.getCaminho());
		byte[] bFile = new byte[(int) file.length()];

		try {
			fileInputStream = new FileInputStream(file);
			fileInputStream.read(bFile);
			fileInputStream.close();
		} catch (IOException e) {
			throw new GestaoEstagioException(documento.getCaminho() + EXCEPTION_BUSCAR_ARQUIVO + e.getMessage());
		}
		
		documento.setArquivo(bFile);
	}
	
}
