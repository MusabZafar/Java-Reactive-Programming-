package org.reactive.Operators;

import org.reactive.DefaultSubscriber.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

public class DoCallbacks {

    // Logger to track the lifecycle of the reactive stream
    private static final Logger log = LoggerFactory.getLogger(DoCallbacks.class);

    public static void main(String[] args) {

        // Create a Flux programmatically using Flux.create
        Flux.<Integer>create(fluxSink -> {
                    log.info("producer begins"); // Log when the producer starts
                    for (int i = 0; i < 4; i++) {
                        fluxSink.next(i); // Emit items 0 to 3
                    }
                    fluxSink.complete(); // Signal the stream's completion
                    log.info("producer ends"); // Log when the producer ends
                })
                // ----------------- Lifecycle Hooks/Callbacks -----------------
                // doFirst: Executes callbacks BEFORE anything else in the pipeline (in reverse order of declaration)
                .doFirst(() -> log.info("doFirst-1")) // Runs 2nd
                .doFirst(() -> log.info("doFirst-2")) // Runs 1st

                // doOnSubscribe: Executes when a subscription is made to the stream
                .doOnSubscribe(subscription -> log.info("doOnSubscribe-1: {}", subscription))
                .doOnSubscribe(subscription -> log.info("doOnSubscribe-2: {}", subscription))

                // doOnRequest: Executes when the subscriber requests items
                .doOnRequest(request -> log.info("doOnRequest-1: {}", request))
                .doOnRequest(request -> log.info("doOnRequest-2: {}", request))

                // doOnNext: Executes for each emitted item in the stream
                .doOnNext(item -> log.info("doOnNext-1: {}", item))
                .doOnNext(item -> log.info("doOnNext-2: {}", item))

                // doOnComplete: Executes when the stream completes successfully
                .doOnComplete(() -> log.info("doOnComplete-1"))
                .doOnComplete(() -> log.info("doOnComplete-2"))

                // doOnTerminate: Executes when the stream ends (either by completion or error)
                .doOnTerminate(() -> log.info("doOnTerminate-1"))
                .doOnTerminate(() -> log.info("doOnTerminate-2"))

                // doOnError: Executes when an error occurs
                .doOnError(error -> log.info("doOnError-1: {}", error.getMessage()))
                .doOnError(error -> log.info("doOnError-2: {}", error.getMessage()))

                // doOnCancel: Executes when the subscriber cancels the subscription
                .doOnCancel(() -> log.info("doOnCancel-1"))
                .doOnCancel(() -> log.info("doOnCancel-2"))

                // doOnDiscard: Executes when items are discarded due to cancellation or backpressure
                .doOnDiscard(Object.class, o -> log.info("doOnDiscard-1: {}", o))
                .doOnDiscard(Object.class, o -> log.info("doOnDiscard-2: {}", o))

                // doFinally: Executes at the very end of the stream lifecycle
                .doFinally(signal -> log.info("doFinally-1: {}", signal))
                .doFinally(signal -> log.info("doFinally-2: {}", signal))

                // Subscribe to the Flux to trigger the callbacks and lifecycle hooks
                .subscribe(Util.subscriber("subscriber"));
    }
}