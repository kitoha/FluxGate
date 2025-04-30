package com.fluxgate.fluxgate.client

import com.fluxgate.fluxgate.config.ClientProperties
import com.fluxgate.fluxgate.dto.NotificationInfo
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux

@Component
class NotificationServiceClient (
  private val webClient: WebClient,
  private val clientProperties: ClientProperties
){

  fun getNotificationInfo(userId: String): Flux<NotificationInfo>{
    val baseUrl = clientProperties.notificationService.baseUrl
    return webClient
      .get()
      .uri("$baseUrl/notification-info/{userId}", userId)
      .retrieve()
      .bodyToFlux(NotificationInfo::class.java)
  }
}