package tw.niq.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(SystemController.PATH)
public class SystemController {
	
	public static final String PATH = "/system";

	@Secured({ "ROLE_ADMIN" })
	@GetMapping
	public String system() {
		return "system/system";
	}
	
}
