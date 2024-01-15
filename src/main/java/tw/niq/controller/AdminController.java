package tw.niq.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(AdminController.PATH)
public class AdminController {
	
	public static final String PATH = "/admin";

	@Secured({ "ROLE_ADMIN" })
	@GetMapping
	public String admin() {
		return "admin";
	}
	
}
