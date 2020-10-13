package com.iteye.wwwcomy.appdemo;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@SpringBootApplication
@EnableOAuth2Sso
public class OAuthClientApplication implements ServletContextInitializer {

	public static void main(String[] args) {
		SpringApplication.run(OAuthClientApplication.class, args);
	}

	/**
	 * To resolve the issue that if auth service and client are all in localhost,
	 * there might be JSESSION ID collision issue
	 * https://my.oschina.net/xuzimian/blog/3069711
	 */
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		servletContext.getSessionCookieConfig().setName("SESSIONID");
	}
}

// OAuth2SsoDefaultConfiguration is being used, I might need to look into spring security 5 on how to configure a client

//https://github.com/okta/okta-spring-boot/issues/91

// TODO the following configuration is for customize those URLs not under protection, however, I havn't found how to overwrite OAuth2SsoDefaultConfiguration yet.

//@Configuration
//@Order(10)
//class SecurityConfig extends WebSecurityConfigurerAdapter {
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http.antMatcher("/**").authorizeRequests().antMatchers("/index.html").permitAll().anyRequest().authenticated()
//				.and().logout().logoutSuccessUrl("/").permitAll().and().csrf().disable();
//	}
//}

//@Configuration
//class OAuth2LoginSecurityConfig extends WebSecurityConfigurerAdapter {
//
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//
//		// all requests
//		http.authorizeRequests().anyRequest().authenticated();
//
//		http.oauth2Login().defaultSuccessUrl("/index.html");
//	}
//}