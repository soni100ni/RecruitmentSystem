package com.company.recruitmentsystem.secuity;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	@Value("${jwt.secret}")
	private String jwtSecret;

	@Value("${jwt.expirationMs}")
	private Long jwtExpirationMs;

	// Convert secret string to a secure SecretKey
	private SecretKey getSigningKey() {
		return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
	}

	public String generateToken(String email) {
		return Jwts.builder().setSubject(email).setIssuedAt(new Date())
				.setExpiration(
						new Date(System.currentTimeMillis() + jwtExpirationMs))
				.signWith(getSigningKey(), SignatureAlgorithm.HS512) // Use
																		// SecretKey
																		// instead
																		// of
																		// String
				.compact();
	}

	public String extractEmail(String token) {
		return getClaims(token).getSubject();
	}

	public boolean validateToken(String token, String email) {
		String tokenEmail = extractEmail(token);
		return tokenEmail.equals(email) && !isTokenExpired(token);
	}

	private boolean isTokenExpired(String token) {
		return getClaims(token).getExpiration().before(new Date());
	}

	private Claims getClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(getSigningKey()) // Use
																	// SecretKey
																	// here too
				.build().parseClaimsJws(token).getBody();
	}
}
