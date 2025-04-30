package com.fluxgate.fluxgate.client

import com.fluxgate.fluxgate.dto.OrderInfo
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component
class OrderServiceClient (
  private val webClient: WebClient
){

  private val baseUrl = "http://localhost:8081"

  fun getOrderInfo(userId : String) : Flux<OrderInfo>{
    return webClient
      .get()
      .uri("$baseUrl/order-info/{userId}", userId)
      .retrieve()
      .bodyToFlux(OrderInfo::class.java)
  }
}