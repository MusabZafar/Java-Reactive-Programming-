package org.reactive.CombiningPublishers.helper;

import org.reactive.DefaultSubscriber.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

public class NameGenerator {

    public static final Logger logger = LoggerFactory.getLogger(NameGenerator.class);
    private final List<String> redis=new ArrayList<>();
    public Flux<String> generateNames() {
        return Flux.generate(sink->{
            logger.info("Generating names");
            try {
                Util.sleepSeconds(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            var name=Util.faker().name().fullName();
            redis.add(name);
            sink.next(name);
        })
                .startWith(redis)
                .cast(String.class);
    }
}
