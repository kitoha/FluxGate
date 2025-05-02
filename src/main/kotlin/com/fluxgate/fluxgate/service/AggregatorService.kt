package com.fluxgate.fluxgate.service

import com.fluxgate.fluxgate.client.NotificationServiceClient
import com.fluxgate.fluxgate.client.OrderServiceClient
import com.fluxgate.fluxgate.client.UserServiceClient
import com.fluxgate.fluxgate.dto.AggregatedResponse
import com.fluxgate.fluxgate.dto.UserInfo
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class AggregatorService(
  private val userServiceClient: UserServiceClient,
  private val orderServiceClient: OrderServiceClient,
  private val notificationServiceClient: NotificationServiceClient
) {

  fun getAggregatedResponse(userId: String): Mono<AggregatedResponse>{
    val userMono = userServiceClient.getUserInfo(userId)
    val orderMono = orderServiceClient.getOrderInfo(userId).collectList()
    val notificationsMono = notificationServiceClient.getNotificationInfo(userId).collectList()

    return Mono.zip(userMono, orderMono, notificationsMono)
      .map { tuple ->
        AggregatedResponse(
          user = tuple.t1,
          orders = tuple.t2,
          notifications = tuple.t3
        )
      }
      .onErrorResume { _ ->
        Mono.just(
          AggregatedResponse(
            user = UserInfo(0, "Unknown", ""),
            orders = emptyList(),
            notifications = emptyList()
          )
        )
      }
  }

}