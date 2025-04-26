package org.reactive.DefaultSubscriber;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// A reusable Subscriber implementation
public class DefaultSubscriber<T> implements Subscriber<T> {

    // Logger instance for logging events
    private static final Logger log = LoggerFactory.getLogger(DefaultSubscriber.class);

    // Name to identify this subscriber (useful for multiple subscribers)
    private final String name;

    // Constructor to set the name of the subscriber
    public DefaultSubscriber(String name) {
        this.name = name;
    }

    @Override
    public void onSubscribe(Subscription subscription) {
        // Request an unlimited number of items (Long.MAX_VALUE ensures this)
        subscription.request(Long.MAX_VALUE);
    }

    @Override
    public void onNext(T item) {
        // Log the received item along with the subscriber's name
        log.info("{} received: {}", this.name, item);
    }

    @Override
    public void onError(Throwable throwable) {
        // Log any errors encountered along with the subscriber's name
        log.error("{} error", this.name, throwable);
    }

    @Override
    public void onComplete() {
        // Log the completion signal along with the subscriber's name
        log.info("{} completed", this.name);
    }
}

