package yte.intern.application.usecases.manageevents.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class JwtUtil {
    public String generateToken(Authentication user,String secretKey,Integer expirationDay){
        return Jwts.builder()
                .setSubject(user.getName())
                .claim("authorities",getAuthorities(user))
                .setIssuedAt(new Date())
                .setExpiration(calculateExpirationDate(expirationDay))
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .compact();
    }
    private static List<String> getAuthorities(Authentication user){
       return user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
    }

    private static Date calculateExpirationDate(Integer expirationDay){
        Instant expirationTime= LocalDate.now()
                .plusDays(expirationDay)
                .atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant();
        return Date.from(expirationTime);
    }
    public static String extractUsername(String jwtToken, String secretKey){
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .build()
                .parseClaimsJws(jwtToken)
                .getBody()
                .getSubject();
    }
}
