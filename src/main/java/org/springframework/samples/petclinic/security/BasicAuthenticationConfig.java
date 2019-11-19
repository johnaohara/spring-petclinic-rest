package org.springframework.samples.petclinic.security;

import org.springframework.context.annotation.Configuration;

@Configuration
//@EnableGlobalMethodSecurity(prePostEnabled = true) // Enable @PreAuthorize method-level security
//@ConditionalOnProperty(name = "petclinic.security.enable", havingValue = "true")
//public class BasicAuthenticationConfig extends WebSecurityConfigurerAdapter {
public class BasicAuthenticationConfig  {

//    @Autowired
//    private DataSource dataSource;

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        // @formatter:off
//        http
//            .authorizeRequests()
//                .anyRequest()
//                    .authenticated()
//                    .and()
//                .httpBasic()
//                    .and()
//                .csrf()
//                    .disable();
//        // @formatter:on
//    }
//
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        // @formatter:off
//        auth
//            .jdbcAuthentication()
//                .dataSource(dataSource)
//                .usersByUsernameQuery("select username,password,enabled from users where username=?")
//                .authoritiesByUsernameQuery("select username,role from roles where username=?");
//        // @formatter:on
//    }
}
