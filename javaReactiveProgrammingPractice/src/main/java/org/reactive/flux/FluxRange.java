package org.reactive.flux;

import org.reactive.DefaultSubscriber.Util;
import reactor.core.publisher.Flux;

public class FluxRange {
    public static void main(String[] args) {
        /*
        Flux.range creates a sequence of integers starting from a given number.
        The first parameter is the starting number.
        The second parameter specifies how many items to emit that number.
        */

        // Emit a sequence of 10 integers starting from 1
        Flux.range(1, 10)
                .subscribe(Util.subscriber("sub1")); // Subscriber 1 processes integers 1 to 10

        // Emit a sequence of 10 integers starting from 2
        Flux.range(2, 10)
                .subscribe(Util.subscriber("sub2")); // Subscriber 2 processes integers 2 to 11

        // Emit a sequence of 10 integers starting from 1
        // Map each integer to a random full name using the Faker library
        Flux.range(1, 10)
                .map(i -> Util.faker().name().fullName()) // Transform integers into random names
                .subscribe(Util.subscriber("sub3")); // Subscriber 3 processes the transformed names
    }
}