package com.example.smartExpenseAdvisorProject;
import java.sql.Date;

public class Goal {
	
	
	private Long id;
	private String description;
	private Double amount;
	private Date date;
	private Long userId;
	
	public void setId(Long id) 
	{
		this.id = id;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setDescription(String description){
		this.description=description;
		
		
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setAmount(Double amount){
		this.amount=amount;
		
		
		
	}
	
	public Double getAmount() {
	       return amount;
	}

	
	public void setDate(Date date){
		
		this.date=date;
		
	}
	public Date getDate() {
		return date;
	}
	
	public void setUserId(Long userId){
		this.userId=userId;
		
	}
	
	public Long getUserId() {
		return userId;
	}
}
