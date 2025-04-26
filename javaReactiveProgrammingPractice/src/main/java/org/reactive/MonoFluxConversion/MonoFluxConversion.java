package org.reactive.MonoFluxConversion;

import org.reactive.DefaultSubscriber.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class MonoFluxConversion {

    public static void main(String[] args) {

        // Convert a Flux to Mono
        var flux = Flux.range(1, 10); // Create a Flux emitting integers 1 to 10
        Mono.from(flux) // Convert Flux to Mono, emitting only the first element
                .subscribe(Util.subscriber("Mono from Flux"));

        // Demonstrate Mono to Flux conversion
        monoToFlux();
    }

    private static void monoToFlux() {
        // Convert a Mono to Flux
        var mono = getUserName(1); // Fetch username for userId 1
        save(Flux.from(mono)); // Convert Mono to Flux and save it
    }

    // Method to fetch a username based on userId
    private static Mono<String> getUserName(int userId) {
        // Return Mono based on userId
        return switch (userId) {
            case 1 -> Mono.just("John"); // Emit "John" for userId 1
            case 2 -> Mono.just("Jane"); // Emit "Jane" for userId 2
            case 3 -> Mono.empty();      // Emit no value for userId 3
            default -> Mono.error(new RuntimeException("Invalid input")); // Emit an error for other userIds
        };
    }

    // Method to process a Flux (save operation)
    private static void save(Flux<String> flux) {
        // Subscribe to the Flux to process the emitted values
        flux.subscribe(Util.subscriber("Flux from Mono"));
    }
}


