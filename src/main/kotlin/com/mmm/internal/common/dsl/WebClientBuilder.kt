package com.mmm.internal.common.dsl

import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

/**
 * Builder class for configuring and executing WebClient requests.
 * Provides a fluent API for setting up HTTP GET requests with custom parameters.
 * 
 * Example usage:
 * ```
 * val result = WebClientBuilder()
 *     .webClient(webClient)
 *     .uri("/api/resource")
 *     .queryParam("id", "123")
 *     .header("Authorization", "Bearer token")
 *     .build { response ->
 *         MyResponseType.fromJsonString(response)
 *     }
 * ```
 */
class WebClientBuilder {

    private var webClient: WebClient? = null
    private var uri: String = "/"
    private val queryParams: MultiValueMap<String, String?> = LinkedMultiValueMap()
    private val headers: MutableMap<String, String?> = mutableMapOf()

    /**
     * Sets the WebClient instance for making HTTP requests.
     *
     * @param wc The WebClient instance
     * @return The current WebClientBuilder instance for method chaining
     */
    fun webClient(wc: WebClient) = apply { webClient = wc }

    /**
     * Sets the URI path for the request.
     *
     * @param input The URI path string (defaults to "/" if not set)
     * @return The current WebClientBuilder instance for method chaining
     */
    fun uri(input: String) = apply { uri = input }

    /**
     * Adds a query parameter to the request URI.
     * Multiple values can be added for the same key.
     *
     * @param key The query parameter key
     * @param value The query parameter value, will be converted to string
     * @return The current WebClientBuilder instance for method chaining
     */
    fun queryParam(key: String, value: Any?) = apply { queryParams[key] = value.toString() }

    /**
     * Adds a header to the HTTP request.
     * If the header already exists, it will be replaced.
     *
     * @param key The header key
     * @param value The header value, will be converted to string
     * @return The current WebClientBuilder instance for method chaining
     */
    fun header(key: String, value: Any?) = apply { headers[key] = value.toString() }

    /**
     * Builds and executes the HTTP GET request with the configured parameters.
     *
     * @param mapper A function to transform the response string to the desired type T
     * @return A Mono containing the mapped response. Returns an empty Mono if no WebClient is set
     * @param T The target type to map the response to
     */
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