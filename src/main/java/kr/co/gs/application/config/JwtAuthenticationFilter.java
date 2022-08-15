package kr.co.gs.application.config;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {


  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    try {
      String jwt = getJwtFromRequest(request); //request에서 jwt 토큰을 꺼낸다.
      if (StringUtils.hasText(jwt) && JwtTokenProvider.validateToken(jwt)) {
        String userId = JwtTokenProvider.getUserIdFromJWT(jwt); //jwt에서 사용자 id를 꺼낸다.

        UserAuthentication authentication = new UserAuthentication(userId, null, null); //id를 인증한다.
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); //기본적으로 제공한 details 세팅

        SecurityContextHolder.getContext()
            .setAuthentication(authentication);
      } else {
        if (!StringUtils.hasText(jwt)) {
          request.setAttribute("unauthorization", "401 인증키 없음.");
        }

        if (!JwtTokenProvider.validateToken(jwt)) {
          request.setAttribute("unauthorization", "401-001 인증키 만료.");
        }
      }
    } catch (Exception ex) {
      logger.error("Could not set user authentication in security context", ex);
    }

    filterChain.doFilter(request, response);
  }

  private String getJwtFromRequest(HttpServletRequest request) {
    String bearerToken = request.getHeader("Authorization");
    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring("Bearer ".length());
    }
    return null;
  }
}