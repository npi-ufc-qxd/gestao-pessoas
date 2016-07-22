package br.ufc.quixada.npi.ge.validation;

import javax.inject.Named;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

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
		
		validateEnums(errors, avaliacaoRendimento.getInicioAvaliacao(), "inicioAvaliacao", "Campo obrigatório.");
		validateEnums(errors, avaliacaoRendimento.getTerminoAvaliacao(), "terminoAvaliacao", "Campo obrigatório.");
		
		validateEnums(errors, avaliacaoRendimento.getFrequencia(), "frequencia", "Campo obrigatório.");
		validateEnums(errors, avaliacaoRendimento.getPermanencia(), "permanencia", "Campo obrigatório.");
		validateEnums(errors, avaliacaoRendimento.getDisciplina(), "disciplina", "Campo obrigatório.");
		validateEnums(errors, avaliacaoRendimento.getIniciativa(), "iniciativa", "Campo obrigatório.");
		
		validateStrings(errors, avaliacaoRendimento.getAtividadeCurricular(), "atividadeCurricular", "Campo obrigatótio.");
		validateStrings(errors, avaliacaoRendimento.getObjetivoEstagio(), "objetivoEstagio", "Campo obrigatótio.");
		
	}

	void validateStrings(Errors erros, String object, String field, String message){
		if(object.isEmpty()){
			erros.rejectValue(field, field,message);
		}
	}
	
	void validateEnums(Errors erros, Object object, String field, String message){
		if (object == null) {
			erros.rejectValue(field, field,message);
		}
	}
	
}
