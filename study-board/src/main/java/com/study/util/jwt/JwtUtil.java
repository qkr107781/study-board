package com.study.util.jwt;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

	private final Key secretKey;
	private final long exprationDate;
	
	public JwtUtil() {
		this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode("spring-study-board-secret-key"));
		this.exprationDate = 86400 * 1000;
	}

	public String createToken(String username) {
		Date validDate = new Date(new Date().getTime() + exprationDate);
		return Jwts.builder()
					.subject("auth")
					.claim("username", username)
					.signWith(secretKey)
					.expiration(validDate)
					.compact();
	}
	
	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(secretKey).build().parseClaimsJws(token);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return false;
	}
}
