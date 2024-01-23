package tw.niq.controller.advice;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import tw.niq.domain.Menu;
import tw.niq.domain.MenuType;
import tw.niq.service.MenuService;

@RequiredArgsConstructor
@ControllerAdvice
public class CommonModel {

	private final MenuService menuService;
	
	@ModelAttribute("menus")
	public List<Menu> getMenus(Model model) {		
		return menuService.getAllMenus();
	}
	
	@ModelAttribute("parentsOpt")
	public List<Menu> getParentsOpt(Model model) {
		
		List<Menu> parentsOpt = new ArrayList<>();
		
		parentsOpt.add(Menu.builder().menuName("None").menuType(MenuType.CATALOG).uri("").build());
		
		parentsOpt.addAll(menuService.getAllMenus().stream()
				.filter((parent) -> parent.getMenuType().equals(MenuType.CATALOG))
				.collect(Collectors.toList()));
		
		return parentsOpt;
	}
	
    @ModelAttribute("requestURI")
    String getRequestServletPath(HttpServletRequest request) {
        return request.getServletPath();
    }
	
}
