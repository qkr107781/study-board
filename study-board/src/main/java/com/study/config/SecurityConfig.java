package com.study.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.study.util.jwt.JwtFilter;
import com.study.util.jwt.JwtUtil;
import com.study.util.jwt.LoginFilter;

import lombok.RequiredArgsConstructor;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration; 
    private final JwtUtil jwtUtil;
    
    @Bean
    protected AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
    	return configuration.getAuthenticationManager();
    }

    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    	//csrf 미사용
    	http.csrf((auth) -> auth.disable());
    	
    	//Form 로그인 방식 미사용
    	http.formLogin((auth) -> auth.disable());
    	
    	//basic 인증 방식 미사용
    	http.httpBasic((auth) -> auth.disable());
    	
    	
    	//JwtFilter 적용
        http.addFilterBefore(new JwtFilter(jwtUtil), LoginFilter.class);
    	
    	//JwtAuthenticationFilter 적용
    	http.addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration),jwtUtil),UsernamePasswordAuthenticationFilter.class);
    	
    	//인가여부 확인
    	http.authorizeHttpRequests((auth) -> auth.requestMatchers("/login/**").permitAll()
    			.requestMatchers("/api/user/**").permitAll()
    			.requestMatchers("/api/board/**").hasRole("USER")//ROLE_를 prefix로 붙여서 권한 확인함
    			.anyRequest().authenticated());
    	
    	//세션 설정
    	http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));//세션 사용 안 함
    	
        return http.build();
    }
    
    @Bean
    protected BCryptPasswordEncoder bCryptPasswordEncoder() {
    	return new BCryptPasswordEncoder();
    }
}
