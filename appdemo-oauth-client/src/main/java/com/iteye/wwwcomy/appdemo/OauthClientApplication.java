package com.iteye.wwwcomy.appdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;

@SpringBootApplication
@EnableOAuth2Sso
public class OauthClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(OauthClientApplication.class, args);
	}

}

//@Configuration
//@Order(10)
//class SecurityConfig extends WebSecurityConfigurerAdapter {
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http.antMatcher("/**").authorizeRequests().antMatchers("/", "/js/**", "/login", "/index.html").permitAll()
//				.anyRequest().authenticated().and().logout().logoutSuccessUrl("/").permitAll().and().csrf().disable();
//	}
//}