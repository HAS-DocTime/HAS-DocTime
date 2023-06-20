package com.spring.hasdoctime.security.jwt;

import com.spring.hasdoctime.security.customuserclass.UserDetailForToken;
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
import java.util.Optional;
import java.util.function.Function;

@Service
public class JwtService {
    @Value("${jwt.secret.key}")
    final String SECRET_KEY = null;

    @Value("${jwt.expiration.time.in.millis}")
    final Optional<Integer> EXPIRATION_TIME = Optional.empty();

    /**
     * Extracts the username from the JWT token.
     *
     * @param token  the JWT token
     * @return the username extracted from the token
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extracts a specific claim from the JWT token.
     *
     * @param token           the JWT token
     * @param claimsResolver  the function to resolve the desired claim
     * @param <T>             the type of the claim
     * @return the extracted claim
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Generates a JWT token for the given user details.
     *
     * @param userDetailForToken  the user details for token generation
     * @return the generated JWT token
     */
    public String generateToken(UserDetailForToken userDetailForToken){
        return generateToken(new HashMap<>(), userDetailForToken);
    }

    /**
     * Generates a JWT token with additional claims for the given user details.
     *
     * @param extraClaims         the additional claims to include in the token
     * @param userDetailForToken  the user details for token generation
     * @return the generated JWT token
     */
    public String generateToken(Map<String, Object> extraClaims,UserDetailForToken userDetailForToken){
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetailForToken.getEmail().toString())
                .claim("id", userDetailForToken.getId().toString())
                .claim("role", userDetailForToken.getRole().toString())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME.get()))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Checks if the JWT token is valid for the given user details.
     *
     * @param token         the JWT token
     * @param userDetails  the user details
     * @return true if the token is valid, false otherwise
     */
    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    /**
     * Checks if the JWT token has expired.
     *
     * @param token  the JWT token
     * @return true if the token has expired, false otherwise
     */
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
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
