package org.reactive.CombiningPublishers.applications;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

public class UserService {

    public static final Map<String,Integer> userTable=Map.of(
      "musab",1,
      "haris",2,
      "sulie",3
    );
    public Flux<User> getAllUsers() {
        return Flux.fromIterable(userTable.entrySet())
                .map(entry->new User(entry.getValue(),entry.getKey()));

    }

    public Mono<Integer> getUserId(String userName) {
        return Mono.fromSupplier(() -> userTable.get(userName));
    }
}
