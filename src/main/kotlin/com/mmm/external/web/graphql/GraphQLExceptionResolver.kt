package com.mmm.external.web.graphql

import com.mmm.internal.common.exception.BusinessException
import graphql.GraphQLError
import graphql.GraphqlErrorBuilder
import graphql.schema.DataFetchingEnvironment
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter
import org.springframework.graphql.execution.ErrorType
import org.springframework.stereotype.Component
import java.time.Instant

/**
 * Resolver for handling GraphQL exceptions.
 *
 * <p>Supported exceptions:</p>
 * <ul>
 *   <li>{@link BusinessException} - Represents domain-specific business errors.</li>
 *   <li>{@link RuntimeException} - Handles unexpected runtime errors.</li>
 * </ul>
 */
@Component
private class GraphQLExceptionResolver : DataFetcherExceptionResolverAdapter() {

    override fun resolveToSingleError(exception: Throwable, env: DataFetchingEnvironment): GraphQLError? {
        return when (exception) {
            is BusinessException -> createBusinessExceptionError(exception, env)
            is RuntimeException -> createUnexpectedErrorError(exception)
            else -> null
        }
    }

    private fun createBusinessExceptionError(
        exception: BusinessException,
        env: DataFetchingEnvironment
    ): GraphQLError {
        return GraphqlErrorBuilder.newError()
            .message(exception.message ?: "Business exception occurred")
            .errorType(exception.errorType)
            .extensions(buildExtensions(exception))
            .path(env.executionStepInfo.path)
            .location(env.field.sourceLocation)
            .build()
    }

    private fun createUnexpectedErrorError(exception: RuntimeException): GraphQLError {
        return GraphqlErrorBuilder.newError()
            .message("Internal server error: ${exception.message}")
            .errorType(ErrorType.INTERNAL_ERROR)
            .build()
    }

    private fun buildExtensions(exception: BusinessException): Map<String, Any> {
        return exception.extensions.apply {
            put("timestamp", Instant.now().toEpochMilli())
            put("exceptionClass", exception.javaClass.simpleName)
        }
    }

}