package org.reactive.FluxEmittingItemsProgrammatically.FluxCreateForMoreComplexLogic;

import org.reactive.DefaultSubscriber.Util;
import reactor.core.publisher.Flux;

public class FluxCreateRefactor {
    public static void main(String[] args) {
        // Create an instance of the NameGenerator
        var generator = new NameGenerator();

        // Use Flux.create to initialize the reactive stream using NameGenerator
        var flux = Flux.create(generator);

        // Subscribe to the Flux to process emitted names
        flux.subscribe(Util.subscriber("NameSubscriber"));

        // Simulate generating names dynamically
        for (int i = 0; i < 10; i++) {
            generator.generate(); // Emit a name
        }

        // Complete the stream after emitting all names
        generator.complete();

        // Example: Emitting an error (uncomment to test error handling)
        // generator.emitError(new RuntimeException("Unexpected error occurred!"));
    }
}