package com.mmm.internal.common.util

import com.mmm.internal.data.client.response.TiktokVideoResponse
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.longOrNull

fun String.mapToTiktokResponse(): List<TiktokVideoResponse> {

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

        // video.bitrateInfo[0].PlayAddr.UrlList[]
        val urlList = item["video"]?.jsonObject?.get("bitrateInfo")?.jsonArray?.get(0)
            ?.jsonObject?.get("PlayAddr")?.jsonObject?.get("UrlList")?.jsonArray
            ?.map { it.toString() }

        val statsV2 = item["statsV2"]?.jsonObject

        TiktokVideoResponse(
            title = item["desc"]?.jsonPrimitive?.contentOrNull ?: "",
            hashtags = hashtags,
            heartCount = statsV2?.get("diggCount")?.jsonPrimitive?.longOrNull ?: 0,
            commentsCount = statsV2?.get("commentCount")?.jsonPrimitive?.longOrNull ?: 0,
            viewsCount = statsV2?.get("playCount")?.jsonPrimitive?.longOrNull ?: 0,
            category = item["CategoryType"]?.jsonPrimitive?.contentOrNull ?: "",
            urlList = urlList ?: listOf()
        )
    } ?: emptyList()

}