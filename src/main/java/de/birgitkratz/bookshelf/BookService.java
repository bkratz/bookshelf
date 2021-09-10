package de.birgitkratz.bookshelf;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
class BookService {

    private final BookRepository repository;

    BookService(final BookRepository repository) {
        this.repository = repository;
    }

    List<Book> getAllBooks() {
        return repository.findAll();
    }

    Book getByIsbn(String isbn) throws BookNotFoundException {
        return repository.findByIsbn(isbn);
    }

    List<Book> getByAuthor(String author) {
        return repository.findAllByAuthor(author);
    }

    List<Book> getBySearch(final SearchParameter searchParameter) {
        final var books = new ArrayList<Book>();
        if (searchParameter.getIsbn() != null) {
            try {
                books.add(repository.findByIsbn(searchParameter.getIsbn()));
            } catch (Exception e) {
                log.error("Could not find a book for isbn {}", searchParameter.getIsbn());
            }
        }
        if (searchParameter.getAuthor() != null) {
            books.addAll(repository.findAllByAuthor(searchParameter.getAuthor()));
        }
        return books;
    }
}
