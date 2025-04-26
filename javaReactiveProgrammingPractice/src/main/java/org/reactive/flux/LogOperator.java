package org.reactive.flux;

import org.reactive.DefaultSubscriber.Util;
import reactor.core.publisher.Flux;

public class LogOperator {
    public static void main(String[] args) {

        /*
        Flux.range(1, 10) creates a Flux that emits integers from 1 to 10.

        log() is a Reactor operator used for debugging purposes. It logs all the reactive
        signals (onSubscribe, request, onNext, onError, and onComplete) generated
        during the interaction between the publisher and the subscriber.

        The `log()` operator acts as a middleman:
        - It intercepts all requests made by the subscriber to the producer.
        - It logs the requests, responses, and events passing through it.
        - It helps visualize the flow of data and lifecycle events in the reactive pipeline.

        This is particularly useful for debugging and understanding reactive streams behavior.
         */

        // Create a Flux, log its lifecycle, transform data, and log the transformation
        Flux.range(1, 10)
                .log() // Logs the lifecycle events from producer to subscriber
                .map(i -> Util.faker().artist().name()) // Transform integers into artist names
                .log() // Logs the lifecycle events after the transformation
                .subscribe(Util.subscriber()); // Subscribe to process the emitted values
    }
}