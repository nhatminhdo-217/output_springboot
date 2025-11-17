package nhatm.project.demo.jwt.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import nhatm.project.demo.jwt.model.RefreshToken;
import nhatm.project.demo.jwt.model.User;
import nhatm.project.demo.jwt.repository.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@Data
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    @Value("${security.jwt.refresh-key}")
    private String refreshKey;

    @Value("${security.jwt.refresh-expiration}")
    private Long refreshExpiration;


    public String generateRefreshToken(UserDetails userDetails) {
        return generateRefreshToken(new HashMap<>(), userDetails);
    }

    public String  generateRefreshToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return buildRefreshToken(extraClaims, userDetails, refreshExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String buildRefreshToken(Map<String, Object> extraClaims, UserDetails userDetails, Long jwtExpiration) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() +  jwtExpiration))
                .signWith(getRefreshKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getRefreshKey())
                .build()
                .parseClaimsJws(token).getBody();
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Key getRefreshKey() {
        byte[] keyBytes = Decoders.BASE64.decode(refreshKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public void addRefreshToken(String refreshToken, User loginUser) {
        RefreshToken refreshTokenModel = RefreshToken.builder()
                .token(refreshToken)
                .expiryDate((long) (30*60*60*24))
                .revoked(false)
                .user(loginUser)
                .build();

        refreshTokenRepository.save(refreshTokenModel);
    }

    public RefreshToken getRefreshTokenByToken(String refreshToken) {
        return refreshTokenRepository.findByToken(refreshToken);
    }

    public void deleteFreshToken(String refreshToken) {
        RefreshToken refreshTokenModel = refreshTokenRepository.findByToken(refreshToken);

        if (refreshTokenModel == null) {
            return;
        }

        refreshTokenRepository.delete(refreshTokenModel);
    }
}
