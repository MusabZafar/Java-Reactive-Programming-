package org.reactive.fileService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileServiceImpl implements FileService {

    public static final Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);
    private static final Path PATH=Path.of("src/main/resources/");
    @Override
    public Mono<String> readFile(String fileName) {
//        Files.readString(PATH.resolve(fileName), StandardCharsets.UTF_8);
        return Mono.fromCallable(()->Files.readString(PATH.resolve(fileName)));
    }

    @Override
    public Mono<Void> writeFile(String fileName, String content) {
        return null;
    }

    @Override
    public Mono<Void> deleteFile(String fileName) {
        return null;
    }
}
