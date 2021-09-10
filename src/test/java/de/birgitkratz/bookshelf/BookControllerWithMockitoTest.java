package de.birgitkratz.bookshelf;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookControllerWithMockitoTest {

    @InjectMocks
    BookController bookController;

    @Mock BookService bookService;

    @Captor
    ArgumentCaptor<SearchParameter> searchParameterArgumentCaptor;

    @Test
    void getAllBooks() {
        final var book = new Book();
        when(bookService.getAllBooks()).thenReturn(List.of(book));

        final var books = bookController.getBooks();
        assertThat(books.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(books.getBody()).hasSize(1);
    }

    @Test
    void getBookWithIsbn() throws BookNotFoundException {
        String isbn = "1234";
        bookController.getBookByIsbn(isbn);
        verify(bookService).getByIsbn(isbn);
    }

    @Test
    void getBookWithCaptor() {
        final var searchParameter = new SearchParameter();
        searchParameter.setAuthor("author");
        searchParameter.setIsbn("isbn");

        bookController.searchBooks(searchParameter);

        verify(bookService).getBySearch(searchParameterArgumentCaptor.capture());
        final var serviceMethodArgument = searchParameterArgumentCaptor.getValue();
        assertThat(serviceMethodArgument.getIsbn()).isEqualTo("isbn");
        assertThat(serviceMethodArgument.getAuthor()).isEqualTo("author");
    }
}
