package org.reactive.Operators;

import org.reactive.DefaultSubscriber.Util;
import reactor.core.publisher.Flux;

/*
   Handle behaves like a combination of filter and map operators.

   Behavior Description:
   - For item `1`: Convert it to `-2` and emit.
   - For item `4`: Do not send it downstream (filter out).
   - For item `7`: Emit an error and stop processing.
   - For everything else: Emit the item as it is.
 */

public class HandleOperator {
    public static void main(String[] args) {


        Flux.range(1, 10) // Generate a stream of integers from 1 to 10
                .filter(i -> i != 7) // (Optional) Early filter to exclude '7'
                .handle((item, sink) -> { // Handle operator provides custom logic
                    switch (item) {
                        case 1 -> sink.next(-2); // Transform 1 to -2 and emit
                        case 4 -> { /* Do nothing => filter out */ } // Skip item 4
                        case 7 -> sink.error(new RuntimeException("Error")); // Throw an error for item 7
                        default -> sink.next(item); // Emit the item as it is
                    }
                })
                .cast(Integer.class) // Cast items to Integer type for type safety
                .subscribe(Util.subscriber()); // Subscribe with a custom subscriber

        Flux<String> userInput = Flux.just("Alice", "", "John", "invalid", "Mike");

        userInput
                .handle((name, sink) -> {
                    if (name.isEmpty()) {
                        sink.error(new IllegalArgumentException("Name cannot be empty")); // Emit an error
                    } else if (name.equals("invalid")) {
                        // Skip "invalid" names
                    } else {
                        sink.next(name.toUpperCase()); // Transform valid names to uppercase
                    }
                })
                .subscribe(
                        name -> System.out.println("Processed: " + name),
                        err -> System.err.println("Error: " + err.getMessage()),
                        () -> System.out.println("Processing complete!")
                );



    }


}


