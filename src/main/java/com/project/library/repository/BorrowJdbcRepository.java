package com.project.library.repository;

import com.project.library.domain.*;
import com.project.library.utils.Queries;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

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
    public Borrow insert(Borrow borrow) {
        jdbcTemplate.update(Queries.BORROW_INSERT_SQL.getQuery(), toBorrowParamMap(borrow));
        borrow.getBorrowItems().forEach(borrowItem -> {
                    jdbcTemplate.update(Queries.BORROW_ITEM_INSERT_SQL.getQuery(),
                            toBorrowItemParamMap(borrow.getBorrowId(), borrow.getCreatedAt(), borrow.getUpdatedAt(), borrowItem));
                }

        );
        return borrow;
    }

    @Override
    public List<Borrow> findAll() {
        return jdbcTemplate.query(Queries.BORROW_FIND_ALL_SQL.getQuery(), borrowRowMapper);
    }

    @Override
    public List<BorrowItem> findBorrowingItem() {
        return jdbcTemplate.query(
                Queries.BORROW_ITEM_FIND_BORROWING_SQL.getQuery(),
                borrowItemRowMapper
        );
    }

    @Override
    public BorrowItem update(Borrow borrow, BorrowItem borrowItem) {
        int updatedBorrowItem = jdbcTemplate.update(
                Queries.BORROW_ITEM_UPDATE_SQL.getQuery(),
                toBorrowItemParamMap(borrow.getBorrowId(), LocalDateTime.now(), borrow.getUpdatedAt(), borrowItem)
        );
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("borrowId", borrow.getBorrowId().toString().getBytes());
        paramMap.put("updatedAt", borrow.getUpdatedAt());
        int updatedBorrow = jdbcTemplate.update(
                Queries.BORROW_UPDATE_SQL.getQuery(),
                paramMap
        );
        if (updatedBorrow != 1 || updatedBorrowItem != 1) {
            throw new RuntimeException("Nothing was updated");
        }
        return borrowItem;
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
}
