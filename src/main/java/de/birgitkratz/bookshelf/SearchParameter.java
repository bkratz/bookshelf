package de.birgitkratz.bookshelf;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
class SearchParameter {
    private String isbn;
    private String author;
}
