package org.reactive.HotAndColdPublishers;

import org.reactive.DefaultSubscriber.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.util.concurrent.atomic.AtomicInteger;

public class ColdPublisher {

    public static final Logger logger = LoggerFactory.getLogger(ColdPublisher.class);

    public static void main(String[] args) {

        // AtomicInteger is a thread-safe integer to keep track of values
        AtomicInteger atomicInteger = new AtomicInteger(0);

        /*
         * Flux.create() is used to programmatically create a Flux.
         * Here, it acts as a Cold Publisher because the logic inside Flux.create
         * will run independently for each subscriber.
         */
        var flux = Flux.create(fluxSink -> {
            logger.info("invoked"); // Logs every time the Flux is invoked for a new subscriber
            for (int i = 0; i < 10; i++) {
                fluxSink.next(atomicInteger.getAndIncrement()); // Emit a series of numbers incrementally
            }
            fluxSink.complete(); // Signal that the data emission is complete
        });

        /*
         * Subscribe with the first subscriber ("sub1")
         * - The "invoked" message will appear because Flux logic is re-invoked.
         * - The subscriber receives numbers from 0 to 9.
         */
        flux.subscribe(Util.subscriber("sub1"));

        /*
         * Subscribe with the second subscriber ("sub2")
         * - The Flux logic runs again from the beginning.
         * - The "invoked" message will appear again because Cold Publishers start afresh for each subscription.
         * - This subscriber also receives numbers starting from 10 (due to AtomicInteger's state).
         */
        flux.subscribe(Util.subscriber("sub2"));
    }
}