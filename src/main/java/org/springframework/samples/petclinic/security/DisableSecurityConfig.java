package org.springframework.samples.petclinic.security;

import org.springframework.context.annotation.Configuration;

/**
 * Starting from Spring Boot 2, if Spring Security is present, endpoints are secured by default
 * using Spring Security’s content-negotiation strategy.
 */
@Configuration
//@ConditionalOnProperty(name = "petclinic.security.enable", havingValue = "false")
//public class DisableSecurityConfig extends WebSecurityConfigurerAdapter {
public class DisableSecurityConfig {

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        // @formatter:off
//        http
//            .authorizeRequests()
//                .anyRequest().permitAll()
//                .and()
//            .csrf()
//                .disable();
//        // @formatter:on
//    }
}
