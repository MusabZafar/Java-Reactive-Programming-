package org.reactive.mono;

import org.reactive.DefaultSubscriber.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.util.List;

/*
    To delay the execution using supplier/callable
 */
public class MonoFromSupplier {
    private static final Logger log = LoggerFactory.getLogger(MonoFromSupplier.class);

    public static void main(String[] args) {

        // Sample list of integers
        var list = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        // Use Mono.fromSupplier to lazily compute the sum when subscribed to
        Mono.fromSupplier(() -> sum(list))
                .subscribe(Util.subscriber()); // Subscribe with reusable subscriber
    }

    // Method to calculate the sum of a list
    private static int sum(List<Integer> list) {
        log.info("Calculating sum of {} elements", list.size()); // Log computation
        return list.stream().mapToInt(a -> a).sum(); // Compute sum
    }
}
