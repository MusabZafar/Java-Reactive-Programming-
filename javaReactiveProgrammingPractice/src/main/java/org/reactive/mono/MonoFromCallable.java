package org.reactive.mono;

import org.reactive.DefaultSubscriber.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.util.List;

public class MonoFromCallable {

    // Logger for logging computation or events
    private static final Logger log = LoggerFactory.getLogger(MonoFromCallable.class);

    public static void main(String[] args) {

        // Sample list of integers
        var list = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        // Use Mono.fromCallable to lazily compute the sum and handle checked exceptions
        Mono.fromCallable(() -> sum(list))
                .subscribe(Util.subscriber()); // Subscribe with a reusable subscriber to handle emitted data
    }

    // Method to calculate the sum of a list
    // This method throws an Exception to simulate checked exception handling
    private static int sum(List<Integer> list) throws Exception {
        log.info("Calculating sum of {} elements", list.size()); // Log the computation
        return list.stream().mapToInt(a -> a).sum(); // Compute and return the sum of the list
    }
}
