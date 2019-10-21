package com.iteye.wwwcomy.appdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

@SpringBootApplication
@EnableAuthorizationServer
@EnableWebSecurity
public class AppdemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppdemoApplication.class, args);
	}

}

@Configuration
class SecurityConfig extends WebSecurityConfigurerAdapter {

}

@Configuration
class OAuth2AuthorizationConfig extends AuthorizationServerConfigurerAdapter {

//	@Autowired
//	AuthenticationManager manager;

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory().withClient("m1").secret("{noop}s1")
				.authorizedGrantTypes("authorization_code", "refresh_token", "password", "client_credentials")
				.scopes("openid").redirectUris("http://wwwcomy.com:8080/login").autoApprove(true);
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
		oauthServer.allowFormAuthenticationForClients();
	}

//	@Override
//	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//		endpoints.authenticationManager(manager);
//	}
}