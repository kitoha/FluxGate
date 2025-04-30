package com.fluxgate.fluxgate.client

import com.fluxgate.fluxgate.dto.UserInfo
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Component
class UserServiceClient (
  private val webClient: WebClient
){
  private val baseUrl = "http://localhost:8081" // wireMock 주소 (가짜 응답 객체로 사용할 예정)

  fun getUserInfo(userId: String): Mono<UserInfo>{
    return webClient
      .get()
      .uri("$baseUrl/user-info/{userId}", userId)
      .retrieve()
      .bodyToMono(UserInfo::class.java)
  }
}