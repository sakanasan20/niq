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
import tw.niq.domain.Role;
import tw.niq.domain.User;
import tw.niq.security.annotation.authority.ReadPermission;
import tw.niq.service.RoleService;
import tw.niq.service.UserService;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping(UserController.PATH_ROOT)
public class UserController {
	
	public static final String PATH_ROOT = "/system/admin/users";
	public static final String TEMPLATE_ROOT = "users/";
	
	private final UserService userService;
	private final RoleService roleService;

	@ReadPermission
	@GetMapping
	public String getList(Model model) {
		
		List<User> users = userService.getAllUsers();
		
		model.addAttribute("users", users);
		
		return TEMPLATE_ROOT + "list";
	}
	
	@PreAuthorize("hasRole('ADMIN') or (hasAuthority('write'))")
	@GetMapping("/add")
	public String create(Model model) {

		model.addAttribute("user", User.builder().build());
		
		return TEMPLATE_ROOT + "addOrUpdate";
	}
	
	@PreAuthorize("hasRole('ADMIN') or (hasAuthority('write') and #id == principal.id)")
	@GetMapping("/{id}/update")
	public String updateById(@PathVariable(name = "id") Long id, Model model) {
		
		User user = userService.getUserById(id);
		List<Role> roles = roleService.getAllRoles();
		
		model.addAttribute("user", user);
		model.addAttribute("roles", roles);
		
		return TEMPLATE_ROOT + "addOrUpdate";
	}
	
	@PreAuthorize("hasRole('ADMIN') or (hasAuthority('write'))")
	@PostMapping("/save")
	public String save(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, Model model) {
		
		log.debug("### Saving...: " + user);
		
		if (bindingResult.hasErrors()) {
			model.addAttribute("user", user);
			return TEMPLATE_ROOT + "addOrUpdate";
		}
		
		userService.saveUser(user);
		
		return "redirect:" + PATH_ROOT;
	}
	
	@PreAuthorize("hasRole('ADMIN') or (hasAuthority('delete') and #id == principal.id)")
	@GetMapping("/{id}/delete")
	public String deleteById(@PathVariable(name = "id") Long id, Model model) {
		
		userService.deleteUserById(id);
		
		return "redirect:" + PATH_ROOT;
	}
	
}
