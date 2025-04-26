package org.reactive.FluxEmittingItemsProgrammatically;

import reactor.core.publisher.Flux;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StatefulFluxGenerateExample {
    private static final Logger logger = LoggerFactory.getLogger(StatefulFluxGenerateExample.class);

    public static void main(String[] args) {

        Flux.generate(
                () -> 1, // Initial state: Start counting from 1
                (state, sink) -> { 
                    logger.info("Emitting: {}", state); // Log the current state (count)
                    sink.next(state); // Emit the current state
                    
                    if (state >= 10) { // Stop condition: when state reaches 10
                        sink.complete(); // Signal completion
                    }
                    
                    return state + 1; // Increment the state for the next invocation
                }
        )
        .subscribe(
                value -> logger.info("Subscriber received: {}", value), // Process each value
                err -> logger.error("Error occurred", err), // Handle errors
                () -> logger.info("Stream complete!") // Handle stream completion
        );
    }
}
