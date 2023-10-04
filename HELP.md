# HELP

This page is for helping you out with some things you might trip over, for example if you get an error like
 
> Error occurred while executing flywayMigrate
Unable to obtain connection from database (jdbc:postgresql://localhost:5432/demo) for user 'postgres': Connection to localhost:5432 refused.

the reason might be, that you'll need to start a postgres database first. 

The build relies on this, because it tries to migrate the database with Flyway before it generates JOOQ code from it.
The easiest way would be to use the accompanied docker-compose file as explained below (unless I use Revolut's Docker-JOOQ plugin to make your life easier). 

## Starting Postgres

To run this locally you need to start Postgres. I will assume that you already have the Docker software installed on your machine. If not, you will find instructions to install Docker below.

#### Step 1: Check if Docker is installed

You can verify if Docker is installed by opening a terminal and typing:

> docker --version

If Docker is installed, its version will be displayed in the terminal. If not, follow the instructions in the next section to install Docker.

#### Step 2: Install Docker

If Docker is not installed on your machine, follow these instructions to install it:
For Ubuntu:

> sudo apt-get update
> sudo apt-get install docker-ce
 
For other Linux distributions or operating systems, refer to the official Docker documentation specific to your OS: Docker docs

#### Step 3: Verify that Docker is running

> systemctl status docker

If Docker is not running, start it with the command:

> sudo systemctl start docker

#### Step 4: Running your PostgreSQL Database with Docker

Once Docker is installed and running, navigate to the folder containing the docker-compose.yml.

> cd /path_to_postgres_folder

If you use IntelliJ IDEA you can just open the compose file and click on the green arrows on the left to run it. 

If not, you can open a terminal in the folder and type:

> docker-compose up

This command starts your PostgreSQL database in a Docker container. You should now be able to interact with your database (for example, add it as a Datasource with IDEA and check if migrating the database with Flyway is working).

For additional instructions or support, refer to the official Docker documentation.

---

### Reference Documentation
For further reference, please consider the following sections:

* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.1.4/gradle-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.1.4/gradle-plugin/reference/html/#build-image)
* [Coroutines section of the Spring Framework Documentation](https://docs.spring.io/spring/docs/6.0.12/spring-framework-reference/languages.html#coroutines)
* [Spring Reactive Web](https://docs.spring.io/spring-boot/docs/3.1.4/reference/htmlsingle/index.html#web.reactive)
* [Spring Data R2DBC](https://docs.spring.io/spring-boot/docs/3.1.4/reference/htmlsingle/index.html#data.sql.r2dbc)
* [Flyway Migration](https://docs.spring.io/spring-boot/docs/3.1.4/reference/htmlsingle/index.html#howto.data-initialization.migration-tool.flyway)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a Reactive RESTful Web Service](https://spring.io/guides/gs/reactive-rest-service/)
* [Accessing data with R2DBC](https://spring.io/guides/gs/accessing-data-r2dbc/)

### Additional Links
These additional references should also help you:

* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)
* [R2DBC Homepage](https://r2dbc.io)

