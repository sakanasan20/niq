package tw.niq.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(LoginController.PATH)
public class LoginController {
	
	public static final String PATH = "/login";

	@GetMapping
	public String login() {
		return "login";
	}
	
}
