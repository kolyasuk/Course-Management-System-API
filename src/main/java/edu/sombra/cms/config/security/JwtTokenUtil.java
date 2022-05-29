package edu.sombra.cms.config.security;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

import static java.lang.String.format;

@Component
@RequiredArgsConstructor
public class JwtTokenUtil {

    @Value("${cms-api.jwt.secret}")
    private String jwtSecret;
    
    @Value("${cms-api.jwt.tokenIssuer}")
    private String tokenIssuer;

    @Value("${cms-api.jwt.expirationTimeMs}")
    private int accessTokenExpirationTimeMs;

    @Value("${cms-api.jwt.refreshExpirationTimeMs}")
    private int refreshTokenExpirationTimeMs;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenUtil.class);

    public String generateAccessToken(UserDetailsImpl userDetails) {
        return Jwts.builder()
                .setSubject(format("%s,%s", userDetails.getId(), userDetails.getUsername()))
                .claim("role", userDetails.getRole())
                .setIssuer(tokenIssuer)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpirationTimeMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String generateRefreshToken(UserDetailsImpl userDetails) {
        return Jwts.builder()
                .setSubject(format("%s,%s", userDetails.getId(), userDetails.getUsername()))
                .setIssuer(tokenIssuer)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpirationTimeMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }


    public String getUserId(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject().split(",")[0];
    }

    public String getUsername(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject().split(",")[1];
    }

    public String getRole(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return claims.get("role", String.class);
    }

    public boolean containsRole(String token){
        String role = getRole(token);
        return role != null && !role.isEmpty();
    }

    public Date getExpirationDate(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getExpiration();
    }

    public boolean validate(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            LOGGER.error("Invalid JWT signature - {}", ex.getMessage());
        } catch (MalformedJwtException ex) {
            LOGGER.error("Invalid JWT token - {}", ex.getMessage());
        } catch (ExpiredJwtException ex) {
            LOGGER.error("Expired JWT token - {}", ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            LOGGER.error("Unsupported JWT token - {}", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            LOGGER.error("JWT claims string is empty - {}", ex.getMessage());
        }
        return false;
    }

}
