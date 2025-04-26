package org.reactive.Operators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

public class SubscriberOperator {
    private static final Logger log = LoggerFactory.getLogger(SubscriberOperator.class);

    public static void main(String[] args) {
        // Flux.range generates a stream of integers from 1 to 10
        Flux.range(1, 10)
                // doOnNext hook is triggered whenever an item is emitted
                .doOnNext(i -> log.info("received: {}", i)) // Logs each emitted item
                // doOnComplete hook is executed when the Flux completes successfully
                .doOnComplete(() -> log.info("completed")) // Logs when the stream completes
                // doOnError hook is triggered when an error occurs in the stream
                .doOnError(err -> log.error("error", err)) // Logs errors in the stream
                // subscribe method starts the stream processing
                .subscribe(); // Starts the stream but does nothing with emitted items
    }

    public static void main1(String[] args) {
        Flux.range(1, 10)
                .doOnNext(i -> log.info("received: {}", i))
                .doOnComplete(() -> log.info("completed"))
                .doOnError(err -> log.error("error", err))
                // Custom logic for the subscriber
                .subscribe(
                        item -> log.info("Processing item: {}", item), // OnNext logic
                        err -> log.error("Error occurred: {}", err.getMessage()), // OnError logic
                        () -> log.info("All items processed successfully") // OnComplete logic
                );
    }
}
