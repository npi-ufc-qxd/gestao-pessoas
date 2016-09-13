package br.ufc.quixada.npi.ge.validation;

import javax.inject.Named;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import br.ufc.quixada.npi.ge.model.AvaliacaoRendimento;


@Named
public class AvaliacaoRendimentoValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return AvaliacaoRendimento.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		AvaliacaoRendimento avaliacaoRendimento = (AvaliacaoRendimento) target;
		
		validateStrings(errors, avaliacaoRendimento.getAtividadeCurricular(), "atividadeCurricular", "Campo obrigatótio");
		validateStrings(errors, avaliacaoRendimento.getObjetivoEstagio(), "objetivoEstagio", "Campo obrigatório");

		validateNotNull(errors, avaliacaoRendimento.getInicioAvaliacao(), "inicioAvaliacao", "Campo obrigatório");
		validateNotNull(errors, avaliacaoRendimento.getTerminoAvaliacao(), "terminoAvaliacao", "Campo obrigatório");
		
		validateNotNull(errors, avaliacaoRendimento.getFrequencia(), "frequencia", "Campo obrigatório");
		validateNotNull(errors, avaliacaoRendimento.getPermanencia(), "permanencia", "Campo obrigatório");
		validateNotNull(errors, avaliacaoRendimento.getDisciplina(), "disciplina", "Campo obrigatório");

		validateNotNull(errors, avaliacaoRendimento.getIniciativa(), "iniciativa", "Campo obrigatório");
		validateNotNull(errors, avaliacaoRendimento.getQuantidadeTrabalho(), "quantidadeTrabalho", "Campo obrigatório");
		validateNotNull(errors, avaliacaoRendimento.getQualidadeTrabalho(), "qualidadeTrabalho", "Campo obrigatório");
		validateNotNull(errors, avaliacaoRendimento.getCumprimentoPrazos(), "cumprimentoPrazos", "Campo obrigatório");

		validateNotNull(errors, avaliacaoRendimento.getComprometimento(), "comprometimento", "Campo obrigatório");
		validateNotNull(errors, avaliacaoRendimento.getCuidadoMateriais(), "cuidadoMateriais", "Campo obrigatório");

		validateNotNull(errors, avaliacaoRendimento.getRelacionamento(), "relacionamento", "Campo obrigatório");
		validateNotNull(errors, avaliacaoRendimento.getTrabalhoEquipe(), "trabalhoEquipe", "Campo obrigatório");

		validateNotNull(errors, avaliacaoRendimento.getNota(), "nota", "Campo obrigatório");
	}
	
	public void validate(Double nota, MultipartFile documento, Errors errors){
		validateNotNull(errors, nota, "nota", "Campo obrigatório");
		if(documento.isEmpty()){
			errors.rejectValue("documento", "documento", "Campo obrigatório");
		}
		if(!errors.hasErrors() && arquivoInvalido(documento)){
			errors.rejectValue("documento", "documento", "Escolha um arquivo pdf");
		}
		
	}

	void validateStrings(Errors erros, String object, String field, String message){
		if(object.isEmpty()){
			erros.rejectValue(field, field,message);
		}
	}
	
	void validateNotNull(Errors erros, Object object, String field, String message){
		if (object == null) {
			erros.rejectValue(field, field,message);
		}
	}
	
	private boolean arquivoInvalido(MultipartFile anexo){
		if(anexo == null || !anexo.getContentType().equals("application/pdf")) {
			return true;
		}	
		return false;
	}
	
}
