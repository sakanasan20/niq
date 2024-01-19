package tw.niq.domain;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Singular;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "niq_user")
public class User implements UserDetails {

	private static final long serialVersionUID = 3635349131816497567L;

	@ToString.Include
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ToString.Include
	private String username;
	
	private String password;
	
	@ToString.Include
	@Builder.Default
	private Boolean accountNonExpired = true;
	
	@ToString.Include
	@Builder.Default
	private Boolean accountNonLocked = true;
	
	@ToString.Include
	@Builder.Default
	private Boolean credentialsNonExpired = true;
	
	@ToString.Include
	@Builder.Default
	private Boolean enabled = true;
	
	@ToString.Include
	@CreationTimestamp
	@Column(updatable = false)
	private Timestamp createdDate;
	
	@ToString.Include
	@UpdateTimestamp
	private Timestamp lastModifiedDate;
	
	@Singular
	@ManyToMany(cascade = { CascadeType.MERGE }, fetch = FetchType.EAGER)
	@JoinTable(name = "niq_user_role", 
			joinColumns = { @JoinColumn(name = "USER_ID", referencedColumnName = "ID") }, 
			inverseJoinColumns = { @JoinColumn(name = "ROLE_ID", referencedColumnName = "ID") })
	private Set<Role> roles;
	
	@Transient
	private Set<Authority> authorities;

	@Override
	public boolean isAccountNonExpired() {
		return this.accountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return this.accountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return this.credentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return this.enabled;
	}
	
	public Set<GrantedAuthority> getAuthorities() {
		
		Set<GrantedAuthority> grantedRoles = this.roles.stream()
			.map(Role::getRoleName)
			.map((roleName) -> new SimpleGrantedAuthority("ROLE_" + roleName.toUpperCase()))
			.collect(Collectors.toSet());
		
		Set<GrantedAuthority> grantedAuthorities = this.roles.stream()
				.map(Role::getAuthorities)
				.flatMap(Set::stream)
				.map(Authority::getPermission)
				.map((permission) -> new SimpleGrantedAuthority(permission))
				.collect(Collectors.toSet());
		
		Set<GrantedAuthority> authorities = new HashSet<>();
		authorities.addAll(grantedRoles);
		authorities.addAll(grantedAuthorities);
		
		return authorities;
	}
}
