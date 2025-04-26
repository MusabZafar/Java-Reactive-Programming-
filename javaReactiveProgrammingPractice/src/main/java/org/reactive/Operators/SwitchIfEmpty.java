package org.reactive.Operators;

import org.reactive.DefaultSubscriber.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

public class SwitchIfEmpty {
    public static final Logger logger = LoggerFactory.getLogger(SwitchIfEmpty.class);

    public static void main(String[] args) {
        Flux.range(1, 10) // Generates a sequence of numbers from 1 to 10
                .filter(i -> i > 11) // Filters numbers, but none satisfy the condition (>11)
                .switchIfEmpty(fallback()) // Switches to the fallback Flux when no items are emitted
                .subscribe(Util.subscriber("SwitchIfEmptySubscriber")); // Subscribes to process and print items
    }

    // Fallback publisher: Emits numbers starting from 100, for 3 items
    private static Flux<Integer> fallback() {
        return Flux.range(100, 3); // Generates numbers: 100, 101, 102
    }
}
