package com.quangtruong.be.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class JwtProvider {

    SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes(StandardCharsets.UTF_8));

    public String generateToken(Authentication auth) {
        String role = "";
        if (auth.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
            role = populateAuthorities(authorities);
        }

        return Jwts.builder()
                .setIssuedAt(new Date())
                .setSubject(auth.getName()) // Set subject here
                .setExpiration(new Date(new Date().getTime() + 86400000)) // Correct the expiration time calculation
                .claim("email", auth.getName())
                .claim("authorities", role) // Populate authorities into the JWT
                .signWith(key)
                .compact();
    }

    public String getEmailFromToken(String jwt) {
        if (jwt != null && jwt.startsWith("Bearer ")) {
            jwt = jwt.substring(7);
            Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
            return claims.get("email", String.class);
        }
        return null;
    }

    private String populateAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Set<String> auths = new HashSet<>();
        for (GrantedAuthority authority : authorities) {
            auths.add(authority.getAuthority());
        }
        return String.join(",", auths);
    }
}