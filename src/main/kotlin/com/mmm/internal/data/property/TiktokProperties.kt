package com.mmm.internal.data.property

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("tiktok")
data class TiktokProperties(
    val baseUrl: String,
    val id: TiktokIdProperties,
    val api: TiktokApiProperties
)

data class TiktokIdProperties(
    val device: String,
    val a: Int
)

data class TiktokApiProperties(
    val trending: String,
    val explore: String
)