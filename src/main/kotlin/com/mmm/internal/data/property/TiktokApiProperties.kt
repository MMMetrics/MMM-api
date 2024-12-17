package com.mmm.internal.data.property

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("tiktok")
data class TiktokApiProperties(
    val baseUrl: String,
    val trendingApi: String
)