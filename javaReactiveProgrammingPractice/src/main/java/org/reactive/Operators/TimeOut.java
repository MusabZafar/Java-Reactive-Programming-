package org.reactive.Operators;

import org.reactive.DefaultSubscriber.Util;
import reactor.core.publisher.Mono;

import java.time.Duration;

/*
    timeout - will produce timeout error.
        - We can handle as part of onError methods
    there is also an overloaded method to accept a publisher
    we can have multiple timeouts. the closest one to the subscriber will take effect for the subscriber.
 */
public class TimeOut {
    public static void main(String[] args) {
        // 1. Simple timeout with a fallback value using onErrorReturn
        getProductName()
                .timeout(Duration.ofSeconds(1)) // Timeout after 1 second
                .onErrorReturn("fallback") // Return "fallback" if timeout occurs
                .subscribe(Util.subscriber("Timeout 1"));

        // 2. Timeout with an alternative publisher (fallback method)
        getProductName()
                .timeout(Duration.ofSeconds(1), fallback()) // Switch to fallback publisher on timeout
                .onErrorReturn("fallback") // If fallback also fails, return "fallback"
                .subscribe(Util.subscriber("Timeout with Fallback"));

        // 3. Timeout with faster stream (less than the timeout duration)
        getProductNameInMillis()
                .timeout(Duration.ofSeconds(1)) // Timeout after 1 second
                .onErrorReturn("fallback") // Fallback value on timeout
                .subscribe(Util.subscriber("No Timeout"));

        // Keep the main thread alive to observe the results
        try {
            Util.sleepSeconds(5);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    // Simulate a delayed product name retrieval (3 seconds delay)
    private static Mono<String> getProductName() {
        return Mono.fromSupplier(() -> "Service " + Util.faker().commerce().productName())
                .delayElement(Duration.ofSeconds(3)); // Simulated delay
    }

    // Simulate a fallback method that produces a product name
    private static Mono<String> fallback() {
        return Mono.fromSupplier(() -> "fallback- " + Util.faker().commerce().productName())
                .delayElement(Duration.ofSeconds(3)); // Simulated delay
    }

    // Simulate a fast product name retrieval (900ms delay)
    private static Mono<String> getProductNameInMillis() {
        return Mono.fromSupplier(() -> Util.faker().commerce().productName())
                .delayElement(Duration.ofMillis(900)); // Fast response
    }
}


/*
    timeout - will produce timeout error.
        - We can handle as part of onError methods
    there is also an overloaded method to accept a publisher
    we can have multiple timeouts. the closest one to the subscriber will take effect for the subscriber.
 */
//public class Lec09Timeout {
//
//    private static final Logger log = LoggerFactory.getLogger(Lec09Timeout.class);
//
//    public static void main(String[] args) {
//
//        getProductName()
//                .timeout(Duration.ofSeconds(1), fallback())
//                .subscribe(Util.subscriber());
//
//        Util.sleepSeconds(5);
//
//    }
//
//    private static Mono<String> getProductName() {
//        return Mono.fromSupplier(() -> "service-" + Util.faker().commerce().productName())
//                .delayElement(Duration.ofMillis(1900));
//    }
//
//    private static Mono<String> fallback() {
//        return Mono.fromSupplier(() -> "fallback-" + Util.faker().commerce().productName())
//                .delayElement(Duration.ofMillis(300))
//                .doFirst(() -> log.info("do first"));
//    }
//
//}
