package com.github.nicolasholanda.blog.service;

import com.github.nicolasholanda.blog.exception.AuthorizationException;
import com.github.nicolasholanda.blog.exception.ObjectNotFoundException;
import com.github.nicolasholanda.blog.model.Comment;
import com.github.nicolasholanda.blog.repository.CommentRepository;
import com.github.nicolasholanda.blog.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static java.lang.String.format;

@Service
public class CommentService {

    @Autowired
    CommentRepository repository;

    public Comment findById(Long id) {
        return repository.findById(id).orElseThrow(() -> {
            throw new ObjectNotFoundException(format("Comentário de id %s não encontrado.", id));
        });
    }

    public void remove(Long id, UserDetailsImpl loggedUser) {
        var comment = findById(id);
        if(loggedUser == null || !loggedUser.getId().equals(comment.getUser().getId())) {
            throw new AuthorizationException("Somente o autor do comentário pode removê-lo.");
        }
        repository.delete(comment);
    }

    public Comment update(Long id, Comment comment, UserDetailsImpl loggedUser) {
        var savedComment = findById(id);
        if(loggedUser == null || !savedComment.getUser().getId().equals(loggedUser.getId())) {
            throw new AuthorizationException("Somente o autor do comentário pode atualizá-la.");
        }
        savedComment.setContent(comment.getContent());
        savedComment.setUpdateDate(LocalDateTime.now());
        return repository.save(savedComment);
    }
}
