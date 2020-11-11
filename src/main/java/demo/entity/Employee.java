package demo.entity;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import com.sun.istack.NotNull;

@Entity
@Table(name="employee_new_sec")
public class Employee {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="employee_seq")
	@SequenceGenerator(name="employee_seq", sequenceName="employee_id_sequence", allocationSize=1)
	private int id;
	
	@Column(name="first_name")
	@NotNull
	@Size(min=2, message="Enter first name")
	private String firstName;
	
	@Column(name="last_name")
	private String lastName;
	
	@NotNull
	@Email
	@Size(min=5, message="Enter email")
	private String email;
	
	@NotNull
	@Size(min=2, message="username")
	private String userName;
	
	@NotNull
	@Size(min=2, message="Enter password")
	private String password;
	
	@OneToMany(mappedBy="employee", cascade=CascadeType.ALL, orphanRemoval=true, fetch=FetchType.EAGER)
	private Set<Role> roles;
	
	public Employee() {
	}

	public Employee(String firstName, String lastName, String email, String userName, String password, Set<String> roles) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.userName = userName;
		this.password = password;
		this.roles = roles.stream().map(role -> new Role("ROLE_"+role, this)).collect(Collectors.toSet());
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<String> roles) {
		this.roles = roles.stream().map(role -> new Role("ROLE_"+role, this)).collect(Collectors.toSet());
	}
	
	public void setRolesFromString(String roles) {
		this.roles = Arrays.asList(roles.split(", ")).stream().map(role -> new Role(role, this)).collect(Collectors.toSet());
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}	
			
}
