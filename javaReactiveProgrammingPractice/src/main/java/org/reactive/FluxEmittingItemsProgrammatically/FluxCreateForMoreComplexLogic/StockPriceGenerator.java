package org.reactive.FluxEmittingItemsProgrammatically.FluxCreateForMoreComplexLogic;

import reactor.core.publisher.FluxSink;

import java.util.function.Consumer;

public class StockPriceGenerator implements Consumer<FluxSink<Double>> {

    private FluxSink<Double> sink;

    @Override
    public void accept(FluxSink<Double> fluxSink) {
        this.sink = fluxSink;
    }

    // Emit a random stock price
    public void emitPrice() {
        double price = 100 + (Math.random() * 20 - 10); // Random price between 90 and 110
        sink.next(price);
    }

    // Signal the stream has completed
    public void complete() {
        sink.complete();
    }

    // Emit an error
    public void emitError(Exception e) {
        sink.error(e);
    }
}
