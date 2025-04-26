package org.reactive.Operators;


import org.reactive.DefaultSubscriber.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

/**
 * Demonstrates error handling using onErrorReturn in a reactive pipeline.
 * Its also work with Mono
 */
public class ErrorHandling {
    // Logger instance for logging events in the reactive stream
    public static final Logger logger = LoggerFactory.getLogger(ErrorHandling.class);

    public static void main(String[] args) {
        Flux.range(1, 10) // Generate a range of numbers from 1 to 10
                .map(i -> i == 5 ? 5 / 0 : i) // Intentional error when i == 5 (division by zero)
                /*
                 * onErrorReturn: Handles errors by providing fallback values.
                 * 1. General error fallback value.
                 * 2. Specific fallback values for particular exception types.
                 */
                .onErrorReturn(-1) // Fallback value for any type of exception
                .onErrorReturn(IllegalArgumentException.class, -2) // Specific fallback for IllegalArgumentException
                .onErrorReturn(ArithmeticException.class, -3) // Specific fallback for ArithmeticException
                // Subscribe to the stream to consume emitted values
                .subscribe(Util.subscriber()); // Custom subscriber for consuming and logging data
    }

    public static void main1(String[] args) {
        Flux.range(1, 6)
                .map(i -> {
                    if (i == 3) throw new IllegalArgumentException("Invalid argument");
                    if (i == 5) throw new ArithmeticException("Division by zero");
                    return i;
                })
                .onErrorReturn(IllegalArgumentException.class, -2) // Handles IllegalArgumentException
                .onErrorReturn(ArithmeticException.class, -3) // Handles ArithmeticException
                .onErrorReturn(-1) // General fallback for other errors
                .subscribe(
                        data -> System.out.println("Received: " + data),
                        error -> System.err.println("Error: " + error),
                        () -> System.out.println("Stream Completed!")
                );
    }
}

