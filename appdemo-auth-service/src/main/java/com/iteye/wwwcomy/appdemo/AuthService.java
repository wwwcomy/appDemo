package com.iteye.wwwcomy.appdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@SpringBootApplication
@EnableAuthorizationServer
@EnableWebSecurity
@EnableResourceServer
public class AuthService {

	public static void main(String[] args) {
		SpringApplication.run(AuthService.class, args);
	}

}

/**
 *
 * Really don't understand why I should use ACCESS_OVERRIDE_ORDER to make it
 * work
 * 
 * @see https://stackoverflow.com/questions/49970346/correctly-configure-spring-security-oauth2
 * @see https://github.com/spring-projects/spring-security-oauth/issues/980
 * 
 *      From what I understand, using spring security OAuth2, multiple
 *      configurations are done to protect different endpoints, like authorize
 *      endpoint, user endpoint, token endpoint, login endpoint.
 * 
 *      Such kind of endpoints are actually defined in different configurations
 *      with different filters enabled, which, might have conflicts.
 * 
 *      That's why it is so hard to make it work...
 * @author xingnliu
 */
@Configuration
//@Order(-20)
class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.formLogin()
				// .loginPage("/login") // This is for custom login page, although it is the
				// same URL with the out-of-box login page
				.permitAll().and().authorizeRequests().antMatchers("/login*").permitAll().anyRequest().authenticated()
				.and().csrf().disable();
	}

}

@Configuration
class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) {
		resources.resourceId("USERS");
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http
		// Since we want the protected resources to be accessible in the UI as well we
		// need
//				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED).and()
				.requestMatchers().antMatchers("/api/**").and().authorizeRequests()
//                .antMatchers("/product/**").access("#oauth2.hasScope('select') and hasRole('ROLE_USER')")
				.antMatchers("/user").authenticated();
	}
}

@Configuration
class OAuth2AuthorizationConfig extends AuthorizationServerConfigurerAdapter {

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory().withClient("m1").secret("{noop}s1")
				.authorizedGrantTypes("authorization_code", "refresh_token", "password", "client_credentials")
				.scopes("openid").redirectUris("http://localhost:8089/login").autoApprove(true);
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
		oauthServer.allowFormAuthenticationForClients();
	}

}