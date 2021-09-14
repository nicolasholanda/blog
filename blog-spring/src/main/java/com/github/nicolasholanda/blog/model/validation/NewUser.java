package com.github.nicolasholanda.blog.model.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NewUserValidator.class)
public @interface NewUser {
	String message() default "Erro ao validar usuário";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}