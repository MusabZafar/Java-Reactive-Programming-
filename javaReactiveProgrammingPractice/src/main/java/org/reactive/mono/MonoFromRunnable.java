package org.reactive.mono;
import org.reactive.DefaultSubscriber.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

public class MonoFromRunnable {
    // Logger instance for logging events
    public static final Logger logger = LoggerFactory.getLogger(MonoFromRunnable.class);

    public static void main(String[] args) {
        // Example usage of getProductName
        getProductName(1)
                .subscribe(Util.subscriber()); // Simulate subscription for product ID 1

        getProductName(99)
                .subscribe(Util.subscriber()); // Simulate subscription for an unavailable product
    }

    // Method to get a product name based on productId
    private static Mono<String> getProductName(int productId) {
        if (productId == 1) {
            // If product ID is 1, simulate fetching a product name
            return Mono.fromSupplier(() -> Util.faker().commerce().productName());
        }
        // For other product IDs, notify the business of the unavailable product
        return Mono.fromRunnable(() -> notifyBusiness(productId));
    }

    // Notify business logic for unavailable products
    private static void notifyBusiness(int productId) {
        logger.info("Notify business on unavailable product {}", productId);
    }
}
