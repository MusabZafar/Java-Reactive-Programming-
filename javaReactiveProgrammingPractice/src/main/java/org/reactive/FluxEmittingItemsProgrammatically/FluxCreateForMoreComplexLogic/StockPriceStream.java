package org.reactive.FluxEmittingItemsProgrammatically.FluxCreateForMoreComplexLogic;

import reactor.core.publisher.Flux;

public class StockPriceStream {
    public static void main(String[] args) {
        var stockPriceGenerator = new StockPriceGenerator();

        // Create a Flux using StockPriceGenerator
        var flux = Flux.create(stockPriceGenerator);

        // Subscribe to the stream
        flux.subscribe(
                price -> System.out.println("Received stock price: $" + price),
                error -> System.err.println("Error occurred: " + error),
                () -> System.out.println("Price stream completed!")
        );

        // Simulate emitting stock prices dynamically
        for (int i = 0; i < 10; i++) {
            stockPriceGenerator.emitPrice(); // Emit stock price
            try {
                Thread.sleep(500); // Simulate a delay in price updates
            } catch (InterruptedException e) {
                stockPriceGenerator.emitError(e); // Signal an error if interrupted
            }
        }

        // Complete the stream after emitting all prices
        stockPriceGenerator.complete();
    }
}

