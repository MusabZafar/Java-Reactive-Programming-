package org.reactive.CombiningPublishers.applications;

import org.reactive.DefaultSubscriber.Util;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;
import java.util.Map;

/**
 * just for demo
 * Imagine order-service, as an application, has an endpoint.
 * THis is a client class which represents to call the endpoint (ID Request)
 */
public class OrderService {

    public static final Map<Integer, List<Order>> orderTable=Map.of(
            1, List.of(
                    new Order(1, Util.faker().commerce().productName(),
                            Util.faker().random().nextInt(10,100)),
                    new Order(1, Util.faker().commerce().productName(),
                            Util.faker().random().nextInt(10,100))
            ),
            2, List.of(
                    new Order(1, Util.faker().commerce().productName(),
                            Util.faker().random().nextInt(10,100)),
                    new Order(1, Util.faker().commerce().productName(),
                            Util.faker().random().nextInt(10,100)),
                    new Order(1, Util.faker().commerce().productName(),
                            Util.faker().random().nextInt(10,100))

            ),
            3, List.of()
    );
    public static Flux<Order> getUserOrders(Integer userId) {
        return Flux.fromIterable(orderTable.get(userId))
                .delayElements(Duration.ofMillis(500));
    }
}
