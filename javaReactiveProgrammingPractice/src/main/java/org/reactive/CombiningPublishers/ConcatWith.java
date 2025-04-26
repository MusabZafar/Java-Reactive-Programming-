package org.reactive.CombiningPublishers;

import org.reactive.DefaultSubscriber.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.time.Duration;


public class ConcatWith {


    public static final Logger logger = LoggerFactory.getLogger(ConcatWith.class);

    public static void main(String[] args) throws InterruptedException {

        demo();

        Util.sleepSeconds(3);

    }

    private static void demo1(){
        producer1()
                .concatWith(producer2())
                .startWith(1000)
                .subscribe(Util.subscriber());
    }
    private static void demo2(){
        Flux.concat(producer1(), producer2())
                .subscribe(Util.subscriber());
    }

    private static void demo(){
        producer1()
                .concatWithValues(-1,0)
                .take(2)
                .subscribe(Util.subscriber());
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
}
