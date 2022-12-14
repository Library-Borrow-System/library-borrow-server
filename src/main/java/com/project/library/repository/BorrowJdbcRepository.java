package com.project.library.repository;

import com.project.library.domain.*;
import com.project.library.utils.Queries;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

import static com.project.library.utils.JdbcUtils.toLocalDateTime;
import static com.project.library.utils.JdbcUtils.toUUID;

@Repository
public class BorrowJdbcRepository implements BorrowRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public BorrowJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public Borrow insert(Borrow borrow) {
        jdbcTemplate.update(Queries.BORROW_INSERT_SQL.getQuery(), toBorrowParamMap(borrow));

        borrow.getBorrowItems().forEach(borrowItem -> {
            jdbcTemplate.update(Queries.BORROW_ITEM_INSERT_SQL.getQuery(),
                    toBorrowItemParamMap(borrow.getBorrowId(), borrow.getCreatedAt(), borrow.getUpdatedAt(), borrowItem));

            HashMap<String, Object> bookParamMap = new HashMap<>();
            bookParamMap.put("bookId", borrowItem.getBookId().toString().getBytes());
            bookParamMap.put("status", Status.BORROW_IMPOSSIBLE.toString());
            jdbcTemplate.update(Queries.BOOK_UPDATE_STATUS_SQL.getQuery(), bookParamMap);
        });
        return borrow;
    }

    @Override
    public List<Borrow> findAll() {
        return jdbcTemplate.query(Queries.BORROW_FIND_ALL_SQL.getQuery(), borrowRowMapper);
    }

    @Override
    public List<BorrowingItem> findBorrowingItem() {
        return jdbcTemplate.query(
                Queries.BORROW_ITEM_FIND_BORROWING_SQL.getQuery(),
                Collections.singletonMap("status", Status.BORROW_IMPOSSIBLE.toString()),
                borrowingItemRowMapper
        );
    }

    @Override
    @Transactional
    public void update(UUID borrowId, UUID bookId) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("borrowId", borrowId.toString().getBytes());
        paramMap.put("bookId", bookId.toString().getBytes());
        paramMap.put("updatedAt", LocalDateTime.now());
        paramMap.put("status", Status.BORROW_POSSIBLE.toString());
        jdbcTemplate.update(Queries.BOOK_UPDATE_STATUS_SQL.getQuery(), paramMap);
        jdbcTemplate.update(Queries.BORROW_UPDATE_SQL.getQuery(), paramMap);
        jdbcTemplate.update(Queries.BORROW_ITEM_UPDATE_SQL.getQuery(), paramMap);
    }

    private Map<String, Object> toBorrowParamMap(Borrow borrow) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("borrowId", borrow.getBorrowId().toString().getBytes());
        paramMap.put("email", borrow.getEmail());
        paramMap.put("phoneNum", borrow.getPhoneNum());
        paramMap.put("createdAt", borrow.getCreatedAt());
        paramMap.put("updatedAt", borrow.getUpdatedAt());
        return paramMap;
    }

    private List<BorrowItem> findBorrowItems(UUID borrowId) {
        return jdbcTemplate.query(
                Queries.BORROW_ITEM_FIND_BY_BORROW_ID_SQL.getQuery(),
                Collections.singletonMap("borrowId", borrowId.toString().getBytes()),
                borrowItemRowMapper
        );
    }

    private Map<String, Object> toBorrowItemParamMap(UUID borrowId, LocalDateTime createdAt, LocalDateTime updatedAt, BorrowItem borrowItem) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("borrowId", borrowId.toString().getBytes());
        paramMap.put("bookId", borrowItem.getBookId().toString().getBytes());
        paramMap.put("fee", borrowItem.getFee());
        paramMap.put("term", borrowItem.getTerm());
        paramMap.put("borrowAt", createdAt);
        paramMap.put("returnAt", updatedAt);
        return paramMap;
    }

    private final RowMapper<Borrow> borrowRowMapper = (resultSet, i) -> {
        UUID borrowId = toUUID(resultSet.getBytes("borrow_id"));
        String email = resultSet.getString("email");
        String phoneNum = resultSet.getString("phone_num");
        List<BorrowItem> borrowItems = findBorrowItems(borrowId);
        LocalDateTime createdAt = toLocalDateTime(resultSet.getTimestamp("created_at"));
        LocalDateTime updatedAt = resultSet.getTimestamp("updated_at") != null ? toLocalDateTime(resultSet.getTimestamp("updated_at")) : null;
        return new Borrow(borrowId, email, phoneNum, borrowItems, createdAt, updatedAt);
    };

    private static final RowMapper<BorrowItem> borrowItemRowMapper = (resultSet, i) -> {
        UUID bookId = toUUID(resultSet.getBytes("book_id"));
        int fee = resultSet.getInt("fee");
        int term = resultSet.getInt("term");
        return new BorrowItem(bookId, fee, term);
    };

    private static final RowMapper<BorrowingItem> borrowingItemRowMapper = (resultSet, i) -> {
        UUID borrowId = toUUID(resultSet.getBytes("borrow_id"));
        UUID bookId = toUUID(resultSet.getBytes("book_id"));
        String title = resultSet.getString("title");
        String email = resultSet.getString("email");
        String phoneNum = resultSet.getString("phone_num");
        LocalDateTime borrowAt = toLocalDateTime(resultSet.getTimestamp("borrow_at"));
        int term = resultSet.getInt("term");
        return new BorrowingItem(borrowId, bookId, title, email, phoneNum, borrowAt, term);
    };
}
