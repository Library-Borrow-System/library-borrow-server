package com.project.library.repository;

import com.project.library.domain.*;
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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BorrowJdbcRepositoryTest {

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

    @Autowired
    BorrowRepository borrowRepository;

    private final Book newBook = new Book(UUID.randomUUID(), "테스트 책책", Category.MYSTERY, 1000, "테스트 작가", Status.BORROW_POSSIBLE, "1111-1112", LocalDateTime.now(), LocalDateTime.now());
    private final BorrowItem newBorrowItem = new BorrowItem(newBook.getBookId(), 0, 7);
    List<BorrowItem> borrowItems = new ArrayList<>();

    @Test
    @Order(1)
    public void addBook() {
        bookRepository.insert(newBook);
    }

    @Test
    @Order(2)
    public void addOrderItem() {
        borrowItems.add(newBorrowItem);
    }

    private final Borrow newBorrow = new Borrow(UUID.randomUUID(), "sohyeon@gmail.com", "010-1111-2222", borrowItems, LocalDateTime.now(), null);

    @Test
    @Order(3)
    @DisplayName("책을 대출할 수 있다")
    public void testInsert() {
        borrowRepository.insert(newBorrow);
        List<Borrow> all = borrowRepository.findAll();
        assertThat(all.isEmpty(), is(false));
        assertThat(all.get(0).getBorrowItems().isEmpty(), is(false));
    }

    @Test
    @Order(4)
    @DisplayName("대여 중인 내역을 조회할 수 있다")
    public void testFindBorrowingItem() {
        List<BorrowItem> borrowingItem = borrowRepository.findBorrowingItem();
        assertThat(borrowingItem.isEmpty(), is(false));
    }

}