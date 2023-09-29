import nu.studer.gradle.jooq.JooqGenerate
import org.jooq.meta.jaxb.*
import org.jooq.meta.jaxb.Logging

group = "demo.eisenbarth"
version = "0.0.1-SNAPSHOT"
description = "Demo Reactive Spring Boot With Kotlin Coroutines And JOOQ"


object Versions {
	const val KOTLINX = "1.7.3"
	const val JOOQ = "3.18.6"
	const val SPRING ="3.1.4"
	const val R2DBC_POSTGRES = "1.0.2.RELEASE"
}

// Jooq code generation and flyway rely on JDBC
data class JdbcDataSource(
	val driver: String = System.getenv("driver") ?: project.property("driver") as String,
	val jdbcUrl: String = System.getenv("jdbc_url") ?: project.property("jdbc_url") as String,
	val username: String = System.getenv("username") ?: project.property("username") as String,
	val password: String = System.getenv("password") ?: project.property("password") as String
)
val dbConfig = JdbcDataSource()

repositories {
	mavenLocal()
	mavenCentral()
}

plugins {
	id("org.springframework.boot") version "3.1.4"
	id("io.spring.dependency-management") version "1.1.3"
	id("nu.studer.jooq") version "8.2.1"
	id("org.flywaydb.flyway") version "9.22.1"
	kotlin("jvm") version "1.9.10"
	kotlin("plugin.spring") version "1.9.10"
}

dependencies {

	// coroutines
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactive")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-slf4j")

	// marshalling
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("com.fasterxml.jackson.core:jackson-databind")
	implementation("org.jetbrains.kotlin:kotlin-reflect")

	// web
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("org.springframework.boot:spring-boot-starter-validation")

	// database
	implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
	implementation("org.postgresql:r2dbc-postgresql:${Versions.R2DBC_POSTGRES}")
	implementation("org.postgresql:postgresql")

	// flyway
	implementation("org.flywaydb:flyway-core")

	// jooq
	jooqGenerator("org.postgresql:postgresql")
	implementation("org.jooq:jooq:${Versions.JOOQ}}")
	implementation("org.jooq:jooq-kotlin")
	implementation("org.jooq:jooq-kotlin-coroutines")
	// What about: spring-boot-starter-jooq?
	// Don't need it, because we use studer's plugin to configure JOOQ

	// test
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.projectreactor:reactor-test")
}

flyway {
	println("Configuring Flyway")
	driver = dbConfig.driver
	url = dbConfig.jdbcUrl
	user = dbConfig.username
	password = dbConfig.password
	locations = arrayOf("filesystem:./src/main/resources/db/migration")
	placeholders = mapOf("schema_name" to "demo")
}

// Configure jooq plugin. Make sure this comes before configuring tasks, otherwise "generateJooq" will be unknown.
jooq {
	version.set("${Versions.JOOQ}")
	edition.set(nu.studer.gradle.jooq.JooqEdition.OSS)
	configurations {
		create(name = "main") {
			generateSchemaSourceOnCompilation.set(true)
			jooqConfiguration.apply {
				logging = Logging.WARN
				jdbc.apply {
					driver = dbConfig.driver
					url = dbConfig.jdbcUrl
					user = dbConfig.username
					password = dbConfig.password
				}
				generator.apply {
					name = "org.jooq.codegen.KotlinGenerator"
					database.apply {
						name = "org.jooq.meta.postgres.PostgresDatabase"
						inputSchema = "demo"
						includes = ".*"
						isIncludeIndexes = false
						// Excludes match before includes, i.e. excludes have a higher priority.
						excludes = "flyway.*"
						schemaVersionProvider = "SELECT MAX(\"version\") FROM \"flyway_schema_history\""
						// Give enough time to jOOQ to trigger the queries needed for generating sources
						logSlowQueriesAfterSeconds = 20
					}

					generate.apply {
						isDeprecated = false
						isRecords = true
						isPojosAsKotlinDataClasses = true
						isDaos = true
						isValidationAnnotations = true
						isSpringAnnotations = true
					}
					target.apply {
						packageName = "jooq.generated"
						directory = "build/generated-sources"
					}
				}
			}
		}
	}
}

tasks {
	compileKotlin {
		kotlinOptions {
			freeCompilerArgs = listOf("-Xjsr305=strict")
			jvmTarget = "17"
		}
	}
	test {
		useJUnitPlatform()
	}
	// Configure jOOQ code generation task such that it only executes when something that potentially affects code generation has changed.
	named<JooqGenerate>("generateJooq") {

		// Ensure database schema has been prepared by Flyway before generating the jOOQ sources.
		dependsOn("flywayMigrate")

		// Declare Flyway migration scripts as inputs on the jOOQ task.
		inputs.files(fileTree("${rootDir}/src/main/resources/db/migration"))
			.withPropertyName("migrations")
			.withPathSensitivity(PathSensitivity.RELATIVE)

		// Make jOOQ task participate in incremental builds and build caching.
		allInputsDeclared.set(true)
		outputs.cacheIf { true }

	}
}