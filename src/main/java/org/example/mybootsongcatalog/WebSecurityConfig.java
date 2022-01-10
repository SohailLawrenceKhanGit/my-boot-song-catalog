package org.example.mybootsongcatalog;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // this method is overridden to configure the UsernamePassword filter
        // NB: typically you would code your own auth provider by implementing UserDetailsService
        auth.inMemoryAuthentication()
                .withUser("stuart")
                .password(passwordEncoder().encode("password"))
                .roles("ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // this method is overridden to configure the Interceptor filter
        // NB: this config is suitable for a REST API but NOT for a trad. web MVC app

        // perform basic authentication via the Authorization header
        http.httpBasic();

        // prevent the app from sending a session cookie; make the app stateless
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // turn off CSRF protection (you would NOT do this for an MVC app that has forms etc.)
        http.csrf().disable();

        http.authorizeRequests().antMatchers("/songs").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/**").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.POST).authenticated();
        http.authorizeRequests().antMatchers(HttpMethod.PUT).authenticated();
        http.authorizeRequests().antMatchers(HttpMethod.DELETE).hasRole("ADMIN");
    }
}
