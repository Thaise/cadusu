package br.com.softplan.cadusu.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = DataNascValidator.class)
public @interface DataNascimento {
	String message() default "Data de nascimento não deve ser posterior ao dia atual.";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
