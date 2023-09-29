package demo.eisenbarth.CoRoutineJooq

import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.jooq.impl.DSL
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.datasource.DriverManagerDataSource

@SpringBootApplication
class CoRoutineJooqApplication {}

fun main(args: Array<String>) {
    runApplication<CoRoutineJooqApplication>(*args)
}
