package org.reactive.CombiningPublishers;


import org.reactive.CombiningPublishers.applications.PaymentService;
import org.reactive.CombiningPublishers.applications.UserService;
import org.reactive.DefaultSubscriber.Util;
import reactor.core.publisher.Mono;

/**
 * Sequential non-blocking IO calls
 * flatMap is used to flaten the inner publisher / to subscribe to the inner publisher
 */
public class MonoFlatMap {
    public static void main(String[] args) {
        /**
         * we have username
         * Get user account balance we use flatmap instead of flat
         */

        /**
         * Map is good for in memory operation we have value that is in memory to do task
         */
        UserService userService = new UserService();
        userService.getUserId("musab")
                .map(userID-> "Hello " + userID + "!")
                .subscribe(Util.subscriber());

        UserService userService2 = new UserService();
        userService2.getUserId("musab")
                .map(userID-> PaymentService.getUserBalance(userID))
                .subscribe(Util.subscriber());
        UserService userService3 = new UserService();
        userService2.getUserId("musab")
                .map(userID-> Mono.fromSupplier(()->"Hello " + userID + "!"))
                .subscribe(Util.subscriber());

        UserService userService4 = new UserService();
        userService2.getUserId("musab")
                .flatMap(userID-> Mono.fromSupplier(()->"Hello " + userID + "!"))
                .subscribe(Util.subscriber());
        UserService userService5 = new UserService();
        userService5.getUserId("musab")
                .flatMap(userID-> PaymentService.getUserBalance(userID))
                .subscribe(Util.subscriber());
    }
}
