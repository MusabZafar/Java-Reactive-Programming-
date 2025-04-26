package org.reactive.RepeatAndRetry;

import org.reactive.DefaultSubscriber.Util;
import reactor.core.publisher.Mono;

/**
 * Demonstrates the Repeat Operator:
 * - The Repeat operator simply re-subscribes to the source upon receiving a "complete" signal.
 * - It does not handle "error" signals. If an error occurs, the sequence stops, and the error propagates.
 */
public class Repeat {
    public static void main(String[] args) {

        // Creates a Mono that emits a random company name using a supplier
        var mono = Mono.fromSupplier(() -> Util.faker().company().name());

        // Create a subscriber for the Mono
        var subscriber = Util.subscriber();

        /**
         * Repeat Operator:
         * - The `repeat(4)` operator re-subscribes to the Mono 4 additional times after the first completion.
         * - Total subscriptions = 1 original + 4 repeats = 5 times.
         */
        mono.repeat(4).subscribe(subscriber);

        /**
         * Manual Subscription:
         * - This block manually subscribes to the Mono 4 times in a loop.
         * - This approach explicitly triggers a subscription without using the `repeat` operator.
         * - The output will be similar to the repeat operator but requires manual control.
         */
        for (int i = 0; i < 4; i++) {
            mono.subscribe(subscriber);
        }
    }
}
