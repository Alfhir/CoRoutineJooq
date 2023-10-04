# CoRoutineJooq

## Motivation

I'm a big fan of [jOOQ](https://www.jooq.org/) and  [Kotlin](https://kotlinlang.org/) and a curious person, so I had to try this combination of excellent technologies together.

If you plan on using Reactive programming in a real project, using Kotlin will make your life a lot less miserable. 

Spring has great modules for (reactive) database access, but using JOOQ to access relational databases is not as represented as it should be imho.

## Built With

* [jOOQ](https://www.jooq.org/)
* [Spring Data R2DBC](https://spring.io/projects/spring-data-r2dbc)
* [Spring WebFlux](https://spring.io/projects/spring-webflux)
* [Kotlin](https://kotlinlang.org)
* [Flyway](https://flywaydb.org/)
* [MockK](https://mockk.io/)

## Roadmap

I am not going to take this far, so unfortunately there is none.

## How to Install

This is a Kotlin Gradle project, so you need to have the following tools installed:

* JDK 17
* Gradle 6.x or later

To install these tools:

### JDK 17 & Gradle

If you have no JVM installed, [SDKMAN](https://sdkman.io/) is a great tool to use and manage different JVMs.
You can also use it to install Gradle:

> $ sdk install gradle

### Project Setup without IntelliJ IDEA
You don't necessarily need IntelliJ IDEA to set up the project. You can use any editor you prefer (like Visual Studio Code, Atom, etc.). However, IntelliJ IDEA provides powerful support for Kotlin and I would highly recommend using it if possible. You can work with the project from the terminal. To build the project run:

> ./gradlew build

To run the project, use:

> ./gradlew run

Remember to replace ./gradlew with gradlew.bat in the above commands if you're using Windows.

## How to use

Firstly, if you haven't done so already, clone the project:

> git clone <repository-url>

Then change into the directory:

> cd <project-name>

As this is a Spring Boot project, you would run your application using gradle:

> ./gradlew bootRun

Ensure you have the database installed and running, see [Help File](HELP.md).

## Contributing

I don't expect anyone to read this at all, but if you have any suggestions, or ideas feel free to open an issue or open a PR immediately. I'm happy to receive any suggestions.