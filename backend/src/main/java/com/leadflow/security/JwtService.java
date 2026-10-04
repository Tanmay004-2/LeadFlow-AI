package com.leadflow.security;

     import com.leadflow.user.entity.User;
     import io.jsonwebtoken.Claims;
     import io.jsonwebtoken.Jwts;
     import io.jsonwebtoken.security.Keys;
     import jakarta.annotation.PostConstruct;
     import org.springframework.beans.factory.annotation.Value;
     import org.springframework.core.env.Environment;
     import org.springframework.stereotype.Service;

     import java.security.Key;
     import java.util.Date;
     import java.util.HashMap;
     import java.util.Map;
     import java.util.function.Function;

     @Service
     public class JwtService {

           @Value("${app.jwt.secret:defaultSecretKeyThatIsAtLeast32BytesLongForHS256Algorithm}")
           private String jwtSecret;

           private final Environment env;
           private final long JWT_EXPIRATION = 86400000; // 1 day
           private final long CUSTOMER_JWT_EXPIRATION = 3600000; // 1 hour

           public JwtService(Environment env) {
               this.env = env;
           }

           @PostConstruct
           public void init() {
               if (env.acceptsProfiles(org.springframework.core.env.Profiles.of("prod"))
                       && "defaultSecretKeyThatIsAtLeast32BytesLongForHS256Algorithm".equals(jwtSecret)) {
                   throw new IllegalStateException("JWT Secret must be configured in production!");
               }
           }

           private Key getSigningKey() {
               return Keys.hmacShaKeyFor(jwtSecret.getBytes());
           }

           public String generateToken(User user) {
               Map<String, Object> claims = new HashMap<>();
               claims.put("userId", user.getId().toString());
               if (user.getAccountId() != null) claims.put("accountId", user.getAccountId().toString());
               if (user.getBusinessId() != null) claims.put("businessId", user.getBusinessId().toString());






                long expiration = user.getRole().name().equals("CUSTOMER") ? CUSTOMER_JWT_EXPIRATION : JWT_EXPIRATION;
                return createToken(claims, user.getEmail() != null ? user.getEmail() : user.getId().toString(), user.getRole().name(), expiration);
           }

           private String createToken(Map<String, Object> claims, String subject, String role, long expirationMillis) {
               return Jwts.builder()
                       .setClaims(claims)
                       .setSubject(subject)
                       .claim("role", role)
                       .setIssuedAt(new Date(System.currentTimeMillis()))
                       .setExpiration(new Date(System.currentTimeMillis() + expirationMillis))
                       .signWith(getSigningKey())
                       .compact();
           }

           public String extractUsername(String token) {
               return extractClaim(token, Claims::getSubject);
           }

           public Claims extractAllClaims(String token) {
               return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
           }

           public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
               final Claims claims = extractAllClaims(token);
               return claimsResolver.apply(claims);
           }

           public boolean isTokenValid(String token) {
               try {
                   return !isTokenExpired(token);
               } catch (Exception e) {
                   return false;
               }
           }

           private boolean isTokenExpired(String token) {
               return extractExpiration(token).before(new Date());
           }

           private Date extractExpiration(String token) {
               return extractClaim(token, Claims::getExpiration);
           }
     }
