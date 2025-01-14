package com.mmm.internal.data.client

import com.mmm.internal.data.client.response.TiktokVideoResponse
import reactor.core.publisher.Mono

/**
 * Client interface for interacting with TikTok API endpoints.
 * Provides methods to fetch trending videos, explore categories, and search content.
 */
interface TiktokApiClient {
    /**
     * Retrieves trending videos from TikTok.
     * Limited to maximum 3 videos per request.
     *
     * @param count Number of videos to retrieve (max: 3)
     * @return Mono containing list of TikTok videos, empty if count exceeds limit
     */
    fun trending(count: Int): Mono<List<TiktokVideoResponse>>

    /**
     * Explores videos by category from TikTok.
     * Limited to maximum 3 videos per request.
     *
     * @param category Category ID to explore
     * @param count Number of videos to retrieve (max: 3)
     * @return Mono containing list of TikTok videos, empty if count exceeds limit
     */
    fun explore(category: Int, count: Int): Mono<List<TiktokVideoResponse>>

    /**
     * Searches TikTok videos by keyword.
     *
     * @param keyword Search term
     * @param count Number of videos to retrieve
     * @return Mono containing list of matching TikTok videos
     */
    fun search(keyword: String, count: Int): Mono<List<TiktokVideoResponse>>
}