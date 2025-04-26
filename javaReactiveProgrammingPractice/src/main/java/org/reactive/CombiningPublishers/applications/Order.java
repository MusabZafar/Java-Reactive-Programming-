package org.reactive.CombiningPublishers.applications;

public record Order(Integer userId,
                    String productName,
                    Integer price) {
}
