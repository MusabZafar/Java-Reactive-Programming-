package org.reactive.CombiningPublishers;

import org.reactive.CombiningPublishers.applications.OrderService;
import org.reactive.CombiningPublishers.applications.User;
import org.reactive.CombiningPublishers.applications.UserService;
import org.reactive.DefaultSubscriber.Util;

/**
 * Sequential non-blocking IO calls
 * flatMap is used to flaten the inner publisher / to subscribe to the inner publisher
 */
public class FLuxFLatMap {

    /*
    Get All Orders
     */
    public static void main(String[] args) {
        UserService userService = new UserService();
        userService.getAllUsers()
                .map(User::id)
                .flatMap(OrderService::getUserOrders)
//                .flatMap(OrderService::getUserOrders,2)
                .subscribe(Util.subscriber());
    }

}
