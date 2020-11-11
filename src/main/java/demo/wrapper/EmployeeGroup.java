package demo.wrapper;

import java.util.ArrayList;
import java.util.List;

import demo.DTO.EmployeeDTO;
import demo.entity.Employee;

public class EmployeeGroup {
	public List<EmployeeDTO> employees = new ArrayList<>();
	
	public EmployeeGroup() {
		// TODO Auto-generated constructor stub
	}
	
	public EmployeeGroup(Iterable<Employee> employees) {
		employees.forEach(emp -> {
			EmployeeDTO dto = new EmployeeDTO(emp);
			this.employees.add(dto);
		});
	}
	
	public EmployeeGroup(Employee employee) {
		EmployeeDTO dto = new EmployeeDTO(employee);
		this.employees.add(dto);
	}

	public List<EmployeeDTO> getEmployees() {
		return employees;
	}

	public void setEmployees(List<EmployeeDTO> employees) {
		this.employees = employees;
	}
	
	
}
