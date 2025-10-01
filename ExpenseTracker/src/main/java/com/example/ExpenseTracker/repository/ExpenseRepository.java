package com.example.ExpenseTracker.repository;

import com.example.ExpenseTracker.model.Expense;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.math.BigDecimal;


@Repository
public class ExpenseRepository {
	private final JdbcTemplate jdbc;
	
	public ExpenseRepository(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}
	
	private final RowMapper<Expense> expenseRowMapper = (rs, rowNum) -> {
		Expense e = new Expense();
		e.setId(rs.getLong("id"));
		e.setTitle(rs.getString("title"));
		e.setAmount(rs.getBigDecimal("amount"));
		e.setCategory(rs.getString("category"));
		e.setExpenseDate(rs.getDate("expense_date").toLocalDate());
		e.setNote(rs.getString("note"));
		e.setCreateAt(rs.getTimestamp("created_at").toLocalDateTime());
		
		return e;
	};
	
	public List<Expense> findAll(){
		String sql = "select * from expenses order by expense_date desc, created_at desc";
		return jdbc.query(sql, expenseRowMapper);
	}
	
	@SuppressWarnings("deprecation")
	public Expense findById(Long id) {
		String sql = "select * from expenses where id = ?";
		return jdbc.queryForObject(sql, new Object[] {id}, expenseRowMapper);
	}
	
	public int save(Expense e) {
		String sql = "insert into expenses (title, amount, category, expense_date, note)values(?, ?, ?, ?, ?)";
		return jdbc.update(sql, e.getTitle(), e.getAmount(), e.getCategory(), java.sql.Date.valueOf(e.getExpenseDate()), e.getNote());
	}
	
	public int update(Expense e) {
		String sql = "update expenses set title=?, amount=?, category=?, expense_date=?, note=?, where id=?";
		return jdbc.update(sql, e.getTitle(), e.getAmount(), e.getCategory(), java.sql.Date.valueOf(e.getExpenseDate()), e.getNote(), e.getId());
	}
	
	public int deleteById(Long id) {
		String sql = "delete from expenses where id=?";
		return jdbc.update(sql, id);
	}
	
	// returns BigDecimal (0 if no rows)
	@SuppressWarnings("deprecation")
	public BigDecimal getMonthlyTotal(int year, int month) {
	    String sql = "SELECT COALESCE(SUM(amount), 0) FROM expenses WHERE YEAR(expense_date) = ? AND MONTH(expense_date) = ?";
	    BigDecimal total = jdbc.queryForObject(sql, new Object[]{year, month}, BigDecimal.class);
	    return total == null ? BigDecimal.ZERO : total;
	}

}
