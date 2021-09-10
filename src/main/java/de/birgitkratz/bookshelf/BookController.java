package de.birgitkratz.bookshelf;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("book")
@Slf4j
public class BookController {

    private final BookService service;

    public BookController(final BookService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Book>> getBooks() {

        final var allBooks = service.getAllBooks();
        if (allBooks == null || allBooks.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(allBooks);
    }

    @GetMapping("{isbn}")
    public Book getBookByIsbn(@PathVariable final String isbn) throws BookNotFoundException {
        return service.getByIsbn(isbn);
    }

    @GetMapping(params = "author")
    public List<Book> getBookByAuthor(@RequestParam String author) {
        return service.getByAuthor(author);
    }

    @PostMapping("search")
    public List<Book> searchBooks(@RequestBody SearchParameter searchParameter) {
        return service.getBySearch(searchParameter);
    }

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<Void> nothingFound(BookNotFoundException e) {
        return ResponseEntity.badRequest().build();
    }
}
