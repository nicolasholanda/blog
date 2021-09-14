package com.github.nicolasholanda.blog.api;

import com.github.nicolasholanda.blog.model.BlogUser;
import com.github.nicolasholanda.blog.model.dto.NewUserDTO;
import com.github.nicolasholanda.blog.security.UserDetailsImpl;
import com.github.nicolasholanda.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserResource {

    @Autowired
    private UserService service;

    @PostMapping
    public BlogUser save(@Valid @RequestBody NewUserDTO newUser) {
        return service.save(newUser);
    }

    @GetMapping("/me")
    public UserDetailsImpl me(@AuthenticationPrincipal UserDetailsImpl loggedUser) {
        return loggedUser;
    }
}
