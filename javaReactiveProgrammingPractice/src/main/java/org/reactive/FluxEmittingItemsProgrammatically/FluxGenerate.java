package org.reactive.FluxEmittingItemsProgrammatically;

import org.reactive.DefaultSubscriber.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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


public class FluxGenerate {
    private static final Logger logger = LoggerFactory.getLogger(FluxGenerate.class);

    public static void main(String[] args) {
        /**
         * Flux.generate:
         * - Allows the programmatic generation of a Flux stream where a single item
         *   can be emitted at a time using SynchronousSink.
         * - The lambda function is repeatedly invoked based on downstream demand.
         * - Stops emitting items when:
         *   1. `complete()` is called
         *   2. `error(Throwable)` is called
         *   3. The subscriber cancels the stream
         */

        Flux.generate(synchronousSink -> {
                    logger.info("invoked"); // Log each invocation of the lambda
                    synchronousSink.next("hello"); // Emit a single item: "hello"

                    // Invalid: You cannot emit more than one value in a single invocation.
                    synchronousSink.next(2); // This will throw an error because only one value is allowed.

                    synchronousSink.complete(); // Signal completion to stop the stream
                })
                .take(5) // Request only the first 5 items from the generated Flux
                .subscribe(Util.subscriber("FluxGenerate")); // Subscribe with a custom subscriber
    }
}
