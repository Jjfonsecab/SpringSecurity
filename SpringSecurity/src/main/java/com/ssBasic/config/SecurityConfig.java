package com.ssBasic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    //Configuration One
/*    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity)throws Exception{
        return httpSecurity
                .authorizeHttpRequests()
                    .requestMatchers("/v1/index2").permitAll()
                    .anyRequest().authenticated()
                .and()
                .formLogin().permitAll()
                .and()
                .httpBasic()//permite enviar las credenciales en el header de la aplicacion
                .and()
                .build();
    }*/

    //Configuration two
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
                .authorizeHttpRequests( auth -> {
                        auth.requestMatchers("/v1/index2").permitAll();
                        auth.anyRequest().authenticated();
                })
                .formLogin()
                    .successHandler(successHandler())//url de redireccionamiento, despues del inicio de sesion
                    .permitAll()
                .and()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)//ALWAYS - IF_REQUIRED - NEVER - STATELESS
                    .invalidSessionUrl("/login")
                    .maximumSessions(1)
                    .expiredUrl("/login")
                    .sessionRegistry(sessionRegistry())
                .and()
                .sessionFixation()
                    .migrateSession()// migrateSession - newSession - none
                .and()
                .build();
    }
    @Bean
    public SessionRegistry sessionRegistry(){
        return new SessionRegistryImpl();// ayudara a obtener los datos de la session registry
    }

    public AuthenticationSuccessHandler successHandler(){
        return ((request, response, authentication) -> {
            response.sendRedirect("/v1/session");
        });
    }

}
