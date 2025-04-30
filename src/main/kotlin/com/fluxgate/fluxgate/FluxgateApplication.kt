package com.fluxgate.fluxgate

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FluxgateApplication

fun main(args: Array<String>) {
	runApplication<FluxgateApplication>(*args)
}
