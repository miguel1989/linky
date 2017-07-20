package linky.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity()
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	@Lazy //todo dirty hack. passwordEncoder is still in creation exception occurs
	private CustomUserDetailsAuthenticationProvider customUserDetailsAuthenticationProvider;

	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder auth) {
		auth.authenticationProvider(customUserDetailsAuthenticationProvider);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.authorizeRequests()
				.antMatchers("/admin/**").hasRole("ADMIN")
				.antMatchers("/api/**").hasAnyRole("USER", "ADMIN")
				.antMatchers("/",
						"/*",
						"/register",
						"/logout")
				.permitAll()
				.anyRequest().authenticated()
				.and().formLogin().loginPage("/service/login").permitAll()
				.and().httpBasic()
				.and().logout()
				.and().csrf().disable();
		//.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint())
		// .logout().logoutSuccessHandler(customLogoutSuccessHandler())
		// .successHandler(customLoginSuccessHandler())
//                    .failureHandler(customLoginFailureHandler())
	}
}
