package demo.rest;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ReflectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import demo.DTO.EmployeeUpdateDTO;
import demo.entity.Employee;
import demo.repository.EmployeeRepository;
import demo.wrapper.EmployeeGroup;
import demo.wrapper.Status;

@RestController
@RequestMapping("/rest/employees")
public class EmployeeAPI {
	
	@Autowired
	EmployeeRepository repo;
	
	
	@RolesAllowed("ROLE_ADMIN")
	@GetMapping
	public ResponseEntity<EmployeeGroup> displayEmployees() {
		
		Iterable<Employee> emp = repo.findAll();

		return ResponseEntity.status(HttpStatus.OK).body(new EmployeeGroup(emp));
	}
	
	@GetMapping(value="/profile", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<EmployeeGroup> getEmployee(Authentication authentication) throws Exception {
		
		String userName = authentication.getName();
		Optional<Employee> employee = repo.findByUserName(userName);
		employee.orElseThrow(() -> new Exception());
		return ResponseEntity.status(HttpStatus.OK).body(new EmployeeGroup(employee.get()));
	}
//	
//	private boolean isvalidToken(String authToken) {
//		UserDetails userDetails = service.loadUserByUsername(jwtManager.getUsername(authToken));
//		
//		if(!userDetails.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN")))
//		{
//			return false;
//		}
//		return jwtManager.isValidToken(authToken, userDetails);
//	}
	
	@RolesAllowed("ROLE_ADMIN")
	@PostMapping(consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<EmployeeGroup> addEmployee(@Valid @RequestBody Employee employee) {
		
		repo.save(employee);
		return ResponseEntity.status(HttpStatus.CREATED).body(new EmployeeGroup(employee));
	}
	
	@RolesAllowed("ROLE_ADMIN")
	@PutMapping(value="/{employeeId}",consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<EmployeeGroup> updateEmployee(@PathVariable(name="employeeId") int employeeId, @RequestBody Employee employee) {
		
		employee.setId(employeeId);
		repo.save(employee);
		return ResponseEntity.status(HttpStatus.CREATED).body(new EmployeeGroup(employee));
	}
	
	@RolesAllowed("ROLE_ADMIN")
	@PutMapping(consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Status> updateEmployee(@RequestBody EmployeeUpdateDTO employeeDto) {
		
		Employee employee = null;
		Optional<Employee> e = repo.findByUserName(employeeDto.getUserName());
		if(e.isPresent()) {
			employee = employeeDto.getEmployee(e.get().getId());
			repo.save(employee);

		}
		return ResponseEntity.status(HttpStatus.OK).body(new Status(true));
	}
	
	@RolesAllowed("ROLE_ADMIN")
	@DeleteMapping("/{employeeId}")
	public ResponseEntity<Status> deleteEmployee(@PathVariable("employeeId") int employeeId) {
		
		repo.deleteById(employeeId);
		return ResponseEntity.status(HttpStatus.OK).body(new Status(true));
	}
	
	@PatchMapping("/{employeeId}")
	public ResponseEntity<Status> updateFields(@PathVariable("employeeId") int employeeId,
				@RequestBody Map<Object, Object> fields){
		Optional<Employee> employee = repo.findById(employeeId);
		
		if(employee.isPresent()) {
			fields.forEach((field, value) -> {
				Field f = ReflectionUtils.findField(Employee.class, (String) field);
				f.setAccessible(true);
				ReflectionUtils.setField(f, employee.get(), value);
			});
			repo.save(employee.get());
			return ResponseEntity.status(HttpStatus.OK).body(new Status(true));
		}
		else {
			return ResponseEntity.notFound().build();
		}
	}

}
