package br.com.softplan.cadusu.validator;

import java.time.LocalDate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DataNascValidator implements ConstraintValidator<DataNascimento, LocalDate> {

	@Override
	public void initialize(DataNascimento dtNasc) {
		ConstraintValidator.super.initialize(dtNasc);
	}

	@Override
	public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
		if (value != null) {
			return !value.isAfter(LocalDate.now());
		}

		return true;
	}

}
