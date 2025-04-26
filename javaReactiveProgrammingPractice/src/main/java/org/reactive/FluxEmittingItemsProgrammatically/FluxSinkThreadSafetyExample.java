package org.reactive.FluxEmittingItemsProgrammatically;

import org.reactive.DefaultSubscriber.Util;
import org.reactive.FluxEmittingItemsProgrammatically.FluxCreateForMoreComplexLogic.NameGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.util.ArrayList;

public class FluxSinkThreadSafetyExample {
    public static final Logger logger = LoggerFactory.getLogger(FluxSinkThreadSafetyExample.class);

    public static void main(String[] args) {
        // Demonstrating a non-thread-safe operation with ArrayList
        notThreadSafeExample();

        // Demonstrating a thread-safe operation using FluxSink
        fluxSinkThreadSafeExample();
    }

    // Example: Using ArrayList in a non-thread-safe manner
    private static void notThreadSafeExample() {
        var list = new ArrayList<Integer>(); // Non-thread-safe ArrayList

        // Runnable task to add integers to the list
        Runnable runnable = () -> {
            for (int i = 0; i < 1000; i++) {
                list.add(i); // Add integers to the list
            }
        };

        // Launch 10 threads to execute the task concurrently
        for (int i = 0; i < 10; i++) {
            Thread.ofPlatform().start(runnable); // Start threads using Java 21's virtual threads
        }

        // Wait for all threads to complete execution
        try {
            Util.sleepSeconds(3);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Print the size of the list
        logger.info("List size (non-thread-safe): {}", list.size());
    }

    // Example: Using FluxSink to handle thread-safety
    private static void fluxSinkThreadSafeExample() {
        var list = new ArrayList<String>(); // Non-thread-safe ArrayList to store emitted items
        var nameGenerator = new NameGenerator(); // Name generator to emit names
        var flux = Flux.create(nameGenerator); // Create a Flux using the name generator

        // Subscribe to the Flux and add emitted names to the list
        flux.subscribe(list::add);

        // Runnable task to generate names
        Runnable runnable = () -> {
            for (int i = 0; i < 1000; i++) {
                nameGenerator.generate(); // Emit a new name
            }
        };

        // Launch 10 threads to execute the task concurrently
        for (int i = 0; i < 10; i++) {
            Thread.ofPlatform().start(runnable); // Start threads using Java 21's virtual threads
        }

        // Wait for all threads to complete execution
        try {
            Util.sleepSeconds(3);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Print the size of the list
        logger.info("List size (thread-safe with FluxSink): {}", list.size());
    }
}
