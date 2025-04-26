package org.reactive.mono;

import org.reactive.DefaultSubscriber.Util;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

/*
        if you have a CompleteableFUture already, that we can convert into a Mono
 */

public class MonoFromFuture {
    // Logger instance for logging events
    public static final Logger log = Logger.getLogger(MonoFromFuture.class.getName());

    public static void main(String[] args) throws InterruptedException {
        // Create a Mono from a CompletableFuture and subscribe to it
        Mono.fromFuture(getName())
                .subscribe(Util.subscriber()); // Log and process the emitted value

        // Another example: Pass a CompletableFuture supplier method reference to Mono.fromFuture
        Mono.fromFuture(MonoFromFuture::getName)
                .subscribe(Util.subscriber()); // Log and process the emitted value

        // Sleep to allow asynchronous processing to complete
        Util.sleepSeconds(1);
    }

    // Method that returns a CompletableFuture to asynchronously generate a name
    private static CompletableFuture<String> getName() {
        return CompletableFuture.supplyAsync(() -> {
            log.info("Generating name"); // Log when the name generation starts
            return Util.faker().name().fullName(); // Return a fake full name
        });
    }
}