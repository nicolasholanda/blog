package com.github.nicolasholanda.blog.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.nicolasholanda.blog.model.enuns.Role;
import com.github.nicolasholanda.blog.security.UserDetailsImpl;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BlogUser implements Serializable {

    @Id
    @GeneratedValue(strategy= IDENTITY)
    private Long id;

    @Column(unique = true)
    @Email(message = "{user.email.email}")
    @NotNull(message = "{user.email.notnull}")
    private String email;

    @JsonIgnore
    @NotNull(message = "{user.password.notnull}")
    private String password;

    @NotNull(message = "{user.lastname.notnull}")
    private String lastName;

    @NotNull(message = "{user.firstname.notnull}")
    private String firstName;

    @JsonIgnore
    @Builder.Default
    @Column(name = "role")
    @ElementCollection(fetch = EAGER)
    @CollectionTable(name="user_role")
    private Set<Role> roles = new HashSet<>(Collections.singletonList(Role.ROLE_USER));

    @JsonIgnore
    @OneToMany(fetch = LAZY)
    private List<Post> posts;

    @JsonIgnore
    @OneToMany(fetch = LAZY)
    private List<Comment> comments;

    public static BlogUser of(UserDetailsImpl userDetails) {
        return BlogUser.builder()
                .id(userDetails.getId())
                .email(userDetails.getEmail())
                .password(userDetails.getPassword())
                .lastName(userDetails.getLastName())
                .firstName(userDetails.getFirstName()).build();
    }

}
