package org.reactive.flux.StockService;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StockPriceObserver implements Subscriber<Integer> {

    // Logger instance for logging events
    public static final Logger logger = LoggerFactory.getLogger(StockPriceObserver.class);

    private int quantity = 0; // Number of stocks owned
    private int balance = 1000; // Initial balance
    private Subscription subscription; // Reactive subscription to control flow

    @Override
    public void onSubscribe(Subscription subscription) {
        // Request all items (unbounded)
        subscription.request(Long.MAX_VALUE);
        this.subscription = subscription;
    }

    @Override
    public void onNext(Integer price) {
        // Buy stock if price < 90 and sufficient balance is available
        if (price < 90 && price <= balance) {
            quantity++; // Increase stock quantity
            balance -= price; // Deduct price from balance
            logger.info("Bought a stock at {}, total quantity: {}, remaining balance: {}", price, quantity, balance);
        }
        // Sell all stocks if price > 110 and stocks are available
        else if (price > 110 && quantity > 0) {
            logger.info("Selling {} quantities at {}", quantity, price);
            balance += (quantity * price); // Add profit to balance
            quantity = 0; // Reset stock quantity
            subscription.cancel(); // Stop further subscription after selling
            logger.info("Profit: {}", balance - price);
        }
    }

    @Override
    public void onError(Throwable throwable) {
        // Log any error encountered during the subscription
        logger.error("Error occurred", throwable);
    }

    @Override
    public void onComplete() {
        // Log when the subscription completes
        logger.info("Price stream completed");
    }
}

/*
Purpose:

    Implements a stock trading strategy based on price changes.
    Buys stocks when prices are low (< 90).
    Sells stocks when prices are high (> 110).

Reactive Workflow:

    onSubscribe: Requests all available items from the publisher.
    onNext: Processes each price, buying or selling stocks based on conditions.
    onError: Handles errors during the stream.
    onComplete: Logs when the stream completes.

Stock Trading Logic:

    Buying:
        Only buys if the price is below 90 and there is enough balance.
    Selling:
        Sells all stocks if the price is above 110.
        Cancels the subscription after selling.
 */