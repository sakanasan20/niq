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
import tw.niq.domain.User;
import tw.niq.security.annotation.authority.ReadPermission;
import tw.niq.service.UserService;

@Slf4j
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
	
	@PreAuthorize("hasRole('ADMIN') or (hasAuthority('write'))")
	@GetMapping("/add")
	public String createUser(Model model) {

		model.addAttribute("user", User.builder().build());
		
		return "users/addOrUpdate";
	}
	
	@PreAuthorize("hasRole('ADMIN') or (hasAuthority('write') and #id == principal.id)")
	@GetMapping("/{id}/update")
	public String updateUserById(@PathVariable(name = "id") Long id, Model model) {
		
		User user = userService.getUserById(id);
		
		model.addAttribute("user", user);
		
		return "users/addOrUpdate";
	}
	
	@PreAuthorize("hasRole('ADMIN') or (hasAuthority('write'))")
	@PostMapping("/save")
	public String saveUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, Model model) {
		
		log.debug("### Saving...: " + user);
		
		if (bindingResult.hasErrors()) {
			model.addAttribute("user", user);
			return "users/addOrUpdate";
		}
		
		userService.saveUser(user);
		
		return "redirect:" + PATH;
	}
	
	@PreAuthorize("hasRole('ADMIN') or (hasAuthority('delete') and #id == principal.id)")
	@GetMapping("/{id}/delete")
	public String deleteUserById(@PathVariable(name = "id") Long id, Model model) {
		
		userService.deleteUserById(id);
		
		return "redirect:" + PATH;
	}
	
}
