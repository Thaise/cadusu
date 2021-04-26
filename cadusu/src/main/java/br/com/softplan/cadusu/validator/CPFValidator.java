package br.com.softplan.cadusu.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CPFValidator implements ConstraintValidator<CPF, String> {

	@Override
	public void initialize(CPF cpf) {
		ConstraintValidator.super.initialize(cpf);
	}

	@Override
	public boolean isValid(String cpf, ConstraintValidatorContext cxt) {
		if (cpf != null) {
			return cpf.matches("[0-9]{3,}\\.[0-9]{3,}\\.[0-9]{3,}\\-[0-9]{2,}");
		}
		return true;
	}

}
