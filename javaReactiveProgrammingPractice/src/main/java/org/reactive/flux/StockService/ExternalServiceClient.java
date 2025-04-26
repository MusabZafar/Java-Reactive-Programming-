package org.reactive.flux.StockService;

import org.reactive.DefaultSubscriber.AbstractHttpClient;
import reactor.core.publisher.Flux;
import reactor.netty.http.client.HttpClient;

public class ExternalServiceClient extends AbstractHttpClient {

    // Constructor to initialize the client
    public ExternalServiceClient(HttpClient client) {
        super(client); // Pass the HttpClient instance to the parent class
    }

    // Method to fetch price changes from an external service
    public Flux<Integer> getPriceChanges() {
        return this.client.get() // Perform a GET request
                .uri("/stock/stream") // Specify the URI for the stock price stream
                .responseContent() // Extract the response body
                .asString() // Convert the response body to a Flux<String>
                .map(i -> Integer.parseInt(i)); // Parse each string into an integer
        // Alternative using method reference:
        // .map(Integer::parseInt);
    }
}

/*
Purpose:

    Fetches stock price changes as a reactive stream (Flux).
    Converts the stream of strings (received from the HTTP response) into integers.

Reactive Workflow:

    responseContent: Extracts the response body as a stream of data.
    asString: Converts the raw response content to a Flux<String>.
    map: Parses each string into an integer to represent stock prices.
 */
