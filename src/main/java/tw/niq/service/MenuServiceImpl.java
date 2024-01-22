package tw.niq.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import tw.niq.domain.Menu;
import tw.niq.repository.MenuRepository;

@RequiredArgsConstructor
@Service
public class MenuServiceImpl implements MenuService {

	private final MenuRepository menuRepository;
	
	@Override
	public List<Menu> getAllMenus() {
		return menuRepository.findAll();
	}

	@Override
	public Menu getMenuById(Long id) {
		return menuRepository.findById(id).orElseThrow();
	}

	@Override
	public Menu saveMenu(Menu menu) {
		
		Menu savedMenu;
		
		if (menu.getId() != null) {
			Menu foundMenu = menuRepository.findById(menu.getId()).get();
			foundMenu.setMenuName(menu.getMenuName());
			savedMenu = menuRepository.save(foundMenu);
		} else {
			savedMenu = menuRepository.save(menu);
		}

		return savedMenu;
	}

	@Override
	public void deleteMenuById(Long id) {
		menuRepository.deleteById(id);
	}

}
