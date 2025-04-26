package org.reactive.Operators;

import reactor.core.publisher.Flux;

import java.util.function.Function;

public class TransformOperatorExample {
//
//    public static void main(String[] args) {
//        Flux<Integer> pipeline1 = Flux.range(1, 10)
//                .transform(applyCommonSteps()) // Apply reusable logic
//                .subscribe(Util.subscriber("Pipeline1"));
//
//        Flux<Integer> pipeline2 = Flux.range(10, 5)
//                .transform(applyCommonSteps()) // Reuse the same logic
//                .subscribe(Util.subscriber("Pipeline2"));
//    }

    // Reusable function with common logic
    private static Function<Flux<Integer>, Flux<Integer>> applyCommonSteps() {
        return flux -> flux
                .filter(i -> i % 2 == 0) // Step 1: Filter even numbers
                .map(i -> i * 10)        // Step 2: Multiply by 10
                .doOnNext(i -> System.out.println("Processed: " + i)); // Step 3: Log processed data
    }
}
