package com.fluxgate.fluxgate.dto

data class AggregatedResponse(
  val user: UserInfo,
  val orders: List<OrderInfo>,
  val notifications: List<NotificationInfo>
)