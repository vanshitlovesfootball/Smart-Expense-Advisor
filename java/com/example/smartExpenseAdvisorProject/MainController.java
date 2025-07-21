package com.example.smartExpenseAdvisorProject;
import java.sql.Date;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.*;

import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/do")
public class MainController {
	
	@Autowired
	private UserRepository ur;
	
	
	@Autowired
	private ExpenseRepository er;
	
	
	@Autowired
	private GoalRepository gr;
	
	@GetMapping("/start")
	public String getFirstPage(){
		
		return "info";
		
	}
	
	
	@PostMapping("/process")
		public String processResult(@RequestParam String name , @RequestParam String email , @RequestParam String contact ,@RequestParam String password,@RequestParam String monthlymoney, Model model){
		
		boolean hasError = false;
		
		//name validation
		
		if(name==null || name.trim().isEmpty()) {
			model.addAttribute("nameError" , "Name is required");
			hasError = true;
			
		}
		
		else if(!name.matches("[a-zA-Z\\s]+")){
			model.addAttribute("nameError" , "Please enter valid name");
			hasError=true;
			
		}
		
		
		// email validation
		
		if(email==null || email.trim().isEmpty()){
			model.addAttribute("emailError" , "Email is required");
			hasError = true;
			
		}
		
		else if(!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")){
			model.addAttribute("emailError" , "Invalid email format");
			hasError=true;
			
		}
		
		
		// contact validation
		
		if(contact==null || contact.trim().isEmpty()){
			
			model.addAttribute("contactError" , "Contact is required");
			hasError = true;
			
		}
		
		else if(!contact.matches("\\d{10}")) {
			model.addAttribute("contactError" , "Contact must contain exactly 10 digits");
			hasError = true;
			
		}
		
		
		// password validation
		
		 if(password==null || password.trim().isEmpty()){
			 
			 model.addAttribute("passwordError" , "Password is required");
			 
			 hasError=true;
			 
		 }
	
	
	else if(!password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,}$")) {
		model.addAttribute("passwordError" ,"Password must be strong");
		hasError=true;
	}
		 
		 // Monthly balance validation
		 
		 if(monthlymoney==null || monthlymoney.trim().isEmpty()) {
			 model.addAttribute("moneyError", "Monthly Budget is required");
			 hasError=true;
		 }
		 
		 
		 else if(!monthlymoney.matches("\\d+(\\.\\d{1,2})?")){
			 model.addAttribute("moneyError" , "Please enter valid Budget");
			 
			 hasError=true;
			 
		 }
		  
		       
		 if(hasError) {
			 model.addAttribute("name" , name);
			 model.addAttribute("email", email);
			 model.addAttribute("contact" , contact);
			 model.addAttribute("password", password);
			 model.addAttribute("monthlymoney" , monthlymoney);
			 return "info";
		 }
		 
		 if(ur.isEmailexists(email)) {
			 model.addAttribute("exists" , "Account is already registered with this email.please login now.");
			 return "info";
		 }
		 
		 
		 Long new_contact= Long.parseLong(contact);
		 User u = new User();
		 
		 u.setContact(new_contact);
		 u.setName(name);
		 u.setPassword(password);
		 u.setEmail(email);
		 u.setBudget(Double.parseDouble(monthlymoney));
		 
		 ur.save(u);
		 
		 
		 return "redirect:/do/log";
		 
		 
	}
	
	         @GetMapping("/log")
	      public String loginPage() {
	        	
	        	return "login";
	    	  
	      }
	
		         
	        @PostMapping("/dekho")
		        public String dekho(@RequestParam String email , @RequestParam String password,Model model ,HttpSession session){
		        	
		        	// email validation
		        	boolean hasError = false;
		        	
		        	if(email==null || email.trim().isEmpty()) {	   
		        		model.addAttribute("emailError","Email is required");
		        		hasError=true;
		        		
		        	}
		        	
		        	else if(!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
		        		
		        		model.addAttribute("emailError","Invalid email format");
		        		hasError=true;
		        		
		        	}
		        	
		        	// password validation
		        	
		        	if(password==null || password.trim().isEmpty()){
		        		model.addAttribute("passwordError" , "Password is required");
		        		hasError=true;
		        		
		        	}
		        	
		        	else if(!password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,}$")) {
		        		model.addAttribute("passwordError" ,"Password must be strong");
		        		hasError=true;
		        	}
		        	
		        	if(hasError) {
		        		model.addAttribute("password",password);
		        		model.addAttribute("email" , email);
		        		return "login";
		        	}
		        	
		        	
	
		        		
		        	 if(!ur.isEmailexists(email)) {
		        		 
		        		 model.addAttribute("emailError", "Email id does not exist");
		        		 model.addAttribute("password",password);
		        		 return "login";
		        	 }
		        	 
		        	
		        		 
		        	 try {
		        		 
		        	 User u = ur.getUserByemail(email);
		        	 
		        	 String old_password=u.getPassword();
		        	 
		        	 
		        	 if(!old_password.equals(password)) {
		        		 model.addAttribute("passwordError", "Incorrect Password");
		        		 model.addAttribute("email", email);
		        		 return "login";
		        	 }
		        	 
		        	 
		        	 
		            session.setAttribute("loggedUser" , u);
		        	
		        	 
		        	return "redirect:/do/dashboard";
		        }
		        	 catch(Exception e){
		        		 model.addAttribute("passwordError", "User not found");
		        		 model.addAttribute("email", email);
		        		 return "login";
		        		 
		        	 
		 }
		        	 
		        	
		        		 
		     }
		        
	        
	        
	        
	        @GetMapping("/forgot")
	        public String forgetPassword(){
	        	
	        	return "forget";
	        	
	        }
	        
	        
	        
	        @PostMapping("/processForget")
	        public String processForget(@RequestParam String email ,Model model ,HttpSession session){
	        	
	        	
	        	boolean hasError=false;
	        	
	        	
	        	// email validation
	        	
	        	if(email==null || email.trim().isEmpty()) {
	        		model.addAttribute("emailError" , "Email is required");
	        		hasError=true;
	        	}
	        	
	        	else if(!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")){
	        		model.addAttribute("emailError" , "Invalid email format");
	        		hasError=true;
	        		
	        	}
	        	
	        	if(hasError) {
	        		return "forget";
	        	}
	        
	        	
	        	
	        	
	        	if(!ur.isEmailexists(email)) {
	        		model.addAttribute("emailError" , "Email does not exist");
	        		return "forget";
	        	}
	        	
	        	session.setAttribute("user" ,email);
	        	
	        	return "newpassword";
	        	
	        }
	        
	        
	        
	        @PostMapping("/change")
	        public String change(@RequestParam String newpass , @RequestParam String confirmpass , Model model ,HttpSession session, RedirectAttributes redirectAttributes){
	        	
	        	
	        	String email = (String)session.getAttribute("user");
	        	boolean hasError = false;
	        	
	        	
 
	        	// new password validation
	        	
	        	
	        	if(newpass==null || newpass.trim().isEmpty()){
	        		
	        		model.addAttribute("newError" , "Password is required");
	        		hasError = true;
	        		
	        		
	        	}
	        	
	        	else if(!newpass.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,}$")){
	        		model.addAttribute("newError" , "Password must be strong");
	        		hasError=true;
	        		
	        	}
	        	
	        	// confirm password validation
	        	
	        	if(confirmpass==null || confirmpass.trim().isEmpty()){
	        		
	        		model.addAttribute("conError"  ,"This field is required");	    
	        		hasError = true;
	        	}
	        	
	        	else if(!confirmpass.equals(newpass)){
	        		model.addAttribute("conError" , "Confirm password does not match");
	        		hasError = true;
	        		
	        		
	        	}
	        	
	        	if(hasError){
	        		model.addAttribute("newPass" , newpass);
	        		model.addAttribute("confirmpass" , confirmpass);
	        		return "newpassword";
	        		
	        	}
	        	
	        	    ur.updatePassword(newpass ,email);
	        	    
	        	    redirectAttributes.addFlashAttribute("successmsg" ,"Password changed sucessfully");
	        	
	        		 return "redirect:/do/log";
	        }
	        
	        
	        
	        
	        
	        
		        
		        @GetMapping("/dashboard")
		        public String dashboardDetailsofloggedinUser(HttpSession session , Model model) {
		        	
		        	User u = (User)session.getAttribute("loggedUser");
		        	
		        	if(u==null) {
		        		return "redirect:/do/log";
		        	}
		        	
		        	Double totalBudget=u.getBudget();
		        	Double expenses = er.getExpensesOfuserbyUserId(u.getId());
		        	 Double goalAmount =gr.getTotalAmountBYUserid(u.getId());
		        	Double remaining = totalBudget-expenses-goalAmount;
		        	
		        	
		        	
		        	model.addAttribute("expenses" , expenses);
		        	model.addAttribute("remaining" , remaining);
		        	model.addAttribute("hasExpenses" ,expenses>0);
		        	
		        	
		        	double percent = (expenses/totalBudget)*100;
		        	 String budgetStatus;
		        	
		        	if(percent>=90){
		        		 budgetStatus="❗ You have used more than 90% of your budget.";
		        		
		        		
		        	}
		        	else if(percent>=70){
		        		budgetStatus="⚠ You've spent 70% of your budget";
		        		
		        	}
		        	else {
		        		budgetStatus="Great , You are spending wisely!";
		        	}
		        	
		        	model.addAttribute("budgetStatus" , budgetStatus);
		        	
		        	return "dashboard";
		        }
		        
		       
		        
		        
		       @GetMapping("/expenseadd")
		        public String addExpense(){
		        	
		        	return "addExpense";
		        	
		            }
		        
		        
        
		        @PostMapping("/addingexpense")
		        public String addexpense(@RequestParam String title , @RequestParam String money , @RequestParam String expensedate , @RequestParam String category ,Model model , HttpSession session, RedirectAttributes redirectAttributes){
		        	
		        	
		        	boolean hasError= false;
		        	
		        	 // title validation
		        	
		        	if(title==null || title.trim().isEmpty()) {
		        		model.addAttribute("titleError" , "Title is required");
		        		hasError = true;
		        		
		        		
		        	}
		        	
		        	 // money validation
		        	
		        	if(money==null || money.trim().isEmpty()) {
		        		model.addAttribute("moneyError" , "Money is required");
		        		hasError = true;
		        	}
		        	
		        		
		        	else if(!money.matches("\\d+(\\.\\d{1,2})?")){
		        		model.addAttribute("moneyError" , "Invalid money");
		        		hasError = true;
		        		
		        	}
		        	
		        	
		        	
		        	// expense date validation
		        	
		        	if(expensedate==null || expensedate.trim().isEmpty()){
		        		
		        		model.addAttribute("dateError" , "Date is required");	
		        		hasError = true;
		        	}
		 
		        	 
		        	
		        	// category validation
		        	if(category==null || category.trim().isEmpty()) {
		        		model.addAttribute("categoryError" , "Category is required");
		        		hasError=true;
		        		
		        	}
		        	
		        
		        	
		        	if(hasError) {
		        		model.addAttribute("title" , title);
		        		model.addAttribute("category" , category);
		        		model.addAttribute("money" ,money);
		        		model.addAttribute("expensedate",expensedate);
		        		return "addExpense";
		        	}
		        	
		        	try {
		        	Double new_money = Double.parseDouble(money);
		        	// ✅ Proper way to parse ISO date (yyyy-MM-dd)
		            LocalDate localDate = LocalDate.parse(expensedate);
		            Date sqlDate = Date.valueOf(localDate); // Convert to java.sql.Date for JDBC
		        	
		        	
		        	User u = (User)session.getAttribute("loggedUser");  
		        	if(u==null) {
		        		return "redirect:/do/log";
		        	}
		        	
		        	Double totalBudget = u.getBudget();
		        	Double spent= er.getExpensesOfuserbyUserId(u.getId());
		        	
		        	Double remaining = totalBudget-spent;
		        	
		        	if(new_money>remaining){
		        		model.addAttribute("globalError" , "Expense exceeds of your remaining budget of ₹"+remaining);
		        		model.addAttribute("title" , title);
		        		model.addAttribute("category" , category);
		        		model.addAttribute("money" ,money);
		        		model.addAttribute("expensedate",expensedate);
		        		return "addExpense";
		        		
		        	}
		        			
		        			
		        			
		        	Expense e = new Expense();
		        	e.setAmount(new_money);
		        	e.setCategory(category);
		        	e.setExpenseDate(sqlDate);
		        	e.setTitle(title);
		        	e.setUserId(u.getId());
		        	
		        	
		        	
		        	er.save(e);
		        	
		        	redirectAttributes.addFlashAttribute("successmsg" , " ✅ Expense addedd successfully !");
		        	
		        	return "redirect:/do/dashboard";
		        	
		       
		        	
		        	}
		        	
		        	
		        	catch(Exception e){
		        		e.printStackTrace();
		                model.addAttribute("globalError", "Something went wrong. Please try again.");
		                return "addExpense";

		        		
		        	}
		        
		        	
		        	
		  }
		        
		        
		        @GetMapping("/logout")
		        public String logoutUser(HttpSession session , RedirectAttributes redirectAttributes){
		        	
		        	User user = (User)session.getAttribute("loggedUser");
		        	
		        	if(user!=null) {
		        		
		        		session.invalidate();
		        		
		        	}
		        	
		        	redirectAttributes.addFlashAttribute("message" , "You have been successfully logged out");
		        	
		        	
		        	return "redirect:/do/log";
		        	
		        }
		        
		        
		        
		        
		        @GetMapping("/goal")
		        public String addGoal() {
		        	return "addgoal";
		        }
		        
		        
		        @PostMapping("/addnow")
		        public String addGoals(@RequestParam String description , @RequestParam String money ,@RequestParam String date , Model model , HttpSession session ,RedirectAttributes redirectAttributes){
		        	
		        	
		        	boolean hasError = false;
		        	// description validation
		        	
		        	if(description==null || description.trim().isEmpty()){
		        		model.addAttribute("desError" , "Desciption is required");
		        		hasError = true;
		        	}
		        	
		        	
		        	// money validation
		        	
		        	if(money.trim().isEmpty() || money==null){
		        		model.addAttribute("moneyError" , "Money is required ");
		        		hasError = true;
		        		
		        	}
		        	
		        	else if(!money.matches("\\d+(\\.\\d{1,2})?")){
		        		model.addAttribute("moneyError" , "Invalid money");
		        		hasError = true;
		        		
		        	}
		        	
		        	
		        	// Date validation
		        	
		        	if(date==null || date.trim().isEmpty()){
		        		model.addAttribute("dateError" , "Date is required");
		        		hasError = true;
		        		
		        	}
		        	
		        	
		        	if(hasError) {
		        		
		        		
		        		model.addAttribute("desc" , description);
		        		model.addAttribute("money" , money);
		        		model.addAttribute("Date" , date);
		        		
		        		return "addgoal";
		        		
		        		
		        		
		        	}
		        	
		        	 
		        	   try {
		        		   Double new_money = Double.parseDouble(money);
		        		   
		        		   LocalDate localDate = LocalDate.parse(date);
				            Date sqlDate = Date.valueOf(localDate); // Convert to java.sql.Date for JDBC
				            
				            User u =(User)session.getAttribute("loggedUser");
				            
				            if(u==null) {
				            	return "redirect:/do/log";				            	
				            }
				            
				          
				            
				            
				            Double budget = u.getBudget();
				            Double spent = er.getExpensesOfuserbyUserId(u.getId());
				            Double goalAmount =gr.getTotalAmountBYUserid(u.getId());
				            Double remaining = budget-spent-goalAmount;
				            
				            
				            
				            if(new_money>remaining) {
				            	model.addAttribute("goalError" , "Goal amount is more than your remaining balance of (₹"+ remaining+")");
				            	model.addAttribute("desc" , description);
				        		model.addAttribute("money" , money);
				        		model.addAttribute("Date" , date);
				        		
				        		return "addgoal";
				        		
				            }
				            
				            
				            
				            Goal g = new Goal();
				            
				            g.setAmount(new_money);
				            g.setDate(sqlDate);
				            g.setDescription(description);
				            g.setUserId(u.getId());
				            
				            
				            gr.save(g);
				            
				            
				            
				            redirectAttributes.addFlashAttribute("msg" , "✅ Goal added successfully");
				            
				            
				            return "redirect:/do/dashboard";
				            
				            
				            
		
		        	   }
		        	   
		        	   catch(Exception e){
		        		   e.printStackTrace();
		        		   model.addAttribute("globalError" ,"Something went wrong please try again");
		        		   return "addgoal";		        		   
		        	   }
		        	
		        	
		        	
		        	
		        	
		        	
		        }
		                
		        
		        @GetMapping("/viewGoals")
		        public String viewGoals(HttpSession session , Model model){
		        	
		        	User u = (User)session.getAttribute("loggedUser");
		        	
		        	if(u==null) {
		        		return "redirect:/do/log";
		        	}
		        	
		        	List<Goal> list = gr.getGoalsbyUserId(u.getId());
		        	Double amount = gr.getTotalAmountBYUserid(u.getId());
		        	
		        	model.addAttribute("goals" , list);
		        	model.addAttribute("amt" , amount);
		        	
		        	return "view";
		        	
		        	
		        }
		        
		        
		        
		        @GetMapping("/delete/{id}")
                public String deleteGoalbasedonId(@PathVariable Long id , RedirectAttributes redirectAttributes, HttpSession session){
		        	
		        	User u =(User)session.getAttribute("loggedUser");
		        	if(u==null) {
		        		return "redirect:/do/log";
		        	}
		        	
		        	gr.deleteGoalByuserId(id , u.getId());
		        	
		        	
		        	redirectAttributes.addFlashAttribute("msgee" , "✅ Goal deleted successfully");
		        	
		        	return "redirect:/do/dashboard";
		        	
		        	
		        }
		        
		        @GetMapping("/viewExpenses")
		        public String viewExpenses(HttpSession session , Model model){
		        	
		        	User u =(User)session.getAttribute("loggedUser");
		        	
		        	if(u==null)
		        	{
		        		return "redirect:/do/log";
		        	}
		        	
		        	List<Expense> list = er.getAllExpensesOfuserbyUserId(u.getId());
		        	
		        	Double amount = er.getExpensesOfuserbyUserId(u.getId());
		        	
		        	model.addAttribute("expenses" , list);
		        	model.addAttribute("amt" , amount);
		        	return "viewexpenses";
		        	
		        } 
		        
		        
		        
		        
		        @GetMapping("/viewChart")
		        public String getChart(HttpSession session ,Model model){  
		        	
		        	User u = (User)session.getAttribute("loggedUser");
		        	
		        	
		        	
		        	if(u==null) {
		        		return "redirect:/do/log";
		        	}
		        	
		        	
		        	List<Map<String , Object>> list = er.getExpensesSummaryByCategory(u.getId());
		        	
		        	
		        	System.out.println("Data sent is : "+list);
		        	
		        	model.addAttribute("data" , list);
		        	
		        	return "chart";
		        	
		        	
		        }

		        
		        
		        
		        
		        
		        

}
