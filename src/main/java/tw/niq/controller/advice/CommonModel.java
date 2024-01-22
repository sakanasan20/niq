package tw.niq.controller.advice;

import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import tw.niq.domain.Menu;
import tw.niq.service.MenuService;

@RequiredArgsConstructor
@ControllerAdvice
public class CommonModel {

	private final MenuService menuService;
	
	@ModelAttribute("menus")
	public List<Menu> getList(Model model) {		
		return menuService.getAllMenus();
	}
	
    @ModelAttribute("requestURI")
    String getRequestServletPath(HttpServletRequest request) {
        return request.getServletPath();
    }
	
}
