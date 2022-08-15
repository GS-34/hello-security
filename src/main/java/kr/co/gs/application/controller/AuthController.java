package kr.co.gs.application.controller;

import kr.co.gs.application.config.JwtTokenProvider;
import kr.co.gs.application.dto.TokenRequest;
import kr.co.gs.application.dto.TokenResponse;
import kr.co.gs.application.service.UserService;
import kr.co.gs.application.service.UserService.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

  @Autowired
  private final UserService userService;

  @PostMapping("/login")
  public TokenResponse login(@RequestBody TokenRequest request) {

    User user = userService.get(request.getId()).orElseThrow(() -> new IllegalArgumentException("없는 사용자입니다."));

    if (!request.getPw().equals(user.getPw())) {
      throw new IllegalArgumentException("비밀번호를 확인하세요.");
    }

    String token = JwtTokenProvider.generateToken(user.getId());

    return TokenResponse.builder().token(token).build();
  }

  @PostMapping("/test")
  public TokenResponse test() {
    return TokenResponse.builder().token("test").build();
  }
}
