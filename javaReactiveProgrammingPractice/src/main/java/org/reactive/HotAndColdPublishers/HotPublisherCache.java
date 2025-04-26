package org.reactive.HotAndColdPublishers;

import org.reactive.DefaultSubscriber.Util;
import org.reactive.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.awt.event.MouseAdapter;
import java.time.Duration;

/**
 * Demonstrates the use of Hot Publishers with autoConnect(0) and replay().autoConnect(0)
 * - autoConnect(0): Starts emitting data immediately, even if no subscribers are present.
 * - replay(): Allows new subscribers to see past emissions (caches data).
 */
public class HotPublisherCache {

    // Logger to log events
    public static final Logger logger = LoggerFactory.getLogger(HotPublisherCache.class);

    public static void main(String[] args) throws InterruptedException {

        /**
         * Example 1: Hot Publisher with autoConnect(0)
         * - autoConnect(0) starts emitting data immediately when the stream is created.
         * - Late subscribers will miss previously emitted data (no caching).
         */
        var stockFlux = stockStream()
                .publish() // Convert to a Hot Publisher
                .autoConnect(0); // Start immediately, even without subscribers

        Util.sleepSeconds(4); // Simulate delay before the first subscriber joins

        logger.info("Musab Joining");
        stockFlux
                .subscribe(Util.subscriber("musab")); // First subscriber joins

        Util.sleepSeconds(4); // Simulate delay before the second subscriber joins

        logger.info("Ali Joining");
        stockFlux
                .subscribe(Util.subscriber("Ali")); // Second subscriber joins (misses earlier data)

        Util.sleepSeconds(15); // Allow the stream to continue emitting data

        /**
         * Example 2: Hot Publisher with replay().autoConnect(0)
         * - replay() caches emitted items so late subscribers receive past emissions.
         * - autoConnect(0) starts emitting data immediately, regardless of subscribers.
         */
        var stockFluxReplayCache = stockStream()
                .replay() // Cache previously emitted data
                .autoConnect(0); // Start immediately

        Util.sleepSeconds(4); // Simulate delay before the first subscriber joins

        logger.info("Musab Joining");
        stockFluxReplayCache
                .subscribe(Util.subscriber("musab")); // First subscriber joins

        Util.sleepSeconds(4); // Simulate delay before the second subscriber joins

        logger.info("Ali Joining");
        stockFluxReplayCache
                .subscribe(Util.subscriber("Ali")); // Second subscriber joins (receives cached data)

        Util.sleepSeconds(15); // Allow the stream to continue emitting data
    }

    /**
     * Simulates a stock price stream:
     * - Generates random stock prices between 10 and 100.
     * - Emits a new price every 1 second.
     */
    private static Flux<Integer> stockStream() {
        return Flux.generate(sink -> sink.next(Util.faker().random().nextInt(10, 100))) // Generate random stock prices
                .delayElements(Duration.ofSeconds(1)) // Emit a new price every 1 second
                .doOnNext(price -> logger.info("emitting price: {}", price)) // Log each emitted price
                .cast(Integer.class); // Cast to Integer type
    }
}

