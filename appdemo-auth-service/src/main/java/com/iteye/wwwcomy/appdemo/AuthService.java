package com.iteye.wwwcomy.appdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

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
		http.requestMatchers().antMatchers("/api/**").and().authorizeRequests().anyRequest().authenticated();
	}
}

@Configuration
class OAuth2AuthorizationConfig extends AuthorizationServerConfigurerAdapter {

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory().withClient("m1").secret("{noop}s1")
				.authorizedGrantTypes("authorization_code", "refresh_token", "password", "client_credentials")
				.scopes("openid").redirectUris("http://localhost:8089/login").autoApprove(true)
				//
				.and().withClient("m2").secret("{noop}s2")
				.authorizedGrantTypes("authorization_code", "refresh_token", "password", "client_credentials")
				.scopes("openid").redirectUris("http://wwwcomy:8090/").autoApprove(true)
				//
				.and().withClient("m3").secret("{noop}s3")
				.authorizedGrantTypes("authorization_code", "refresh_token", "password", "client_credentials")
				.scopes("openid").redirectUris("http://localhost:9998/login").autoApprove(true);
	}

	@Autowired
	private ClientDetailsService clientDetailsService;

	@Bean
	@Primary
	public DefaultTokenServices tokenServices() {
		DefaultTokenServices services = new DefaultTokenServices();
		services.setTokenStore(tokenStore());
		services.setTokenEnhancer(jwtTokenConverter());
		services.setSupportRefreshToken(true);
		services.setClientDetailsService(clientDetailsService);
		return services;
	}

	@Bean
	public TokenStore tokenStore() {
//		return new InMemoryTokenStore();
		return new JwtTokenStore(jwtTokenConverter());
	}

	@Bean
	public JwtAccessTokenConverter jwtTokenConverter() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("mytest.jks"),
				"passwd".toCharArray());
		converter.setKeyPair(keyStoreKeyFactory.getKeyPair("mytest"));
		return converter;
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
		oauthServer.allowFormAuthenticationForClients();
//		oauthServer.allowFormAuthenticationForClients().tokenKeyAccess("permitAll()").checkTokenAccess("permitAll()");
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.tokenServices(tokenServices()).tokenStore(tokenStore());
	}

}