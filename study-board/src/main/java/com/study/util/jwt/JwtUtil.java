package com.study.util.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * JWT(JSON Web Token) 생성 및 검증을 위한 유틸리티 클래스.
 */
@Component
public class JwtUtil {

	@Value("${jwt.secret.key}")
	private String secretKeyBase64;
	
	// JWT 서명을 위한 비밀 키 (BASE64 인코딩된 문자열, 최소 256비트 이상 권장)
	// 실제 운영 환경에서는 환경 변수, Vault 또는 다른 보안된 방법으로 관리해야함
    private Key secretKey;

    // 토큰 유효 시간 (밀리초 단위)
    private long expirationMs;

    /**
     * JwtUtil 생성자.
     * @param secretKeyBase64 비밀 키를 BASE64로 인코딩한 문자열
     * @param expirationDays 토큰 만료 시간 (일 단위)
     */
    @PostConstruct
    public void init() {
        // BASE64로 인코딩된 문자열을 Key 객체로 변환
        String decodeSecretKeyBase64 = new String(Base64.getUrlDecoder().decode(secretKeyBase64), StandardCharsets.UTF_8);
        this.secretKey = Keys.hmacShaKeyFor(decodeSecretKeyBase64.getBytes());
        this.expirationMs = 1 * 24 * 60 * 60 * 1000; // 일 -> 밀리초 변환
    }

    /**
     * 사용자 이름을 기반으로 JWT 토큰을 생성합니다.
     * @param username 토큰에 포함될 사용자 이름 (sub -> subject)
     * @return 생성된 JWT 토큰 문자열
     */
    public String generateToken(String username,String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("iss", "study-board");//토큰 발급자
        claims.put("sub", username);//토큰 제목(사용자 고유값)
        claims.put("iat", new Date());//토큰 발급 시간
        claims.put("exp", expirationMs);//토큰 만료 시간
        claims.put("roles", role);//권한
        return createToken(claims, username);
    }

    /**
     * 주어진 클레임과 주체(subject)로 JWT 토큰을 생성하는 내부 메소드.
     * @param claims 토큰에 포함될 클레임 맵
     * @param subject 토큰의 주체 (일반적으로 사용자 이름 또는 ID)
     * @return 생성된 JWT 토큰 문자열
     */
    private String createToken(Map<String, Object> claims, String subject) {
        Date now = new Date(System.currentTimeMillis());
        Date expiryDate = new Date(now.getTime() + expirationMs);
        
        return Jwts.builder()
                .setClaims(claims)//클레임 설정 (페이로드)
                .setSubject(subject)//토큰의 주체 (보통 사용자 ID 또는 이름)
                .setIssuedAt(now)//토큰 발행 시간
                .setExpiration(expiryDate)//토큰 만료 시간
                .signWith(secretKey, SignatureAlgorithm.HS256)//서명 알고리즘 및 비밀 키
                .compact();//JWT 문자열 생성
    }

	private Claims extractAllClaims(String token){
		return Jwts.parserBuilder()
                    .setSigningKey(secretKey)//서명 키 설정
                    .build()
                    .parseClaimsJws(token)//토큰 파싱 및 서명 검증
                    .getBody();//클레임 추출
	}
	
	public String extractUsername(String token){
		return extractAllClaims(token).getSubject();
	}
	
	public String extractRole(String token){
		return (String) extractAllClaims(token).get("roles");
	}
	
    /**
     * 토큰의 유효성을 검증합니다.
     * 사용자 이름이 일치하고 토큰이 만료되지 않았는지 확인
     *
     * @param token 검증할 JWT 토큰 문자열
     * @param username 토큰과 비교할 사용자 이름
     * @exception ExpiredJwtException: 만료된 토큰
     * @exception MalformedJwtException: 유효하지 않은 형식의 토큰
     * @exception SignatureException: 유효하지 않은 서명
     * @exception UnsupportedJwtException: 지원하지 않는 형식의 토큰
     * @exception IllegalArgumentException: 비어있는 페이로드
     * @return 토큰이 유효하면 true, 그렇지 않으면 false
     */
    public Boolean validateToken(String token, String username) {
        try {
        	Claims claims = extractAllClaims(token);
        	
        	String claimUsername = claims.getSubject();
        	Date claimExpirationDate = claims.getExpiration();
        	
            return (username.equals(claimUsername) && claimExpirationDate.after(new Date()));
        } catch (ExpiredJwtException e) {
            System.err.println("JWT token is expired: " + e.getMessage());
            return false;
        } catch (MalformedJwtException e) {
            System.err.println("Invalid JWT token: " + e.getMessage());
            return false;
        } catch (SignatureException e) {
            System.err.println("Invalid JWT signature: " + e.getMessage());
            return false;
        } catch (UnsupportedJwtException e) {
            System.err.println("Unsupported JWT token: " + e.getMessage());
            return false;
        } catch (IllegalArgumentException e) {
            System.err.println("JWT claims string is empty: " + e.getMessage());
            return false;
        }
    }
}

