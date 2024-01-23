package tw.niq.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
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
	
	@NotBlank
	@ToString.Include
	private String menuName;
	
	@ManyToOne
	private Menu parent;
	
	@ToString.Include
	@Builder.Default
	@Enumerated(EnumType.STRING)
	private MenuType menuType = MenuType.NONE;

	@NotBlank
	@ToString.Include
	private String uri;
	
	@ToString.Include
	private String permission;
	
	@ToString.Include
	private String icon;
	
	@ToString.Include
	private String toggleTarget;
	
}
