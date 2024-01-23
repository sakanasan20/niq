package tw.niq.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import tw.niq.domain.Notice;
import tw.niq.service.NoticeService;

@RequiredArgsConstructor
@Controller
@RequestMapping(IndexController.PATH)
public class IndexController {
	
	public static final String PATH = "/";
	
	private final NoticeService noticeService;

	@GetMapping
	public String index(Model model) {
		
		List<Notice> notices = noticeService.getAllNotices();
		
		model.addAttribute("notices", notices);
		
		return "index";
	}
	
}
