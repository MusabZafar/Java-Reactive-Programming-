package org.reactive.FluxEmittingItemsProgrammatically;

import org.reactive.DefaultSubscriber.Util;
import reactor.core.publisher.Flux;

public class GenerateStateless {

    public static void main(String[] args) {

        Flux.generate(sink -> {
            // Generate a random country
            var country = Util.faker().country().name();
            sink.next(country); // Emit the country name

            // Stop when "Canada" is encountered
            if (country.equalsIgnoreCase("Canada")) {
                sink.complete();
            }
        })
        .take(10) // Take only the first 10 items (instead of using a counter)
        .subscribe(Util.subscriber());
    }
}
