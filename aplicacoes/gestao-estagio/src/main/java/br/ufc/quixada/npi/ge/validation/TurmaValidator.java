package br.ufc.quixada.npi.ge.validation;

import javax.inject.Named;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import br.ufc.quixada.npi.ge.model.AvaliacaoRendimento;
import br.ufc.quixada.npi.ge.model.Turma;


@Named
public class TurmaValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return AvaliacaoRendimento.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Turma turma = (Turma) target;

		validateStrings(errors, turma.getNomeSeguradora(), "nomeSeguradora", "Informe o nome da seguradora");
		validateStrings(errors, turma.getCargaHorariaSemanal(), "cargaHorariaSemanal", "Informe a carga horaria semanal");
		validateStrings(errors, turma.getSeguroInvalidezPermanente(), "seguroInvalidezPermanente", "Informe o valor");
		validateStrings(errors, turma.getSeguroMorteAcidental(), "seguroMorteAcidental", "Informe o valor");
		validateNotNull(errors, turma.getInicioVigencia(), "inicioVigencia", "Informe o inicio da vigência");
		validateNotNull(errors, turma.getTerminoVigencia(), "terminoVigencia", "Informe o termino da vigência");
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

}
