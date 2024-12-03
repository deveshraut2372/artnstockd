package com.zplus.ArtnStockMongoDB.config.security.jwt;

//import com.zplus.ArtnStockMongoDB.service.security.UserDetailsImpl;
import io.imagekit.sdk.exceptions.UnauthorizedException;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.security.Keys;
import org.springframework.web.client.HttpClientErrorException;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {
  private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

  @Value("${bezkoder.app.jwtSecret}")
  private String jwtSecret;



  @Value("${bezkoder.app.jwtExpirationMs}")
  private int jwtExpirationMs;

  private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
  private final long validityInMilliseconds = 86400000; // 1 hour 100000  3600000 /// 9000000;


  public String generateToken(String username) {
    Date now = new Date();
    Date expiryDate = new Date(now.getTime() + validityInMilliseconds);

    return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(now)
            .setExpiration(expiryDate)
            .signWith(key, SignatureAlgorithm.HS512)
            .compact();
  }

public String generateShortToken(String username)
{

  Date now =new Date();
  Date expireDate=new Date(now.getTime()+validityInMilliseconds);
  return Jwts.
          builder().
          setSubject(username)
          .setIssuedAt(now)
          .setExpiration(expireDate)
          .signWith(key,SignatureAlgorithm.HS512)
          .compact();
}

  public boolean validateToken(String token) {
    try {
      Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
      return true;
    } catch (Exception e) {
      return false;
//      throw new RuntimeException(" Please Login again ");
    }
  }

  public String getUsernameFromToken(String token) {
    Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    return claims.getSubject();
  }

  public String getUserNameFromJwtToken1(String token) {
    return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody().getSubject();
  }



  //////////////

//  public String generateJwtToken(String userName) {
//    return generateTokenFromUsername(userName);
//  }
//
//  public String generateTokenFromUsername(String email) {
//    return Jwts.builder().setSubject(email).setIssuedAt(new Date())
//        .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs)).signWith(SignatureAlgorithm.HS512, jwtSecret)
//        .compact();
//  }



// public Key getKey()
//  {
//    SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
//    return key;
//  }

  public String getUserNameFromJwtToken(String token) {
    return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
  }

//  public boolean validateJwtToken(String authToken) {
//    try {
//      Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
//      return true;
//    } catch (SignatureException e) {
//      logger.error("Invalid JWT signature: {}", e.getMessage());
//    } catch (MalformedJwtException e) {
//      logger.error("Invalid JWT token: {}", e.getMessage());
//    } catch (ExpiredJwtException e) {
//      logger.error("JWT token is expired: {}", e.getMessage());
//    } catch (UnsupportedJwtException e) {
//      logger.error("JWT token is unsupported: {}", e.getMessage());
//    } catch (IllegalArgumentException e) {
//      logger.error("JWT claims string is empty: {}", e.getMessage());
//    }
//    return false;
//  }

}
