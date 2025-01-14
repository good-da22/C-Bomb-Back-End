package com.fireprohibition.CBomb.global.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.fireprohibition.CBomb.global.authentication.LoginSuccessHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource dataSource;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http
				.authorizeRequests()
				.antMatchers("/", "/login/**", "/register/**", "/register", "/login", "/assets/**", "/images/**", "/test",
						"/theater/**", "/chat/**", "/testTheaterRepo", "/testTheaterRepo/**", "/room", "rooms").permitAll()
				.anyRequest().authenticated()
				.and()
				.formLogin()
					.loginPage("/login")
					.defaultSuccessUrl("/")
					.failureUrl("/login?error=true")
					.usernameParameter("username")
					.passwordParameter("password")
				.and()
				.logout()
				.permitAll();
	}

	public void configureGlobal(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.jdbcAuthentication()
				.dataSource(dataSource)
				.passwordEncoder(passwordEncoder())
				.usersByUsernameQuery("select username,password,enabled "
						+ "from user "
						+ "where username = ?")
				.authoritiesByUsernameQuery("select u.username, r.name "
						+ "from user_role ur inner join user u on ur.user_id = u.id "
						+ "inner join role r on ur.role_id = r.id"
						+ "where username = ?");
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	public AuthenticationSuccessHandler successHandler() {
		return new LoginSuccessHandler("/");
	}
}
