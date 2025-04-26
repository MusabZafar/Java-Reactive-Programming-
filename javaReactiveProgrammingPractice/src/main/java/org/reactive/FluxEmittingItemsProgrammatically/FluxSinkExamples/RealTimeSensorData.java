package org.reactive.FluxEmittingItemsProgrammatically.FluxSinkExamples;

import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class RealTimeSensorData {
    public static void main(String[] args) {
        // Create a Flux for sensor data
        Flux<String> sensorFlux = Flux.create(fluxSink -> {
            Runnable task = () -> {
                for (int i = 1; i <= 10; i++) {
                    if (fluxSink.isCancelled()) return; // Stop emitting if subscription is canceled
                    String sensorData = "SensorReading: " + i; // Simulated sensor reading
                    fluxSink.next(sensorData); // Emit data into the sink
                    try {
                        Thread.sleep(500); // Simulate delay between readings
                    } catch (InterruptedException e) {
                        fluxSink.error(e); // Handle interruption
                    }
                }
                fluxSink.complete(); // Signal completion
            };

            // Run the task in a separate thread
            Executors.newSingleThreadExecutor().submit(task);
        });

        // Subscribe to the Flux and process the data
        sensorFlux.subscribe(
                data -> System.out.println("Received: " + data), // Handle each reading
                err -> System.err.println("Error: " + err), // Handle errors
                () -> System.out.println("Stream complete!") // Handle completion
        );
    }
}
