package org.reactive.mono;

import org.reactive.DefaultSubscriber.Util;
import reactor.core.publisher.Mono;

//EMitting empty error
public class MonoEmptyError {
    public static void main(String[] args) {

        // Subscribe to the Mono returned by getUserName with a reusable subscriber
        getUserName(6)
                .subscribe(Util.subscriber()); // Handles data, errors, and completion
    }

    // Method to fetch a username based on userId
    private static Mono<String> getUserName(int userId) {
        return switch (userId) {
            case 1 -> Mono.just("John"); // Emit "John" for userId 1
            case 2 -> Mono.just("Jane"); // Emit "Jane" for userId 2
            case 3 -> Mono.empty();      // Emit no value for userId 3
            default -> Mono.error(new RuntimeException("Invalid input")); // Emit an error for any other userId
        };
    }
}
