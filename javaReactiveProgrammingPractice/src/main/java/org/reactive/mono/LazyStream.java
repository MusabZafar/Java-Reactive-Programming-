package org.reactive.mono;

import org.slf4j.LoggerFactory;

import java.util.logging.Logger;
import java.util.stream.Stream;

public class LazyStream {
    /*
    If we do not have the terminal operator, then stream operators will not execute
 */

    private static final Logger log = (Logger) LoggerFactory.getLogger(LazyStream.class);

    public static void main(String[] args) {

        Stream.of(1)
                .peek(i -> log.info("received : {}"))
                .toList();


    }
}
