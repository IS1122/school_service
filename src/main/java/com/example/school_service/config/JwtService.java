package com.example.school_service.config;

import com.example.school_service.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRET_KEY="6a424d564ab210f084cd01afa295eb06f9ff434b59a8541e1ab0eaf50ec70a47";
    public String getUsrname(String jwtToken){
        return getClaims(jwtToken,Claims::getSubject);
    }
    public <T> T getClaims(String jwtToken, Function<Claims,T> claimsResolver){
        final Claims claims = getAllClaims(jwtToken);
        return claimsResolver.apply(claims);
    }
    private Claims getAllClaims(String jwtToken){
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();
    }

    public String generateToken(String userName){
        Map<String,Object> claims=new HashMap<>();
        return createToken(claims,userName);
    }
    private String createToken(Map<String, Object> claims, String userName) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*30))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256).compact();
    }
    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username=getUsrname(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return getExpiration(token).before(new Date());
    }

    private Date getExpiration(String token) {
        return getClaims(token, Claims::getExpiration);
    }

    private Key getSignInKey() {
        byte [] key= Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(key);
    }


}
