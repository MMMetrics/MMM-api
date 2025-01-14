package com.mmm.web.graphql

import com.mmm.external.web.graphql.TiktokGraphQLResolver
import com.mmm.internal.common.exception.TiktokAPIBadRequestException
import com.mmm.internal.data.client.TiktokApiClient
import com.mmm.internal.data.client.response.TiktokVideoResponse
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

@DisplayName("TiktokGraphQLResolver unit 테스트")
class TiktokGraphQLResolverTest {
    
    private val tiktokApiClient: TiktokApiClient = mockk()
    private lateinit var resolver: TiktokGraphQLResolver

    @BeforeEach
    fun setUp() {
        resolver = TiktokGraphQLResolver(tiktokApiClient)
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @Nested
    @DisplayName("tiktokTrending 쿼리 테스트")
    inner class TiktokTrendingTest {
        
        @Test
        @DisplayName("트렌딩 데이터를 정상적으로 가져온다")
        fun fetchTiktokTrendData_ShouldReturnTrendingVideos() {
            // Given
            val count = 1
            val expectedResponse = listOf(
                TiktokVideoResponse(
                    title = "Explore Video",
                    hashtags = listOf("trending", "category"),
                    heartCount = 500,
                    commentsCount = 50,
                    viewsCount = 5000,
                    category = "123"
                )
            )
            
            every { tiktokApiClient.trending(count) } returns Mono.just(expectedResponse)

            // When
            val result = resolver.fetchTiktokTrendData(count)

            // Then
            StepVerifier.create(result)
                .expectNext(expectedResponse)
                .verifyComplete()

            verify(exactly = 1) { tiktokApiClient.trending(count) }
        }

        @Test
        @DisplayName("트렌딩 데이터를 가져오는데 실패한다")
        fun fetchTiktokTrendData_ShouldFailWhenCountExceedsLimit() {
            // Given
            val count = 10
            val exception = TiktokAPIBadRequestException

            every { tiktokApiClient.trending(count) } returns Mono.error(exception)

            // When
            val result = resolver.fetchTiktokTrendData(count)

            // Then
            StepVerifier.create(result)
                .expectErrorMatches { it is TiktokAPIBadRequestException && it.message == exception.message }
                .verify()

            verify(exactly = 1) { tiktokApiClient.trending(count) }
        }
    }

    @Nested
    @DisplayName("tiktokExplore 쿼리 테스트")
    inner class TiktokExploreTest {
        
        @Test
        @DisplayName("카테고리별 탐색 데이터를 정상적으로 가져온다")
        fun fetchTiktokExploreData_ShouldReturnExploreVideos() {
            // Given
            val category = 100
            val count = 1
            val expectedResponse = listOf(
                TiktokVideoResponse(
                    title = "Explore Video",
                    hashtags = listOf("explore", "category"),
                    heartCount = 500,
                    commentsCount = 50,
                    viewsCount = 5000,
                    category = "123"
                )
            )
            
            every { tiktokApiClient.explore(category, count) } returns Mono.just(expectedResponse)

            // When
            val result = resolver.fetchTiktokExploreData(category, count)

            // Then
            StepVerifier.create(result)
                .expectNext(expectedResponse)
                .verifyComplete()

            verify(exactly = 1) { tiktokApiClient.explore(category, count) }
        }

        @Test
        @DisplayName("카테고리별 탐색 데이터를 가져오는데 실패한다")
        fun fetchTiktokExploreData_ShouldFailWhenCountExceedsLimit() {
            // Given
            val category = 100
            val count = 10
            val exception = TiktokAPIBadRequestException

            every { tiktokApiClient.explore(category, count) } returns Mono.error(exception)

            // When
            val result = resolver.fetchTiktokExploreData(category, count)

            // Then
            StepVerifier.create(result)
                .expectErrorMatches { it is TiktokAPIBadRequestException && it.message == exception.message }
                .verify()

            verify(exactly = 1) { tiktokApiClient.explore(category, count) }
        }
    }

    @Nested
    @DisplayName("tiktokSearch 쿼리 테스트")
    inner class TiktokSearchTest {
        
        @Test
        @DisplayName("키워드 검색 결과를 정상적으로 가져온다")
        fun fetchTiktokSearchedData_ShouldReturnSearchResults() {
            // Given
            val keyword = "dance"
            val count = 3
            val expectedResponse = listOf(
                TiktokVideoResponse(
                    title = "Explore Video",
                    hashtags = listOf("search", "category"),
                    heartCount = 500,
                    commentsCount = 50,
                    viewsCount = 5000,
                    category = "123"
                )
            )
            
            every { tiktokApiClient.search(keyword, count) } returns Mono.just(expectedResponse)

            // When
            val result = resolver.fetchTiktokSearchedData(keyword, count)

            // Then
            StepVerifier.create(result)
                .expectNext(expectedResponse)
                .verifyComplete()

            verify(exactly = 1) { tiktokApiClient.search(keyword, count) }
        }
    }

}