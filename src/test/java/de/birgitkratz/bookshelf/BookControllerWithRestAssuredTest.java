package de.birgitkratz.bookshelf;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import io.restassured.module.mockmvc.RestAssuredMockMvc;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest()
class BookControllerWithRestAssuredTest {

    @Autowired
    BookController controllerUnderTest;

    @Test
    void getAllBooks() {
        RestAssuredMockMvc.standaloneSetup(controllerUnderTest);
        given().log().all()
            .when()
                .get("book")
            .then()
                .log().all()
                .status(HttpStatus.OK)
                .body("author[0]", equalTo("Erich Gamma"));
    }

    @Test
    void getBookByIsbn() {
        RestAssuredMockMvc.standaloneSetup(controllerUnderTest);
        given().log().all()
            .when()
                .get("/book/978-3836211161")
            .then()
                .log().all()
                .status(HttpStatus.OK)
                .body("author", equalTo("Gottfried Wolmeringer"));
    }


}