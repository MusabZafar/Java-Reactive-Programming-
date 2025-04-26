package org.reactive.RepeatAndRetry;

import org.reactive.DefaultSubscriber.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.time.Duration;

/**
 * Demonstrates the Retry Operator:
 * - The retry operator re-subscribes to the source upon receiving an "error signal."
 * - Can be used for handling transient errors or for implementing retry logic.
 * - Variants shown include finite retries, infinite retries, condition-based retries, and retries with delays.
 */
public class Retry {

    // Logger to log retry events
    public static final Logger logger = LoggerFactory.getLogger(reactor.util.retry.Retry.class);

    public static void main(String[] args) {

        // Example 1: Retry a Mono up to 2 times
        demo1();

        // Example 2: Retry indefinitely on errors
        demo2();

        // Example 3: Conditional retry with filtering and retry exhaustion handling
        demo3();

        // Example 4: Retry with a fixed delay between attempts
        demo4();

        try {
            Util.sleepSeconds(10); // Allow sufficient time for asynchronous retries
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Example 1: Finite Retry
     * - Retries the Mono up to 2 times after an error occurs.
     * - After 2 retries, if the error persists, the error propagates to the subscriber.
     */
    private static void demo1() {
        getCountryName()
                .retry(2) // Retry up to 2 times
                .subscribe(Util.subscriber()); // Subscribe and handle emissions or errors
    }

    /**
     * Example 2: Infinite Retry
     * - Retries the Mono indefinitely upon encountering an error.
     * - Use cautiously, as it may lead to infinite loops if the error persists.
     */
    private static void demo2() {
        getCountryName()
                .retryWhen(reactor.util.retry.Retry.indefinitely()) // Retry indefinitely
                .subscribe(Util.subscriber()); // Subscribe and handle emissions or errors
    }

    /**
     * Example 3: Conditional Retry
     * - Retries the Mono up to 2 times, but only for specific exceptions.
     * - Executes logic before and after retries using filters and handlers.
     */
    private static void demo3() {
        getCountryName()
                .retryWhen(
                        reactor.util.retry.Retry.max(2) // Retry up to 2 times
                                .filter(ex -> RuntimeException.class.equals(ex.getClass())) // Retry only for RuntimeException
                                .onRetryExhaustedThrow((retrySpec, signal) -> signal.failure()) // Throw the original error after retries
                )
                .subscribe(Util.subscriber());
    }

    /**
     * Example 4: Retry with Fixed Delay
     * - Retries the Mono up to 2 times, with a 1-second delay between retries.
     * - Useful for handling transient failures in a controlled manner.
     */
    private static void demo4() {
        getCountryName()
                .retryWhen(reactor.util.retry.Retry.fixedDelay(2, Duration.ofSeconds(1))) // Retry with a fixed 1-second delay
                .subscribe(Util.subscriber());
    }

    /**
     * Generates a Mono that emits a random country name.
     * - Simulates non-blocking data generation.
     * - Errors can be simulated for retry examples by modifying the supplier logic.
     */
    private static Mono<String> getCountryName() {
        return Mono.fromSupplier(() -> {
            if (Math.random() < 0.5) {
                throw new RuntimeException("Transient Error"); // Simulate a transient error
            }
            return Util.faker().country().name(); // Emit a random country name
        });
    }
}

