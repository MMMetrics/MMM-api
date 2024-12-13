package com.mmm

import graphql.scalars.ExtendedScalars
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.graphql.execution.RuntimeWiringConfigurer
import org.springframework.web.reactive.config.EnableWebFlux

@EnableWebFlux
@SpringBootApplication
internal class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}

@Bean
fun runtimeWiringConfigurer(): RuntimeWiringConfigurer = RuntimeWiringConfigurer {
    wiringBuilder -> wiringBuilder.scalar(ExtendedScalars.DateTime)
}

