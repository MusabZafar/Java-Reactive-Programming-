# Java Reactive Programming

This repository demonstrates the implementation of Reactive Programming concepts in Java. It covers the core principles of Reactive Programming, including handling asynchronous data streams, managing backpressure, and building reactive applications using frameworks like Spring WebFlux and Project Reactor.

## Table of Contents
- [Introduction](#introduction)
- [Technologies Used](#technologies-used)
- [Getting Started](#getting-started)
- [Usage](#usage)
- [Project Structure](#project-structure)
- [Examples](#examples)
- [Contributing](#contributing)
- [License](#license)

## Introduction

Reactive Programming is a programming paradigm that revolves around data streams and the propagation of changes. In this repository, you’ll find examples of building non-blocking, asynchronous systems in Java using popular libraries like **Spring WebFlux** and **Project Reactor**.

This repository is an excellent resource for learning how to apply the reactive paradigm to various types of Java applications, such as REST APIs and microservices.

## Technologies Used

- **Java 11 or later**
- **Spring WebFlux**: A part of the Spring Framework that provides support for building non-blocking, event-driven applications.
- **Project Reactor**: A reactive library used for composing asynchronous and event-driven applications using a fluent API.
- **Maven/Gradle**: Build tools to manage dependencies and build the project.
- **Reactive Streams**: For handling asynchronous streams of data with backpressure.

## Getting Started

To get started with this repository, follow the steps below:

### 1. Clone the Repository

```bash
git clone https://github.com/MusabZafar/Java-Reactive-Programming-.git
cd Java-Reactive-Programming-
```

##Usage
The project contains various examples of reactive programming concepts such as:
Flux and Mono: The building blocks of reactive programming in Project Reactor, with Flux representing a stream of multiple elements and Mono representing a stream of a single element.
Backpressure Handling: Managing the flow of data when the consumer is slower than the producer.
Error Handling: Implementing strategies for handling errors in reactive streams.
Spring WebFlux: Building non-blocking REST APIs with Spring WebFlux.

###Example: Basic Reactive Stream

import reactor.core.publisher.Mono;
public class ReactiveExample {
    public static void main(String[] args) {
        Mono.just("Hello, Reactive Programming!")
            .subscribe(System.out::println);
    }
}
In this example, we create a Mono that emits a single string and subscribe to it, printing the message to the console.

###Project Structure
```bash
Java-Reactive-Programming/
│
├── src/                           # Source code for the project
│   ├── main/
│   │   ├── java/                  # Java files
│   │   │   └── com/
│   │   │       └── example/
│   │   │           ├── Application.java # Main entry point
│   │   │           └── ReactiveExamples.java # Example reactive code
│   │   └── resources/
│   │       └── application.properties  # Spring Boot application config
│   └── test/                      # Unit tests
│       └── java/                  
│           └── com/
│               └── example/
│                   └── ReactiveExamplesTest.java # Test cases for reactive code
└── docs/                          # Documentation files
    └── ReactiveProgramming.odt    # Reactive programming overview (optional) its an odt format in ubantu you can convert it to pdf
```

Examples
Basic Reactive Streams: An example demonstrating the Flux and Mono types.
Error Handling: A demonstration of how to handle errors within a reactive stream.
WebFlux Example: A non-blocking web application that processes HTTP requests asynchronously.
Refer to the src folder for more detailed examples.

Contributing
Contributions are welcome! If you have ideas for new examples or improvements to the project, feel free to fork the repository and submit a pull request.
Steps for Contributing:[
Fork the repository.
Create a new branch (git checkout -b feature-name).
Make your changes and add tests if necessary.
Commit your changes (git commit -am 'Add new feature').
Push to your branch (git push origin feature-name).
Create a new Pull Request.

