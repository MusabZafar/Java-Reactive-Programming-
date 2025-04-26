package org.reactive.flux;

import org.reactive.DefaultSubscriber.Util;
import org.reactive.flux.GenerateNames.NameGenerator;

public class FluxVsList {
    public static void main(String[] args) {

        // Generate names using the traditional List approach
        var list = NameGenerator.getNamesList(10); // Generate 10 names
        System.out.println(list); // Print all names at once after generation is complete

        // Generate names using Flux
        NameGenerator.getNamesListFlux(10)
                .subscribe(Util.subscriber()); // Emit names reactively to the subscriber
    }
}
