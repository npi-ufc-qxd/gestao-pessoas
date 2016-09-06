package br.ufc.quixada.npi.ge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import br.ufc.quixada.npi.ge.model.DocumentoEntityListener;


@SpringBootApplication
public class GestaoPessoasApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestaoPessoasApplication.class, args);
	}
	
	@Bean
	public DocumentoEntityListener getDocumentoEntityListener() {
		return new DocumentoEntityListener();
	}
}
