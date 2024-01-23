package tw.niq.controller;

import java.util.List;

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
import tw.niq.domain.Notice;
import tw.niq.service.NoticeService;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping(NoticeController.PATH_ROOT)
public class NoticeController {

	public static final String PATH_ROOT = "/system/admin/notices";
	public static final String TEMPLATE_ROOT = "notices/";
	
	private final NoticeService noticeService;
	
	@GetMapping
	public String getList(Model model) {
		
		List<Notice> notices = noticeService.getAllNotices();
		
		model.addAttribute("notices", notices);
		
		return TEMPLATE_ROOT + "list";
	}
	
	@GetMapping("/add")
	public String create(Model model) {

		model.addAttribute("notice", Notice.builder().build());
		
		return TEMPLATE_ROOT + "addOrUpdate";
	}
	
	@GetMapping("/{id}/update")
	public String updateById(@PathVariable(name = "id") Long id, Model model) {
		
		Notice notice = noticeService.getNoticeById(id);
		
		model.addAttribute("notice", notice);
		
		return TEMPLATE_ROOT + "addOrUpdate";
	}
	
	@PostMapping("/save")
	public String save(@Valid @ModelAttribute("notice") Notice notice, BindingResult bindingResult, Model model) {
		
		log.debug("### Saving...: " + notice);
		
		if (bindingResult.hasErrors()) {
			model.addAttribute("notice", notice);
			return TEMPLATE_ROOT + "addOrUpdate";
		}

		noticeService.saveNotice(notice);
		
		return "redirect:" + PATH_ROOT;
	}
	
	@GetMapping("/{id}/delete")
	public String deleteById(@PathVariable(name = "id") Long id, Model model) {
		
		noticeService.deleteNoticeById(id);
		
		return "redirect:" + PATH_ROOT;
	}
	
}
