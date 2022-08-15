package kr.co.gs.application.service;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.Builder;
import lombok.Getter;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final Map<String, User> userRepository;

  public UserService() {
    userRepository = List.of(
            User.builder()
                .id("wally")
                .pw("pw")
                .build())
        .stream().collect(toMap(User::getId, identity()));
  }

  public Optional<User> get(String id) {
    return Optional.ofNullable(userRepository.get(id));
  }


  @Builder
  @Getter
  public static class User {

    private String id;
    private String pw;
  }

}


