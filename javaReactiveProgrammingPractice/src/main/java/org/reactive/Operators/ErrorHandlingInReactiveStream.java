package org.reactive.Operators;

import org.reactive.DefaultSubscriber.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ErrorHandlingInReactiveStream {

    private static final Logger log = LoggerFactory.getLogger(ErrorHandlingInReactiveStream.class);

    public static void main(String[] args) {
        // Call different error-handling methods to observe behavior
        onErrorContinue(); // Skip error and continue processing
        onErrorComplete(); // Stop processing on error and complete
        onErrorReturn();   // Return fallback values for specific or any errors
        onErrorResume();   // Switch to fallback publishers in case of errors
    }

    // 1. Skip the error and continue processing
    private static void onErrorContinue() {
        Flux.range(1, 10)
            .map(i -> i == 5 ? 5 / 0 : i) // Intentional error for element 5
            .onErrorContinue((ex, obj) -> log.error("==> Skipping: {}", obj, ex))
            .subscribe(Util.subscriber("onErrorContinue"));
    }
    /*
     * Explanation:
     * - The `onErrorContinue` operator ignores the error and continues emitting subsequent items.
     * - The error, along with the faulty item, is logged using a callback.
     * Usage:
     * - Use when some items can cause errors, but you want to continue processing other items.
     * - For example, when validating or processing a large list of data.
     */

    // 2. In case of an error, stop and emit 'complete'
    private static void onErrorComplete() {
        Mono.just(1)
            .onErrorComplete() // Completes the stream silently if an error occurs
            .subscribe(Util.subscriber("onErrorComplete"));
    }
    /*
     * Explanation:
     * - `onErrorComplete` terminates the stream quietly when an error occurs without propagating the error.
     * Usage:
     * - Use when errors are irrelevant, and completion is enough.
     * - Suitable for optional or non-critical operations.
     */

    // 3. Return a hardcoded value or fallback value in case of an error
    private static void onErrorReturn() {
        Mono.just(5)
            .map(i -> i == 5 ? 5 / 0 : i) // Intentional error when i = 5
            .onErrorReturn(IllegalArgumentException.class, -1) // Handle specific exception
            .onErrorReturn(ArithmeticException.class, -2)      // Handle arithmetic exception
            .onErrorReturn(-3) // Fallback for any other error
            .subscribe(Util.subscriber("onErrorReturn"));
    }
    /*
     * Explanation:
     * - `onErrorReturn` returns a fallback value based on:
     *   1. Specific exception types (e.g., ArithmeticException).
     *   2. A general fallback value for any other error.
     * Usage:
     * - Use when you want to return a default value in case of errors.
     * - Useful for providing graceful degradation for non-critical operations.
     */

    // 4. Use another publisher as a fallback in case of an error
    private static void onErrorResume() {
        Mono.error(new RuntimeException("oops"))
            .onErrorResume(ArithmeticException.class, ex -> fallback1()) // Switch to fallback1 for ArithmeticException
            .onErrorResume(ex -> fallback2()) // Switch to fallback2 for any other error
            .onErrorReturn(-5) // Fallback value as a final resort
            .subscribe(Util.subscriber("onErrorResume"));
    }
    /*
     * Explanation:
     * - `onErrorResume` switches to another publisher when an error occurs.
     * - You can specify recovery publishers dynamically based on the error type or condition.
     * Usage:
     * - Use when you want to recover by switching to another data source or fallback logic.
     * - Useful for failover mechanisms in microservices or multi-service systems.
     */

    // Fallback publisher returning a random integer between 10 and 100
    private static Mono<Integer> fallback1() {
        return Mono.fromSupplier(() -> Util.faker().random().nextInt(10, 100));
    }

    // Fallback publisher returning a random integer between 100 and 1000
    private static Mono<Integer> fallback2() {
        return Mono.fromSupplier(() -> Util.faker().random().nextInt(100, 1000));
    }
}