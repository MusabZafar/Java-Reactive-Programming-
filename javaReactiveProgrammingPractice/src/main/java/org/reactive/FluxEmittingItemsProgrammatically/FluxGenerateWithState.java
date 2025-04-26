package org.reactive.FluxEmittingItemsProgrammatically;

import org.reactive.DefaultSubscriber.Util;
import reactor.core.publisher.Flux;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FluxGenerateWithState {
    private static final Logger logger = LoggerFactory.getLogger(FluxGenerateWithState.class);

    public static void main(String[] args) {
        // Example: Generate numbers starting from 1
        Flux.generate(
                () -> 1, // Initial state: Starting value
                (state, sink) -> {
                    logger.info("Emitting: {}", state);
                    sink.next(state); // Emit the current state
                    if (state == 10) { // Stop when state reaches 10
                        sink.complete();
                    }
                    return state + 1; // Increment state for the next invocation
                }
        ).subscribe(value -> logger.info("Received: {}", value));
    }
}
