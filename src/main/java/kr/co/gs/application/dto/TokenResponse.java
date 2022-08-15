package kr.co.gs.application.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TokenResponse {

  private String token;
}
