package com.github.nicolasholanda.blog.model.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CredentialsDTO implements Serializable {

    private String email;
    private String password;
}
