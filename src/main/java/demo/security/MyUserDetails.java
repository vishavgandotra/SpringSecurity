package demo.security;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import demo.entity.Employee;

public class MyUserDetails implements UserDetails {
	
	private String userName;
	private String password;
	private List<GrantedAuthority> authorities;
	
	public MyUserDetails() {
	}
	
	public MyUserDetails(Employee employee) {
		this.userName = employee.getUserName();
		this.password = employee.getPassword();
		this.authorities = employee.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getRole()))
				.collect(Collectors.toList());
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
