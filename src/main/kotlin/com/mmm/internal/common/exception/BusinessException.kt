package com.mmm.internal.common.exception

import org.springframework.graphql.execution.ErrorType

sealed class BusinessException(
    message: String,
    val errorType: ErrorType,
    val extensions: MutableMap<String, Any> = mutableMapOf()
) : RuntimeException(message) {
    
    fun withExtension(key: String, value: Any): BusinessException {
        extensions[key] = value
        return this
    }

}

data object TiktokAPIBadRequestException : BusinessException(
    "BAD REQUEST: failed to fetch TIKTOK video data",
    ErrorType.BAD_REQUEST
) {

    private fun readResolve(): Any = TiktokAPIBadRequestException

}