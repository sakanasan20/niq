package tw.niq.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tw.niq.domain.Authority;
import tw.niq.domain.Role;
import tw.niq.security.annotation.authority.ReadPermission;
import tw.niq.service.AuthorityService;
import tw.niq.service.RoleService;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping(RoleController.PATH_ROOT)
public class RoleController {
	
	public static final String PATH_ROOT = "/system/admin/roles";
	public static final String TEMPLATE_ROOT = "roles/";
	
	private final RoleService roleService;
	private final AuthorityService authorityService;

	@ReadPermission
	@GetMapping
	public String getList(Model model) {
		
		List<Role> roles = roleService.getAllRoles();
		
		model.addAttribute("roles", roles);
		
		return TEMPLATE_ROOT + "list";
	}
	
	@PreAuthorize("hasRole('ADMIN') or (hasAuthority('write'))")
	@GetMapping("/add")
	public String create(Model model) {

		model.addAttribute("role", Role.builder().build());
		
		return TEMPLATE_ROOT + "addOrUpdate";
	}
	
	@PreAuthorize("hasRole('ADMIN') or (hasAuthority('write') and #id == principal.id)")
	@GetMapping("/{id}/update")
	public String updateById(@PathVariable(name = "id") Long id, Model model) {
		
		Role role = roleService.getRoleById(id);
		List<Authority> authorities = authorityService.getAllAuthorities();
		
		model.addAttribute("role", role);
		model.addAttribute("authorities", authorities);	
		
		return TEMPLATE_ROOT + "addOrUpdate";
	}
	
	@PreAuthorize("hasRole('ADMIN') or (hasAuthority('write'))")
	@PostMapping("/save")
	public String save(@Valid @ModelAttribute("role") Role role, BindingResult bindingResult, Model model) {
		
		log.debug("### Saving...: " + role);
		log.debug("### Saving...: " + role.getAuthorities());
		
		if (bindingResult.hasErrors()) {
			model.addAttribute("role", role);
			return TEMPLATE_ROOT + "addOrUpdate";
		}
		
		roleService.saveRole(role);
		
		return "redirect:" + PATH_ROOT;
	}
	
	@PreAuthorize("hasRole('ADMIN') or (hasAuthority('delete') and #id == principal.id)")
	@GetMapping("/{id}/delete")
	public String deleteById(@PathVariable(name = "id") Long id, Model model) {
		
		roleService.deleteRoleById(id);
		
		return "redirect:" + PATH_ROOT;
	}
	
}
