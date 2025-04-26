package org.reactive.Operators;

import org.reactive.DefaultSubscriber.Util;
import reactor.core.publisher.Flux;

import java.time.Duration;

/**
 * Delay Elements
 */
public class DelayOperator {
    public static void main(String[] args) {
        // Flux emits integers from 1 to 10
        Flux.range(1, 10)
                .log() // Logs the flow of the Flux for debugging
                .delayElements(Duration.ofSeconds(1)) // Introduces a delay of 1 second for each emitted item
                .subscribe(Util.subscriber()); // Subscribes to the Flux and processes the items

        // Sleep the main thread to allow time for the delayed emission to complete
        try {
            Util.sleepSeconds(12); // Main thread sleeps for 12 seconds to wait for all emissions
        } catch (InterruptedException e) {
            throw new RuntimeException(e); // Handle interruption exception
        }
    }

    public static void main1(String[] args) {
        System.out.println("Starting delayed stream...");

        Flux.range(1, 5) // Emit 1 to 5
                .delayElements(Duration.ofSeconds(2)) // Delay each item by 2 seconds
                .doOnNext(i -> System.out.println("Emitting: " + i)) // Debug log for each emission
                .doOnComplete(() -> System.out.println("Stream completed!")) // Log when stream completes
                .subscribe(); // Subscribe to the Flux

        // Keep the main thread alive for enough time to process the stream
        try {
            Util.sleepSeconds(12); // Sleep for 12 seconds to allow delays to finish
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
