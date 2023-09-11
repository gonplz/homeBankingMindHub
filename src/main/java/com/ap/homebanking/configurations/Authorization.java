package com.ap.homebanking.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@EnableWebSecurity
@Configuration
public class Authorization {

    @Bean
    public SecurityFilterChain filterChain (HttpSecurity http) throws Exception{

        http.authorizeRequests()

                .antMatchers("/web/index.html", "/web/js/","/web/css/","/web/img/").permitAll()
                .antMatchers(HttpMethod.POST,"/api/login","/api/logout","/api/clients").permitAll()
                .antMatchers("/h2-console/","/rest/", "/api/clients").hasAuthority("ADMIN")
                .antMatchers("/api/**","/api/clients/{id}","/loans").hasAuthority("CLIENT")
                .antMatchers("/api/clients/current", "/web/","/api/clients/","/clients/current/accounts").hasAuthority("CLIENT")
                .antMatchers(HttpMethod.POST, "/clients/current/accounts/**","/clients/current/cards","/transactions","/loans").hasAuthority("CLIENT");

//                .antMatchers(HttpMethod.POST,"/api/login","/api/clients").permitAll()
//                .antMatchers( "/web/index.html", "/web/js/", "/web/css/", "/web/img/").permitAll()
//                .antMatchers(HttpMethod.GET,"/admin/", "/rest/","/api/clients", "/api/accounts").hasAuthority("ADMIN")
//                .antMatchers( "/api/clients/current", "/web/","/api/logout","/api/clients/current/accounts",
//                        "/api/clients/current/cards", "/api/transactions/*","/api/accounts","/api/accounts/", "/api/loans").hasAnyAuthority("CLIENT", "ADMIN")
//                .antMatchers(HttpMethod.POST, "/api/clients/current", "/api/transactions").hasAnyAuthority("CLIENT", "ADMIN")
//                .anyRequest().denyAll();

//                .antMatchers(HttpMethod.POST,"/api/clients","/api/login").permitAll()
//                .antMatchers(HttpMethod.POST,"/api/logout").hasAnyAuthority("CLIENT","ADMIN")
//                .antMatchers("/web/index.html","/web/js/**","/web/css/**","/web/img/**").permitAll()
//                .antMatchers("/admin/**").hasAuthority("ADMIN")
//                .antMatchers("/api/clients/current","/web/**","/api/clients/current/cards","/api/clients/current/accounts",
//                        "/api/transaction","/api/transactions/**").hasAnyAuthority("CLIENT","ADMIN");

        http.formLogin()
                .usernameParameter("email")
                .passwordParameter("password")
                .loginPage("/api/login");

        http.logout().logoutUrl("/api/logout");

        http.csrf().disable();
        http.headers().frameOptions().disable();
        http.exceptionHandling().authenticationEntryPoint((request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED));
        http.formLogin().successHandler((request, response, authentication) -> clearAuthenticationAttributes(request));
        http.formLogin().failureHandler((request, response, exception) -> response.sendError(HttpServletResponse.SC_FORBIDDEN));
        http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());

        return http.build();
    }
    private void clearAuthenticationAttributes(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }
    }
}
