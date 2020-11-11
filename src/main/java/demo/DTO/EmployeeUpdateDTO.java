package demo.DTO;

import java.util.stream.Collectors;

import demo.entity.Employee;

public class EmployeeUpdateDTO {
	
	private String firstName;
	
	private String lastName;
	
	private String email;
	
	private String userName;
		
	private String roles;
	
	private String password;
	
	public EmployeeUpdateDTO() {
	}
	
//	public EmployeeUpdateDTO(Employee employee) {
//		this.id = employee.getId();
//		this.firstName = employee.getFirstName();
//		this.lastName = employee.getLastName();
//		this.userName = employee.getUserName();
//		this.email = employee.getEmail();
//		this.roles = employee.getRoles().stream()
//				.map(role -> role.getRole()).collect(Collectors.joining(", "));
//		this.password = employee.getPassword();
//	}



	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}
	
	
	public Employee getEmployee(int id) {
		Employee employee = new Employee();
		employee.setId(id);
		employee.setEmail(email);
		employee.setFirstName(firstName);
		employee.setLastName(lastName);
		employee.setPassword(password);
		employee.setRolesFromString(roles);
		employee.setUserName(userName);
		return employee;
	}
	
	
}
