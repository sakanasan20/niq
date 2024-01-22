package tw.niq.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import tw.niq.domain.Role;
import tw.niq.repository.RoleRepository;

@RequiredArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {

	private final RoleRepository roleRepository;

	@Override
	public List<Role> getAllRoles() {
		return roleRepository.findAll();
	}

	@Override
	public Role getRoleById(Long id) {
		return roleRepository.findById(id).orElseThrow();
	}

	@Override
	public Role saveRole(Role role) {
		
		Role savedRole;
		
		if (role.getId() != null) {
			Role foundRole = roleRepository.findById(role.getId()).get();
			foundRole.setRoleName(role.getRoleName());
			foundRole.setAuthorities(role.getAuthorities());
			savedRole = roleRepository.save(foundRole);
		} else {
			savedRole = roleRepository.save(role);
		}

		return savedRole;
	}

	@Override
	public void deleteRoleById(Long id) {
		roleRepository.deleteById(id);
	}
	
}
