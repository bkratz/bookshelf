package de.birgitkratz.bookshelf;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
class BookRepository {
    private List<Book> books;

    @Autowired
    private ObjectMapper mapper;

    @PostConstruct
    void init() throws IOException {
        this.books = Arrays.asList(mapper.readValue(new File("target/classes/books.json"), Book[].class));
    }

    List<Book> findAll() {
        return books;
    }

    Book findByIsbn (String isbn) throws BookNotFoundException {
        return findAll().stream()
                .filter(book -> book.getIsbn().equals(isbn))
                .findFirst()
                .orElseThrow(BookNotFoundException::new);
    }

    List<Book> findAllByAuthor (String author) {
        return findAll().stream()
                .filter(book -> book.getAuthor().equals(author))
                .collect(Collectors.toUnmodifiableList());
    }
}
