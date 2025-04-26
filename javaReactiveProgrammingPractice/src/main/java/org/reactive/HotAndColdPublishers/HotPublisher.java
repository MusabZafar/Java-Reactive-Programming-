package org.reactive.HotAndColdPublishers;

import org.reactive.DefaultSubscriber.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.time.Duration;

/**
 * Hot Publisher Concept:
 *
 * - A Hot Publisher shares the same data source (producer) among all subscribers.
 * - Unlike a Cold Publisher, it **starts emitting data immediately** and does not re-execute the logic for each new subscriber.
 * - `share()` acts as a shorthand for `publish().refCount(1)`, which makes the publisher hot.
 * - Subscribers may miss previously emitted data if they subscribe late.
 * - `publish().refCount(n)` ensures that the publisher starts emitting only after a minimum of 'n' subscribers connect.
 */
public class HotPublisher {
    public static final Logger logger = LoggerFactory.getLogger(HotPublisher.class);

    public static void main(String[] args) throws InterruptedException {

        /**
         * Example 1: Hot Publisher using share()
         * - share() makes the publisher hot.
         * - Late subscribers will miss the previously emitted elements.
         */
        var movieFlux = movieStream().share(); // share() makes it a Hot Publisher
        Util.sleepSeconds(2); // Delay to simulate late subscription

        movieFlux
                .take(4) // Take only the first 4 elements
                .subscribe(Util.subscriber("musab")); // First subscriber
        Util.sleepSeconds(3); // Delay before the second subscriber joins

        movieFlux
                .take(4) // Second subscriber joins
                .subscribe(Util.subscriber("ali"));
        Util.sleepSeconds(15); // Allow enough time for stream to complete

        /**
         * Example 2: Hot Publisher with publish().refCount(2)
         * - publish().refCount(2) ensures a minimum of 2 subscribers before emitting data.
         * - The producer starts emitting data only after 2 subscribers connect.
         */
        var movieFlux1 = movieStream().publish().refCount(2); // Requires 2 subscribers to emit data
        Util.sleepSeconds(2); // Delay to simulate late subscription

        movieFlux1
                .take(4)
                .subscribe(Util.subscriber("musab")); // First subscriber
        Util.sleepSeconds(3);

        movieFlux1
                .take(4)
                .subscribe(Util.subscriber("ali")); // Second subscriber
        Util.sleepSeconds(15);

        /**
         * Example 3: Another share() example to show late subscribers
         * - First subscriber takes 1 item and completes.
         * - Second subscriber will receive new data starting from where the stream continues.
         */
        var movieFlux3 = movieStream().share();
        Util.sleepSeconds(2);

        movieFlux3
                .take(1)
                .subscribe(Util.subscriber("musab")); // First subscriber
        Util.sleepSeconds(3);

        movieFlux3
                .take(4)
                .subscribe(Util.subscriber("ali")); // Second subscriber
        Util.sleepSeconds(15);
    }

    /**
     * Movie Stream Generator:
     * - This method generates a stream of movie scenes programmatically.
     * - The stream will produce "movie scene X" for each state.
     * - Delay of 1 second between emissions to simulate streaming.
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
                .take(3) // Limit the stream to 3 scenes
                .delayElements(Duration.ofSeconds(1)) // Delay each element by 1 second
                .cast(String.class); // Cast elements to String
    }
}
