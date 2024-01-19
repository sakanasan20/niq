package tw.niq.service;

import java.util.List;

import tw.niq.domain.Role;

public interface RoleService {

	List<Role> getAllRoles();

	Role getRoleById(Long id);

	Role saveRole(Role role);

	void deleteRoleById(Long id);

}
