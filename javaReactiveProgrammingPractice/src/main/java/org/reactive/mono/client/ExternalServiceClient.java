package org.reactive.mono.client;

import org.reactive.DefaultSubscriber.AbstractHttpClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

public class ExternalServiceClient extends AbstractHttpClient {
    // Constructor initializing the base HTTP client
    public ExternalServiceClient() {
        super(HttpClient.create());
    }

    // Method to fetch product details by ID
    public Mono<String> getProductDetails(String productId) {
        return this.client.get() // Make a GET request
                .uri("/products/" + productId) // Append the product ID to the URI
                .responseContent() // Extract the response content
                .aggregate() // Aggregate the response into a single chunk
                .asString(); // Convert the response to a String
    }
}