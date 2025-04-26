package org.reactive.Operators;

import org.reactive.DefaultSubscriber.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.time.Duration;

/*
 * Demonstrates the usage of timeout, fallback, and multiple timeout operators in Project Reactor.
 * Key Features:
 * 1. `timeout` operator generates a timeout error if the upstream takes longer than the specified duration.
 * 2. If a timeout occurs, we can handle it using a fallback publisher.
 * 3. Multiple timeouts can exist in the chain; the one closest to the subscriber takes precedence.
 */
public class MultipleTimeout {

    // Logger for observing the lifecycle and behavior
    private static final Logger log = LoggerFactory.getLogger(MultipleTimeout.class);

    public static void main(String[] args) {
        // Call the product service with timeout and fallback handling
        getProductName()
                .timeout(Duration.ofSeconds(1), fallback()) // If it exceeds 1 second, switch to fallback
                .timeout(Duration.ofMillis(500)) // Additional timeout closer to the subscriber
                .subscribe(Util.subscriber("MultipleTimeouts"));

        // Keep the main thread alive to allow for delayed responses
        try {
            Util.sleepSeconds(5);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /*
     * Simulated product name service
     * - Introduces an artificial delay of 1900ms (1.9 seconds) to mimic a slow service.
     */
    private static Mono<String> getProductName() {
        return Mono.fromSupplier(() -> {
            log.info("Calling primary product service...");
            return "service-" + Util.faker().commerce().productName();
        }).delayElement(Duration.ofMillis(1900)); // Simulated delay
    }

    /*
     * Fallback service to handle timeout scenarios
     * - Returns a fallback value after a shorter delay (300ms).
     * - The `doFirst` hook logs an action when this publisher is triggered.
     */
    private static Mono<String> fallback() {
        return Mono.fromSupplier(() -> {
                    log.info("Calling fallback service...");
                    return "fallback-" + Util.faker().commerce().productName();
                })
                .delayElement(Duration.ofMillis(300)) // Simulated delay for fallback response
                .doFirst(() -> log.info("Fallback publisher initiated")); // Logs before the fallback runs
    }
}
