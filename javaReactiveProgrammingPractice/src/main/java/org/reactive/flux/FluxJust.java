package org.reactive.flux;

import org.reactive.DefaultSubscriber.Util;
import reactor.core.publisher.Flux;

import java.util.List;

import reactor.core.publisher.Flux;

public class FluxJust {
    public static void main(String[] args) {
        // Create a Flux that emits the integers 1 to 10
        var flux = Flux.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        // Subscribe a subscriber to consume the emitted values
        flux.subscribe(Util.subscriber("subscriber1")); // First subscription, processes raw values

        // Apply transformations (filter even numbers and append "even")
        flux
                .filter(i -> i % 2 == 0) // Keep only even numbers
                .map(i -> i + " even")  // Transform even numbers to a string
                .subscribe(Util.subscriber("subscriber2")); // Second subscription, processes transformed values
    }
}

