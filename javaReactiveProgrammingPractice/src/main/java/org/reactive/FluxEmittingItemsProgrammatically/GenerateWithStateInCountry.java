package org.reactive.FluxEmittingItemsProgrammatically;

import org.reactive.DefaultSubscriber.Util;
import reactor.core.publisher.Flux;

public class GenerateWithStateInCountry {

    public static void main(String[] args) {
        Flux.generate(
                () -> 0, // Initial state (counter = 0)
                (counter, sink) -> { // Lambda invoked per emission
                    var country = Util.faker().country().name(); // Generate a random country
                    sink.next(country); // Emit the country name
                    counter++; // Increment the counter

                    // Stop emitting if 10 items are emitted or "Canada" is encountered
                    if (counter == 10 || country.equalsIgnoreCase("canada")) {
                        sink.complete(); // Signal completion
                    }
                    return counter; // Return updated state for the next invocation
                }
        ).subscribe(Util.subscriber());
    }
}
