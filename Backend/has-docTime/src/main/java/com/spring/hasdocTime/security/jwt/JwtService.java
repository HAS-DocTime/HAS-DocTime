package com.spring.hasdocTime.security.jwt;

import com.spring.hasdocTime.security.customUserClass.UserDetailForToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${SECRET_KEY}")
    private String secretKey;

    @Value("${JWT_ACCESS_TOKEN_EXPIRATION}")
    private long jwtAccessTokenExpiration;

    @Value("${JWT_REFRESH_TOKEN_EXPIRATION}")
    private long jwtRefreshTokenExpiration;



    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserDetailForToken userDetailForToken){
        return generateToken(new HashMap<>(), userDetailForToken);
    }

    public String generateToken(Map<String, Object> extraClaims,UserDetailForToken userDetailForToken){
        return buildToken(extraClaims, userDetailForToken, this.jwtAccessTokenExpiration);
    }

    public String generateRefreashToken(UserDetailForToken userDetailForToken){
        return buildToken(new HashMap<>(), userDetailForToken, this.jwtRefreshTokenExpiration);
    }

    public String buildToken(Map<String, Object> extraClaims,UserDetailForToken userDetailForToken, long expiration){
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetailForToken.getEmail().toString())
                .claim("id", userDetailForToken.getId().toString())
                .claim("role", userDetailForToken.getRole().toString())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    public boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public Claims extractAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
