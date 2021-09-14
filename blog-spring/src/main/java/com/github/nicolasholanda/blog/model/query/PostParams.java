package com.github.nicolasholanda.blog.model.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Optional;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostParams {

    private Integer page;

    private String orderBy;

    private String direction;

    private Integer linesPerPage;

    public PageRequest toPageRequest() {
        return PageRequest.of(
                Optional.ofNullable(page).orElse(0),
                Optional.ofNullable(linesPerPage).orElse(10),
                Sort.Direction.valueOf(Optional.ofNullable(direction).orElse("DESC").toUpperCase()),
                Optional.ofNullable(orderBy).orElse("creationDate")
        );
    }
}
