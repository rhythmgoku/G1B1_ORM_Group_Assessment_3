package com.greatlearning.tickettracker.configuration;

import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.greatlearning.tickettracker.services.CustomUserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	private static final String[] All_ALLOWED_PATHS = { "/", "/{role}/tickets", "/{role}/tickets/search",
			"/{role}/tickets/addnew", "/{role}/tickets/save", "/{role}/tickets/{id}/view", "/{role}/tickets/{id}",
			"/{role}/tickets/healthCheck" };

	private static final String[] ONLY_ADMIN_PATHS = { "/{role}/tickets/{id}/edit", "/{role}/tickets/{id}/delete" };

	private static final String ADMIN = "ADMIN";
	private static final String USER = "USER";

	@Bean
	Map<Integer, String> roleMap() {
		return Map.of(1, ADMIN, 2, USER);
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http.authorizeHttpRequests(requests -> requests.requestMatchers(All_ALLOWED_PATHS).hasAnyAuthority(USER, ADMIN)
				.requestMatchers(ONLY_ADMIN_PATHS).hasAuthority(ADMIN)
				.requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).permitAll().anyRequest()
				.authenticated())
				.formLogin(formLogin -> formLogin.loginProcessingUrl("/login").successForwardUrl("/home").permitAll())
				.logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/login").invalidateHttpSession(true)
						.deleteCookies("JSESSIONID").permitAll())
				.exceptionHandling(exceptionHandling -> exceptionHandling.accessDeniedPage("/accessdenied"))
				.cors(cors -> cors.disable()).csrf(csrf -> csrf.disable());

		http.authenticationProvider(daoAuthenticationProvider());
		return http.build();
	}

	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(userDetailsService());
		daoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());
		return daoAuthenticationProvider;

	}

	@Bean
	public PasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public UserDetailsService userDetailsService() {
		return new CustomUserDetailsServiceImpl();
	}

}