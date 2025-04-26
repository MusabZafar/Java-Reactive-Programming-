package org.reactive.flux;

import reactor.core.publisher.Flux;

public class FluxEmptyExample {
    public static void main(String[] args) {
        // Create an empty Flux
        Flux.empty()
                .subscribe(
                        item -> System.out.println("Received: " + item), // onNext
                        err -> System.err.println("Error: " + err),      // onError
                        () -> System.out.println("Completed!")          // onComplete
                );
    }
}
