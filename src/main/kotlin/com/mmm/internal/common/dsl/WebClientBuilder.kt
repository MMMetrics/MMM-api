package com.mmm.internal.common.dsl

import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

class WebClientBuilder {

    private var webClient: WebClient? = null
    private var uri: String = "/"
    private val queryParams: MultiValueMap<String, String?> = LinkedMultiValueMap()
    private val headers: MutableMap<String, String?> = mutableMapOf()

    fun webClient(wc: WebClient) = apply { webClient = wc }
    fun uri(input: String) = apply { uri = input }
    fun queryParam(key: String, value: Any?) = apply { queryParams[key] = value.toString() }
    fun header(key: String, value: Any?) = apply { headers[key] = value.toString() }

    fun <T> build(mapper: (String) -> T): Mono<T> {
        if (webClient == null) {
            return Mono.empty()
        }

        return webClient!!.get()
            .uri { uriBuilder ->
                uriBuilder.path(uri)
                    .queryParams(queryParams)
                    .build()
            }
            .headers { httpHeader -> httpHeader.setAll(headers) }
            .retrieve()
            .bodyToMono(String::class.java)
            .map { mapper(it) }
    }

}