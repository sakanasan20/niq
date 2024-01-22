package tw.niq.service;

import java.util.List;

import tw.niq.domain.Menu;

public interface MenuService {

	List<Menu> getAllMenus();

	Menu getMenuById(Long id);
	
	Menu saveMenu(Menu menu);

	void deleteMenuById(Long id);

}
