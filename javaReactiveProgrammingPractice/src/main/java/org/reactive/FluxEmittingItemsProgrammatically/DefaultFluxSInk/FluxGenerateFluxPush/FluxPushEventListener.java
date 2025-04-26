package org.reactive.FluxEmittingItemsProgrammatically.DefaultFluxSInk.FluxGenerateFluxPush;

import org.reactive.DefaultSubscriber.Util;
import reactor.core.publisher.Flux;

public class FluxPushEventListener {
    public static void main(String[] args) {
        var flux = Flux.push(sink -> {
            Runnable eventEmitter = () -> {
                for (int i = 1; i <= 10; i++) {
                    System.out.println("Emitting: Event " + i);
                    sink.next("Event " + i); // Emit external event
                    try {
                        Thread.sleep(500); // Simulate delay
                    } catch (InterruptedException e) {
                        sink.error(e); // Signal an error
                    }
                }
                sink.complete(); // Signal completion
            };
            new Thread(eventEmitter).start(); // Run the emitter in a separate thread
        });

        flux.subscribe(Util.subscriber("ExternalEventSubscriber")); // Subscribe to the Flux
    }
}
