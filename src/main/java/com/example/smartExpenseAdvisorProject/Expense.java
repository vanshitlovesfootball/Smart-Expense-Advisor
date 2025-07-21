package com.example.smartExpenseAdvisorProject;

import java.sql.Date;

public class Expense {

	 private Long id; 
	 private String title;
	 private String category;
	 private Double amount;
	 private Date expenseDate;
	 private Long userId;
	 
	 public void setId(Long id){
		 this.id = id;
	 }
	 public Long getId() {
		 return id;
	 }
	 
	 
	 public void setTitle(String title){
		 this.title=title;
	 }
	 
	 
	 public String getTitle() {
		 return title;
	 }
	 
	 public void setCategory(String category){
		 this.category=category;
		 
	 }
	 
	 public String getCategory() {
		 return category;
	 }
	 
	 public void setAmount(Double amount) {
		 this.amount = amount;
		 
	 }
	
	 public Double getAmount() {
		 return amount;
	 }
	 public void setExpenseDate(Date expenseDate){
		 this.expenseDate=expenseDate;
		 
	 }
	 
	 public Date getExpenseDate()
	 {
		 return expenseDate;
	 }
	 
	 public void setUserId(Long userId){
		 this.userId=userId;
		 
	 }
	 
	 public Long getUserId() {
		 return userId;
	 }
}
