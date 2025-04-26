package org.reactive.flux;

import org.reactive.DefaultSubscriber.Util;
import reactor.core.publisher.Flux;

import java.util.List;


public class FluxFromArray {
    public static void main(String[] args) {
        // Create a list of integers
        var list = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        // Create a Flux from an Iterable (List) and subscribe to it
        Flux.fromIterable(list)
                .subscribe(System.out::println); // Print each element to the console

        // Create an array of integers
        Integer[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        // Create a Flux from an Array and subscribe to it using a custom subscriber
        Flux.fromArray(array)
                .subscribe(Util.subscriber()); // Use a custom subscriber to process the data
    }
}
