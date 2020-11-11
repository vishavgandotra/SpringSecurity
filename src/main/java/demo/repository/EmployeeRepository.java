package demo.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import demo.entity.Employee;

public interface EmployeeRepository extends CrudRepository<Employee, Integer> {
	
	public Optional<Employee> findByUserName(String username);
}
