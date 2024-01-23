package tw.niq.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tw.niq.domain.Menu;
import tw.niq.repository.MenuRepository;

@Slf4j
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
		
		log.debug("### Saving: " + menu);
		
		Menu savedMenu;
		
		if (menu.getId() != null) {
			Menu foundMenu = menuRepository.findById(menu.getId()).get();
			foundMenu.setMenuName(menu.getMenuName());
			foundMenu.setMenuType(menu.getMenuType());
			foundMenu.setUri(menu.getUri());
			foundMenu.setIcon(menu.getIcon());
			foundMenu.setParent(menu.getParent() == null ? null : menu.getParent());
			foundMenu.setToggleTarget(menu.getToggleTarget());
			foundMenu.setPermission(menu.getPermission());
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
