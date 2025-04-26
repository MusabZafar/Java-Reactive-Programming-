package org.reactive.FluxEmittingItemsProgrammatically.DefaultFluxSInk;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomSubscriber<T> implements Subscriber<T> {

    private static final Logger log = LoggerFactory.getLogger(CustomSubscriber.class);

    private Subscription subscription;
    private final int initialRequest;
    private final String name;

    public CustomSubscriber(String name, int initialRequest) {
        this.name = name; // Subscriber name for identification
        this.initialRequest = initialRequest; // Initial number of items to request
    }

    public CustomSubscriber(String name, int initialRequest, int additionalRequest) {
        this(name, initialRequest); // Call primary constructor
        requestMore(additionalRequest); // Simulate additional requests
    }

    @Override
    public void onSubscribe(Subscription subscription) {
        this.subscription = subscription; // Save the subscription reference
        log.info("[{}] Subscribed and requesting {} items", name, initialRequest);
        subscription.request(initialRequest); // Request the initial set of items
    }

    @Override
    public void onNext(T item) {
        log.info("[{}] Received: {}", name, item); // Log received item
    }

    @Override
    public void onError(Throwable throwable) {
        log.error("[{}] Error occurred: {}", name, throwable.getMessage(), throwable); // Log the error
    }

    @Override
    public void onComplete() {
        log.info("[{}] Completed!", name); // Log when the stream completes
    }

    // Method to request more items
    public void requestMore(int n) {
        if (subscription != null) {
            log.info("[{}] Requesting {} more items", name, n);
            subscription.request(n);
        } else {
            log.warn("[{}] Subscription is null, cannot request more items", name);
        }
    }

    // Method to cancel the subscription
    public void cancel() {
        if (subscription != null) {
            log.info("[{}] Canceling subscription", name);
            subscription.cancel();
        } else {
            log.warn("[{}] Subscription is null, cannot cancel", name);
        }
    }
}
