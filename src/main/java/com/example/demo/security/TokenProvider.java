package com.example.demo.security;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.example.demo.model.UserEntity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TokenProvider {

    // Base64로 인코딩된 비밀 키
    private static final String SECRET_KEY = "FlRpX30pMqDbiAkmlfArbrmVkDD4RqISskGZmBFax5oGVxzXXWUzTR5JyskiHMIV9M1Oicegkpi46AdvrcX1E6CmTUBc6IFbTPiD";
    
    // Base64로 디코딩하여 Key 객체 생성
    private final Key key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(SECRET_KEY));

    public String create(UserEntity userEntity) {
        // 1일 유효기간 설정
        Date expiryDate = Date.from(
                Instant.now()
                        .plus(1, ChronoUnit.DAYS)
        );

        // JWT Token 생성
        return Jwts.builder()
                .setSubject(userEntity.getId())  // sub
                .setIssuer("demo app")           // iss
                .setIssuedAt(new Date())         // iat
                .setExpiration(expiryDate)       // exp
                .signWith(key)                   // Base64 디코딩된 key 사용
                .compact();						 // Base64 url_safe 버전을 반환
    }
    
    public String validateAndGetUserId(String token) {
        // JWT를 파싱하고 서명을 검증
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)            	// Base64 디코딩된 key 사용
                .build()                       	// 파서 빌드
                .parseClaimsJws(token)		   	// Jwt파싱 및 서명 검증
                .getBody();						// 페이로드를 반환

        return claims.getSubject();            	// JWT의 sub 클레임 반환
    }
}
