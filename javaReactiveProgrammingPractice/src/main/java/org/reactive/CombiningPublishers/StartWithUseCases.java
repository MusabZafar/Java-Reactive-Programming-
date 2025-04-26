package org.reactive.CombiningPublishers;

import org.reactive.CombiningPublishers.helper.NameGenerator;
import org.reactive.DefaultSubscriber.Util;

public class StartWithUseCases {
    public static void main(String[] args) {
        var nameGenerator = new NameGenerator();

        nameGenerator.generateNames()
                .take(2)
                .subscribe(Util.subscriber("sam"));

        nameGenerator.generateNames()
                .take(2)
                .subscribe(Util.subscriber("musab"));


        nameGenerator.generateNames()
                .take(2)
                .subscribe(Util.subscriber("sulie"));
    }
}
