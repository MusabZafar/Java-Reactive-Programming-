package org.reactive.flux;

import org.reactive.DefaultSubscriber.Util;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class FluxInterval {
    public static void main(String[] args) {
        // Create a Flux that emits values at a fixed interval of 3 seconds
        Flux.interval(Duration.ofSeconds(3))
                // Transform the emitted value into a random full name using Faker library
                .map(i -> Util.faker().name().fullName())
                // Subscribe to the Flux to start receiving and processing data
                .subscribe(Util.subscriber());

        // Allow the main thread to sleep for 5 seconds to keep the application alive
        // and allow Flux emissions to occur
        try {
            Util.sleepSeconds(5);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
