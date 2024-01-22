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
import tw.niq.security.annotation.authority.ReadPermission;
import tw.niq.service.AuthorityService;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping(AuthorityController.PATH_ROOT)
public class AuthorityController {
	
	public static final String PATH_ROOT = "/system/admin/authorities";
	public static final String TEMPLATE_ROOT = "authorities/";
	
	private final AuthorityService authorityService;

	@ReadPermission
	@GetMapping
	public String getList(Model model) {
		
		List<Authority> authorities = authorityService.getAllAuthorities();
		
		model.addAttribute("authorities", authorities);
		
		return TEMPLATE_ROOT + "list";
	}
	
	@PreAuthorize("hasRole('ADMIN') or (hasAuthority('write'))")
	@GetMapping("/add")
	public String create(Model model) {

		model.addAttribute("authority", Authority.builder().build());
		
		return TEMPLATE_ROOT + "addOrUpdate";
	}
	
	@PreAuthorize("hasRole('ADMIN') or (hasAuthority('write') and #id == principal.id)")
	@GetMapping("/{id}/update")
	public String updateById(@PathVariable(name = "id") Long id, Model model) {
		
		Authority authority = authorityService.getAuthorityById(id);
		
		model.addAttribute("authority", authority);
		
		return TEMPLATE_ROOT + "addOrUpdate";
	}
	
	@PreAuthorize("hasRole('ADMIN') or (hasAuthority('write'))")
	@PostMapping("/save")
	public String save(@Valid @ModelAttribute("authority") Authority authority, BindingResult bindingResult, Model model) {
		
		log.debug("### Saving...: " + authority);
		
		if (bindingResult.hasErrors()) {
			model.addAttribute("authority", authority);
			return TEMPLATE_ROOT + "addOrUpdate";
		}
		
		authorityService.saveAuthority(authority);
		
		return "redirect:" + PATH_ROOT;
	}
	
	@PreAuthorize("hasRole('ADMIN') or (hasAuthority('delete') and #id == principal.id)")
	@GetMapping("/{id}/delete")
	public String deleteById(@PathVariable(name = "id") Long id, Model model) {
		
		authorityService.deleteAuthorityById(id);
		
		return "redirect:" + PATH_ROOT;
	}
	
}
