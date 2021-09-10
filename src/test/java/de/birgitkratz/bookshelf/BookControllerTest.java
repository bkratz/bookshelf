package de.birgitkratz.bookshelf;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BookControllerTest {

    @Autowired
    BookController bookController;

    @Test
    void getAllBooks() {
        final var booksResponseEntity = bookController.getBooks();
        final var books = booksResponseEntity.getBody();

        // assert with JUnit Assertion
        assertEquals(3, books.size());

        // assert with assertJ
        assertThat(books).hasSize(3);
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getAllBookWithMockMvc() throws Exception {
        mockMvc.perform(get("/book"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    void getAllBooksWithMockMvcResult() throws Exception {
        final var result = mockMvc.perform(get("/book"))
                .andReturn();

        final var payload = result.getResponse().getContentAsString();
        final var books = objectMapper.readValue(payload, Book[].class);

        assertThat(books).hasSize(3);
    }
}
