package com.galleryvote.config;

import javax.crypto.spec.SecretKeySpec;
import com.nimbusds.jose.jwk.source.ImmutableSecret;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.http.HttpMethod;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

@Configuration @EnableMethodSecurity
public class SecurityConfig {
 @Bean PasswordEncoder passwordEncoder(){return Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8();}
 @Bean JwtEncoder jwtEncoder(@Value("${galleryvote.jwt-secret}") String secret){return new NimbusJwtEncoder(new ImmutableSecret<>(new SecretKeySpec(secret.getBytes(),"HmacSHA256")));}
 @Bean JwtDecoder jwtDecoder(@Value("${galleryvote.jwt-secret}") String secret){return NimbusJwtDecoder.withSecretKey(new SecretKeySpec(secret.getBytes(),"HmacSHA256")).build();}
 @Bean SecurityFilterChain security(HttpSecurity http)throws Exception{return http
  .cors(c->c.configurationSource(r->{var x=new CorsConfiguration();x.addAllowedOrigin("http://localhost:4200");x.addAllowedHeader("*");x.addAllowedMethod("*");return x;}))
  .csrf(c->c.disable()).sessionManagement(s->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
  .authorizeHttpRequests(a->a
   .requestMatchers("/api/auth/**","/actuator/health","/v3/api-docs/**","/swagger-ui/**").permitAll()
   .requestMatchers(HttpMethod.GET,"/api/contests/**","/api/images/**").permitAll()
   .anyRequest().authenticated())
  .oauth2ResourceServer(o->o.jwt(j->j.jwtAuthenticationConverter(JwtSupport.authenticationConverter()))).build();}
}
