package com.github.nicolasholanda.blog.model.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.github.nicolasholanda.blog.exception.FieldMessage;
import com.github.nicolasholanda.blog.model.dto.NewUserDTO;
import com.github.nicolasholanda.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;


public class NewUserValidator implements ConstraintValidator<NewUser, NewUserDTO> {

	@Autowired
	private UserRepository userRepository;

	@Override
	public boolean isValid(NewUserDTO userDTO, ConstraintValidatorContext context) {
		var errors = new ArrayList<FieldMessage>();
		if(userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
			errors.add(new FieldMessage("email", "Email já cadastrado."));
		}
		for (FieldMessage e : errors) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return errors.isEmpty();
	}
}
