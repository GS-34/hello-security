package kr.co.gs.application.config;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

  @Override
  public void commence(
      HttpServletRequest request,
      HttpServletResponse response,
      AuthenticationException e) throws IOException {

    log.error("Responding with unauthorized error. Message - {}", e.getMessage());

    String unAuthorizationCode = (String) request.getAttribute("unauthorization");

    request.setAttribute("response.failure.code", unAuthorizationCode);
    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, unAuthorizationCode);
  }
}