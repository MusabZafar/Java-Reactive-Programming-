package org.reactive.FluxEmittingItemsProgrammatically.DefaultFluxSInk.FluxGenerateFluxPush;

import org.reactive.DefaultSubscriber.Util;
import reactor.core.publisher.Flux;

public class FluxGenerateWithState {
    public static void main(String[] args) {
        Flux.generate(
                () -> 1, // Initial state (counter starting at 1)
                (state, sink) -> {
                    sink.next("Count: " + state); // Emit current state
                    if (state == 10) {
                        sink.complete(); // Stop when the counter reaches 10
                    }
                    return state + 1; // Increment the counter
                }
        ).subscribe(Util.subscriber("StatefulSubscriber")); // Subscriber processes items
    }
}
