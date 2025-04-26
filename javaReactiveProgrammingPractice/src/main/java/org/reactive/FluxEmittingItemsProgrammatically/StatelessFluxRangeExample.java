package org.reactive.FluxEmittingItemsProgrammatically;

import reactor.core.publisher.Flux;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StatelessFluxRangeExample {
    private static final Logger logger = LoggerFactory.getLogger(StatelessFluxRangeExample.class);

    public static void main(String[] args) {

        Flux.range(1, 10) // Emit numbers 1 to 10
            .subscribe(
                value -> logger.info("Subscriber received: {}", value), // Process each value
                err -> logger.error("Error occurred", err), // Handle errors
                () -> logger.info("Stream complete!") // Handle stream completion
            );
    }
}
