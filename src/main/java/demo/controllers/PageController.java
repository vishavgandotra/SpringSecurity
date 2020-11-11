package demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import demo.entity.Employee;

@Controller
public class PageController {

	@GetMapping({"/","/home"})
	public String openHomePage() {
		return "home";
	}
	
	@GetMapping("/employees")
	public String displayEmployeesPage() {
		return "employees";
	}
	
	@GetMapping("/newEmployee")
	public String displayAddEmployeePage() {
		return "addEmployee";
	}
	
	@GetMapping("/update/{employeeId}")
	public String displayUpdatePage() {

		return "updateEmployee";
	}
	
	@GetMapping("/profile")
	public String displayProfilePage() {
		return "profile";
	}
}
