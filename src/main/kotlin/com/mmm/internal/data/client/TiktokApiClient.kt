package com.mmm.internal.data.client

import com.mmm.internal.data.client.response.TiktokTrendingResponse
import reactor.core.publisher.Mono

fun interface TiktokApiClient {
    fun trending(count: Int): Mono<List<TiktokTrendingResponse>>
}