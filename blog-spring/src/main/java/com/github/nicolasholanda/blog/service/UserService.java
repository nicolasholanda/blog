package com.github.nicolasholanda.blog.service;

import com.github.nicolasholanda.blog.model.BlogUser;
import com.github.nicolasholanda.blog.model.dto.NewUserDTO;
import com.github.nicolasholanda.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.lang.String.format;

@Service
public class UserService {

    @Autowired
    UserRepository repository;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    public BlogUser findByEmail(String email) {
        return repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(format("E-mail não encontrado: %s", email)));
    }

    @Transactional
    public BlogUser save(NewUserDTO newUser) {
        return repository.saveAndFlush(fromDTO(newUser));
    }

    private BlogUser fromDTO(NewUserDTO newUser) {
        return BlogUser.builder()
                .email(newUser.getEmail())
                .lastName(newUser.getLastName())
                .firstName(newUser.getFirstName())
                .password(passwordEncoder.encode(newUser.getPassword())).build();
    }
}
