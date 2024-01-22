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
import tw.niq.domain.Menu;
import tw.niq.service.MenuService;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping(MenuController.PATH_ROOT)
public class MenuController {

	public static final String PATH_ROOT = "/system/admin/menus";
	public static final String TEMPLATE_ROOT = "menus/";
	
	private final MenuService menuService;
	
	@GetMapping
	public String getList(Model model) {
		
		List<Menu> menus = menuService.getAllMenus();
		
		model.addAttribute("menus", menus);
		
		return TEMPLATE_ROOT + "list";
	}
	
	@GetMapping("/add")
	public String create(Model model) {

		model.addAttribute("menu", Menu.builder().build());
		
		return TEMPLATE_ROOT + "addOrUpdate";
	}
	
	@GetMapping("/{id}/update")
	public String updateById(@PathVariable(name = "id") Long id, Model model) {
		
		Menu menu = menuService.getMenuById(id);
		
		model.addAttribute("menu", menu);
		
		return TEMPLATE_ROOT + "addOrUpdate";
	}
	
	@PostMapping("/save")
	public String save(@Valid @ModelAttribute("menu") Menu menu, BindingResult bindingResult, Model model) {
		
		log.debug("### Saving...: " + menu);
		
		if (bindingResult.hasErrors()) {
			model.addAttribute("menu", menu);
			return TEMPLATE_ROOT + "addOrUpdate";
		}
		
		menuService.saveMenu(menu);
		
		return "redirect:" + PATH_ROOT;
	}
	
	@GetMapping("/{id}/delete")
	public String deleteById(@PathVariable(name = "id") Long id, Model model) {
		
		menuService.deleteMenuById(id);
		
		return "redirect:" + PATH_ROOT;
	}
	
}
