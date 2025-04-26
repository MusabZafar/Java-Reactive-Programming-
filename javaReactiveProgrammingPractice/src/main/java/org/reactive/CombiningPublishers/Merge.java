package org.reactive.CombiningPublishers;

import org.reactive.DefaultSubscriber.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.time.Duration;

/**
 * Subscriber subscribes to all producers at the same time
 */
public class Merge {
    public static final Logger logger = LoggerFactory.getLogger(Merge.class);

    public static void main(String[] args) {
        Flux.merge(producer1(), producer2(), producer3())
                .subscribe(Util.subscriber());

        try {
            Util.sleepSeconds(3);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static Flux<Integer> producer1(){
        return Flux.just(1, 2, 3)
                .doOnSubscribe(s -> logger.info("Subscribing to producer 1"))
                .delayElements(Duration.ofMillis(10));
    }
    private static Flux<Integer> producer2(){
        return Flux.just(51, 52, 53)
                .doOnSubscribe(s -> logger.info("Subscribing to producer 2"))
                .delayElements(Duration.ofMillis(10));
    }

    private static Flux<Integer> producer3(){
        return Flux.just(11, 12, 13)
                .doOnSubscribe(s -> logger.info("Subscribing to producer 3"))
                .delayElements(Duration.ofMillis(10));
    }
}
