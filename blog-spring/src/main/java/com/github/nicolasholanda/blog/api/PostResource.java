package com.github.nicolasholanda.blog.api;

import com.github.nicolasholanda.blog.model.Comment;
import com.github.nicolasholanda.blog.model.Post;
import com.github.nicolasholanda.blog.model.query.PostParams;
import com.github.nicolasholanda.blog.security.UserDetailsImpl;
import com.github.nicolasholanda.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostResource {

    @Autowired
    PostService service;

    @GetMapping
    public Page<Post> findPage(PostParams params) {
        return service.findPage(params);
    }

    @PostMapping
    public Post save(@RequestBody Post post, @AuthenticationPrincipal UserDetailsImpl loggedUser) {
        return service.save(post, loggedUser);
    }

    @PutMapping("/{id}")
    public Post update(@RequestBody Post post,
                       @PathVariable("id") Long id,
                       @AuthenticationPrincipal UserDetailsImpl loggedUser) {
        return service.update(id, post, loggedUser);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id,
                       @AuthenticationPrincipal UserDetailsImpl loggedUser) {
        service.delete(id, loggedUser);
    }

    @PostMapping("/{id}/comments")
    public Comment addComment(@PathVariable("id") Long id,
                              @RequestBody Comment comment,
                              @AuthenticationPrincipal UserDetailsImpl loggedUser) {
        return service.addComment(id, comment, loggedUser);
    }

    @GetMapping("/{id}/comments")
    public List<Comment> getComments(@PathVariable("id") Long id) {
        return service.getComments(id);
    }
}
