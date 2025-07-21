package com.example.smartExpenseAdvisorProject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public class GoalRepository {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	 public Double getTotalAmountBYUserid(Long id){
		
		String sql = "SELECT NVL(SUM(AMOUNT),0) FROM goals where user_id=?";

		return jdbcTemplate.queryForObject(sql ,new Object[]{id} , Double.class);
		
		
	}
	
	
	public void save(Goal g){
		
		Long id = jdbcTemplate.queryForObject("SELECT goal_seq.NEXTVAL FROM dual", Long.class);
		g.setId(id);
		
		String sql = "INSERT INTO goals(id , description , amount , createddate , user_id) VALUES(? , ? , ? , ? , ?)";
		
		jdbcTemplate.update(sql ,
				id,
				g.getDescription(),
		      g.getAmount(),
		      g.getDate(),
		      g.getUserId()
				
				
				
				
				);
		
		
		
	}
	
	
	public List<Goal> getGoalsbyUserId(Long id){
		
		String sql = "SELECT * FROM goals WHERE user_id=?";
		
		return jdbcTemplate.query(sql , new Object[]{id} ,(rs , rowNum) -> {
			
			Goal g = new Goal();
			
			
			g.setId(rs.getLong("id"));
			g.setDescription(rs.getString("description"));
			g.setAmount(rs.getDouble("amount"));
			g.setDate(rs.getDate("createddate"));
			g.setUserId(rs.getLong("user_id"));

			
			
			
			
			
			
			
			return g;
			
			
			
			
		}
				
				
				
				
				
				
				
				
				
				
				
				
				
				);
		
		
		
		
		
		
		
		
		
	}
	
	public void deleteGoalByuserId(Long id1 , Long id) {
		String sql = "DELETE FROM goals WHERE id=? AND user_id=?";
		
		jdbcTemplate.update(sql , id1 , id);
	}
	
	

}
