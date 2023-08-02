package com.example.springframeworke13.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;


@Configuration
public class ProjectConfig extends WebSecurityConfigurerAdapter {



    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        var manager = new InMemoryUserDetailsManager();

        UserDetails u1 = User.withUsername("Yash")
                .password("1234")
                .authorities("ADMIN")
                .roles("write")
                .build();

        UserDetails u2 = User.withUsername("Usopp")
                .password("1234")
                .authorities("MANAGER")
                .roles("read")
                .build();

        manager.createUser(u1);
        manager.createUser(u2);
        return manager;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic();
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET ,"/hello")
                .access("hasRole('ADMIN')")
                .anyRequest()
                .permitAll();
    }
}
