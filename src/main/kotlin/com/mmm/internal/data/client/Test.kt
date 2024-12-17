package com.mmm.internal.data.client

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class Test(
    private val tiktokApiClient: TiktokApiClient
) {

    @GetMapping("/test")
    fun test() = tiktokApiClient.trending(3)
}