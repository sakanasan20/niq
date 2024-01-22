package tw.niq.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "niq_menu")
public class Menu {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ToString.Include
	private String menuName;
	
	@ManyToOne
	private Menu parent;
	
	@Builder.Default
	private MenuType menuType = MenuType.NONE;

	private String uri;
	
	private String permission;
	
	private String icon;
	
	private String toggleTarget;
	
}
