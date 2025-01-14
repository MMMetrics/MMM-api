package com.mmm.internal.data.client

import com.mmm.internal.common.dsl.WebClientBuilder
import com.mmm.internal.common.exception.TiktokAPIBadRequestException
import com.mmm.internal.common.util.mapToTiktokResponse
import com.mmm.internal.data.client.response.TiktokVideoResponse
import com.mmm.internal.data.property.TiktokProperties
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import java.time.Duration

@Component
private class _TiktokApiClient(
    @Qualifier("tiktok") private val tiktokClient: WebClient,
    private val tiktokProperties: TiktokProperties
): TiktokApiClient {

    private val webClientBuilder: WebClientBuilder = WebClientBuilder()

    override fun trending(
        count: Int
    ): Mono<List<TiktokVideoResponse>> {
        if (count > 3) {
            return Mono.error(TiktokAPIBadRequestException)
        }

        return webClientBuilder.webClient(tiktokClient)
            .uri(tiktokProperties.api.trending)
            .queryParam("count", count)
            .queryParam("region", REGION)
            .queryParam("language", LANGUAGE)
            .queryParam("tz_name", TIMEZONE)
            .build { it.mapToTiktokResponse() }
            .subscribeOn(Schedulers.boundedElastic())
            .timeout(Duration.ofSeconds(10))
            .cache(Duration.ofHours(1))
            .onErrorResume { Mono.empty() }
    }

    override fun explore(
        category: Int,
        count: Int
    ): Mono<List<TiktokVideoResponse>> {
        if (count > 3) {
            return Mono.error(TiktokAPIBadRequestException)
        }

        return webClientBuilder.webClient(tiktokClient)
            .uri(tiktokProperties.api.explore)
            .queryParam("count", count)
            .queryParam("categoryType", category)
            .queryParam("region", REGION)
            .queryParam("language", LANGUAGE)
            .queryParam("tz_name", TIMEZONE)
            .queryParam("aid", tiktokProperties.id.a)
            .queryParam("device_id", tiktokProperties.id.device)
            .queryParam("msToken", tiktokProperties.security.msToken)
            .queryParam("_signature", tiktokProperties.security.signature)
            .queryParam("X-Bogus", tiktokProperties.security.xBogus)
            .build { it.mapToTiktokResponse() }
            .subscribeOn(Schedulers.boundedElastic())
            .timeout(Duration.ofSeconds(10))
            .cache(Duration.ofHours(1))
            .onErrorResume { Mono.empty() }
    }

    override fun search(
        keyword: String,
        count: Int
    ): Mono<List<TiktokVideoResponse>> {
        return webClientBuilder.webClient(tiktokClient)
            .uri(tiktokProperties.api.search)
            .queryParam("count", count)
            .queryParam("keyword", keyword)
            .queryParam("region", REGION)
            .queryParam("language", LANGUAGE)
            .queryParam("tz_name", TIMEZONE)
            .queryParam("aid", tiktokProperties.id.a)
            .queryParam("device_id", tiktokProperties.id.device)
            .queryParam("msToken", tiktokProperties.security.msToken)
            .queryParam("_signature", tiktokProperties.security.signature)
            .queryParam("X-Bogus", tiktokProperties.security.xBogus)
            .build { it.mapToTiktokResponse() }
            .subscribeOn(Schedulers.boundedElastic())
            .timeout(Duration.ofSeconds(10))
            .cache(Duration.ofHours(1))
            .onErrorResume { Mono.empty() }
    }

    companion object {
        private const val REGION = "KR"
        private const val LANGUAGE = "ko"
        private const val TIMEZONE = "Asia%2FSeoul"
    }

}