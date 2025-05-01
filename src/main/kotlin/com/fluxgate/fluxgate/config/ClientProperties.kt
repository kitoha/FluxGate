package com.fluxgate.fluxgate.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "client")
data class ClientProperties(
  val userService: EndpointProperties,
  val orderService: EndpointProperties,
  val notificationService: EndpointProperties,
) {
  data class EndpointProperties(
    val baseUrl: String
  )
}