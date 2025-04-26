package org.reactive.HotAndColdPublishers;

import org.reactive.DefaultSubscriber.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.time.Duration;

/**
 * Hot Publisher:
 * - Demonstrates share(), publish().refCount(), autoConnect(), and autoConnect(0) methods.
 * - Hot publishers share the data source among multiple subscribers.
 */
public class HotPublisherAutoConnect {
    public static final Logger logger = LoggerFactory.getLogger(HotPublisher.class);

    public static void main(String[] args) throws InterruptedException {

        /**
         * Example 1: Hot Publisher with share()
         * - share() starts emitting data immediately when there is at least one subscriber.
         * - Late subscribers miss previously emitted items.
         */
        var movieFlux = movieStream().share();
        Util.sleepSeconds(2);

        movieFlux
                .take(4)
                .subscribe(Util.subscriber("musab")); // First subscriber
        Util.sleepSeconds(3);

        movieFlux
                .take(4)
                .subscribe(Util.subscriber("ali")); // Second subscriber
        Util.sleepSeconds(15);

        /**
         * Example 2: Hot Publisher with publish().refCount(2)
         * - Starts emitting only when there are at least 2 subscribers.
         * - Stops when there are 0 subscribers.
         */
        var movieFlux1 = movieStream().publish().refCount(2);
        Util.sleepSeconds(2);

        movieFlux1
                .take(4)
                .subscribe(Util.subscriber("musab")); // First subscriber
        Util.sleepSeconds(3);

        movieFlux1
                .take(4)
                .subscribe(Util.subscriber("ali")); // Second subscriber
        Util.sleepSeconds(15);

        /**
         * Example 3: Hot Publisher with autoConnect()
         * - autoConnect(1): Starts emitting data when at least 1 subscriber connects.
         * - Unlike share(), previously emitted data is not missed by late subscribers.
         */
        var movieFlux2 = movieStream().publish().autoConnect(1); // Starts on 1 subscriber
        Util.sleepSeconds(2);

        movieFlux2
                .take(4)
                .subscribe(Util.subscriber("musab")); // First subscriber
        Util.sleepSeconds(3);

        movieFlux2
                .take(4)
                .subscribe(Util.subscriber("ali")); // Second subscriber
        Util.sleepSeconds(15);

        /**
         * Example 4: Hot Publisher with autoConnect(0)
         * - autoConnect(0) starts emitting immediately, even without subscribers.
         * - It behaves like share() but does not wait for subscribers to connect.
         */
        var movieFlux3 = movieStream().publish().autoConnect(0);
        Util.sleepSeconds(2);

        movieFlux3
                .take(4)
                .subscribe(Util.subscriber("musab")); // First subscriber
        Util.sleepSeconds(3);

        movieFlux3
                .take(4)
                .subscribe(Util.subscriber("ali")); // Second subscriber
        Util.sleepSeconds(15);
    }

    /**
     * Movie Stream Generator:
     * - Generates movie scenes programmatically.
     * - Simulates a real-time stream with delays between elements.
     */
    private static Flux<String> movieStream() {
        return Flux.generate(
                        () -> {
                            logger.info("received the request"); // Log when stream starts
                            return 1; // Initial state
                        },
                        (state, sink) -> {
                            var scene = "movie scene " + state; // Simulated movie scene
                            logger.info("playing {}", scene); // Log the scene being played
                            sink.next(scene); // Emit the scene
                            return ++state; // Increment the state
                        }
                )
                .take(10) // Limit the stream to 10 scenes
                .delayElements(Duration.ofSeconds(1)) // Delay each element by 1 second
                .cast(String.class);
    }
}
