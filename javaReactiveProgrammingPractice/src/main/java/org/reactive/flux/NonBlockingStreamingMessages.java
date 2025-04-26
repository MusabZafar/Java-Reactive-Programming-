package org.reactive.flux;

import ch.qos.logback.core.net.server.Client;
import org.reactive.DefaultSubscriber.Util;
import org.reactive.flux.client.ExternalServiceClient;

public class NonBlockingStreamingMessages {
    public static void main(String[] args) throws InterruptedException {

        // Instantiate the external service client
        var client = new ExternalServiceClient();

        // Subscribe to the name stream with the first subscriber
        client.getNames()
                .subscribe(Util.subscriber("sub1")); // "sub1" processes the streamed names

        // Subscribe to the name stream with the second subscriber
        client.getNames()
                .subscribe(Util.subscriber("sub2")); // "sub2" processes the same streamed names

        // Sleep the main thread to allow time for the streaming to complete
        // (in a real-world application, the program would continue running reactively)
        Util.sleepSeconds(6);
    }
}