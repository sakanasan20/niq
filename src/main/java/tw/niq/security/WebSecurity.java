package tw.niq.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import lombok.RequiredArgsConstructor;
import tw.niq.service.UserService;

@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true)
@EnableWebSecurity
@Configuration
public class WebSecurity {
	
	private final UserService userService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final PersistentTokenRepository persistentTokenRepository;

	@Bean
	protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
		
		AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
		
		authenticationManagerBuilder.userDetailsService(userService)
									.passwordEncoder(bCryptPasswordEncoder);
		
		AuthenticationManager authenticationManager = authenticationManagerBuilder.build();
		
		http.authenticationManager(authenticationManager)
			.authorizeHttpRequests((authorizeHttpRequests) -> 
				authorizeHttpRequests
					.requestMatchers("/resources/**", "/webjars/**", "/css/**").permitAll()
					.requestMatchers("/login", "/logout").permitAll()
					.requestMatchers("/h2-console/**").permitAll()
					.requestMatchers("/api/**").permitAll()
					.anyRequest().authenticated())
			.httpBasic(Customizer.withDefaults())
			.formLogin(formLogin -> 
				formLogin
					.loginProcessingUrl("/login")
					.loginPage("/login").permitAll()
					.successForwardUrl("/")
					.defaultSuccessUrl("/"))
			.logout(logout -> 
				logout
					.logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
					.logoutSuccessUrl("/login?logout"))
			.rememberMe(rememberMe -> 
				rememberMe
					.tokenRepository(persistentTokenRepository)
					.userDetailsService(userService)
					.rememberMeParameter("remember-me"))
			.csrf((csrf) -> 
				csrf
					.ignoringRequestMatchers("/h2-console/**")
					.ignoringRequestMatchers("/api/**"))
			.headers((headers) -> 
				headers
					.frameOptions((frameOptions) -> 
						frameOptions
							.sameOrigin()))
			.cors(Customizer.withDefaults());
		
		return http.build();
	}
	
}
