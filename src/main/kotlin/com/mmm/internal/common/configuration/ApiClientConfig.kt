package com.mmm.internal.common.configuration

import com.mmm.internal.data.property.TiktokApiProperties
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient

@Configuration
class ApiClientConfig(
    private val tiktokApiProperties: TiktokApiProperties
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
            .baseUrl(tiktokApiProperties.baseUrl)
            .build()
    }

}
