package org.reactive.flux.client;

import org.reactive.DefaultSubscriber.AbstractHttpClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

public class ExternalServiceClient extends AbstractHttpClient {

    // Constructor initializing the base HTTP client
    public ExternalServiceClient() {
        super(HttpClient.create()); // Use Reactor Netty's HttpClient for making HTTP requests
    }

    // Method to fetch a continuous stream of names
    public Flux<String> getNames() {
        return this.client.get() // Make a GET request
                .uri("/name/stream/") // Specify the URI for the name stream endpoint
                .responseContent() // Extract the response content (streamed response body)
                .asString(); // Convert the response content to a Flux<String>
    }
}
