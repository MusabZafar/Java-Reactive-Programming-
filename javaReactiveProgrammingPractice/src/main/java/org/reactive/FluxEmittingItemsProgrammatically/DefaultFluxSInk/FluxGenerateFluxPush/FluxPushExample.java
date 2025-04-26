package org.reactive.FluxEmittingItemsProgrammatically.DefaultFluxSInk.FluxGenerateFluxPush;

import org.reactive.DefaultSubscriber.Util;
import reactor.core.publisher.Flux;

public class FluxPushExample {
    public static void main(String[] args) {
        Flux.push(sink -> {
            for (int i = 1; i <= 5; i++) {
                sink.next("Event " + i); // Emit an event
            }
            sink.complete(); // Signal completion
        }).subscribe(Util.subscriber("EventSubscriber")); // Subscriber processes events
    }
}
