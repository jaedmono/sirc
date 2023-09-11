package co.gov.movilidadbogota.core.dto.validator;

import org.springframework.validation.Errors;

public abstract class AbstractValidator {
	
	public AbstractValidator() {
	}

	public abstract void validate(Object target, Errors errors);
	
}
