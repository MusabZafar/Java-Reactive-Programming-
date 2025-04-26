package org.reactive.CombiningPublishers;

import org.reactive.CombiningPublishers.applications.PaymentService;
import org.reactive.CombiningPublishers.applications.UserService;
import org.reactive.DefaultSubscriber.Util;

/**
 * Sequential non-blocking IO calls
 * flatMap is used to flaten the inner publisher / to subscribe to the inner publisher
 * MOno is supposed to be 1 item -what if the flatMap returns Multiple items
 */
public class FLatMapMany {


    public static void main(String[] args) {
        /**
         * We have username
         * get all user order
         */
        //Mono<Flux<Order>> mono=
        UserService userService = new UserService();
        userService.getUserId("musab")
                .flatMapMany(userID-> PaymentService.getUserBalance(userID))
                .subscribe(Util.subscriber());


        try {
            Util.sleepSeconds(3);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
