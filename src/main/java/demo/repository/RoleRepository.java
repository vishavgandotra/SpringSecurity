package demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import demo.entity.Role;

public interface RoleRepository extends CrudRepository<Role, Integer> {

//	@Query("SELECT Role.id, Role.role FROM Role WHERE (Role.employees = :employeeId)")
//	public List<Role> findRolesForEmployee(int employeeId);
}
