package org.reactive.flux.StockService;

import org.reactive.DefaultSubscriber.Util;
import reactor.netty.http.client.HttpClient;

public class Assignment {
    public static void main(String[] args) {
        // Instantiate the external service client
        var client = new ExternalServiceClient(HttpClient.create());

        // Create a subscriber to observe stock price changes
        var subscriber = new StockPriceObserver();

        // Subscribe to the price change stream
        client.getPriceChanges().subscribe(subscriber);

        // Keep the application alive for 20 seconds to observe the stream
        try {
            Util.sleepSeconds(20);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
/*
    Purpose:
        Integrates the external client and stock price observer.
        Subscribes to the price stream and applies the trading logic.

    Reactive Workflow:
        client.getPriceChanges(): Fetches the price stream as a Flux.
        .subscribe(subscriber): Subscribes the StockPriceObserver to process the price stream.

    Thread Sleep:
        Keeps the application alive for 20 seconds to allow the streaming process to complete.

Key Points

    Reactive Integration:
        The ExternalServiceClient fetches data reactively from an external service.
        The StockPriceObserver applies business logic in response to streamed data.

    Separation of Concerns:
        ExternalServiceClient handles HTTP communication.
        StockPriceObserver handles data processing and trading logic.

    Non-Blocking and Scalable:
        The entire workflow is reactive and non-blocking, suitable for real-time stock price monitoring.
 */