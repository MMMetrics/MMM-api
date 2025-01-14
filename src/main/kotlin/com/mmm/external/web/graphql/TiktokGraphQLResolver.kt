package com.mmm.external.web.graphql

import com.mmm.internal.common.annotation.GraphQLResolver
import com.mmm.internal.data.client.TiktokApiClient
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping

@GraphQLResolver
class TiktokGraphQLResolver(
    private val tiktokApiClient: TiktokApiClient
) {

    @QueryMapping("tiktokTrending")
    fun fetchTiktokTrendData(@Argument count: Int) = tiktokApiClient.trending(count)

    @QueryMapping("tiktokExplore")
    fun fetchTiktokExploreData(@Argument category: Int, @Argument count: Int) = tiktokApiClient.explore(category, count)

    @QueryMapping("tiktokSearch")
    fun fetchTiktokSearchedData(@Argument keyword: String, @Argument count: Int) = tiktokApiClient.search(keyword, count)

}