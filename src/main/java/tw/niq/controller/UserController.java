package tw.niq.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import tw.niq.domain.User;
import tw.niq.security.annotation.authority.ReadPermission;
import tw.niq.service.UserService;

@RequiredArgsConstructor
@Controller
@RequestMapping(UserController.PATH)
public class UserController {
	
	public static final String PATH = "/users";
	
	private final UserService userService;

	@ReadPermission
	@GetMapping
	public String getUsersList(Model model) {
		
		List<User> users = userService.getAllUsers();
		
		model.addAttribute("users", users);
		
		return "users/list";
	}
	
	@PreAuthorize("hasRole('ADMIN') or (hasAuthority('write') and #id == principal.id)")
	@GetMapping("/{id}/update")
	public String updateUserById(@PathVariable(name = "id") Long id, Model model) {
		
		User user = userService.getUserById(id);
		
		model.addAttribute("user", user);
		
		return "users/update";
	}
	
	@PreAuthorize("hasRole('ADMIN') or (hasAuthority('delete') and #id == principal.id)")
	@GetMapping("/{id}/delete")
	public String deleteUserById(@PathVariable(name = "id") Long id, Model model) {
		
		User user = userService.getUserById(id);
		
		model.addAttribute("user", user);
		
		return "users/delete";
	}
	
}
