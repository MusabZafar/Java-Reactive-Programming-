package org.reactive.RepeatAndRetry;


import org.reactive.DefaultSubscriber.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Demonstrates the Repeat Operator:
 * - The Repeat operator re-subscribes to the source upon receiving a "complete" signal.
 * - It does not handle "error" signals; errors propagate and stop the sequence.
 */
public class RepeatPart2 {
    public static void main(String[] args) {

        // Example 1: Basic Repeat with Flux
        Flux.just(1, 2, 3) // Emit 1, 2, 3
                .repeat(3) // Repeat the sequence 3 times (total 4 executions)
                .subscribe(Util.subscriber()); // Subscribe and print the emitted items

        demo();   // Example 2: Repeating a Mono
        demo3();  // Example 3: Conditional Repeat with a Predicate
        demo4();  // Example 4: Repeat with Delay
        demo5();  // Example 5: Repeat with Delay and Limited Repetition

        try {
            Util.sleepSeconds(2); // Allow enough time for asynchronous operations to complete
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Example 2: Repeating a Mono with a Fixed Count
     * - The Mono emits a random country name.
     * - It repeats the emission 3 times after the first completion.
     */
    private static void demo() {
        getCountryName()
                .repeat(3) // Repeat the Mono 3 additional times (4 total emissions)
                .subscribe(Util.subscriber()); // Subscribe and print emitted country names
    }

    /**
     * Example 3: Conditional Repeat Using a Predicate
     * - Uses a condition to determine whether to repeat the Mono.
     * - Repeats until the emitted country name equals "Canada".
     */
    private static void demo2() {
        getCountryName()
                .repeat() // Repeat indefinitely
                .takeUntil(c -> c.equalsIgnoreCase("Canada")) // Stop when "Canada" is emitted
                .subscribe(Util.subscriber()); // Subscribe and print emitted items
    }

    /**
     * Example 4: Conditional Repeat Using a Dynamic Predicate
     * - Repeats the Mono while a condition (based on an AtomicInteger) evaluates to true.
     */
    private static void demo3() {
        var atomicInteger = new AtomicInteger(0); // Counter to track repetitions
        getCountryName()
                .repeat(() -> atomicInteger.incrementAndGet() < 4) // Repeat while counter < 4
                .subscribe(Util.subscriber()); // Subscribe and print emitted items
    }

    /**
     * Example 5: Repeat with a Delay
     * - Repeats the Mono after a delay between completions.
     * - Delays the repetition by 2 seconds.
     */
    private static void demo4() {
        getCountryName()
                .repeatWhen(flux -> flux.delayElements(Duration.ofSeconds(2))) // Add 2-second delay before repeating
                .subscribe(Util.subscriber()); // Subscribe and print emitted items
    }

    /**
     * Example 6: Repeat with a Delay and Limited Repetition
     * - Repeats the Mono after a delay but limits the number of repetitions.
     * - The delay is 2 seconds, and it repeats only 3 times.
     */
    private static void demo5() {
        getCountryName()
                .repeatWhen(flux -> flux.delayElements(Duration.ofSeconds(2)).take(3)) // Add delay and limit to 3 repetitions
                .subscribe(Util.subscriber()); // Subscribe and print emitted items
    }

    /**
     * A Mono that emits a random country name.
     * - Uses Faker library to generate non-blocking random country names.
     * - Acts as the data source for the repeat operations.
     */
    private static Mono<String> getCountryName() {
        return Mono.fromSupplier(() -> Util.faker().country().name()); // Non-blocking IO
    }
}

