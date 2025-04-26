package org.reactive.FluxEmittingItemsProgrammatically.FluxSinkExamples;

import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import java.util.concurrent.Executors;

public class EventDrivenUI {
    public static void main(String[] args) {
        // Create a Flux for UI events
        Flux<String> uiEventFlux = Flux.create(fluxSink -> {
            Runnable task = () -> {
                String[] events = {"Click", "Scroll", "Hover", "Resize", "Drag"};
                for (String event : events) {
                    if (fluxSink.isCancelled()) return; // Stop emitting if canceled
                    fluxSink.next("UI Event: " + event); // Emit event
                    try {
                        Thread.sleep(300); // Simulate delay between events
                    } catch (InterruptedException e) {
                        fluxSink.error(e); // Handle interruption
                    }
                }
                fluxSink.complete(); // Signal completion
            };

            // Run the task in a separate thread
            Executors.newSingleThreadExecutor().submit(task);
        });

        // Subscribe to the Flux to update the UI
        uiEventFlux.subscribe(
                event -> System.out.println("Processing: " + event), // Process each event
                err -> System.err.println("Error: " + err), // Handle errors
                () -> System.out.println("All events processed!") // Handle completion
        );
    }
}
