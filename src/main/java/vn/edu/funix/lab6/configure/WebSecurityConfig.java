package vn.edu.funix.lab6.configure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	private static final String LOGIN_URL = "/login";
	private static final String LOGOUT_URL = "/logout";

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

		// set service finding user in database
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());

	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.headers().frameOptions().disable();

		// page access without authentication
		http.authorizeRequests().antMatchers("/", LOGIN_URL, LOGOUT_URL, "/h2-console").permitAll();

		// page access with authentication
		http.authorizeRequests().antMatchers("/userInfo").access("hasAnyRole('ROLE_USER')");

		http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");

		// Configure for login form
		http.authorizeRequests().and().formLogin()//
				// Submit form login
				.loginProcessingUrl("/j_spring_security_check") // Submit URL
				.loginPage(LOGIN_URL)//
				.defaultSuccessUrl("/firstEntryPage")//
				.failureUrl("/login?error=true")//
				.usernameParameter("username")//
				.passwordParameter("password")
				// Configure for Logout Page.
				.and().logout().logoutUrl(LOGOUT_URL).logoutSuccessUrl(LOGIN_URL);

	}

}