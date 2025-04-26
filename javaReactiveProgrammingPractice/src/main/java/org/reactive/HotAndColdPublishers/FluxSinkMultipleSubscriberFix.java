package org.reactive.HotAndColdPublishers;
import org.reactive.DefaultSubscriber.Util;
import org.reactive.FluxEmittingItemsProgrammatically.FluxCreateForMoreComplexLogic.NameGenerator;
import reactor.core.publisher.Flux;

public class FluxSinkMultipleSubscriberFix {

    public static void main(String[] args) {
        // Create a NameGenerator instance that programmatically emits items
        var generator = new NameGenerator();

        /*
         * Flux.create(generator): Creates a Flux using the NameGenerator class.
         * share(): Converts the Flux into a Hot Publisher, allowing multiple subscribers
         * to share the same data source without creating independent streams for each subscriber.
         */
        var flux = Flux.create(generator).share();

        // First subscriber subscribes to the shared Flux
        flux.subscribe(Util.subscriber("subscriber1"));

        // Second subscriber subscribes to the same shared Flux
        flux.subscribe(Util.subscriber("subscriber2"));

        /*
         * Emit 10 names programmatically through the NameGenerator instance.
         * Both subscribers will receive the same emitted items because the Flux is shared.
         */
        for (int i = 0; i < 10; i++) {
            generator.generate(); // Generate and emit a new item
        }
    }
}
