package org.reactive.flux;

import org.reactive.DefaultSubscriber.Util;
import reactor.core.publisher.Flux;

import java.util.List;

public class FluxFromStream {
    public static void main(String[] args) {
        // Create a list of integers
        var list = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        // Create a stream from the list
        var stream = list.stream(); // Java Stream can only be consumed once

        // Example 1: Using a Supplier to create a Flux with multiple subscribers
        var multipleflux = Flux.fromStream(() -> list.stream());
        // Each subscription creates a new stream, allowing independent consumption
        multipleflux.subscribe(Util.subscriber("Sub1")); // First subscriber
        multipleflux.subscribe(Util.subscriber("Sub2")); // Second subscriber

        // Example 2: Using a direct Stream to create a Flux
        var Oneflux = Flux.fromStream(stream);
        // Subscribing for the first time consumes the stream
        Oneflux.subscribe(Util.subscriber("Sub1")); // This works because the stream is consumed for the first time
        // Subscribing a second time fails because the stream has already been consumed
        Oneflux.subscribe(Util.subscriber("Sub2")); // This will throw an IllegalStateException
    }
}
/*
Explanation of the Code
1. Java Streams are Single-Use

    The stream object in Java can only be consumed once.
    Once a stream is processed (e.g., through a terminal operation like forEach), it is considered closed and cannot be reused.

2. Using Flux.fromStream(() -> list.stream())

    By providing a supplier (() -> list.stream()), you ensure that:
        A new stream is created for each subscriber.
        Each subscription to the Flux results in an independent data stream, avoiding the issue of a closed stream.

3. Using Flux.fromStream(stream)

    When you directly pass the stream to Flux.fromStream, the same stream is shared across all subscribers.
    This works for the first subscription, but any subsequent subscriptions will fail because the stream is already consumed.

Why Provide a Supplier for Multiple Subscribers?

The supplier (() -> list.stream()) ensures that:

    New Streams for Each Subscriber:
        Each subscription creates a fresh stream, allowing multiple subscribers to consume the same data independently.

    Avoid Stream Consumption Issues:
        A single-use stream does not work for multiple subscriptions. The supplier bypasses this limitation by providing a new stream each time.


 */