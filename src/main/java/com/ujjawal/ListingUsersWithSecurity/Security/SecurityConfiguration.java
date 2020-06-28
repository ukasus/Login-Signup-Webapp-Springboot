package com.ujjawal.ListingUsersWithSecurity.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.ujjawal.ListingUsersWithSecurity.Services.AuthEntryExcepHandler;
import com.ujjawal.ListingUsersWithSecurity.Services.MyUserDetailService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {


 
    @Autowired
    MyUserDetailService myUserDetailsService;
    @Autowired
	AuthEntryExcepHandler authEntryPoint;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/loginPage","/registerUser","/addUser","/css/**","/login").permitAll()
                .anyRequest().authenticated().and().
                exceptionHandling().authenticationEntryPoint(authEntryPoint).and().
                logout().
                logoutSuccessUrl("/loginPage").
                invalidateHttpSession(true).      
                deleteCookies("JSESSIONID").and().csrf().disable();
                

        
    }



    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}