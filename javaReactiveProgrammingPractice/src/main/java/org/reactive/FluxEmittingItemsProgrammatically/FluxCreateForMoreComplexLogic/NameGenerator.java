package org.reactive.FluxEmittingItemsProgrammatically.FluxCreateForMoreComplexLogic;

import org.reactive.DefaultSubscriber.Util;
import reactor.core.publisher.FluxSink;

import java.util.function.Consumer;

public class NameGenerator implements Consumer<FluxSink<String>> {

    private FluxSink<String> sink; // Holds the FluxSink to emit items

    @Override
    public void accept(FluxSink<String> stringFluxSink) {
        // Initialize the FluxSink when Flux.create is called
        this.sink = stringFluxSink;
    }

    // Method to generate names dynamically and emit them using the sink
    public void generate() {
        // Generate a random first name and emit it
        this.sink.next(Util.faker().name().firstName());
    }

    // Method to emit a termination signal
    public void complete() {
        this.sink.complete(); // Notify subscribers that the stream is complete
    }

    // Method to emit an error signal
    public void emitError(Exception e) {
        this.sink.error(e); // Signal an error in the stream
    }
}

