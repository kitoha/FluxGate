package com.fluxgate.fluxgate.service

import com.fluxgate.fluxgate.client.NotificationServiceClient
import com.fluxgate.fluxgate.client.OrderServiceClient
import com.fluxgate.fluxgate.client.UserServiceClient
import com.fluxgate.fluxgate.config.ClientProperties
import com.fluxgate.fluxgate.dto.AggregatedResponse
import com.fluxgate.fluxgate.dto.NotificationInfo
import com.fluxgate.fluxgate.dto.OrderInfo
import com.fluxgate.fluxgate.dto.UserInfo
import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching
import com.github.tomakehurst.wiremock.client.WireMock.get
import com.github.tomakehurst.wiremock.client.WireMock.okJson
import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import org.springframework.web.reactive.function.client.WebClient
import reactor.test.StepVerifier

class AggregatorServiceSpec : StringSpec({

  val wireMock = WireMockServer(WireMockConfiguration.options().dynamicPort())
    .apply { start() }

  val webClient = WebClient.builder().build()
  val clientProps = ClientProperties(
    userService = ClientProperties.EndpointProperties("http://localhost:${wireMock.port()}"),
    orderService = ClientProperties.EndpointProperties("http://localhost:${wireMock.port()}"),
    notificationService = ClientProperties.EndpointProperties("http://localhost:${wireMock.port()}")
  )

  val userClient = UserServiceClient(webClient, clientProps)
  val orderClient = OrderServiceClient(webClient, clientProps)
  val notiClient = NotificationServiceClient(webClient, clientProps)
  val service = AggregatorService(userClient, orderClient, notiClient)

  "should aggregate all three API responses" {
    wireMock.stubFor(get(urlPathMatching("/user-info/.*"))
      .willReturn(okJson("""{"id":1,"name":"Alice","email":"alice@example.com"}""")))
    wireMock.stubFor(get(urlPathMatching("/order-info/.*"))
      .willReturn(okJson("""[{"orderId":10,"productName":"Book","orderDate":"2025-04-30"}]""")))
    wireMock.stubFor(get(urlPathMatching("/notification-info/.*"))
      .willReturn(okJson("""[{"notificationId":100,"message":"Hi","sentAt":"2025-04-30T12:00:00Z"}]""")))

    StepVerifier.create(service.getAggregatedResponse("123"))
      .assertNext { resp: AggregatedResponse ->
        resp.user shouldBe UserInfo(1, "Alice", "alice@example.com")
        resp.orders shouldBe listOf(OrderInfo(10, "Book", "2025-04-30"))
        resp.notifications shouldBe listOf(NotificationInfo(100, "Hi", "2025-04-30T12:00:00Z"))
      }
      .verifyComplete()
  }

  afterSpec { wireMock.stop() }

})
