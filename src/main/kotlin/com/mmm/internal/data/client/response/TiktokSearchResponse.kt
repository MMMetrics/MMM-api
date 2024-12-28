package com.mmm.internal.data.client.response


data class TiktokSearchResponse(
    val title: String,
    val videoCover: String,
    val author: AuthorInfo,
    val stats: VideoStats
)

data class VideoStats(
    val shareCount: Long,
    val commentCount: Long,
    val playCount: Long,
    val collectCount: Long
)

data class AuthorInfo(
    val nickname: String,
    val profileImage: String,
    val stats: AuthorStats
)

data class AuthorStats(
    val followingCount: Long,
    val followerCount: Long,
    val heartCount: Long,
    val videoCount: Long
)