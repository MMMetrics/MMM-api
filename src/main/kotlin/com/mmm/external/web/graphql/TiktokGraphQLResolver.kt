package com.mmm.external.web.graphql

import com.mmm.internal.common.annotation.GraphQLResolver
import com.mmm.internal.data.client.TiktokApiClient
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping

@GraphQLResolver
private class TiktokGraphQLResolver(
    private val tiktokApiClient: TiktokApiClient
) {

    @QueryMapping("tiktokTrending")
    fun fetchTiktokTrendData(@Argument n: Int) = tiktokApiClient.trending(n)

    @QueryMapping("tiktokExplore")
    fun fetchTiktokExploreData(@Argument category: Int, @Argument n: Int) = tiktokApiClient.explore(category, n)

    @QueryMapping("tiktokSearch")
    fun fetchTiktokSearchedData(@Argument keyword: String, @Argument n: Int) = tiktokApiClient.search(keyword, n)
}