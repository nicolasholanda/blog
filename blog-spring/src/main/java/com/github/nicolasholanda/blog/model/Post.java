package com.github.nicolasholanda.blog.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;
import static javax.persistence.CascadeType.REMOVE;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Post implements Serializable {

    @Id
    @GeneratedValue(strategy= IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3, max = 255)
    private String title;

    @NotNull
    @Size(min = 3)
    @Column(columnDefinition = "TEXT")
    private String content;

    @JsonProperty(access = READ_ONLY)
    private LocalDateTime creationDate = LocalDateTime.now();

    @JsonProperty(access = READ_ONLY)
    private LocalDateTime updateDate;

    @ManyToOne
    @JsonProperty(access = READ_ONLY)
    private BlogUser user;

    @OneToMany(fetch = LAZY, cascade = REMOVE)
    private List<Comment> comments;
}
