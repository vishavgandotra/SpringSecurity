package demo.security.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import demo.entity.Employee;
import demo.repository.EmployeeRepository;
import demo.security.MyUserDetails;

@Service
public class MyUserDetailService implements UserDetailsService {

	@Autowired
	EmployeeRepository repo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Employee> employee = repo.findByUserName(username);
		employee.orElseThrow(() -> new UsernameNotFoundException("NOT FOUND"));
		return new MyUserDetails(employee.get());
	}

}
