package com.mmm.internal.common.configuration

import com.mmm.internal.data.property.TiktokProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient

@Configuration
private class ApiClientConfig(
    private val tiktokProperties: TiktokProperties
) {

    @Bean("tiktok")
    fun tiktokClient(): WebClient {
        return WebClient.builder()
            .clientConnector(
                ReactorClientHttpConnector(
                    HttpClient.create()
                        .followRedirect(true)
                )
            )
            .baseUrl(tiktokProperties.baseUrl)
            .build()
    }

}
