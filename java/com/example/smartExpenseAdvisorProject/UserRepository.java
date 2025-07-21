package com.example.smartExpenseAdvisorProject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;

@Repository

public class UserRepository {
	
	
            @Autowired	
	        private JdbcTemplate jdbcTemplate;
            
            
            public void save(User user){
            	
            	Long id= jdbcTemplate.queryForObject("SELECT users_seq.NEXTVAL from dual",Long.class);
            	user.setId(id);
            	
            	String sql = "INSERT INTO users(name , id , contact , email , password , budget) VALUES(?,?,?,?,?,?)";
          
            	jdbcTemplate.update(sql,
            			user.getName(),
            			id,
            			user.getContact(),
            			user.getEmail(),  
            			user.getPassword(),
            			user.getBudget()
            		
            	
                 	);
            	
            	
            }
            
            public boolean isEmailexists(String email){
            	String sql = "SELECT COUNT(*)  FROM users where email=?";
            	
            	Integer count=jdbcTemplate.queryForObject(sql, new Object[] {email} , Integer.class);
            	
            	return count!=null && count>0;
            }
            
            public User getUserByemail(String email) {
            	String sql = "SELECT * FROM users WHERE email=?";
            	
            	return jdbcTemplate.queryForObject(sql, new Object[] {email},(rs, rowNum)->{
            		
            		User u = new User();
            		
            		u.setEmail(rs.getString("email"));
            		u.setName(rs.getString("name"));
            		u.setPassword(rs.getString("password"));
            		u.setContact(rs.getLong("contact"));
            		u.setId(rs.getLong("id"));
            		u.setBudget(rs.getDouble("budget"));           		
            		
            		
            		return u;
            		
            		
            	}
            			
            			
            			
            			
            			);
            	
            	
            	
            }
            
            
            public void updatePassword(String password , String email){
            	
            	String sql = "UPDATE users SET password=? WHERE email=?";
            	
                    jdbcTemplate.update(sql , password , email);       	
            }
            
            
            
            
            
            
            
            
            
            
            

}
 