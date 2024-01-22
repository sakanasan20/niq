package tw.niq.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(H2Controller.PATH)
public class H2Controller {
	
	public static final String PATH = "/system/tools/h2";

	@Secured({ "ROLE_ADMIN" })
	@GetMapping
	public String system() {
		
		return "system/h2";
	}
	
}
