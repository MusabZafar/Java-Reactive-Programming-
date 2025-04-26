package org.reactive.fileService;

import reactor.core.publisher.Mono;

import java.io.IOException;

public interface FileService {
    public Mono<String> readFile(String fileName) throws IOException;
    public Mono<Void> writeFile(String fileName, String content);
    public Mono<Void> deleteFile(String fileName);
}
