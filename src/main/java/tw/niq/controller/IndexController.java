package tw.niq.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping(IndexController.PATH)
public class IndexController {
	
	public static final String PATH = "/";

	@GetMapping
	public String index() {
		return "index";
	}
	
}
