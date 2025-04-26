package org.reactive.FluxEmittingItemsProgrammatically.Operator;

import org.reactive.DefaultSubscriber.Util;
import reactor.core.publisher.Flux;

public class TakeOperator {
    public static void main(String[] args) {
        // Demonstrate the "take" operator
        take();

        // Demonstrate the "takeWhile" operator
        takeWhile();

        // Demonstrate the "takeUntil" operator
        takeUntil();
    }

    /**
     * The take operator takes only the first N elements emitted by the Flux
     * regardless of any condition.
     */
    private static void take() {
        Flux.range(1, 10) // Creates a Flux emitting integers from 1 to 10
                .log("take") // Logs the Flux operations for debugging
                .take(3) // Emits only the first 3 items
                .log("sub") // Logs the downstream subscription behavior
                .subscribe(Util.subscriber("TakeOperator")); // Subscribes to the Flux
    }

    /**
     * The takeWhile operator takes items while a condition is true.
     * It stops as soon as the condition is not met.
     */
    private static void takeWhile() {
        Flux.range(1, 10) // Creates a Flux emitting integers from 1 to 10
                .log("take") // Logs the Flux operations for debugging
                .takeWhile(i -> i < 5) // Emits items while 'i < 5' is true
                .log("sub") // Logs the downstream subscription behavior
                .subscribe(Util.subscriber("TakeWhileOperator")); // Subscribes to the Flux
    }

    /**
     * The takeUntil operator takes items until a condition becomes true.
     * It stops emitting as soon as the condition is met.+ allow the last item
     */
    private static void takeUntil() {
        Flux.range(1, 10) // Creates a Flux emitting integers from 1 to 10
                .log("take") // Logs the Flux operations for debugging
                .takeUntil(i -> i < 5) // Stops emitting as soon as 'i < 5' is met
                .log("sub") // Logs the downstream subscription behavior
                .subscribe(Util.subscriber("TakeUntilOperator")); // Subscribes to the Flux
    }
}
