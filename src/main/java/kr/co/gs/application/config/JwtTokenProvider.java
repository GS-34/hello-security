package kr.co.gs.application.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtTokenProvider {

  private static final String JWT_SECRET = "1111111111222222222233333333334444444444555555555566666666667777777777";

  // 토큰 유효시간
  private static final int JWT_EXPIRATION_MS = 604800000;

  // jwt 토큰 생성
  public static String generateToken(String data) {

    Date now = new Date();
    Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION_MS);

    return Jwts.builder()
        .setSubject(data) // 사용자
        .setIssuedAt(new Date()) // 현재 시간 기반으로 생성
        .setExpiration(expiryDate) // 만료 시간 세팅
        .signWith(SignatureAlgorithm.HS256, JWT_SECRET) // 사용할 암호화 알고리즘, signature에 들어갈 secret 값 세팅
        .compact();
  }

  // Jwt 토큰에서 아이디 추출
  public static String getUserIdFromJWT(String token) {
    Claims claims = Jwts.parser()
        .setSigningKey(JWT_SECRET)
        .parseClaimsJws(token)
        .getBody();

    return claims.getSubject();
  }

  // Jwt 토큰 유효성 검사
  public static boolean validateToken(String token) {
    try {
      Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token);
      return true;
    } catch (SignatureException ex) {
      log.error("Invalid JWT signature");
    } catch (MalformedJwtException ex) {
      log.error("Invalid JWT token");
    } catch (ExpiredJwtException ex) {
      log.error("Expired JWT token");
    } catch (UnsupportedJwtException ex) {
      log.error("Unsupported JWT token");
    } catch (IllegalArgumentException ex) {
      log.error("JWT claims string is empty.");
    }
    return false;
  }


}