package com.example.fishop.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Autowired
    private DataSource dataSource;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((requests) -> requests
            .requestMatchers("/register","/login","/api/user/add").anonymous()
            .requestMatchers("/","/all","/about","/all/*","/all/search").permitAll()

            .requestMatchers("/adminpanel","/adminpanel/products/new","/adminpanel/*","/all/*/edit","/api/all/*").hasAuthority("ADMIN")

            .anyRequest().authenticated()
        )
        .formLogin((form) -> form
            .loginPage("/login")
                .defaultSuccessUrl("/",true)
            .permitAll()
        )
        .logout(httpSecurityLogoutConfigurer -> httpSecurityLogoutConfigurer.logoutUrl("/logout").permitAll().logoutSuccessUrl("/login"));
        return http.build();
    }


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(NoOpPasswordEncoder.getInstance())
                .usersByUsernameQuery("select email, password, true from usr where email=?")
                .authoritiesByUsernameQuery("select email, role from usr where email=?");
    }
}
