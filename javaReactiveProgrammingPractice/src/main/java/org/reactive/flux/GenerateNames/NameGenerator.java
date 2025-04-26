package org.reactive.flux.GenerateNames;

import org.reactive.DefaultSubscriber.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import java.awt.*;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.IntStream;

public abstract class NameGenerator<T> implements Consumer<FluxSink<T>> {

    // Generate a list of names with the given count
    public static List<String> getNamesList(int count) {
        // Create a list using IntStream, generate a name for each index
        return IntStream.rangeClosed(1, count)
                .mapToObj(i -> generateName()) // Generate name for each element
                .toList(); // Convert to List
    }

    // Generate a Flux of names with the given count
    public static Flux<String> getNamesListFlux(int count) {
        // Use Flux.range to emit a range of integers and map them to names
        return Flux.range(1, count)
                .map(i -> generateName()); // Generate name for each element
    }

    // Simulate name generation (e.g., fetching data from a service)
    private static String generateName() {
        try {
            Util.sleepSeconds(1); // Simulate a delay for name generation
        } catch (InterruptedException e) {
            throw new RuntimeException(e); // Handle interruption
        }
        return Util.faker().name().fullName(); // Generate a random full name
    }
}
