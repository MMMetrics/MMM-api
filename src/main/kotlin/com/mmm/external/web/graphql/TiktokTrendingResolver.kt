package com.mmm.external.web.graphql

import com.mmm.internal.common.annotation.GraphQLResolver
import com.mmm.internal.data.client.TiktokApiClient
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping

@GraphQLResolver
class TiktokTrendingResolver(
    private val tiktokApiClient: TiktokApiClient
) {

    @QueryMapping("tiktokTrending")
    fun fetchTiktokTrendData(@Argument n: Int) = tiktokApiClient.trending(n)

}