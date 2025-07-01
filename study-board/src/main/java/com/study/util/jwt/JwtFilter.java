package com.study.util.jwt;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.study.user.domain.CustomUserDetails;
import com.study.user.domain.UserEntity;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter{

	private final JwtUtil jwtUtil;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		//http header에서 Authorization 찾기
		String authorization = request.getHeader("Authorization");
		
		//JWT 방식이 아닌 경우 다음 필터 실행되게 하고 지금 필터는 종료
		if(authorization != null && authorization.startsWith("Bearer")) {
			//JWT 부분만 추출하여 username, role 추출
			String token = authorization.split(" ")[1];
			String username = jwtUtil.extractUsername(token);
			String role = jwtUtil.extractRole(token);
			try {
				if(jwtUtil.validateToken(token, username)) {
					UserEntity userEntity = UserEntity.builder().username(username).role(role).build();
					CustomUserDetails customUserDetails = new CustomUserDetails(userEntity);
					//스프링 시큐리티 인증 토큰 생성
					Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
					//세션에 사용자 등록
					SecurityContextHolder.getContext().setAuthentication(authToken);
				}
			} catch (Exception e) {
				System.out.println("token is invalid");
				filterChain.doFilter(request, response);
				return;
			}
		}
		filterChain.doFilter(request, response);
	}
}
