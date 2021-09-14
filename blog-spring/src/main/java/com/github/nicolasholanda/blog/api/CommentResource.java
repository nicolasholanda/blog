package com.github.nicolasholanda.blog.api;

import com.github.nicolasholanda.blog.model.Comment;
import com.github.nicolasholanda.blog.security.UserDetailsImpl;
import com.github.nicolasholanda.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
public class CommentResource {

    @Autowired
    CommentService service;

    @PutMapping("/{id}")
    public Comment update(@PathVariable("id") Long id,
                       @RequestBody Comment comment,
                       @AuthenticationPrincipal UserDetailsImpl loggedUser) {
        return service.update(id, comment, loggedUser);
    }

    @DeleteMapping("/{id}")
    public void remove(@PathVariable("id") Long id,
                       @AuthenticationPrincipal UserDetailsImpl loggedUser) {
        service.remove(id, loggedUser);
    }
}
