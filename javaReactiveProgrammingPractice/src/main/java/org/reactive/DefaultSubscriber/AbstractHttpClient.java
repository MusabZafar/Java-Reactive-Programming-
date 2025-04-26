package org.reactive.DefaultSubscriber;

import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.LoopResources;

public class AbstractHttpClient {
    // Base URL for the API
    private static final String BASE_URL = "https://localhost:7878/";
    // Shared HttpClient instance
    protected final HttpClient client;

    public AbstractHttpClient(HttpClient client) {
        // Create a LoopResource for managing event loops, limiting to 1 thread
        var loopResources = LoopResources.create("Musab", 1, true);
        // Configure the HttpClient with base URL and loop resources
        this.client = HttpClient.create()
                .runOn(loopResources) // Attach the custom loop resource
                .baseUrl(BASE_URL);   // Set the base URL for API calls
    }
}