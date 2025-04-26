package org.reactive.Operators;

import org.reactive.DefaultSubscriber.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.util.function.Function;
import java.util.function.UnaryOperator;

import javax.swing.plaf.ColorUIResource;

/**
 * This class demonstrates the use of the `transform` operator in Project Reactor.
 * It conditionally adds debugging behavior to reactive streams for better observability.
 */

public class TransformOperator {
    // Logger for logging the lifecycle of the stream
    private static final Logger log = LoggerFactory.getLogger(TransformOperator.class);

    // Record to represent Customer entity
    record Customer(int id, String name) {}

    // Record to represent PurchaseOrder entity
    record PurchaseOrder(String productName, int price, int quantity) {}

    public static void main(String[] args) {

        // Flag to determine if debugging should be enabled or not
        var isDebugEnabled = false;

        /*
         * getCustomers(): Generates a stream of customers.
         * transform(): Conditionally adds debugging logic if isDebugEnabled is true.
         * Function.identity(): If debugging is disabled, returns the same stream as it is (no modifications).
         *
         * Function.identity() is a method that returns the input as it is without modifying it.
           In simple terms, it acts as a pass-through function.
         */
        getCustomers()
                .transform(isDebugEnabled ? addDebugger() : Function.identity()) // Conditionally apply debugging logic
                .subscribe(); // Trigger the pipeline by subscribing

        /*
         * getPurchaseOrders(): Generates a stream of purchase orders.
         * transform(): Always applies the debugging logic (addDebugger()) to the stream.
         */
        getPurchaseOrders()
                .transform(addDebugger()) // Apply debugging logic
                .subscribe(); // Trigger the pipeline by subscribing
    }

    /**
     * Simulates a stream of customers.
     * @return Flux of Customer objects
     */
    private static Flux<Customer> getCustomers() {
        return Flux.range(1, 3) // Emits integers from 1 to 3
                .map(i -> new Customer(i, Util.faker().name().firstName())); // Maps each number to a Customer object
    }

    /**
     * Simulates a stream of purchase orders.
     * @return Flux of PurchaseOrder objects
     */
    private static Flux<PurchaseOrder> getPurchaseOrders() {
        return Flux.range(1, 5) // Emits integers from 1 to 5
                .map(i -> new PurchaseOrder(
                        Util.faker().commerce().productName(), // Generates random product name
                        i,  // Price is set to 'i'
                        i * 10)); // Quantity is 'i * 10'
    }

    /**
     * Adds reusable debugging behavior (logs events such as onNext, onComplete, and onError).
     * @param <T> Type of data flowing through the Flux
     * @return UnaryOperator that modifies the Flux by adding debugging behavior
     */
    private static <T> UnaryOperator<Flux<T>> addDebugger() {
        return flux -> flux
                .doOnNext(i -> log.info("received: {}", i))      // Logs every emitted item
                .doOnComplete(() -> log.info("completed"))       // Logs when the stream completes
                .doOnError(err -> log.error("error", err));      // Logs if an error occurs
    }

}
