package org.reactive.mono;

import org.reactive.DefaultSubscriber.Util;
import org.reactive.mono.client.ExternalServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
        To demo non-blocking IO
        Ensure that the external service is up and running
 */
public class NonBlockingIO {
    // Logger instance for logging
    public static final Logger logger = LoggerFactory.getLogger(NonBlockingIO.class);

    public static void main(String[] args) throws InterruptedException {
        // Create an instance of the ExternalServiceClient
        var client = new ExternalServiceClient();

        logger.info("Starting non-blocking IO");
        // Loop through and make multiple non-blocking requests
        for (int i = 1; i <= 100; i++) {
            client.getProductDetails(String.valueOf(i)) // Fetch product name for each ID
                    .subscribe(Util.subscriber()); // Subscribe to handle the response
        }

        // Wait to ensure all asynchronous requests complete
        Util.sleepSeconds(2);
    }
}
//public class NonBlockingIO2 {
//    private static final Logger log = LoggerFactory.getLogger(NonBlockingIO2.class);
//
//    public static void main(String[] args) throws InterruptedException {
//        // Create an instance of the client
//        var client = new ExternalServiceClient();
//
//        log.info("Starting non-blocking IO requests");
//
//        // Make 10 concurrent requests to fetch product details
//        for (int i = 1; i <= 10; i++) {
//            client.getProductDetails(String.valueOf(i))
//                    .doOnSubscribe(s -> log.info("Requesting product {}", i)) // Log when the request starts
//                    .doOnNext(response -> log.info("Response received: {}", response)) // Log the response
//                    .doOnError(error -> log.error("Error occurred for product {}: {}", i, error.getMessage())) // Handle errors
//                    .doOnComplete(() -> log.info("Request completed for product {}", i)) // Log completion
//                    .subscribe(); // Trigger the execution
//        }
//
//        // Sleep to allow asynchronous requests to complete
//        Thread.sleep(5000);
//    }
//}


//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import reactor.core.publisher.Mono;
//import reactor.netty.http.client.HttpClient;
//import reactor.netty.resources.LoopResources;

//public class AbstractHttpClient {
//    // Base URL for the API
//    private static final String BASE_URL = "https://localhost:7878/";
//    // Shared HttpClient instance
//    protected final HttpClient client;
//
//    public AbstractHttpClient(HttpClient client) {
//        // Create a LoopResource for managing event loops, limiting to 1 thread
//        var loopResources = LoopResources.create("Musab", 1, true);
//        // Configure the HttpClient with base URL and loop resources
//        this.client = HttpClient.create()
//                .runOn(loopResources) // Attach the custom loop resource
//                .baseUrl(BASE_URL);   // Set the base URL for API calls
//    }
//}
//
//public class ExternalServiceClient extends AbstractHttpClient {
//    // Constructor to initialize the parent class with an HttpClient
//    public ExternalServiceClient() {
//        super(HttpClient.create());
//    }
//
//    // Method to get a product name by its ID
//    public Mono<String> getProductName(String productId) {
//        return this.client.get() // Create a GET request
//                .uri("/products/" + productId) // Set the URI with product ID
//                .responseContent() // Get the response content
//                .aggregate() // Aggregate the response
//                .asString(); // Convert the response content to a String
//    }
//}
//
//public class NonBlockingIO {
//    // Logger instance for logging
//    public static final Logger logger = LoggerFactory.getLogger(NonBlockingIO.class);
//
//    public static void main(String[] args) throws InterruptedException {
//        // Create an instance of the ExternalServiceClient
//        var client = new ExternalServiceClient();
//
//        logger.info("Starting non-blocking IO");
//        // Loop through and make multiple non-blocking requests
//        for (int i = 1; i <= 100; i++) {
//            client.getProductName(String.valueOf(i)) // Fetch product name for each ID
//                    .subscribe(Util.subscriber()); // Subscribe to handle the response
//        }
//
//        // Wait to ensure all asynchronous requests complete
//        Util.sleepSeconds(2);
//    }
//}