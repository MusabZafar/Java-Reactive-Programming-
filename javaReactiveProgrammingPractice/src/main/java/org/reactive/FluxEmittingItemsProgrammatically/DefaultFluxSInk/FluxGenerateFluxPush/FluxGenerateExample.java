package org.reactive.FluxEmittingItemsProgrammatically.DefaultFluxSInk.FluxGenerateFluxPush;

import org.reactive.DefaultSubscriber.Util;
import reactor.core.publisher.Flux;

public class FluxGenerateExample {
    public static void main(String[] args) {
        Flux.generate(
                sink -> {
                    String name = Util.faker().name().fullName(); // Generate a random name
                    System.out.println("Producing: " + name);
                    sink.next(name); // Emit the name
                    if (name.equalsIgnoreCase("John Doe")) { // Stop condition
                        sink.complete(); // Signal completion
                    }
                }
        ).subscribe(Util.subscriber("LazySubscriber")); // Subscriber processes items
    }
}
