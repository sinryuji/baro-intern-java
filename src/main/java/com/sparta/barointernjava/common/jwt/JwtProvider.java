package com.sparta.barointernjava.common.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import java.security.Key;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtProvider {

    // Header KEY 값
    public static final String AUTHORIZATION_HEADER = "Authorization";
    // 사용자 권한 값의 KEY
    public static final String AUTHORIZATION_KEY = "auth";
    // Token 식별자
    public static final String BEARER_PREFIX = "Bearer ";

    @Value("${jwt.secret}")
    private String secretKey;
    @Value("${jwt.expiration.access}")
    private long accessTokenExpiration;
    private Key key;

    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    //로그 설정
    public static final Logger logger = LoggerFactory.getLogger("JWT 관련 로그");


    @PostConstruct
    public void init() {
        byte[] bytes = secretKey.getBytes();
        key = Keys.hmacShaKeyFor(bytes);
    }

    // JWT accessToken 생성
    public String generateAccessToken(String username, String role) {
        Date date = new Date();
        Date expireDate = new Date(date.getTime() + accessTokenExpiration);

        //암호화
        return Jwts.builder()
            .claim("sub", username)
            .claim("role", role)
            .setIssuedAt(date)
            .setExpiration(expireDate)
            .signWith(key, signatureAlgorithm)
            .compact();
    }

    // JWT 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException | SignatureException e) {
            logger.error("Invalid JWT Signature, 유효하지 않은 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            logger.error("Expired JWT Token, 만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            logger.error("Unsupported JWT token, 지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims is empty, 잘못된 JWT 토큰입니다.");
        }
        return false;
    }

    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

    // 토큰에서 username 추출
    public String getUsernameFromToken(String token) {
        Claims claims = getClaims(token);
        return (String) claims.get("sub");
    }

    public String getRolesFromToken(String token) {
        Claims claims = getClaims(token);
        return claims.get("role", String.class);
    }
}


