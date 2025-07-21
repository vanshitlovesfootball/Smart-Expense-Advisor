package com.example.smartExpenseAdvisorProject;

public class User {
	
	private Long id;  // uniquely identifies the user
	private String name;
	private Long contact;
	private String email;
	private String password;
	private Double budget;
	
	public void setId(Long id) {
		this.id=id;
	}
	
	public Long getId() {
		return id;
	}
	
	
	public void setName(String name) {
		this.name=name;
	}
	
	
	public String getName() {
		return name;
	}

	
	public void setContact(Long contact){
		this.contact=contact;
		
	}
	public Long getContact() {
		return contact;
	}
	
	public void setEmail(String email){
		
		this.email=email;
		
	}
	public String getEmail() {
		return email;
	}
	
	public void setPassword(String password){
		this.password=password;
		
	}
	
	public String getPassword() {
		return password;
	}
	public void setBudget(Double budget){
		
		this.budget=budget;
		
	}
	
	public Double getBudget(){
		return budget;
		
	}
}
