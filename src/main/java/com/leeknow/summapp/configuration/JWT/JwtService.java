package com.leeknow.summapp.configuration.JWT;

import com.leeknow.summapp.entity.RefreshToken;
import com.leeknow.summapp.entity.User;
import com.leeknow.summapp.repository.RefreshTokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${token.signing.key}")
    private String key;

    public String generateToken(UserDetails userDetails) {
        return Jwts
                .builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + (24 * 60 * 60 * 1000))) // 24 часа
                .signWith(getSigningKey())
                .compact();
    }

    public void generateRefreshToken(User user) {
        String token = Jwts
                .builder()
                .subject(user.getEmail())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + (30L * 24 * 60 * 60 * 1000))) // 30 дней
                .signWith(getSigningKey())
                .compact();
        createRefreshToken(token, user);
    }

    private void createRefreshToken(String token, User user) {
        refreshTokenRepository.deleteAllByUser(user);

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setRefreshTokenId(UUID.randomUUID().toString());
        refreshToken.setToken(token);
        refreshToken.setUser(user);
        refreshToken.setCreationDate(new Timestamp(System.currentTimeMillis()));
        refreshToken.setFinishDate(new Timestamp(System.currentTimeMillis() + (30L * 24 * 60 * 60 * 1000)));
        refreshTokenRepository.save(refreshToken);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private <T> T extractClaim(String token, Function<Claims, T> claims) {
        Claims allClaims = extractAllClaims(token);
        return claims.apply(allClaims);
    }

    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        return userDetails.getUsername().equals(extractEmail(token)) &&
                (new Date().before(extractExpiration(token)));
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(key);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
