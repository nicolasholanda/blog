package com.github.nicolasholanda.blog.service;

import com.github.nicolasholanda.blog.exception.AuthorizationException;
import com.github.nicolasholanda.blog.exception.ObjectNotFoundException;
import com.github.nicolasholanda.blog.model.BlogUser;
import com.github.nicolasholanda.blog.model.Comment;
import com.github.nicolasholanda.blog.model.Post;
import com.github.nicolasholanda.blog.model.query.PostParams;
import com.github.nicolasholanda.blog.repository.CommentRepository;
import com.github.nicolasholanda.blog.repository.PostRepository;
import com.github.nicolasholanda.blog.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static java.lang.String.format;

@Service
public class PostService {

    @Autowired
    PostRepository repository;

    @Autowired
    CommentRepository commentRepository;

    public Post findById(Long id) {
        return repository.findById(id).orElseThrow(() -> {
            throw new ObjectNotFoundException(format("Não foi possível encontrar uma postagem de id %s.", id));
        });
    }

    public Page<Post> findPage(PostParams params) {
        return repository.findBy(params.toPageRequest());
    }

    @Transactional
    public Post save(Post post, UserDetailsImpl loggedUser) {
        if(loggedUser == null) {
            throw new AuthorizationException("Somente usuários podem criar postagens    .");
        }
        post.setUser(BlogUser.of(loggedUser));
        return repository.save(post);
    }

    @Transactional
    public Post update(Long id, Post post, UserDetailsImpl loggedUser) {
        var savedPost = findById(id);
        if(loggedUser == null || !savedPost.getUser().getId().equals(loggedUser.getId())) {
            throw new AuthorizationException("Somente o autor da postagem pode atualizá-la.");
        }
        savedPost.setTitle(post.getTitle());
        savedPost.setContent(post.getContent());
        savedPost.setUpdateDate(LocalDateTime.now());
        return repository.save(savedPost);
    }

    @Transactional
    public void delete(Long id, UserDetailsImpl loggedUser) {
        var savedPost = findById(id);
        if(loggedUser == null || !savedPost.getUser().getId().equals(loggedUser.getId())) {
            throw new AuthorizationException("Somente o autor da postagem pode removê-la.");
        }
        repository.delete(savedPost);
    }

    @Transactional
    public Comment addComment(Long id, Comment comment, UserDetailsImpl userDetails) {
        var post = findById(id);
        comment.setPost(post);
        comment.setUser(BlogUser.of(userDetails));
        return commentRepository.save(comment);
    }

    public List<Comment> getComments(Long id) {
        return commentRepository.findByPostId(id);
    }
}
