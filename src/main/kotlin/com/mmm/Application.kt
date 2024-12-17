package com.mmm

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.reactive.config.EnableWebFlux

@EnableWebFlux
@SpringBootApplication
internal class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
