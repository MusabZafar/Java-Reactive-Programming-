package org.reactive.mono;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

public class MonoSubscribe {

    // Logger instance for logging information, errors, and completion events
    private static final Logger logger = LoggerFactory.getLogger(MonoSubscribe.class);

    public static void main(String[] args) {

        // Create a Mono that emits a single string value "Hello World"
        var mono = Mono.just("Hello World");

        // Create a Mono that emits an integer, processes it using map, and emits the result
        var mono1 = Mono.just(1)
                .map(i -> i / 1); // Divides the emitted value (1) by 1 (no error here)

        // Subscribe to the first Mono and handle its lifecycle events
        mono.subscribe(
                i -> logger.info("received:   {}", i),      // Handle emitted data (onNext)
                err -> logger.error("error", err),         // Handle error signal (onError)
                () -> logger.info("completed"),           // Handle completion signal (onComplete)
                subscription -> subscription.request(1)   // Handle onSubscribe and request 1 item
        );

        // Subscribe to the second Mono and handle its lifecycle events
        mono1.subscribe(
                i -> logger.info("received:   {}", i),      // Handle emitted data (onNext)
                err -> logger.error("error", err),         // Handle error signal (onError)
                () -> logger.info("completed"),           // Handle completion signal (onComplete)
                subscription -> subscription.request(1)   // Handle onSubscribe and request 1 item
        );
    }
}