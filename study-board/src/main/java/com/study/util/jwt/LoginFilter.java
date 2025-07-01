package com.study.util.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.Iterator;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.study.user.domain.CustomUserDetails;

@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

	private final AuthenticationManager authenticationManager;
	private final JwtUtil jwtUtil;
	
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
    	UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(obtainUsername(request), obtainPassword(request), null);
    	return authenticationManager.authenticate(authToken);
    }
    
	//로그인 성공시 실행하는 메소드 (여기서 JWT를 발급)
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) {
    	System.out.println("로그인 성공");
		
		//UserDetails
		CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
		
		String username = customUserDetails.getUsername();
		
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
		GrantedAuthority auth = iterator.next();
		
		String role = auth.getAuthority();
		
		String token = jwtUtil.generateToken(username,role);
		
		response.addHeader("Authorization", "Bearer " + token);
    }

		//로그인 실패시 실행하는 메소드
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
    	System.out.println("로그인 실패");
    	//로그인 실패시 401 응답 코드 반환
        response.setStatus(401);
    }
}
