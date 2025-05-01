package com.fluxgate.fluxgate.dto

data class NotificationInfo(
  val notificationId: Long,
  val message: String,
  val sentAt: String
)