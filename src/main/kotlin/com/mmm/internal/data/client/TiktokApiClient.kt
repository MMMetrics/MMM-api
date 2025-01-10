package com.mmm.internal.data.client

import com.mmm.internal.data.client.response.TiktokVideoResponse
import reactor.core.publisher.Mono

interface TiktokApiClient {
    fun trending(count: Int): Mono<List<TiktokVideoResponse>>
    fun explore(category: Int, count: Int): Mono<List<TiktokVideoResponse>>
    fun search(keyword: String, count: Int): Mono<List<TiktokVideoResponse>>
}