package com.github.nicolasholanda.blog.model.dto;

import com.github.nicolasholanda.blog.model.validation.NewUser;
import lombok.Data;

import javax.validation.constraints.*;
import java.io.Serializable;

@Data
@NewUser
public class NewUserDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Email(message = "{user.email.email}")
    @NotNull(message = "{user.email.notnull}")
    @NotEmpty(message = "{user.email.notnull}")
    private String email;

    @NotNull(message = "{user.password.notnull}")
    @NotEmpty(message = "{user.password.notnull}")
    @Size(min = 6, message = "{user.password.min}")
    private String password;

    @NotNull(message = "{user.lastname.notnull}")
    @NotEmpty(message = "{user.lastname.notnull}")
    @Size(max = 50, message = "{user.firstname.max}")
    private String lastName;

    @NotNull(message = "{user.firstname.notnull}")
    @NotEmpty(message = "{user.firstname.notnull}")
    @Size(max = 50, message = "{user.firstname.max}")
    private String firstName;
}
