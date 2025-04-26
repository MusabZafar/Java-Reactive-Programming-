package org.reactive.Operators;

import org.reactive.DefaultSubscriber.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Similer to error handling empty
 */
public class DefaultIfEmpty {
    public static void main(String[] args) {
        // Example 1: Mono emits no value (empty stream)
        Mono.empty() // Creates an empty Mono
                .defaultIfEmpty("fallback") // Provides a fallback value when no items are emitted
                .subscribe(Util.subscriber("MonoEmpty")); // Subscribes and prints the value or fallback

        // Example 2: Mono emits a value (non-empty stream)
        Mono.just("Musab") // Mono with a non-empty value
                .defaultIfEmpty("fallback") // This fallback will not be used since Mono emits "Musab"
                .subscribe(Util.subscriber("MonoWithValue")); // Subscribes and prints the emitted value

        // Example 3: Flux emits no value after filtering
        Flux.range(1, 10) // Generates numbers 1 to 10
                .filter(i -> i > 11) // Filters all values since no value is > 11
                .defaultIfEmpty(1) // Provides a fallback value (1) when no items are emitted
                .subscribe(Util.subscriber("FluxDefaultIfEmpty")); // Subscribes and prints fallback
//
//        Mono<String> user = findUserById("123"); // Simulate a user search
//        user.defaultIfEmpty("User Not Found")
//                .subscribe(System.out::println);
//
//        Flux.range(1, 10)
//                .filter(i -> i > 10) // No numbers > 10
//                .defaultIfEmpty(0)   // Fallback value
//                .subscribe(System.out::println);
//
//
//        Mono.justOrEmpty(Optional.empty()) // Simulate an empty optional
//                .defaultIfEmpty("Default Value")
//                .subscribe(System.out::println);



    }
}

