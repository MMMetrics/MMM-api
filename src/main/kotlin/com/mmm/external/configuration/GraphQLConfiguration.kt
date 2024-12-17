package com.mmm.external.configuration

import graphql.scalars.ExtendedScalars
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.graphql.execution.RuntimeWiringConfigurer

@Configuration
private class GraphQLConfiguration {

    @Bean
    fun runtimeWiringConfigurer(): RuntimeWiringConfigurer = RuntimeWiringConfigurer {
            wiringBuilder -> wiringBuilder.scalar(ExtendedScalars.DateTime)
    }

}