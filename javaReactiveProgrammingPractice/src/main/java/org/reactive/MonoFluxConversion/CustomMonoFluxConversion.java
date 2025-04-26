package org.reactive.MonoFluxConversion;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public class CustomMonoFluxConversion {

    public static void main(String[] args) {
        // Create a Flux that emits multiple names
        Flux<String> flux = Flux.just("Alice", "Bob", "Charlie");
        // Convert Flux to Mono, emitting only the first element of the Flux
        Mono.from(flux).subscribe(name -> System.out.println("Mono received: " + name));

        // Create a Mono that emits a single name
        Mono<String> mono = Mono.just("Dave");
        // Convert Mono to Flux, making it behave like a Flux
        Flux.from(mono).subscribe(name -> System.out.println("Flux received: " + name));

        // Additional examples for Flux to Mono conversion
        // Create a Flux with multiple names
        var fluxExample = Flux.just("Alice", "Bob", "Charlie");
        // Convert Flux to Mono, which will emit only the first element ("Alice")
        Mono.from(fluxExample)
                .subscribe(name -> System.out.println("Mono received: " + name)); // Print the first name

        // Additional examples for Mono to Flux conversion
        // Create a Mono with a single name
        var monoExample = Mono.just("Dave");
        // Convert Mono to Flux, now it behaves like a Flux
        Flux.from(monoExample)
                .subscribe(name -> System.out.println("Flux received: " + name)); // Print the single name from Flux
    }
}

