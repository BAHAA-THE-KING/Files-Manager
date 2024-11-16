package com.w.ever.files.manager.utiles;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

@Component
public class JwtTokenUtil {

    private final String SECRET_KEY = "8b3f2e94f3c9dceae9a0d3bfe68b95e7104d3b82ab5c5c57c2c94d32791b0842";
    private final long EXPIRATION_TIME = 1000 * 60 * 60; // 1 hour

    public String generateToken(Integer id) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, 1); // Add 1 month to the current date
        Date expirationDate = calendar.getTime(); // Get the expiration date
        return Jwts.builder()
                .setSubject(id.toString())
                .setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }
    public boolean isTokenValid(String token, Integer userId) {
        return !isTokenExpired(token) && userId.equals(extractUserId(token));
    }

    public Integer extractUserId(String token) {
        return Integer.parseInt(extractAllClaims(token).getSubject());
    }
    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }
}
