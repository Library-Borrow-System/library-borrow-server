package com.project.library.repository;

import com.project.library.domain.Book;
import com.project.library.domain.Category;
import com.project.library.domain.Status;
import com.wix.mysql.ScriptResolver;
import com.wix.mysql.config.Charset;
import com.wix.mysql.config.MysqldConfig;
import com.wix.mysql.distribution.Version;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import com.wix.mysql.EmbeddedMysql;

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
class BookJdbcRepositoryTest {

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
    BookRepository bookRepository;

    private final Book newBook = new Book(UUID.randomUUID(), "테스트 책", Category.MYSTERY, 1000, "테스트 작가", Status.BORROW_POSSIBLE, "1111-1111", LocalDateTime.now(), LocalDateTime.now());

    @Test
    @Order(1)
    @DisplayName("책을 추가할 수 있다")
    void testInsert() {
        bookRepository.insert(newBook);
        List<Book> all = bookRepository.findAll();
        assertThat(all.isEmpty(), is(false));
    }

    @Test
    @Order(2)
    @DisplayName("책을 아이디로 조회할 수 있다")
    void testFindById() {
        Optional<Book> book = bookRepository.findById(newBook.getBookId());
        assertThat(book.isEmpty(), is(false));
    }

    @Test
    @Order(3)
    @DisplayName("책을 수정할 수 있다")
    void testUpdate() {
        Book updatedBook = new Book(newBook.getBookId(), newBook.getTitle(), Category.FANTASY, 2000, newBook.getAuthor(), Status.BORROW_IMPOSSIBLE, newBook.getIsbn(), newBook.getCreatedAt(), LocalDateTime.now());
        bookRepository.update(updatedBook);
        Optional<Book> book = bookRepository.findById(newBook.getBookId());
        assertThat(book.isEmpty(), is(false));
        assertThat(book.get(), samePropertyValuesAs(updatedBook));
    }

}