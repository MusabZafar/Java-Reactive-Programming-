package org.reactive.Operators;

public class SwitchIfEmptyDatabaseExample {
    public static void main(String[] args) {
        String userId = "123"; // Simulated user ID input

        DatabaseService1.fetchFromPostgres(userId) // Try fetching from PostgreSQL
                .switchIfEmpty(DatabaseService1.fetchFromRedis(userId)) // Fallback to Redis if empty
                .subscribe(
                        data -> System.out.println("Received: " + data), // OnNext
                        error -> System.err.println("Error: " + error), // OnError
                        () -> System.out.println("Stream Completed!") // OnComplete
                );

        // Another example: userId exists in PostgreSQL
        userId = "456";
        DatabaseService1.fetchFromPostgres(userId)
                .switchIfEmpty(DatabaseService1.fetchFromRedis(userId)) // Fallback to Redis
                .subscribe(
                        data -> System.out.println("Received: " + data), 
                        error -> System.err.println("Error: " + error), 
                        () -> System.out.println("Stream Completed!")
                );
    }
}
