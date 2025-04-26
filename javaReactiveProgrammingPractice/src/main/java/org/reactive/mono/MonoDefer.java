package org.reactive.mono;

import org.reactive.DefaultSubscriber.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.util.List;

public class MonoDefer {
    // Logger instance for logging events
    public static final Logger log = LoggerFactory.getLogger(MonoDefer.class);

    public static void main(String[] args) throws InterruptedException {

        // Example of using Mono.defer to defer the creation of the publisher
        Mono.defer(() -> {
                    try {
                        return createPublisher(); // Create the publisher lazily when subscribed
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e); // Wrap checked exceptions in a RuntimeException
                    }
                })
                .subscribe(Util.subscriber()); // Subscribe to the deferred Mono

        // Directly subscribing to the publisher without deferring
        createPublisher().subscribe(Util.subscriber());
    }

    // Method to create a publisher that emits the sum of a list
    private static Mono<Integer> createPublisher() throws InterruptedException {
        log.info("Creating publisher"); // Log the creation of the publisher
        // Sample list of integers
        var list = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        // Simulate some delay to show deferred execution behavior
        Util.sleepSeconds(1);

        // Return a Mono that emits the sum of the list using fromSupplier
        return Mono.fromSupplier(() -> {
            try {
                return sum(list); // Compute the sum of the list
            } catch (InterruptedException e) {
                throw new RuntimeException(e); // Wrap checked exceptions
            }
        });
    }

    // Time-consuming business logic
    // Method to calculate the sum of a list
    private static int sum(List<Integer> list) throws InterruptedException {
        log.info("Calculating sum of {} elements", list.size()); // Log computation
        Util.sleepSeconds(3); // Simulate a delay in computation
        return list.stream().mapToInt(a -> a).sum(); // Compute and return the sum
    }
}