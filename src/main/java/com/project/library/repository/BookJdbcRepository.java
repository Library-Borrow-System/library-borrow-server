package com.project.library.repository;

import com.project.library.domain.Book;
import com.project.library.domain.Category;
import com.project.library.domain.Status;
import com.project.library.utils.Queries;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

import static com.project.library.utils.JdbcUtils.*;

@Repository
public class BookJdbcRepository implements BookRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public BookJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Book insert(Book book) {
        int update = jdbcTemplate.update(Queries.BOOK_INSERT_SQL.getQuery(), toParamMap(book));
        if (update != 1) {
            throw  new RuntimeException("Nothing was inserted");
        }
        return book;
    }

    @Override
    public List<Book> findAll() {
        return jdbcTemplate.query(Queries.BOOK_FIND_ALL_SQL.getQuery(), bookRowMapper);
    }

    @Override
    public Optional<Book> findById(UUID bookId) {
        try {
            Book book = jdbcTemplate.queryForObject(
                    Queries.BOOK_FIND_BY_ID_SQL.getQuery(),
                    Collections.singletonMap("bookId", bookId.toString().getBytes()),
                    bookRowMapper
            );
            return Optional.ofNullable(book);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Book update(Book book) {
        int update = jdbcTemplate.update(Queries.BOOK_UPDATE_SQL.getQuery(), toParamMap(book));
        if (update != 1) {
            throw new RuntimeException("Nothing was updated");
        }
        return book;
    }

    @Override
    public Book deleteById(UUID bookId) {
        return null;
    }

    private Map<String, Object> toParamMap(Book book) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("bookId", book.getBookId().toString().getBytes());
        paramMap.put("title", book.getTitle());
        paramMap.put("category", book.getCategory().toString());
        paramMap.put("price", book.getPrice());
        paramMap.put("author", book.getAuthor());
        paramMap.put("status", book.getStatus().toString());
        paramMap.put("isbn", book.getIsbn());
        paramMap.put("createdAt", book.getCreatedAt());
        paramMap.put("updatedAt", book.getUpdatedAt() != null ? Timestamp.valueOf(book.getUpdatedAt()) : null);
        return paramMap;
    }

    private static final RowMapper<Book> bookRowMapper = (resultSet, i) -> {
        UUID bookId = toUUID(resultSet.getBytes("book_id"));
        String title = resultSet.getString("title");
        Category category = Category.valueOf(resultSet.getString("category"));
        int price = resultSet.getInt("price");
        String author = resultSet.getString("author");
        Status status = Status.valueOf(resultSet.getString("status"));
        String isbn = resultSet.getString("isbn");
        LocalDateTime createdAt = toLocalDateTime(resultSet.getTimestamp("created_at"));
        LocalDateTime updatedAt = resultSet.getTimestamp("updated_at") != null ? toLocalDateTime(resultSet.getTimestamp("updated_at")) : null;
        return new Book(bookId, title, category, price, author, status, isbn, createdAt, updatedAt);
    };
}
