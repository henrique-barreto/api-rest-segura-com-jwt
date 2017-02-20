package com.henrique.jwt.config;

import com.henrique.jwt.auth.JwtUserDetailsService;
import com.henrique.jwt.auth2.CustomAuthenticationProvider;
import com.henrique.jwt.auth2.JWTAuthenticationFilter;
import com.henrique.jwt.auth2.JWTLoginFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Created by henrique on 19/02/17.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    protected JwtUserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // disable caching
        http.headers().cacheControl();

        http.csrf().disable() // disable csrf for our requests.
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers(HttpMethod.POST, "/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .authenticationProvider(myAuthProvider())
                // We filter the api/login requests
                .addFilterBefore(new JWTLoginFilter("/login", authenticationManager()), UsernamePasswordAuthenticationFilter.class)
                // And filter other requests to check the presence of JWT in header
                .addFilterBefore(new JWTAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
//        // Create a default account
//        auth.inMemoryAuthentication()
//                .withUser("admin")
//                .password("password")
//                .roles("ADMIN");
//    }


    @Bean
    public CustomAuthenticationProvider myAuthProvider() throws Exception {
        CustomAuthenticationProvider provider = new CustomAuthenticationProvider();
//        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

    //    @Autowired
//    private RestAcessDeniedHandler acessDeniedHandler;
//
//    @Autowired
//    private RestAuthenticationSuccessHandler successHandler;
//
//    @Autowired
//    private RestAuthenticationFailureHandler failureHandler;
//
//    @Autowired
//    private JwtUserDetailsService userDetailsService;
//
//    @Autowired
//    private RestAuthenticationEntryPoint authenticationEntryPoint;

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//
//        http
//                .addFilterAt(getJsonUsernamePasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
//                .csrf()
//                    .disable()
//                .authorizeRequests()
//                .antMatchers("/api/**")
//                    .hasRole("USER")
//                .and().exceptionHandling().accessDeniedHandler(acessDeniedHandler).authenticationEntryPoint(authenticationEntryPoint)
//                .and().formLogin().failureHandler(failureHandler).successHandler(successHandler)
//                .and().userDetailsService(userDetailsService);
//
//    }


//    @Bean
//    protected Filter getJsonUsernamePasswordAuthenticationFilter() throws Exception {
//
//        JsonUsernamePasswordAuthenticationFilter filter = new JsonUsernamePasswordAuthenticationFilter();
//        filter.setAuthenticationManager(super.authenticationManagerBean());
//        filter.setAuthenticationSuccessHandler(successHandler);
//        filter.setAuthenticationFailureHandler(failureHandler);
//        return filter;
//    }


}
