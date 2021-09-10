package de.birgitkratz.bookshelf;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BookControllerWithMockBeanTest {

    @Autowired BookController bookController;

    @MockBean BookService bookService;

    @Autowired MockMvc mockMvc;

    @Test
    void getAllBooks() throws Exception {
        when(bookService.getAllBooks()).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/book"))
                .andExpect(status().isNoContent());
    }

    @Test
    void getAllBooks_throwingBookNotFoundException() throws Exception {
        doThrow(BookNotFoundException.class).when(bookService).getByIsbn(anyString());
        mockMvc.perform(get("/book/1234"))
                .andExpect(status().isBadRequest());
    }
}
