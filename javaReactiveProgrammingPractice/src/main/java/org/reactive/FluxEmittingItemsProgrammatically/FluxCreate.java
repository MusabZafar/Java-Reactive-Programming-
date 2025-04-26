package org.reactive.FluxEmittingItemsProgrammatically;

import org.reactive.DefaultSubscriber.Util;
import reactor.core.publisher.Flux;

/**
 The synchronous sync when you are given this synchronous thing using that you can
 emit maximum one value. You are not supposed to emit two values.
 If its emmiting one value it does not mean its mono.
 In the create option we are getting one sync object flexing object. Once we get the flexing object we have we as a
 developer we have the complete control. So we can create a for loop and we can do whatever we want to do with the
 flexing object. We can share it with multiple threads. And do whatever we want to do.we can keep on emitting items without
 worrying about the downstream demand. Downstream means subscriber. SO whether the subscriber requests are not , we
 don't care.we are keeping emitting items. All those things we have complete control with the create flex sync.


 The generate its completely opposite to that.I.e.. we will be given the synchronous sync object using which you are, me and you.
 As a developer, we are allowed to emit only one value. Now if we ask what if we want to emit one more value so that
 time the generate it will gove us one more sync. So one sync is using which we can emit one item. SO it will be invoked again
 and again based on the downstream demand. SO each and every time you will be emitting just one item.
 */
/**
 Flux generate
 - invokes the given lambda expression again and again based on downstream demand.
 - We can emit only one value at a time
 - will stop when complete method is invoked
 - will stop when error method is invoked
 - will stop downstream cancels
 */



/*
        To create a flux & emit items programmatically
 */
public class FluxCreate {
    public static void main(String[] args) {
        // Create a Flux using the create() method
        Flux.create(
                //            fluxSink.next("Hello");
//            fluxSink.next("World");

//            for (int i = 0; i < 10; i++) {
//                fluxSink.next(Util.faker().country().name());
//            }

                /*
                The `create` method allows us to programmatically emit items using a FluxSink.
                A FluxSink is a bridge between imperative code and the reactive world.

                Example Requirement:
                - Emit random country names continuously.
                - Stop emitting when the country name "Pakistan" appears.
                - Emit a complete signal after emitting "Pakistan".
                */
                        fluxSink -> {
                            String country; // Variable to hold the emitted country name
                            do {
                                country = Util.faker().country().name(); // Generate a random country name
                                fluxSink.next(country); // Emit the country name
                            } while (!country.equalsIgnoreCase("Pakistan")); // Stop when "Pakistan" is generated
                            fluxSink.complete(); // Emit the complete signal
                        }
                )
                // Subscribe to the Flux to process the emitted country names
                .subscribe(Util.subscriber());
    }
}
