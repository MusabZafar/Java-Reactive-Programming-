package org.reactive.DefaultSubscriber;

import com.github.javafaker.Faker;
import org.reactivestreams.Subscriber;
import reactor.core.publisher.Mono;

import java.time.Duration;

//Utility class for reusable methods
public class Util {

    public static final Faker faker = Faker.instance();

    public static Faker faker() {
        return faker;
    }
    public static void sleepSeconds(int seconds) throws InterruptedException {
        Thread.sleep(Duration.ofSeconds(seconds));
    }

    // Returns a default subscriber with no specific name
    public static <T> Subscriber<T> subscriber() {
        return new DefaultSubscriber<>("");
    }

    // Returns a subscriber with a given name
    public static <T> Subscriber<T> subscriber(String name) {
        return new DefaultSubscriber<>(name);
    }

    public static void main(String[] args) {
        // Create a Mono emitting the string "Hello"
        var mono = Mono.just("Hello");

        // Subscribe to the Mono using named subscribers
        mono.subscribe(subscriber("sub1")); // Logs events for subscriber "sub1"
        mono.subscribe(subscriber("sub2")); // Logs events for subscriber "sub2"
    }
}
