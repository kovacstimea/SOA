package io.pivotal.microservices.services.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
//import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
//import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

@Configuration
public class OAuth2Configuration {

//    private static final String SERVER_RESOURCE_ID = "oauth2-server";

//    private static InMemoryTokenStore tokenStore = new InMemoryTokenStore();


//    @Configuration
//    @EnableResourceServer
//    protected static class ResourceServer extends ResourceServerConfigurerAdapter {
//
//        @Override
//        public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
//            resources.tokenStore(tokenStore).resourceId(SERVER_RESOURCE_ID);
//        }
//
//        @Override
//        public void configure(HttpSecurity http) throws Exception {
//            http.requestMatchers().antMatchers("/admin**").and().authorizeRequests().antMatchers("/admin**").access("#oauth2.hasScope('read')");
//        }
//    }
//
//    @Configuration
//    @EnableAuthorizationServer
//    protected static class AuthConfig extends AuthorizationServerConfigurerAdapter {
//
//        @Autowired
//        private AuthenticationManager authenticationManager;
//
//
//        @Override
//        public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//            endpoints.authenticationManager(authenticationManager).tokenStore(tokenStore).approvalStoreDisabled();
//        }
//
//        @Override
//        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//            clients.inMemory()
//                    .withClient("my-client")
//                    .authorizedGrantTypes("authorization_code","refresh_token", "password")
//                    .authorities("ROLE_CLIENT")
//                    .scopes("read")
//                    .resourceIds(SERVER_RESOURCE_ID)
//                    .secret("secret")
//            ;
//        }
//    }
}