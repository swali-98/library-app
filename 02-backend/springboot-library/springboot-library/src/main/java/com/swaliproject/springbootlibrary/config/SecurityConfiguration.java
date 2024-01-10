package com.swaliproject.springbootlibrary.config;

import com.okta.spring.boot.oauth.Okta;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.accept.ContentNegotiationStrategy;
import org.springframework.web.accept.HeaderContentNegotiationStrategy;

@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws  Exception{

        // disable cross site forgery

        http.csrf().disable();

        //protect endpoints at api/<type>/secure

        http.authorizeHttpRequests(configurer -> configurer
                .antMatchers("/api/books/secure/**","/api/reviews/secure/**","/api/messages/secure/**")
                .authenticated())
                .oauth2ResourceServer()
                .jwt();
        http.cors();

        // add content negotiation strategy
        http.setSharedObject(ContentNegotiationStrategy.class, new HeaderContentNegotiationStrategy());

        // force a non-empty response body for 401's to make user friendly
        Okta.configureResourceServer401ResponseBody(http);

        return http.build();
    }
}
