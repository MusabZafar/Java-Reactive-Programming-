package org.reactive.flux;

import reactor.core.publisher.Flux;

public class FluxErrorExample {
    public static void main(String[] args) {
        // Create a Flux that emits an error
        Flux.error(new RuntimeException("Something went wrong!"))
                .subscribe(
                        item -> System.out.println("Received: " + item), // onNext
                        err -> System.err.println("Error: " + err),      // onError
                        () -> System.out.println("Completed!")          // onComplete
                );
    }
}
