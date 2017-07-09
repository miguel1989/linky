package linky.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity()
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.authorizeRequests()
				.antMatchers("/",
						"/login",
						"/register",
						"/me",
						"/logout")
						.permitAll()
				.anyRequest().authenticated()
				.and().csrf().disable();
		//.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint())
		// .logout().logoutSuccessHandler(customLogoutSuccessHandler())
		// .successHandler(customLoginSuccessHandler())
//                    .failureHandler(customLoginFailureHandler())
//				.usernameParameter("username").passwordParameter("password")
//				.and()
//				.formLogin()
//					.loginPage("/login")
//					.permitAll()
//				.and()
//				.logout()
//					.permitAll();
	}

	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
}
