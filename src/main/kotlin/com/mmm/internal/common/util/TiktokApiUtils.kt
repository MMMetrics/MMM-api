package com.mmm.internal.common.util

import com.mmm.internal.data.client.response.*
import kotlinx.serialization.json.*

fun String.mapToTrendingResponse(): List<TiktokTrendingResponse> {

    val json = Json { ignoreUnknownKeys = true }
    val rootObject = json.parseToJsonElement(this).jsonObject

    return rootObject["itemList"]?.jsonArray?.mapNotNull { itemElement ->
        val item = itemElement.jsonObject

        val hashtags = item["contents"]?.jsonArray
            ?.flatMap { content ->
                content.jsonObject["textExtra"]?.jsonArray
                    ?.mapNotNull { it.jsonObject["hashtagName"]?.jsonPrimitive?.contentOrNull }
                    ?: emptyList()
            }
            ?.distinct()
            ?: emptyList()


        val statsV2 = item["statsV2"]?.jsonObject

        TiktokTrendingResponse(
            title = item["desc"]?.jsonPrimitive?.contentOrNull ?: "",
            hashtags = hashtags,
            heartCount = statsV2?.get("diggCount")?.jsonPrimitive?.longOrNull ?: 0,
            commentsCount = statsV2?.get("commentCount")?.jsonPrimitive?.longOrNull ?: 0,
            viewsCount = statsV2?.get("playCount")?.jsonPrimitive?.longOrNull ?: 0,
            category = item["CategoryType"]?.jsonPrimitive?.contentOrNull ?: ""
        )
    } ?: emptyList()

}