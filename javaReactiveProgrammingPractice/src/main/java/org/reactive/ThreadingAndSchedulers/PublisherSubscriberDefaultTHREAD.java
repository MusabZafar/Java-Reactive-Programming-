package org.reactive.ThreadingAndSchedulers;

import org.reactive.DefaultSubscriber.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

public class PublisherSubscriberDefaultTHREAD {

    public static final Logger LOGGER = LoggerFactory.getLogger(PublisherSubscriberDefaultTHREAD.class);

    public static void main(String[] args) {
        var flux= Flux.create(sink -> {
            for (int i = 0; i < 10; i++) {
                LOGGER.info("Subscribed to Flux {}", i);
                sink.next(i);
            }
        })
                .doOnNext(i -> LOGGER.info("Subscribed to Flux value : {}", i));

        Runnable runnable=  ()->flux.subscribe(Util.subscriber("subscriber1"));
        Thread.ofPlatform().start(runnable);
    }
}
