package com.mmm.internal.common.annotation

import org.springframework.stereotype.Controller

/**
 * Annotation for handling GraphQL Requests.
 *
 * <p>include annotation:</p>
 * <ul>
 *   <li>{@link Controller}</li>
 *   <li>{@link Target(AnnotationTarget.CLASS)}</li>
 *   <li>{@link Retention(AnnotationRetention.RUNTIME)}</li>
 * </ul>
 *
 * <p>Example Usage:</p>
 * <pre>{@code
 * @GraphQLResolver("User Fetch Resolver")
 * class UserResolver {
 *     // Resolver methods go here
 * }
 * }</pre>
 */
@Controller
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class GraphQLResolver(
    val description: String = ""
)
