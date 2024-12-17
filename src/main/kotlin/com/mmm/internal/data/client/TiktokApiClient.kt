package com.mmm.internal.data.client

import com.mmm.internal.common.dsl.WebClientBuilder
import com.mmm.internal.common.util.mapToTrendingResponse
import com.mmm.internal.data.client.response.TiktokTrendingResponse
import com.mmm.internal.data.property.TiktokApiProperties
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import java.time.Duration

@Component
class TiktokApiClient(
    @Qualifier("tiktok") private val tiktokClient: WebClient,
    private val tiktokApiProperties: TiktokApiProperties
) {

    private val webClientBuilder: WebClientBuilder = WebClientBuilder()

    fun trending(
        count: Int = 1
    ): Mono<List<TiktokTrendingResponse>> {
        if (count > 3) {
            return Mono.empty()
        }

        return webClientBuilder.webClient(tiktokClient)
            .uri(tiktokApiProperties.trendingApi)
            .queryParam("count", count)
            .queryParam("region", REGION)
            .queryParam("language", LANGUAGE)
            .queryParam("tz_name", TIMEZONE)
            .build { it.mapToTrendingResponse() }
            .subscribeOn(Schedulers.boundedElastic())
            .timeout(Duration.ofSeconds(10))
            .onErrorResume { Mono.empty() }
    }

    companion object {
        private const val REGION = "KR"
        private const val LANGUAGE = "ko"
        private const val TIMEZONE = "Asia%2FSeoul"
    }

}