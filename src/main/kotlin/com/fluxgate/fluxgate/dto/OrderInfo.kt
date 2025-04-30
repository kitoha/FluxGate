package com.fluxgate.fluxgate.dto

data class OrderInfo(
  val orderId: Long,
  val productName: String,
  val orderDate: String
)