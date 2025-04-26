package org.reactive.FluxEmittingItemsProgrammatically.FluxGenerateExample;

import org.reactive.DefaultSubscriber.Util;
import reactor.core.publisher.Flux;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FluxGenerateExample {
    private static final Logger logger = LoggerFactory.getLogger(FluxGenerateExample.class);

    public static void main(String[] args) {
        Flux.generate(synchronousSink -> {
            // Generate and emit a random name
            String name = Util.faker().name().fullName();
            logger.info("Generated name: {}", name);

            synchronousSink.next(name); // Emit the name
            if (name.startsWith("A")) { // Stop condition: name starts with 'A'
                synchronousSink.complete();
            }
        })
        .take(5) // Request at most 5 items
        .subscribe(Util.subscriber("RandomNames"));
    }
}
