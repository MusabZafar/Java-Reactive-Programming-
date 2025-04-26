package org.reactive.CombiningPublishers;

import org.reactive.DefaultSubscriber.Util;
import reactor.core.publisher.Flux;

import java.time.Duration;

/**
 * We will subscribe to all producer at the same time
 * all or nothing
 * all producers will have emit an item
 */
public class Zip {

    public static void main(String[] args) {

        record Car(String body,String engine, String tires) {}
        Flux.zip(getBody(),getEngine(),getTyre())
//                .map(t->t.get(3))
                .map(t->new Car(t.getT1(),t.getT2(),t.getT3()))
                .subscribe(Util.subscriber());

        try {
            Util.sleepSeconds(5);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    private static Flux<String> getBody(){
        return Flux.range(1,5)
                .map(i->"body-"+i)
                .delayElements(Duration.ofMillis(100));
    }
    private static Flux<String> getEngine(){
        return Flux.range(1,3)
                .map(i->"engine-"+i)
                .delayElements(Duration.ofMillis(200));
    }
    private static Flux<String> getTyre(){
        return Flux.range(1,10)
                .map(i->"body-"+i)
                .delayElements(Duration.ofMillis(79));
    }

}
