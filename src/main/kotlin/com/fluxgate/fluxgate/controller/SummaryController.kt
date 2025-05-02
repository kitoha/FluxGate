package com.fluxgate.fluxgate.controller

import com.fluxgate.fluxgate.dto.AggregatedResponse
import com.fluxgate.fluxgate.service.AggregatorService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
class SummaryController (
  private val aggregatorService: AggregatorService
){
  @GetMapping("/api/summary")
  fun getSummary(@RequestParam userId: String): Mono<AggregatedResponse>{
    return aggregatorService.getAggregatedResponse(userId)
  }
}