package com.example.smartExpenseAdvisorProject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public class ExpenseRepository {
	
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	public Double getExpensesOfuserbyUserId(Long userId){
		
		String sql = "SELECT NVL(SUM(amount),0) FROM expenses where user_id=?";
		
		return jdbcTemplate.queryForObject(sql, new Object[]{userId} , Double.class);
		
		
		
	}
	
	
	
	public void save(Expense e){
		Long id = jdbcTemplate.queryForObject("SELECT expenses_seq.NEXTVAL FROM DUAL" ,Long.class);
		e.setId(id);
		
		String sql = "INSERT INTO expenses(id,title , category , amount,expense_date ,user_id) VALUES(? ,? ,? ,? ,? ,?)";
		
		jdbcTemplate.update(sql,
				id,
				e.getTitle(),
				e.getCategory(),
				e.getAmount(),
				e.getExpenseDate(),
				e.getUserId()
				
				
	);
		
		
	}
	
	
	public List<Expense> getAllExpensesOfuserbyUserId(Long id){
		
		String sql = "SELECT * FROM expenses WHERE user_id=?";
		
		return jdbcTemplate.query(sql , new Object[] {id} , (rs , rowNum) ->{
			
			Expense e = new Expense();
			
			e.setId(rs.getLong("id"));
			e.setTitle(rs.getString("title"));
			e.setCategory(rs.getString("category"));
			e.setAmount(rs.getDouble("amount"));
			e.setExpenseDate(rs.getDate("expense_date"));
			e.setUserId(rs.getLong("user_id"));
			
			
			
			return e;
			
		}
				
				
				
				
				
				
				
				
				
				
				);
		
	}
	
	
	
	public List<Map<String , Object>> getExpensesSummaryByCategory(Long id){
		
		String sql = "SELECT category ,SUM(amount) as total FROM expenses WHERE user_id=? GROUP BY category";
		
		return jdbcTemplate.queryForList(sql , id);
		
	}
	

}
