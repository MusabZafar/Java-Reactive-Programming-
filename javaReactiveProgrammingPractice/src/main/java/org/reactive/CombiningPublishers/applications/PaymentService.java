package org.reactive.CombiningPublishers.applications;

import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * just for demo
 * Imagine Payment-service, as an application, has an endpoint.
 * THis is a client class which represents to call the endpoint (ID Request)
 */
public class PaymentService {

    public static final Map<Integer,Integer> userBalanceTable=Map.of(
            1,100,
            2,200,
            3,300
    );

    public static Mono<Integer> getUserBalance(Integer userId) {
        return Mono.fromSupplier(() -> userBalanceTable.get(userId));
    }
}
