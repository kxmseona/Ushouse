package com.example.demo.Security;

import com.example.demo.Entity.Authority;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.*;

@RequiredArgsConstructor
@Component
public class JwtProvider {

	
	@Value("${jwt.secret.key}")
	private String salt;
	
	private Key secretKey;
	
	private final long expiredTime = 1000l * 60 * 60;
	
	private final JpaUserDetailsService userDetailsService;
	
	@PostConstruct
	protected void init() {
		secretKey = Keys.hmacShaKeyFor(salt.getBytes(StandardCharsets.UTF_8));
	}
	
	
	// 토큰 생성
    public String createToken(String memberEmail, List<Authority> roles) {
        Claims claims = Jwts.claims().setSubject(memberEmail);
        claims.put("roles", roles);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + expiredTime))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }
    //권한정보 획득
    // Spring Security 인증과정에서 권한확인을 위한 기능
    public Authentication getAuthentication(String token) {
    	UserDetails userDetails = userDetailsService.loadUserByUsername(this.getMemberEmail(token));
    	return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    	
    }
    
    // 토근에 담겨있는 memberEmail 꺼내기
    public String getMemberEmail(String token) {
    	return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().getSubject();
    }
    
    
    
    
    
    public String resolveToken(HttpServletRequest request) {
    	return request.getHeader("Authorization");
    }
    
    // 토큰 검증
    public boolean validateToken(String token) {
    	try {
    		if(!token.startsWith("Bearer ")) {
        		return false;
        	}else {
        		token = token.split(" ")[1].trim();
        		}
        	Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
        	// token이 만료되었을 시 false
        	return !claims.getBody().getExpiration().before(new Date());
    	}catch(Exception e) {
    		return false;
    	}
    	
    }
}
