package org.reactive.CustomPublisherSubscriberSubscription;

/*
   1. publisher does not produce data unless subscriber requests for it.
   2. publisher will produce only <= subscriber requested items. publisher can also produce 0 items!
   3. subscriber can cancel the subscription. producer should stop at that moment as subscriber is no longer interested in consuming the data
   4. producer can send the error signal
 */

import org.reactive.CustomPublisherSubscriberSubscription.publisher.PublisherImpl;
import org.reactive.CustomPublisherSubscriberSubscription.subscriber.SubscriberImpl;

import java.time.Duration;

public class Demo {

    /*
    The class illustrates the core principles of the Reactive Streams Specification:

    Lazy Data Production: The publisher does not produce data until the subscriber requests it.
    Controlled Data Flow: The subscriber controls how much data the publisher emits using backpressure.
    Subscription Cancellation: The subscriber can stop receiving data by canceling the subscription.
    Error Handling: The publisher can signal an error if the subscriber makes an invalid request.


     */

    public static void main(String[] args) throws InterruptedException {

        demo4();

    }

    private static void demo1(){

        /*
        The SubscriberImpl subscribes to the PublisherImpl.
        The subscription (SubscriptionImpl) is established, but no data is requested yet, so no items are emitted.

        Key Takeaway:

        This demonstrates lazy data production: the publisher does not emit any data unless explicitly requested by the subscriber.
         */

        var publisher = new PublisherImpl();
        var subscriber = new SubscriberImpl();
        publisher.subscribe(subscriber);
    }

    private static void demo2() throws InterruptedException {
        /*
                The subscriber requests data in batches of 3 items with some delays in between.
        The publisher emits the requested items one batch at a time.
        The subscriber processes the emitted items until the maximum (MAX_ITEMS) limit is reached.

    Key Takeaway:
        This demonstrates controlled data flow using the backpressure mechanism. The subscriber decides how much data it can handle and requests it in manageable chunks.


         */



        var publisher = new PublisherImpl();
        var subscriber = new SubscriberImpl();
        publisher.subscribe(subscriber);
        subscriber.getSubscription().request(3);
        Thread.sleep(Duration.ofSeconds(2));
        subscriber.getSubscription().request(3);
        Thread.sleep(Duration.ofSeconds(2));
        subscriber.getSubscription().request(3);
        Thread.sleep(Duration.ofSeconds(2));
        subscriber.getSubscription().request(3);
        Thread.sleep(Duration.ofSeconds(2));
        subscriber.getSubscription().request(3);
    }

    private static void demo3() throws InterruptedException {
        /*
                The subscriber requests 3 items, which the publisher emits.
        The subscription is canceled after the first request.
        Any subsequent request() calls are ignored by the publisher because the subscription is no longer active.

    Key Takeaway:
        This demonstrates subscription cancellation: the subscriber can terminate the stream at any time, and the publisher must respect this decision.


         */
        var publisher = new PublisherImpl();
        var subscriber = new SubscriberImpl();
        publisher.subscribe(subscriber);
        subscriber.getSubscription().request(3);
        Thread.sleep(Duration.ofSeconds(2));
        subscriber.getSubscription().cancel();
        subscriber.getSubscription().request(3);
        Thread.sleep(Duration.ofSeconds(2));
    }

    private static void demo4() throws InterruptedException {
        /*
            The subscriber requests 3 items, which are emitted successfully.
    The subscriber then requests 11 items, exceeding the MAX_ITEMS limit.
    The publisher sends an error signal (onError()), and the subscription is canceled.
    Any subsequent request() calls are ignored.

Key Takeaway:

    This demonstrates error handling: the publisher validates the requests and ensures that invalid requests do not cause unexpected behavior.
         */
        var publisher = new PublisherImpl();
        var subscriber = new SubscriberImpl();
        publisher.subscribe(subscriber);
        subscriber.getSubscription().request(3);
        Thread.sleep(Duration.ofSeconds(2));
        subscriber.getSubscription().request(11);
        Thread.sleep(Duration.ofSeconds(2));
        subscriber.getSubscription().request(3);
        Thread.sleep(Duration.ofSeconds(2));
    }

}