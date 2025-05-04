package com.fluxgate.fluxgate

import com.fluxgate.fluxgate.config.ClientProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(ClientProperties::class)
class FluxgateApplication

fun main(args: Array<String>) {
	runApplication<FluxgateApplication>(*args)
}
