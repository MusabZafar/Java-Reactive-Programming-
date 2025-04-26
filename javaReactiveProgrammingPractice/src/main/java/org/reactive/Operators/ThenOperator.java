package org.reactive.Operators;

import org.reactive.DefaultSubscriber.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Vector;

/**
 *  "then" could be hel[pful when we are not intrested in the result of a publisher/
 *  we need to have sequential execution of async tasks
 */
public class ThenOperator {
    public static final Logger logger = LoggerFactory.getLogger(ThenOperator.class);

    public static void main(String[] args) {
//            saveRecords(List.of("a", "b", "c", "d", "e", "f", "g", "h"))
//                    .then()
//                    .subscribe(Util.subscriber());

        var records=List.of("a", "b", "c", "d", "e", "f", "g", "h");
        saveRecords(records)
                .then(sendNotification(records))
                .subscribe(Util.subscriber());
    }

    private static Flux<String> saveRecords(List<String> records) {
        return Flux.fromIterable(records)
                .map(r->"saved"+r)
                .delayElements(Duration.ofMillis(50));
    }

    private static Mono<Void> sendNotification(List<String> records) {
        return Mono.fromRunnable(() -> logger.info("Sending {} records", records.size()));
    }
}
