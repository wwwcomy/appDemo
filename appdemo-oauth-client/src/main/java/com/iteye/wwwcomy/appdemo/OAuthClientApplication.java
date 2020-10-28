package com.iteye.wwwcomy.appdemo;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletContextInitializer;

/**
 * This is the OAuth2 SSO example, notice that there's no JAVA configuration,
 * ALL configuration is done in application.yml.
 * 
 * The flow is:
 * 
 * 1. Visit OAuth client (that is this service) at http://wwwcomy-pc:8090/
 * 
 * 2. This service will let user choose which registered identity provider to
 * use. As there's only one with ID "test" in application.yml, user will be
 * redirected to authorization server. (Done in
 * OAuth2AuthorizationRequestRedirectFilter)
 * 
 * 3. User login to AUTH server successfully and then redirect back to client as
 * "{baseUrl}/login/oauth2/code/{registrationId}", with code and state in the
 * request URL
 * 
 * 4. Client will use the code in step 3 to switch for token and retrieve user
 * information. This is done in OAuth2LoginAuthenticationFilter
 * 
 * 5. Also in OAuth2LoginAuthenticationFilter, the original target URL will be
 * loaded and user will be redirected to that page with proper authenticated
 * context.
 * 
 * @author xingnliu
 *
 */
@SpringBootApplication
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