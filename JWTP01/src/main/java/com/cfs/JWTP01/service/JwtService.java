package com.cfs.JWTP01.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private Long jwtExpirationTime;

    //generate Jwt token

    public String generateToken(String username){
        return generateToken(new HashMap<>(),username);
    }

    private String generateToken(Map<String, Object> extraClaims, String username) {
        return Jwts.builder()
                .claims(extraClaims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+jwtExpirationTime))
                .signWith(getSingKey())
                .compact();
    }
    public String extractUserName(String token){
        return extractClaim(token, Claims :: getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims,T> claimsTFunction) {
        final Claims claims = extaractAllClaims(token);
        return claimsTFunction.apply(claims);
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    public Date extractExpiration(String token){
        return extractClaim(token,Claims :: getExpiration);
    }

    private Claims extaractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSingKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSingKey(){
        byte[] bytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(bytes);
    }
    public Long getJwtExpirationTime(){
        return jwtExpirationTime;
    }
}
