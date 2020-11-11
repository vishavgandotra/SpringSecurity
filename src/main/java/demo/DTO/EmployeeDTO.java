package demo.DTO;

import java.util.stream.Collectors;

import demo.entity.Employee;

public class EmployeeDTO {

	private int id;
	
	private String firstName;
	
	private String lastName;
	
	private String email;
	
	private String userName;
		
	private String roles;
	
	public EmployeeDTO() {
	}
	
	public EmployeeDTO(Employee employee) {
		this.id = employee.getId();
		this.firstName = employee.getFirstName();
		this.lastName = employee.getLastName();
		this.userName = employee.getUserName();
		this.email = employee.getEmail();
		this.roles = employee.getRoles().stream()
				.map(role -> role.getRole()).collect(Collectors.joining(", "));
	}

	public int getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}

	public String getUserName() {
		return userName;
	}

	public String getRoles() {
		return roles;
	}
	
	
	
	
}
