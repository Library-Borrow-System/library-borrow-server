package com.project.library.service;

import com.project.library.domain.Book;
import com.project.library.domain.Category;
import com.project.library.domain.Status;
import com.project.library.repository.BookRepository;
import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.ScriptResolver;
import com.wix.mysql.config.Charset;
import com.wix.mysql.config.MysqldConfig;
import com.wix.mysql.distribution.Version;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DefaultBookServiceTest {


    static EmbeddedMysql embeddedMysql;

    @BeforeAll
    static void setup() {
        MysqldConfig config = MysqldConfig.aMysqldConfig(Version.v8_0_11)
                .withCharset(Charset.UTF8)
                .withPort(2215)
                .withUser("test", "test1234!")
                .withTimeZone("Asia/Seoul")
                .build();
        embeddedMysql = EmbeddedMysql.anEmbeddedMysql(config)
                .addSchema("test_library_mgmt", ScriptResolver.classPathScript("schema.sql"))
                .start();
    }

    @AfterAll
    static void cleanUp() {
        embeddedMysql.stop();
    }

    @Autowired
    BookRepository repository;

    @Autowired
    BookService bookService;

    private final String title = "테스트 책";
    private final Category category = Category.MYSTERY;
    private final int price = 1000;
    private final String author = "테스트 작가";
    private final String isbn = "1111-11-1111";
    private UUID bookId;

    @Test
    @Order(1)
    @DisplayName("책을 추가할 수 있다")
    void testCreateBook() {
        Book book = bookService.createBook(title, category, price, author, isbn);
        bookId = book.getBookId();
        List<Book> allBooks = bookService.getAllBooks();
        assertThat(allBooks.isEmpty(), is(false));
    }

    @Test
    @Order(2)
    @DisplayName("아이디로 조회할 수 있다")
    void testGetBookById() {
        Optional<Book> book = bookService.getBookById(bookId);
        assertThat(book.isEmpty(), is(false));
    }

    @Test
    @Order(3)
    @DisplayName("책을 수정할 수 있다")
    void testUpdateBook() {
        Category updatedCategory = Category.FANTASY;
        int updatedPrice = 2000;
        Status updatedStatus = Status.BORROW_IMPOSSIBLE;
        Optional<Book> updateBook = bookService.updateBook(bookId, updatedCategory, updatedPrice, updatedStatus);
        assertThat(updateBook.isEmpty(), is(false));
        assertThat(updateBook, samePropertyValuesAs(bookService.getBookById(bookId)));
    }


}